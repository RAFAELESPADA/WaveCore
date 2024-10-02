package net.wavemc.core.bukkit.listener;

import lombok.AllArgsConstructor;
import net.wavemc.core.Wave;
import net.wavemc.core.bukkit.account.WavePlayer;
import net.wavemc.core.bukkit.WaveBukkit;

import net.wavemc.core.storage.StorageConnection;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import java.sql.SQLException;

@AllArgsConstructor
public class PlayerLoadListener implements Listener {




    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        WavePlayer wavePlayer = WaveBukkit.getInstance().getPlayerManager().getPlayer(player.getName());
        if (wavePlayer.getPvp().getUuid() != event.getPlayer().getUniqueId().toString()) {
            wavePlayer.getPvp().setUuid(event.getPlayer().getUniqueId().toString());
        }
<<<<<<< HEAD

        Wave.getInstance().getExecutorService().submit(() -> {
            try (StorageConnection storageConnection = WaveBukkit.getInstance().getStorage().newConnection()) {
                WaveBukkit.getInstance().getPlayerManager().getController().load(wavePlayer, storageConnection);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }
=======
        
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        WavePlayer wavePlayer = plugin.getPlayerManager().getPlayer(player.getName());
        if (wavePlayer.getPvp().getUuid() != event.getPlayer().getUniqueId().toString()) {
            wavePlayer.getPvp().setUuid(event.getPlayer().getUniqueId().toString());
        }
Wave.getInstance().getExecutorService().submit(() -> {
            try (StorageConnection storageConnection = plugin.getStorage().newConnection()) {
                plugin.getPlayerManager().getController().load(wavePlayer, storageConnection);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }
>>>>>>> 5dd6987e39d0468094c8d94de57a6b3eaada86e4
}