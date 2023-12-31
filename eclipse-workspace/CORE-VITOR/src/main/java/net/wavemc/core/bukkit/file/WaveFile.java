package net.wavemc.core.bukkit.file;

import lombok.Data;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@Data
public class WaveFile {

    private final File file;
    private final YamlConfiguration yaml;

    public WaveFile(String fileName, File dataFolder) {
        this.file = new File(dataFolder, fileName);
        this.yaml = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            this.yaml.save(file);
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
