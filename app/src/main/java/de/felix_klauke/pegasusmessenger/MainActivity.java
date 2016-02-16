package de.felix_klauke.pegasusmessenger;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import de.felix_klauke.pegasus.protocol.packets.PacketHandshake;
import de.felix_klauke.pegasus.protocol.packets.PacketHandshakeResponse;
import de.felix_klauke.pegasus.protocol.packets.PacketMessage;
import de.felix_klauke.pegasusmessenger.action.SendButtonClickListener;
import de.felix_klauke.pegasusmessenger.action.ViewManager;
import de.felix_klauke.pegasusmessenger.action.chat.ChatAdapter;
import de.felix_klauke.pegasusmessenger.network.NetworkManager;
import de.felix_klauke.pegasusmessenger.network.listener.PacketHandshakeResponseListener;
import de.felix_klauke.pegasusmessenger.network.listener.PacketMessageListener;

public class MainActivity extends AppCompatActivity {

    private static String attemptedName = "";
    private static boolean loggedIn = false;

    private NetworkManager networkManager;
    private ViewManager viewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        networkManager = new NetworkManager();

        ListView listView = (ListView) findViewById(R.id.chatView);
        ChatAdapter chatAdapter = new ChatAdapter(this, R.layout.support_simple_spinner_dropdown_item);
        listView.setAdapter(chatAdapter);

        viewManager = new ViewManager(this, chatAdapter, (TextView) findViewById(R.id.sendMessage));

        Button button = (Button) findViewById(R.id.sendButton);
        button.setOnClickListener(new SendButtonClickListener(networkManager, viewManager));

        /*new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                networkManager.connect();
                return null;
            }
        }.execute();*/
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                networkManager.connect();
            }
        });

        networkManager.registerListener(new PacketMessageListener(viewManager), PacketMessage.class);
        networkManager.registerListener(new PacketHandshakeResponseListener(viewManager), PacketHandshakeResponse.class);

        viewManager.showNotification("", String.valueOf(networkManager.getNettyClient().getPacketHandler().getListeners().size()));
    }

    @Override
    protected void onStop() {
        super.onStop();
        networkManager.getNettyClient().disconnect();
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static String getAttemptedName() {
        return attemptedName;
    }

    public static void setAttemptedName(String attemptedName) {
        MainActivity.attemptedName = attemptedName;
    }

    public static void setLoggedIn(boolean loggedIn) {
        MainActivity.loggedIn = loggedIn;
    }

}
