package net.wavemc.core.bukkit.listener;

import lombok.AllArgsConstructor;
import net.wavemc.core.bukkit.account.WavePlayer;
import net.wavemc.core.bukkit.WaveBukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class PlayerQuitListener implements Listener {

    private final WaveBukkit plugin;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        WavePlayer helixPlayer = plugin.getPlayerManager().getPlayer(player.getName());
        if (helixPlayer.getPvp().getUuid() != event.getPlayer().getUniqueId().toString()) {
            helixPlayer.getPvp().setUuid(event.getPlayer().getUniqueId().toString());
        }
        plugin.getPlayerManager().getController().save(helixPlayer);
    }
}
