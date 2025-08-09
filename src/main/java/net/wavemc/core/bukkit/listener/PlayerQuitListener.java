package net.wavemc.core.bukkit.listener;

import lombok.AllArgsConstructor;
import net.wavemc.core.bukkit.account.WavePlayer;
import net.wavemc.core.bukkit.WaveBukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
public class PlayerQuitListener implements Listener {


    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        WavePlayer helixPlayer = WaveBukkit.getInstance().getPlayerManager().getPlayer(player.getName());
        if (!Objects.equals(helixPlayer.getPvp().getUuid(), event.getPlayer().getUniqueId().toString())) {
            helixPlayer.getPvp().setUuid(UUID.fromString(event.getPlayer().getUniqueId().toString()));
        }
        WaveBukkit.getPlayerController().save(helixPlayer);
    }
}
