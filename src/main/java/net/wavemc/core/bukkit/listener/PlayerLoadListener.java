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
        if (!Objects.equals(wavePlayer.getPvp().getUuid(), event.getPlayer().getUniqueId().toString())) {
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

        if (!WaveBukkit.getInstance().getConfig().getBoolean("mysql.enable")) {
            File f = new File(WaveBukkit.getInstance().getDataFolder(), "data.yml");
            Yaml2 config = new Yaml2(WaveBukkit.getInstance(), WaveBukkit.getInstance().getDataFolder(), "data.yml", true, true);
if (config.getConfig().get("stats." + wavePlayer.getUuid()) == null){
                config.getConfig().set("stats", wavePlayer.getPvp().getUuid());
    Bukkit.getConsoleSender().sendMessage("[KP-PVP - DEBUG] CREATING PLAYER UUID OBJECT IN FILE FOR " + wavePlayer.getName());

}            wavePlayer.getPvp().setKills(config.getConfig().getInt("stats." + wavePlayer.getPvp().getUuid() + ".ID"));
            wavePlayer.getPvp().setXp(config.getConfig().getInt("stats." + wavePlayer.getPvp().getUuid() + ".xp"));
            wavePlayer.getPvp().setKillsfps(config.getConfig().getInt("stats." + wavePlayer.getPvp().getUuid() + ".killsfps"));
            wavePlayer.getPvp().setWinssumo(config.getConfig().getInt("stats." + wavePlayer.getPvp().getUuid() + ".winssumo"));
            wavePlayer.getPvp().setWinstreaksumo(config.getConfig().getInt("stats." + wavePlayer.getPvp().getUuid() + ".kssumo"));
            wavePlayer.getPvp().setDeathssumo(config.getConfig().getInt("stats." + wavePlayer.getPvp().getUuid() + ".losessumo"));
            wavePlayer.getPvp().setDeaths(config.getConfig().getInt("stats." + wavePlayer.getPvp().getUuid() + ".deaths"));
            wavePlayer.getPvp().setWinsx1(config.getConfig().getInt("stats." + wavePlayer.getPvp().getUuid() + ".wins1v1"));
            wavePlayer.getPvp().setDeathsx1(config.getConfig().getInt("stats." + wavePlayer.getPvp().getUuid() + ".deaths1v1"));
            wavePlayer.getPvp().setPassouchallenge(config.getConfig().getInt("stats." + wavePlayer.getPvp().getUuid() + ".passouchallenge"));
            wavePlayer.getPvp().setWinstreakx1(config.getConfig().getInt("stats." + wavePlayer.getPvp().getUuid() + ".ks1v1"));
            wavePlayer.getPvp().setDeathsfps(config.getConfig().getInt("stats." + wavePlayer.getPvp().getUuid() + ".deathsfps"));
            wavePlayer.getPvp().setKillstreak(config.getConfig().getInt("stats." + wavePlayer.getPvp().getUuid() + ".killstreak"));
            wavePlayer.getPvp().setCoins(config.getConfig().getInt("stats." + wavePlayer.getPvp().getUuid() + ".coins"));
            wavePlayer.getPvp().setThepitkills(config.getConfig().getInt("stats." + wavePlayer.getPvp().getUuid() + ".thepitkills"));
            wavePlayer.getPvp().setThepitdeaths(config.getConfig().getInt("stats." + wavePlayer.getPvp().getUuid() + ".thepitdeaths"));
            wavePlayer.getPvp().setThepitstreak(config.getConfig().getInt("stats." + wavePlayer.getPvp().getUuid() + ".thepitstreak"));
            wavePlayer.getPvp().setGold(config.getConfig().getInt("stats." +wavePlayer.getPvp().getUuid() + ".gold"));
            wavePlayer.getPvp().setThepitxp(config.getConfig().getInt("stats." + wavePlayer.getPvp().getUuid() + ".thepitxp"));
            wavePlayer.getPvp().setUuid(UUID.fromString(Objects.requireNonNull(config.getConfig().getString("stats." + wavePlayer.getPvp().getUuid() + ".ID"))));
config.save();

        }}


}
