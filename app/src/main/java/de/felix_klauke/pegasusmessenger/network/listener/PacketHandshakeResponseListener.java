package de.felix_klauke.pegasusmessenger.network.listener;

import de.felix_klauke.pegasus.client.handler.PacketListener;
import de.felix_klauke.pegasus.protocol.packets.PacketHandshakeResponse;
import de.felix_klauke.pegasusmessenger.MainActivity;
import de.felix_klauke.pegasusmessenger.action.ViewManager;
import io.netty.channel.Channel;

/**
 * Created by Klauke on 14.02.2016.
 */
public class PacketHandshakeResponseListener implements PacketListener<PacketHandshakeResponse> {

    private ViewManager viewManager;

    public PacketHandshakeResponseListener(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public void handlePacket(Channel channel, PacketHandshakeResponse packetHandshakeResponse) {
        MainActivity.setLoggedIn(true);
        viewManager.showNotification("Eingeloggt", "Du wurdest als " + MainActivity.getAttemptedName() + " eingeloggt.");
    }

}
