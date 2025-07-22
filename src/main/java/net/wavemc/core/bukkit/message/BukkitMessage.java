package net.wavemc.core.bukkit.message;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.wavemc.core.bukkit.server.WaveServer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BukkitMessage implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("HLX:CRE")) {
            return;
        }
        ByteArrayDataInput input = ByteStreams.newDataInput(message);
        String subChannel = input.readUTF();

        switch (subChannel) {
            case "HelixPlayerCount":
                String serverName = input.readUTF();
                int playerCount = Integer.parseInt(input.readUTF());
                WaveServer.findServer(serverName).ifPresent(
                        server -> server.setPlayerCount(playerCount));
                break;
        }
    }
}
