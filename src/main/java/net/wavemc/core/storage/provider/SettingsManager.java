package net.wavemc.core.storage.provider;


import java.io.File;
import java.io.IOException;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class SettingsManager {
    @Getter
    static SettingsManager instance = new SettingsManager();

    Plugin p;


    @Getter
    FileConfiguration data;

    File dfile;

    public void setup(Plugin p) {
        if (!p.getDataFolder().exists())
            p.getDataFolder().mkdir();
        this.dfile = new File(p.getDataFolder(), "data.yml");
        if (!this.dfile.exists())
            try {
                this.dfile.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create data.yml!");
            }
        this.data = (FileConfiguration)YamlConfiguration.loadConfiguration(this.dfile);
    }

    public void saveData() {
        try {
            this.data.save(this.dfile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");
        }
    }

    public void reloadData() {
        this.data = (FileConfiguration)YamlConfiguration.loadConfiguration(this.dfile);
    }





    public PluginDescriptionFile getDesc() {
        return this.p.getDescription();
    }
}
