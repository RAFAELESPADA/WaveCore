package net.wavemc.core.bukkit.account;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.util.*;

import net.wavemc.core.bukkit.WaveBukkit;
import net.wavemc.core.bukkit.account.provider.PlayerPvP;
import net.wavemc.core.bukkit.data.HelixDataStorageController;
import net.wavemc.core.storage.StorageConnection;
import net.wavemc.core.storage.provider.SettingsManager;
import net.wavemc.core.storage.provider.Yaml2;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class WavePlayerController extends HelixDataStorageController<WavePlayer> {
    public WavePlayerController(WaveBukkit plugin) {
        super(plugin);
    }
static SettingsManager settings = SettingsManager.getInstance();
    public void save(WavePlayer wavePlayer) throws IOException {
       Player p = Bukkit.getPlayerExact(wavePlayer.getName());
        if (WaveBukkit.getInstance().getConfig().getBoolean("mysql.enable")) {
            try {
                Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin) WaveBukkit.getInstance(), new Runnable() {
                    public void run() {
                        try (StorageConnection storageConnection = WaveBukkit.getStorage().newConnection()) {
                            assert p != null;
                            ResultSet resultSet = storageConnection.query("select * from wave_pvp where ID = '" + p.getUniqueId() + "'");
                            String update = "update wave_pvp set `kills` = '" + String.valueOf(wavePlayer.getPvp().getKills()) + "', `passouchallenge` = '" + String.valueOf(wavePlayer.getPvp().getPassouchallenge()) + "', `deaths` = '" + String.valueOf(wavePlayer.getPvp().getDeaths()) + "', `killstreak` = '" + String.valueOf(wavePlayer.getPvp().getKillstreak()) + "' , `killsfps` = '" + String.valueOf(wavePlayer.getPvp().getKillsfps()) + "', `deathsfps` = '" + String.valueOf(wavePlayer.getPvp().getDeathsfps()) + "', `name` = '" + wavePlayer.getName() + "', `winssumo` = '" + String.valueOf(wavePlayer.getPvp().getWinssumo()) + "', `losessumo` = '" + String.valueOf(wavePlayer.getPvp().getDeathssumo()) + "', `kssumo` = '" + String.valueOf(wavePlayer.getPvp().getWinstreaksumo()) + "', `wins1v1` = '" + String.valueOf(wavePlayer.getPvp().getWinstreakx1()) + "', `deaths1v1` = '" + String.valueOf(wavePlayer.getPvp().getDeathsx1()) + "', `ks1v1` = '" + String.valueOf(wavePlayer.getPvp().getWinsx1()) + "', `coins` = '" + String.valueOf(wavePlayer.getPvp().getCoins()) + "', `thepitkills` = '" + String.valueOf(wavePlayer.getPvp().getThepitkills()) + "', `thepitdeaths` = '" + String.valueOf(wavePlayer.getPvp().getThepitdeaths()) + "', `thepitstreak` = '" + String.valueOf(wavePlayer.getPvp().getThepitstreak()) + "', `gold` = '" + String.valueOf(wavePlayer.getPvp().getGold()) + "', `thepitxp` = '" + String.valueOf(wavePlayer.getPvp().getThepitxp()) + "', `xp` = '" + String.valueOf(wavePlayer.getPvp().getXp()) + "' where `ID` = '" + String.valueOf(p.getUniqueId() + "'");
                            String insert = "insert into wave_pvp (name, kills, passouchallenge, deaths, killstreak, killsfps , deathsfps , winssumo , losessumo , kssumo , wins1v1 , deaths1v1 , ks1v1, coins, xp, thepitkills, thepitdeaths, thepitstreak, gold, thepitxp, ID) values ('" + wavePlayer.getName() + "', '" + wavePlayer.getPvp().getKills() + "', '" + wavePlayer.getPvp().getDeaths() + "', '" + wavePlayer.getPvp().getPassouchallenge() + "', '" + wavePlayer.getPvp().getKillstreak() + "', '" + wavePlayer.getPvp().getKillsfps() + "', '" + wavePlayer.getPvp().getDeathsfps() + "', '" + wavePlayer.getPvp().getWinssumo() + "', '" + wavePlayer.getPvp().getDeathssumo() + "', '" + wavePlayer.getPvp().getWinstreaksumo() + "', '" + wavePlayer.getPvp().getWinsx1() + "', '" + wavePlayer.getPvp().getDeathsx1() + "', '" + wavePlayer.getPvp().getWinstreakx1() + "', '" + wavePlayer.getPvp().getCoins() + "', '" + wavePlayer.getPvp().getXp() + "', '" + wavePlayer.getPvp().getThepitkills() + "', '" + wavePlayer.getPvp().getThepitdeaths() + "', '" + wavePlayer.getPvp().getThepitstreak() + "', '" + wavePlayer.getPvp().getThepitxp() + "', '" + wavePlayer.getPvp().getGold() + "', '" +p.getUniqueId() + "')";
                            if (resultSet.next()) {
                                storageConnection.execute(update);
                            } else {
                                storageConnection.execute(insert);
                            }
                            resultSet.close();
                            if (!WaveBukkit.getInstance().getConfig().getBoolean("mysql.enable"))
                                storageConnection.close();
                        } catch (Exception exception) {
                            throw new RuntimeException(exception);
                        }
                    }
                }, 40L);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        } else {
            FileConfiguration f2 = YamlConfiguration.loadConfiguration(new File(WaveBukkit.getInstance().getDataFolder(), "data.yml"));

            f2.options().copyDefaults(true);
            Yaml2 config = new Yaml2(WaveBukkit.getInstance(), WaveBukkit.getInstance().getDataFolder(), "data.yml", true, true);

            Bukkit.getConsoleSender().sendMessage("Saving " + wavePlayer.getName() + " stats to file!");
            assert p != null;
            if (settings.getData().get("stats." + p.getUniqueId().toString() + ".name") != wavePlayer.getName()) {
                settings.getData().set("stats." + p.getUniqueId().toString() + ".name", p.getName());
            }
            else {
                settings.getData().set("stats." + p.getUniqueId().toString() + ".name", wavePlayer.getName());
            }
            Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin) WaveBukkit.getInstance(), new Runnable() {
                public void run() {
                    settings.getData().set("stats." + p.getUniqueId().toString() + ".ID", wavePlayer.getPvp().getUuid().toString());
                    settings.getData().set("stats." + p.getUniqueId().toString() + ".kills", wavePlayer.getPvp().getKills());
                    settings.getData().set("stats." +p.getUniqueId().toString() + ".deaths", wavePlayer.getPvp().getDeaths());
                    settings.getData().set("stats." +p.getUniqueId().toString() + ".killstreak", wavePlayer.getPvp().getKillstreak());
                    settings.getData().set("stats." +p.getUniqueId().toString() + ".coins", wavePlayer.getPvp().getCoins());
                    settings.getData().set("stats." +p.getUniqueId().toString() + ".xp", wavePlayer.getPvp().getXp());
                    settings.getData().set("stats." +p.getUniqueId().toString() + ".winsx1", wavePlayer.getPvp().getWinsx1());
                    settings.getData().set("stats." +p.getUniqueId().toString() + ".thepitxp", wavePlayer.getPvp().getThepitxp());
                    settings.getData().set("stats." +p.getUniqueId().toString() + ".winstreaksumo", wavePlayer.getPvp().getWinstreaksumo());
                    settings.getData().set("stats." +p.getUniqueId().toString() + ".winstreakx1", wavePlayer.getPvp().getWinstreakx1());
                    settings.getData().set("stats." +p.getUniqueId().toString() + ".winsx1", wavePlayer.getPvp().getWinsx1());
                    settings.getData().set("stats." +p.getUniqueId().toString() + ".winssumo", wavePlayer.getPvp().getWinssumo());
                    settings.getData().set("stats." +p.getUniqueId().toString() + ".thepitstreak", wavePlayer.getPvp().getThepitstreak());
                    settings.getData().set("stats." +p.getUniqueId().toString() + ".thepitkills", wavePlayer.getPvp().getThepitkills());
                    settings.getData().set("stats." +p.getUniqueId().toString() + ".thepitdeaths", wavePlayer.getPvp().getThepitdeaths());
                    settings.getData().set("stats." +p.getUniqueId().toString() + ".gold", wavePlayer.getPvp().getGold());
                    settings.getData().set("stats." +p.getUniqueId().toString() + ".deathsx1", wavePlayer.getPvp().getDeathsx1());
                    settings.getData().set("stats." +p.getUniqueId().toString() + ".killsfps", wavePlayer.getPvp().getKillsfps());
                    settings.getData().set("stats." +p.getUniqueId().toString() + ".passouchallenge", wavePlayer.getPvp().getPassouchallenge());
                    settings.saveData();

            }
        }, 20L);
        }

    }

    public WavePlayer load(WavePlayer wavePlayer, StorageConnection storageConnection) {

        if (WaveBukkit.getInstance().getConfig().getBoolean("mysql.enable")) {
            try {
                Bukkit.getScheduler().runTaskAsynchronously((Plugin) WaveBukkit.getInstance(), new Runnable() {
                    public void run() {
                        try {

                            try (StorageConnection storageConnection = WaveBukkit.getStorage().newConnection()) {
                                ResultSet resultSet = storageConnection.query("select * from wave_pvp where ID = '" + wavePlayer.getPvp().getUuid() + "'");
                                if (resultSet.next()) {
                                    wavePlayer.getPvp().setKills(resultSet.getInt("kills"));
                                    wavePlayer.getPvp().setXp(resultSet.getInt("xp"));
                                    wavePlayer.getPvp().setKillsfps(resultSet.getInt("killsfps"));
                                    wavePlayer.getPvp().setWinssumo(resultSet.getInt("winssumo"));
                                    wavePlayer.getPvp().setWinstreaksumo(resultSet.getInt("kssumo"));
                                    wavePlayer.getPvp().setDeathssumo(resultSet.getInt("losessumo"));
                                    wavePlayer.getPvp().setDeaths(resultSet.getInt("deaths"));
                                    wavePlayer.getPvp().setWinsx1(resultSet.getInt("wins1v1"));
                                    wavePlayer.getPvp().setDeathsx1(resultSet.getInt("deaths1v1"));
                                    wavePlayer.getPvp().setPassouchallenge(resultSet.getInt("passouchallenge"));
                                    wavePlayer.getPvp().setWinstreakx1(resultSet.getInt("ks1v1"));
                                    wavePlayer.getPvp().setDeathsfps(resultSet.getInt("deathsfps"));
                                    wavePlayer.getPvp().setKillstreak(resultSet.getInt("killstreak"));
                                    wavePlayer.getPvp().setCoins(resultSet.getInt("coins"));
                                    wavePlayer.getPvp().setThepitkills(resultSet.getInt("thepitkills"));
                                    wavePlayer.getPvp().setThepitdeaths(resultSet.getInt("thepitdeaths"));
                                    wavePlayer.getPvp().setThepitstreak(resultSet.getInt("thepitstreak"));
                                    wavePlayer.getPvp().setGold(resultSet.getInt("gold"));
                                    wavePlayer.getPvp().setThepitxp(resultSet.getInt("thepitxp"));
                                    wavePlayer.getPvp().setUuid(UUID.fromString(resultSet.getString("ID")));
                                }
                                resultSet.close();
                            } catch (Exception exception) {

                                throw new RuntimeException(exception);
                            }
                        } catch (Exception exception) {

                            throw new RuntimeException(exception);
                        }
                    }
                });
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
            return wavePlayer;
        } else {
            Bukkit.getConsoleSender().sendMessage("LOADING " + wavePlayer.getName() + " stats from file!");
            Player p = Bukkit.getPlayerExact(wavePlayer.getName());
            File f = new File(WaveBukkit.getInstance().getDataFolder(), "data.yml");
            Yaml2 config = new Yaml2(WaveBukkit.getInstance(), WaveBukkit.getInstance().getDataFolder(), "data.yml", true, true);
            assert p != null;
            wavePlayer.getPvp().setKills(settings.getData().getInt("stats." + p.getUniqueId() + ".kills"));
            wavePlayer.getPvp().setXp(settings.getData().getInt("stats." +p.getUniqueId() + ".xp"));
            wavePlayer.getPvp().setKillsfps(settings.getData().getInt("stats." +p.getUniqueId() + ".killsfps"));
            wavePlayer.getPvp().setWinssumo(settings.getData().getInt("stats." +p.getUniqueId() + ".winssumo"));
            wavePlayer.getPvp().setWinstreaksumo(settings.getData().getInt("stats." +p.getUniqueId() + ".kssumo"));
            wavePlayer.getPvp().setDeathssumo(settings.getData().getInt("stats." +p.getUniqueId() + ".losessumo"));
            wavePlayer.getPvp().setDeaths(settings.getData().getInt("stats." +p.getUniqueId() + ".deaths"));
            wavePlayer.getPvp().setWinsx1(settings.getData().getInt("stats." +p.getUniqueId() + ".wins1v1"));
            wavePlayer.getPvp().setDeathsx1(settings.getData().getInt("stats." +p.getUniqueId() + ".deaths1v1"));
            wavePlayer.getPvp().setPassouchallenge(settings.getData().getInt("stats." +p.getUniqueId() + ".passouchallenge"));
            wavePlayer.getPvp().setWinstreakx1(settings.getData().getInt("stats." +p.getUniqueId() + ".ks1v1"));
            wavePlayer.getPvp().setDeathsfps(settings.getData().getInt("stats." +p.getUniqueId() + ".deathsfps"));
            wavePlayer.getPvp().setKillstreak(settings.getData().getInt("stats." +p.getUniqueId() + ".killstreak"));
            wavePlayer.getPvp().setCoins(settings.getData().getInt("stats." +p.getUniqueId() + ".coins"));
            wavePlayer.getPvp().setKills(settings.getData().getInt("stats." +p.getUniqueId() + ".kills"));
            wavePlayer.getPvp().setThepitkills(settings.getData().getInt("stats." +p.getUniqueId() + ".thepitkills"));
            wavePlayer.getPvp().setThepitdeaths(settings.getData().getInt("stats." +p.getUniqueId() + ".thepitdeaths"));
            wavePlayer.getPvp().setThepitstreak(settings.getData().getInt("stats." +p.getUniqueId() + ".thepitstreak"));
            wavePlayer.getPvp().setGold(settings.getData().getInt("stats." +p.getUniqueId() + ".gold"));
            wavePlayer.getPvp().setThepitxp(settings.getData().getInt("stats." +p.getUniqueId() + ".thepitxp"));
            wavePlayer.getPvp().setUuid(UUID.fromString(Objects.requireNonNull(config.getConfig().getString("stats." + p.getUniqueId()+ ".ID"))));
            settings.saveData();
        }


        return wavePlayer;
    }

    public List<WavePlayer> load() {
        List<WavePlayer> players = new ArrayList<>();

        if (WaveBukkit.getInstance().getConfig().getBoolean("mysql.enable")) {
            WaveBukkit.getExecutorService().execute(() -> {
                try {
                    getPlugin();
                    try (StorageConnection storageConnection = WaveBukkit.getStorage().newConnection()) {
                        if (storageConnection.query("select * from wave_pvp") == null) {
                            return;
                        }
                        ResultSet resultSet = storageConnection.query("select * from wave_pvp");
                        while (resultSet != null && resultSet.next()) {
                            WavePlayer helixPlayer = new WavePlayer(resultSet.getString("name"), UUID.fromString(resultSet.getString("ID")), true, new PlayerPvP(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                            players.add(load(helixPlayer, storageConnection));
                        }
                        ((ResultSet) Objects.<ResultSet>requireNonNull(resultSet)).close();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
            return players;
        } else {
Bukkit.getConsoleSender().sendMessage("YAML IS GETTING LOADED!");

        }
        return players;
    }

}
