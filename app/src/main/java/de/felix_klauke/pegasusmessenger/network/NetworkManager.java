package de.felix_klauke.pegasusmessenger.network;

import android.os.AsyncTask;
import android.os.Looper;

import de.felix_klauke.pegasus.client.Client;
import de.felix_klauke.pegasus.client.handler.PacketListener;
import de.felix_klauke.pegasus.client.network.NettyClient;
import de.felix_klauke.pegasus.protocol.Packet;
import de.felix_klauke.pegasusmessenger.action.ViewManager;

/**
 * Created by Klauke on 14.02.2016.
 */
public class NetworkManager extends AsyncTask<String, Void, Void> {

    private Client client;
    private NettyClient nettyClient;

    public NetworkManager() {
        client = new Client();
        nettyClient = client.getNettyClient();
    }

    @Override
    protected Void doInBackground(String... params) {
        connect();
        return null;
    }

    public void connect() {
        client.start();
    }

    public Client getClient() {
        return client;
    }

    public NettyClient getNettyClient() {
        return nettyClient;
    }

    public void sendPacket(Packet packet) {
        nettyClient.send(packet);
    }

    public void registerListener(PacketListener listener, Class<? extends Packet> clazz) {
        nettyClient.getPacketHandler().registerListener(listener, clazz);
    }

}
