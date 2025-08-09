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
                            ResultSet resultSet = storageConnection.query("select * from wave_pvp where ID = '" + wavePlayer.getName() + "'");
                            String update = "update wave_pvp set `kills` = '" + String.valueOf(wavePlayer.getPvp().getKills()) + "', `passouchallenge` = '" + String.valueOf(wavePlayer.getPvp().getPassouchallenge()) + "', `deaths` = '" + String.valueOf(wavePlayer.getPvp().getDeaths()) + "', `killstreak` = '" + String.valueOf(wavePlayer.getPvp().getKillstreak()) + "' , `killsfps` = '" + String.valueOf(wavePlayer.getPvp().getKillsfps()) + "', `deathsfps` = '" + String.valueOf(wavePlayer.getPvp().getDeathsfps()) + "', `name` = '" + wavePlayer.getName() + "', `winssumo` = '" + String.valueOf(wavePlayer.getPvp().getWinssumo()) + "', `losessumo` = '" + String.valueOf(wavePlayer.getPvp().getDeathssumo()) + "', `kssumo` = '" + String.valueOf(wavePlayer.getPvp().getWinstreaksumo()) + "', `wins1v1` = '" + String.valueOf(wavePlayer.getPvp().getWinstreakx1()) + "', `deaths1v1` = '" + String.valueOf(wavePlayer.getPvp().getDeathsx1()) + "', `ks1v1` = '" + String.valueOf(wavePlayer.getPvp().getWinsx1()) + "', `coins` = '" + String.valueOf(wavePlayer.getPvp().getCoins()) + "', `thepitkills` = '" + String.valueOf(wavePlayer.getPvp().getThepitkills()) + "', `thepitdeaths` = '" + String.valueOf(wavePlayer.getPvp().getThepitdeaths()) + "', `thepitstreak` = '" + String.valueOf(wavePlayer.getPvp().getThepitstreak()) + "', `gold` = '" + String.valueOf(wavePlayer.getPvp().getGold()) + "', `thepitxp` = '" + String.valueOf(wavePlayer.getPvp().getThepitxp()) + "', `xp` = '" + String.valueOf(wavePlayer.getPvp().getXp()) + "' where `ID` = '" + String.valueOf(wavePlayer.getName() + "'");
                            String insert = "insert into wave_pvp (name, kills, passouchallenge, deaths, killstreak, killsfps , deathsfps , winssumo , losessumo , kssumo , wins1v1 , deaths1v1 , ks1v1, coins, xp, thepitkills, thepitdeaths, thepitstreak, gold, thepitxp, ID) values ('" + wavePlayer.getName() + "', '" + wavePlayer.getPvp().getKills() + "', '" + wavePlayer.getPvp().getDeaths() + "', '" + wavePlayer.getPvp().getPassouchallenge() + "', '" + wavePlayer.getPvp().getKillstreak() + "', '" + wavePlayer.getPvp().getKillsfps() + "', '" + wavePlayer.getPvp().getDeathsfps() + "', '" + wavePlayer.getPvp().getWinssumo() + "', '" + wavePlayer.getPvp().getDeathssumo() + "', '" + wavePlayer.getPvp().getWinstreaksumo() + "', '" + wavePlayer.getPvp().getWinsx1() + "', '" + wavePlayer.getPvp().getDeathsx1() + "', '" + wavePlayer.getPvp().getWinstreakx1() + "', '" + wavePlayer.getPvp().getCoins() + "', '" + wavePlayer.getPvp().getXp() + "', '" + wavePlayer.getPvp().getThepitkills() + "', '" + wavePlayer.getPvp().getThepitdeaths() + "', '" + wavePlayer.getPvp().getThepitstreak() + "', '" + wavePlayer.getPvp().getThepitxp() + "', '" + wavePlayer.getPvp().getGold() + "', '" + wavePlayer.getName() + "')";
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

            Bukkit.getConsoleSender().sendMessage("Saving " + wavePlayer.getName() + " stats to file!");
            config.getConfig().set("stats." +wavePlayer.getName() + ".name", wavePlayer.getName());
            config.getConfig().set("stats." +wavePlayer.getName() + ".ID", wavePlayer.getName());
            config.getConfig().set("stats." +wavePlayer.getName() + ".kills", wavePlayer.getPvp().getKills());
            config.getConfig().set("stats." +wavePlayer.getName() + ".deaths", wavePlayer.getPvp().getDeaths());
            config.getConfig().set("stats." +wavePlayer.getName() + ".killstreak", wavePlayer.getPvp().getKillstreak());
            config.getConfig().set("stats." +wavePlayer.getName() + ".coins", wavePlayer.getPvp().getCoins());
            config.getConfig().set("stats." +wavePlayer.getName() + ".xp", wavePlayer.getPvp().getXp());
            config.getConfig().set("stats." +wavePlayer.getName() + ".winsx1", wavePlayer.getPvp().getWinsx1());
            config.getConfig().set("stats." +wavePlayer.getName() + ".thepitxp", wavePlayer.getPvp().getThepitxp());
            config.getConfig().set("stats." +wavePlayer.getName() + ".winstreaksumo", wavePlayer.getPvp().getWinstreaksumo());
            config.getConfig().set("stats." +wavePlayer.getName() + ".winstreakx1", wavePlayer.getPvp().getWinstreakx1());
            config.getConfig().set("stats." +wavePlayer.getName() + ".winsx1", wavePlayer.getPvp().getWinsx1());
            config.getConfig().set("stats." +wavePlayer.getName() + ".winssumo", wavePlayer.getPvp().getWinssumo());
            config.getConfig().set("stats." +wavePlayer.getName() + ".thepitstreak", wavePlayer.getPvp().getThepitstreak());
            config.getConfig().set("stats." +wavePlayer.getName() + ".thepitkills", wavePlayer.getPvp().getThepitkills());
            config.getConfig().set("stats." +wavePlayer.getName() + ".thepitdeaths", wavePlayer.getPvp().getThepitdeaths());
            config.getConfig().set("stats." +wavePlayer.getName() + ".gold", wavePlayer.getPvp().getGold());
            config.getConfig().set("stats." +wavePlayer.getName() + ".deathsx1", wavePlayer.getPvp().getDeathsx1());
            config.getConfig().set("stats." +wavePlayer.getName() + ".killsfps", wavePlayer.getPvp().getKillsfps());
            config.getConfig().set("stats." +wavePlayer.getName() + ".passouchallenge", wavePlayer.getPvp().getPassouchallenge());
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
                                ResultSet resultSet = storageConnection.query("select * from wave_pvp where ID = '" + wavePlayer.getName() + "'");
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

            File f = new File(WaveBukkit.getInstance().getDataFolder(), "data.yml");
            Yaml2 config = new Yaml2(WaveBukkit.getInstance(), WaveBukkit.getInstance().getDataFolder(), "data.yml", true, true);
            wavePlayer.getPvp().setKills(config.getConfig().getInt("stats." +wavePlayer.getName() + ".ID"));
            wavePlayer.getPvp().setXp(config.getConfig().getInt("stats." +wavePlayer.getName() + ".xp"));
            wavePlayer.getPvp().setKillsfps(config.getConfig().getInt("stats." +wavePlayer.getName() + ".killsfps"));
            wavePlayer.getPvp().setWinssumo(config.getConfig().getInt("stats." +wavePlayer.getName() + ".winssumo"));
            wavePlayer.getPvp().setWinstreaksumo(config.getConfig().getInt("stats." +wavePlayer.getName() + ".kssumo"));
            wavePlayer.getPvp().setDeathssumo(config.getConfig().getInt("stats." +wavePlayer.getName() + ".losessumo"));
            wavePlayer.getPvp().setDeaths(config.getConfig().getInt("stats." +wavePlayer.getName() + ".deaths"));
            wavePlayer.getPvp().setWinsx1(config.getConfig().getInt("stats." +wavePlayer.getName() + ".wins1v1"));
            wavePlayer.getPvp().setDeathsx1(config.getConfig().getInt("stats." +wavePlayer.getName() + ".deaths1v1"));
            wavePlayer.getPvp().setPassouchallenge(config.getConfig().getInt("stats." +wavePlayer.getName() + ".passouchallenge"));
            wavePlayer.getPvp().setWinstreakx1(config.getConfig().getInt("stats." +wavePlayer.getName() + ".ks1v1"));
            wavePlayer.getPvp().setDeathsfps(config.getConfig().getInt("stats." +wavePlayer.getName() + ".deathsfps"));
            wavePlayer.getPvp().setKillstreak(config.getConfig().getInt("stats." +wavePlayer.getName() + ".killstreak"));
            wavePlayer.getPvp().setCoins(config.getConfig().getInt("stats." +wavePlayer.getName() + ".coins"));
            wavePlayer.getPvp().setKills(config.getConfig().getInt("stats." +wavePlayer.getName() + ".kills"));
            wavePlayer.getPvp().setThepitkills(config.getConfig().getInt("stats." +wavePlayer.getName() + ".thepitkills"));
            wavePlayer.getPvp().setThepitdeaths(config.getConfig().getInt("stats." +wavePlayer.getName() + ".thepitdeaths"));
            wavePlayer.getPvp().setThepitstreak(config.getConfig().getInt("stats." +wavePlayer.getName() + ".thepitstreak"));
            wavePlayer.getPvp().setGold(config.getConfig().getInt("stats." +wavePlayer.getName() + ".gold"));
            wavePlayer.getPvp().setThepitxp(config.getConfig().getInt("stats." +wavePlayer.getName() + ".thepitxp"));
            wavePlayer.getPvp().setUuid(UUID.fromString(Objects.requireNonNull(config.getConfig().getString("stats." + wavePlayer.getName() + ".ID"))));
            config.save();
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
