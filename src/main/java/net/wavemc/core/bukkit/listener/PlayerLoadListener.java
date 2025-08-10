package net.wavemc.core.bukkit.listener;

import lombok.AllArgsConstructor;
import net.wavemc.core.Wave;
import net.wavemc.core.bukkit.account.WavePlayer;
import net.wavemc.core.bukkit.WaveBukkit;

import net.wavemc.core.storage.StorageConnection;

import net.wavemc.core.storage.provider.Yaml2;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
public class PlayerLoadListener implements Listener {




    @EventHandler(priority = EventPriority.LOW)
    public void onJoitrn(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        WavePlayer wavePlayer = WaveBukkit.getInstance().getPlayerManager().getPlayer(player.getName());
        if (!Objects.equals(wavePlayer.getPvp().getUuid(), event.getPlayer().getUniqueId())) {
            wavePlayer.getPvp().setUuid(UUID.fromString(event.getPlayer().getUniqueId().toString()));
            Bukkit.getConsoleSender().sendMessage("[KP-PVP - DEBUG] SETTED PLAYER UUID FOR " + wavePlayer.getName());
        }

        Wave.getInstance().getExecutorService().submit(() -> {
            try (StorageConnection storageConnection = WaveBukkit.getInstance().getStorage().newConnection()) {
                WaveBukkit.getInstance().getPlayerManager().getController().load(wavePlayer, storageConnection);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });

        }


}
