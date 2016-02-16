package de.felix_klauke.pegasusmessenger.network.listener;

import de.felix_klauke.pegasus.client.handler.PacketListener;
import de.felix_klauke.pegasus.protocol.packets.PacketMessage;
import de.felix_klauke.pegasusmessenger.action.ViewManager;
import io.netty.channel.Channel;

/**
 * Created by Klauke on 14.02.2016.
 */
public class PacketMessageListener implements PacketListener<PacketMessage> {

    private ViewManager viewManager;

    public PacketMessageListener(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public void handlePacket(Channel channel, PacketMessage packetMessage) {
        viewManager.postMessage(packetMessage.getAuthor(), packetMessage.getMessage());
    }

}
