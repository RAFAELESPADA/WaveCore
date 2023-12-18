package dev.ojvzinn.pvp.game.object.config;

import dev.ojvzinn.pvp.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public abstract class KitPvPConfig {

    private final YamlConfiguration yamlConfig;
    private final String fileName;

    public KitPvPConfig(String fileName) throws IOException {
        File file = new File("plugins/" + Main.getInstance().getDescription().getName() + "/warps/" + fileName + ".yml");
        if (!file.exists()) {
            File folder = file.getParentFile();
            if (!folder.exists()) {
                folder.mkdirs();
            }

            Files.copy(Objects.requireNonNull(getClass().getResourceAsStream("/" + fileName + ".yml")), file.toPath());
        }

        yamlConfig = YamlConfiguration.loadConfiguration(file);
        this.fileName = "plugins/" + Main.getInstance().getDescription().getName() + "/warps/" + fileName + ".yml";
    }

    public YamlConfiguration getYamlConfig() {
        return this.yamlConfig;
    }

    public void setValue(String key, Object value) {
        this.yamlConfig.set(key, value);
        try {
            this.yamlConfig.save(new File(this.fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void reload();
}
