package de.felix_klauke.pegasusmessenger.action;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.felix_klauke.pegasusmessenger.MainActivity;
import de.felix_klauke.pegasusmessenger.action.chat.ChatAdapter;

/**
 * Created by Klauke on 14.02.2016.
 */
public class ViewManager {

    private MainActivity activity;
    private ChatAdapter chatAdapter;
    private TextView editText;

    public ViewManager(MainActivity activity, ChatAdapter chatAdapter, TextView editText) {
        this.activity = activity;
        this.chatAdapter = chatAdapter;
        this.editText = editText;
    }

    public ChatAdapter getChatAdapter() {
        return chatAdapter;
    }

    public TextView getEditText() {
        return editText;
    }

    public void postMessage(String message) {
        chatAdapter.append(message);
    }

    public void postMessage(final String username, final String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                postMessage("[" + getDate() + "] " + username + ": " + message);
            }
        });
    }

    public String getDate() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    public void showNotification(final String title, final String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(activity)
                        .setTitle(title)
                        .setMessage(message)
                        .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }
}
