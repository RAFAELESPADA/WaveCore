package net.wavemc.core.bukkit;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.wavemc.core.bukkit.account.WavePlayer;
import net.wavemc.core.bukkit.account.UUIDFetcher;
import net.wavemc.core.bukkit.account.WavePlayerController;
import net.wavemc.core.storage.provider.SettingsManager;
import net.wavemc.core.storage.provider.Yaml2;
import net.wavemc.core.util.UpdateScheduler;
import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.wavemc.core.bukkit.account.WavePlayerManager;
import net.wavemc.core.bukkit.item.listener.ItemBuilderListener;
import net.wavemc.core.bukkit.listener.PlaceHolderAPIHook;
import net.wavemc.core.bukkit.listener.PlayerLoadListener;
import net.wavemc.core.bukkit.listener.PlayerQuitListener;
import net.wavemc.core.bukkit.message.BukkitMessage;
import net.wavemc.core.bukkit.util.CCommand;
import net.wavemc.core.bukkit.warp.WaveWarpManager;
import net.wavemc.core.storage.Storage;
import net.wavemc.core.storage.provider.MySQL;
import net.wavemc.core.storage.provider.SQLite;

public class WaveBukkit extends JavaPlugin {

    private UUIDFetcher uuidFetcher;
    @Getter private static final ExecutorService executorService = Executors.newFixedThreadPool(50);
    @Getter public static Storage storage;
    static WaveBukkit instance;
    @Getter private static WavePlayerController playerController;
    @Getter private static WavePlayerManager Manager;
    private static SimpleCommandMap scm;
    private SimplePluginManager spm;
    @Getter private WaveWarpManager warpManager;

    @Getter @Setter private boolean chat = true;

    @SneakyThrows
    @Override
    public void onEnable() {
setupSimpleCommandMap();

        SettingsManager settings = SettingsManager.getInstance();
        this.uuidFetcher = new UUIDFetcher(1); // Initialize UUIDFetcher with 1 thread
instance = this;
        saveDefaultConfig();
        new PlaceHolderAPIHook(this).register();
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "wave:cre");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "wave:cre", new BukkitMessage());
if (getConfig().getBoolean("mysql.enable")) {
    storage =
            new MySQL(
                    getConfig().getString("mysql.host"),
                    getConfig().getString("mysql.user"),
                    getConfig().getString("mysql.password"),
                    getConfig().getString("mysql.database"),
                    getConfig().getInt("mysql.port")
            );
    storage.createTables();
} else {
    storage =  new Yaml2(this, this.getDataFolder(), "data", true, true);
                }
Bukkit.getConsoleSender().sendMessage("[KPCORE] Loading listeners and managers.");
Manager = new WavePlayerManager(this);
        this.warpManager = new WaveWarpManager(this);

        loadListeners(Bukkit.getPluginManager());
        startUpdating();
playerController = new WavePlayerController(this);
        settings.setup(this);
    }

    private void loadListeners(PluginManager pluginManager) {
        pluginManager.registerEvents(new PlayerLoadListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new ItemBuilderListener(), this);
    }

    private void registerCommands(CCommand... commands) {
        Arrays.stream(commands).forEach(command -> scm.register("core", command));//Register the plugin
    }

    public static WaveBukkit getInstance() {
        return instance;
    }

    private void setupSimpleCommandMap() {
        spm = (SimplePluginManager) this.getServer().getPluginManager();
        Field f = null;
        try {
            f = SimplePluginManager.class.getDeclaredField("commandMap");
        } catch (Exception e) {
            e.printStackTrace();
        }
        f.setAccessible(true);
        try {
            scm = (SimpleCommandMap) f.get(spm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static SimpleCommandMap getCommandMap() {
        return scm;
    }


    public static WavePlayerManager getPlayerManager() {
        return Manager;
    }

    public static WavePlayerController getPlayerController() {
        return  playerController;
    }



    private void startUpdating() {

    Bukkit.getServer().getScheduler().runTaskTimer(getInstance(), new UpdateScheduler(), 20, 20);
}


    @Override
    public void onDisable() {

            this.uuidFetcher.shutdown(); // Shut down UUIDFetcher after plugin is disabled (important!)
        }


    }

