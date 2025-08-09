package net.wavemc.core.bukkit.account;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import net.wavemc.core.bukkit.WaveBukkit;
import net.wavemc.core.bukkit.account.provider.PlayerPvP;
import net.wavemc.core.bukkit.data.HelixDataStorageController;
import net.wavemc.core.storage.StorageConnection;
import net.wavemc.core.storage.provider.Yaml2;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.Plugin;

public class WavePlayerController extends HelixDataStorageController<WavePlayer> {
    public WavePlayerController(WaveBukkit plugin) {
        super(plugin);
    }

    public void save(final WavePlayer wavePlayer) {
        if (WaveBukkit.getInstance().getConfig().getBoolean("mysql.enable")) {
            try {
                Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin) WaveBukkit.getInstance(), new Runnable() {
                    public void run() {
                        try (StorageConnection storageConnection = WaveBukkit.getStorage().newConnection()) {
                            ResultSet resultSet = storageConnection.query("select * from wave_pvp where ID = '" + wavePlayer.getUuid() + "'");
                            String update = "update wave_pvp set `kills` = '" + String.valueOf(wavePlayer.getPvp().getKills()) + "', `passouchallenge` = '" + String.valueOf(wavePlayer.getPvp().getPassouchallenge()) + "', `deaths` = '" + String.valueOf(wavePlayer.getPvp().getDeaths()) + "', `killstreak` = '" + String.valueOf(wavePlayer.getPvp().getKillstreak()) + "' , `killsfps` = '" + String.valueOf(wavePlayer.getPvp().getKillsfps()) + "', `deathsfps` = '" + String.valueOf(wavePlayer.getPvp().getDeathsfps()) + "', `name` = '" + wavePlayer.getName() + "', `winssumo` = '" + String.valueOf(wavePlayer.getPvp().getWinssumo()) + "', `losessumo` = '" + String.valueOf(wavePlayer.getPvp().getDeathssumo()) + "', `kssumo` = '" + String.valueOf(wavePlayer.getPvp().getWinstreaksumo()) + "', `wins1v1` = '" + String.valueOf(wavePlayer.getPvp().getWinstreakx1()) + "', `deaths1v1` = '" + String.valueOf(wavePlayer.getPvp().getDeathsx1()) + "', `ks1v1` = '" + String.valueOf(wavePlayer.getPvp().getWinsx1()) + "', `coins` = '" + String.valueOf(wavePlayer.getPvp().getCoins()) + "', `thepitkills` = '" + String.valueOf(wavePlayer.getPvp().getThepitkills()) + "', `thepitdeaths` = '" + String.valueOf(wavePlayer.getPvp().getThepitdeaths()) + "', `thepitstreak` = '" + String.valueOf(wavePlayer.getPvp().getThepitstreak()) + "', `gold` = '" + String.valueOf(wavePlayer.getPvp().getGold()) + "', `thepitxp` = '" + String.valueOf(wavePlayer.getPvp().getThepitxp()) + "', `xp` = '" + String.valueOf(wavePlayer.getPvp().getXp()) + "' where `ID` = '" + String.valueOf(wavePlayer.getUuid() + "'");
                            String insert = "insert into wave_pvp (name, kills, passouchallenge, deaths, killstreak, killsfps , deathsfps , winssumo , losessumo , kssumo , wins1v1 , deaths1v1 , ks1v1, coins, xp, thepitkills, thepitdeaths, thepitstreak, gold, thepitxp, ID) values ('" + wavePlayer.getName() + "', '" + wavePlayer.getPvp().getKills() + "', '" + wavePlayer.getPvp().getDeaths() + "', '" + wavePlayer.getPvp().getPassouchallenge() + "', '" + wavePlayer.getPvp().getKillstreak() + "', '" + wavePlayer.getPvp().getKillsfps() + "', '" + wavePlayer.getPvp().getDeathsfps() + "', '" + wavePlayer.getPvp().getWinssumo() + "', '" + wavePlayer.getPvp().getDeathssumo() + "', '" + wavePlayer.getPvp().getWinstreaksumo() + "', '" + wavePlayer.getPvp().getWinsx1() + "', '" + wavePlayer.getPvp().getDeathsx1() + "', '" + wavePlayer.getPvp().getWinstreakx1() + "', '" + wavePlayer.getPvp().getCoins() + "', '" + wavePlayer.getPvp().getXp() + "', '" + wavePlayer.getPvp().getThepitkills() + "', '" + wavePlayer.getPvp().getThepitdeaths() + "', '" + wavePlayer.getPvp().getThepitstreak() + "', '" + wavePlayer.getPvp().getThepitxp() + "', '" + wavePlayer.getPvp().getGold() + "', '" + wavePlayer.getUuid() + "')";
                            if (resultSet.next()) {
                                storageConnection.execute(update);
                            } else {
                                storageConnection.execute(insert);
                            }
                            resultSet.close();
                            if (!WaveBukkit.getInstance().getConfig().getBoolean("mysql.enable"))
                                storageConnection.close();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }, 40L);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {


            File f = new File(WaveBukkit.getInstance().getDataFolder(), "data.yml");
            Yaml2 config = new Yaml2(WaveBukkit.getInstance(), WaveBukkit.getInstance().getDataFolder(), "data.yml", true, true);

            Bukkit.getConsoleSender().sendMessage("Saving "  + wavePlayer.getName() + " stats to file!");
            config.getConfig().set(wavePlayer.getUuid() + ".name", wavePlayer.getName());
            config.getConfig().set(wavePlayer.getUuid() + ".ID", wavePlayer.getPvp().getUuid());
            config.getConfig().set(wavePlayer.getUuid() + ".kills", wavePlayer.getPvp().getKills());
            config.getConfig().set(wavePlayer.getUuid() + ".deaths", wavePlayer.getPvp().getDeaths());
            config.getConfig().set(wavePlayer.getUuid() + ".killstreak", wavePlayer.getPvp().getKillstreak());
            config.getConfig().set(wavePlayer.getUuid() + ".coins", wavePlayer.getPvp().getCoins());
            config.getConfig().set(wavePlayer.getUuid() + ".xp", wavePlayer.getPvp().getXp());
            config.getConfig().set(wavePlayer.getUuid() + ".winsx1", wavePlayer.getPvp().getWinsx1());
            config.getConfig().set(wavePlayer.getUuid() + ".thepitxp", wavePlayer.getPvp().getThepitxp());
            config.getConfig().set(wavePlayer.getUuid() + ".winstreaksumo", wavePlayer.getPvp().getWinstreaksumo());
            config.getConfig().set(wavePlayer.getUuid() + ".winstreakx1", wavePlayer.getPvp().getWinstreakx1());
            config.getConfig().set(wavePlayer.getUuid() + ".winsx1", wavePlayer.getPvp().getWinsx1());
            config.getConfig().set(wavePlayer.getUuid() + ".winssumo", wavePlayer.getPvp().getWinssumo());
            config.getConfig().set(wavePlayer.getUuid() + ".thepitstreak", wavePlayer.getPvp().getThepitstreak());
            config.getConfig().set(wavePlayer.getUuid() + ".thepitkills", wavePlayer.getPvp().getThepitkills());
            config.getConfig().set(wavePlayer.getUuid() + ".thepitdeaths", wavePlayer.getPvp().getThepitdeaths());
            config.getConfig().set(wavePlayer.getUuid() + ".gold", wavePlayer.getPvp().getGold());
            config.getConfig().set(wavePlayer.getUuid() + ".deathsx1", wavePlayer.getPvp().getDeathsx1());
            config.getConfig().set(wavePlayer.getUuid() + ".killsfps", wavePlayer.getPvp().getKillsfps());
            config.getConfig().set(wavePlayer.getUuid() + ".passouchallenge", wavePlayer.getPvp().getPassouchallenge());
            config.save();
        }
    }

    public WavePlayer load(final WavePlayer wavePlayer, StorageConnection storageConnection) {

        if (WaveBukkit.getInstance().getConfig().getBoolean("mysql.enable")) {
            try {
                Bukkit.getScheduler().runTaskAsynchronously((Plugin) WaveBukkit.getInstance(), new Runnable() {
                    public void run() {
                        try {

                            try (StorageConnection storageConnection = WaveBukkit.getStorage().newConnection()) {
                                ResultSet resultSet = storageConnection.query("select * from wave_pvp where ID = '" + wavePlayer.getUuid() + "'");
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
                                    wavePlayer.getPvp().setUuid(resultSet.getString("ID"));
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
            File f = new File(WaveBukkit.getInstance().getDataFolder(), "data.yml");
            Yaml2 config = new Yaml2(WaveBukkit.getInstance(), WaveBukkit.getInstance().getDataFolder(), "data.yml", true, true);
            try {
                config.getConfig().load(f);
                wavePlayer.getPvp().setKills(config.getConfig().getInt(wavePlayer.getUuid() + ".ID"));
                wavePlayer.getPvp().setXp(config.getConfig().getInt(wavePlayer.getUuid() + ".xp"));
                wavePlayer.getPvp().setKillsfps(config.getConfig().getInt(wavePlayer.getUuid() + ".killsfps"));
                wavePlayer.getPvp().setWinssumo(config.getConfig().getInt(wavePlayer.getUuid() + ".winssumo"));
                wavePlayer.getPvp().setWinstreaksumo(config.getConfig().getInt(wavePlayer.getUuid() + ".kssumo"));
                wavePlayer.getPvp().setDeathssumo(config.getConfig().getInt(wavePlayer.getUuid() + ".losessumo"));
                wavePlayer.getPvp().setDeaths(config.getConfig().getInt(wavePlayer.getUuid() + ".deaths"));
                wavePlayer.getPvp().setWinsx1(config.getConfig().getInt(wavePlayer.getUuid() + ".wins1v1"));
                wavePlayer.getPvp().setDeathsx1(config.getConfig().getInt(wavePlayer.getUuid() + ".deaths1v1"));
                wavePlayer.getPvp().setPassouchallenge(config.getConfig().getInt(wavePlayer.getUuid() + ".passouchallenge"));
                wavePlayer.getPvp().setWinstreakx1(config.getConfig().getInt(wavePlayer.getUuid() + ".ks1v1"));
                wavePlayer.getPvp().setDeathsfps(config.getConfig().getInt(wavePlayer.getUuid() + ".deathsfps"));
                wavePlayer.getPvp().setKillstreak(config.getConfig().getInt(wavePlayer.getUuid() + ".killstreak"));
                wavePlayer.getPvp().setCoins(config.getConfig().getInt(wavePlayer.getUuid() + ".coins"));
                wavePlayer.getPvp().setKills(config.getConfig().getInt(wavePlayer.getUuid() + ".kills"));
                wavePlayer.getPvp().setThepitkills(config.getConfig().getInt(wavePlayer.getUuid() + ".thepitkills"));
                wavePlayer.getPvp().setThepitdeaths(config.getConfig().getInt(wavePlayer.getUuid() + ".thepitdeaths"));
                wavePlayer.getPvp().setThepitstreak(config.getConfig().getInt(wavePlayer.getUuid() + ".thepitstreak"));
                wavePlayer.getPvp().setGold(config.getConfig().getInt(wavePlayer.getUuid() + ".gold"));
                wavePlayer.getPvp().setThepitxp(config.getConfig().getInt(wavePlayer.getUuid() + ".thepitxp"));
                wavePlayer.getPvp().setUuid(config.getConfig().getString(wavePlayer.getUuid() + ".ID"));
                config.save();
            } catch (IOException | InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
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
