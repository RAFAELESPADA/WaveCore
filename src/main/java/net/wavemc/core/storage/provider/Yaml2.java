package net.wavemc.core.storage.provider;

import lombok.Getter;
import net.wavemc.core.Wave;
import net.wavemc.core.bukkit.WaveBukkit;
import net.wavemc.core.storage.Storage;
import net.wavemc.core.storage.StorageConnection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.DriverManager;

public class Yaml2 extends Storage {

    protected boolean createIfNotExist;
    protected boolean resource;
    protected final JavaPlugin plugin;

    @Getter
    protected FileConfiguration config;
    protected File file = new File(WaveBukkit.getInstance().getDataFolder(), "data.yml");
    protected File path;
    protected String name;


    public Yaml2(JavaPlugin instance, File path, String name, boolean createIfNotExist, boolean resource) {
        super("Yaml");
        this.plugin = WaveBukkit.getInstance();
        this.path = path;
        this.name = name + ".yml";
        this.createIfNotExist = createIfNotExist;
        this.resource = resource;
        config = YamlConfiguration.loadConfiguration(new File(WaveBukkit.getInstance().getDataFolder(), "data"));
        create();
    }
    public void save() {
        try {
            config.save(file);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public File reloadFile() {
        file = new File(path, name);
        return file;
    }

    public FileConfiguration reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
        return config;
    }

    public void reload() {
        reloadFile();
        reloadConfig();
    }

    public void create() {
        if (file == null) {
            reloadFile();
        }
        if (!createIfNotExist || file.exists()) {
            return;
        }
        file.getParentFile().mkdirs();
        if (resource) {
            WaveBukkit.getInstance().saveResource(name, false);
        } else {
            try {
                file.createNewFile();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        if (config == null) {
            reloadConfig();
        }
    }

    @Override
    public StorageConnection newConnection() {
        StorageConnection connection = null;
        try {
            new Yaml2(plugin, plugin.getDataFolder(), "data.yml", true, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
