package de.felix_klauke.pegasusmessenger.action;

import android.view.View;

import com.google.common.base.Strings;

import de.felix_klauke.pegasus.protocol.packets.PacketHandshake;
import de.felix_klauke.pegasus.protocol.packets.PacketMessage;
import de.felix_klauke.pegasusmessenger.MainActivity;
import de.felix_klauke.pegasusmessenger.network.NetworkManager;

/**
 * Created by Klauke on 14.02.2016.
 */
public class SendButtonClickListener implements View.OnClickListener {

    private NetworkManager networkManager;
    private ViewManager viewManager;

    public SendButtonClickListener(NetworkManager networkManager, ViewManager viewManager) {
        this.networkManager = networkManager;
        this.viewManager = viewManager;
    }

    @Override
    public void onClick(View v) {
        if(Strings.isNullOrEmpty(viewManager.getEditText().getText().toString())){
            viewManager.showNotification("Warnung", "Du musst einen text eingeben.");
        } else {
            String text = viewManager.getEditText().getText().toString();
            if(text.startsWith("/")) {
                String[] args = text.split(" ");
                if(text.startsWith("/login") && args.length == 3) {
                    PacketHandshake packet = new PacketHandshake(args[1], args[2], "1");
                    networkManager.sendPacket(packet);
                    MainActivity.setAttemptedName(args[1]);
                }
            } else {
                if(!MainActivity.isLoggedIn()){
                    viewManager.showNotification("Warnung", "Du bist nicht eingeloggt. Nutze /login <name> <passwort>");
                    return;
                }
                viewManager.postMessage(MainActivity.getAttemptedName(), text);
                PacketMessage packet = new PacketMessage(text);
                networkManager.sendPacket(packet);
            }
        }
    }

}
