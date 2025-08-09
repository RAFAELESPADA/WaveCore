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

@AllArgsConstructor
public class PlayerLoadListener implements Listener {




    @EventHandler(priority = EventPriority.LOW)
    public void onJoitrn(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        WavePlayer wavePlayer = WaveBukkit.getInstance().getPlayerManager().getPlayer(player.getName());
        if (!Objects.equals(wavePlayer.getPvp().getUuid(), event.getPlayer().getUniqueId().toString())) {
            wavePlayer.getPvp().setUuid(event.getPlayer().getUniqueId().toString());
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
                config.getConfig().set("stats", wavePlayer.getUuid());
    Bukkit.getConsoleSender().sendMessage("[KP-PVP - DEBUG] CREATING PLAYER UUID OBJECT IN FILE FOR " + wavePlayer.getName());

}            wavePlayer.getPvp().setKills(config.getConfig().getInt("stats." + wavePlayer.getUuid() + ".ID"));
            wavePlayer.getPvp().setXp(config.getConfig().getInt("stats." + wavePlayer.getUuid() + ".xp"));
            wavePlayer.getPvp().setKillsfps(config.getConfig().getInt("stats." + wavePlayer.getUuid() + ".killsfps"));
            wavePlayer.getPvp().setWinssumo(config.getConfig().getInt("stats." + wavePlayer.getUuid() + ".winssumo"));
            wavePlayer.getPvp().setWinstreaksumo(config.getConfig().getInt("stats." + wavePlayer.getUuid() + ".kssumo"));
            wavePlayer.getPvp().setDeathssumo(config.getConfig().getInt("stats." + wavePlayer.getUuid() + ".losessumo"));
            wavePlayer.getPvp().setDeaths(config.getConfig().getInt("stats." + wavePlayer.getUuid() + ".deaths"));
            wavePlayer.getPvp().setWinsx1(config.getConfig().getInt("stats." + wavePlayer.getUuid() + ".wins1v1"));
            wavePlayer.getPvp().setDeathsx1(config.getConfig().getInt("stats." + wavePlayer.getUuid() + ".deaths1v1"));
            wavePlayer.getPvp().setPassouchallenge(config.getConfig().getInt("stats." + wavePlayer.getUuid() + ".passouchallenge"));
            wavePlayer.getPvp().setWinstreakx1(config.getConfig().getInt("stats." + wavePlayer.getUuid() + ".ks1v1"));
            wavePlayer.getPvp().setDeathsfps(config.getConfig().getInt("stats." + wavePlayer.getUuid() + ".deathsfps"));
            wavePlayer.getPvp().setKillstreak(config.getConfig().getInt("stats." + wavePlayer.getUuid() + ".killstreak"));
            wavePlayer.getPvp().setCoins(config.getConfig().getInt("stats." + wavePlayer.getUuid() + ".coins"));
            wavePlayer.getPvp().setThepitkills(config.getConfig().getInt("stats." + wavePlayer.getUuid() + ".thepitkills"));
            wavePlayer.getPvp().setThepitdeaths(config.getConfig().getInt("stats." + wavePlayer.getUuid() + ".thepitdeaths"));
            wavePlayer.getPvp().setThepitstreak(config.getConfig().getInt("stats." + wavePlayer.getUuid() + ".thepitstreak"));
            wavePlayer.getPvp().setGold(config.getConfig().getInt("stats." +wavePlayer.getUuid() + ".gold"));
            wavePlayer.getPvp().setThepitxp(config.getConfig().getInt("stats." + wavePlayer.getUuid() + ".thepitxp"));
            wavePlayer.getPvp().setUuid(config.getConfig().getString("stats." + wavePlayer.getUuid() + ".ID"));
config.save();

        }}


}
