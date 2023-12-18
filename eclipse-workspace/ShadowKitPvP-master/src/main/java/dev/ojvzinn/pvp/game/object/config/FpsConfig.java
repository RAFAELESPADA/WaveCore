package dev.ojvzinn.pvp.game.object.config;

import dev.ojvzinn.pvp.Main;
import dev.ojvzinn.pvp.utils.WorldUtils;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import dev.slickcollections.kiwizin.utils.CubeID;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.IOException;

public class FpsConfig extends KitPvPConfig {

    private Location spawnLocation = null;
    private CubeID spawnCubeID = null;
    private String worldname;

    public FpsConfig(String fileName) throws IOException {
        super(fileName);
        this.worldname = this.getYamlConfig().getString("world-name");
        if (!this.getYamlConfig().getString("world-name").equals("")) {
            try {
                new WorldUtils(this.worldname).load();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), ()-> {
                    this.spawnCubeID = new CubeID(this.getYamlConfig().getString("spawn-cube-id"));
                    this.spawnLocation = BukkitUtils.deserializeLocation(this.getYamlConfig().getString("spawn-location"));
                }, 10);
            }
        }
    }

    @Override
    public void reload() {
        this.worldname = this.getYamlConfig().getString("world-name");
        this.spawnCubeID = new CubeID(this.getYamlConfig().getString("spawn-cube-id"));
        this.spawnLocation = BukkitUtils.deserializeLocation(this.getYamlConfig().getString("spawn-location"));
    }

    public Location getSpawnLocation() {
        return this.spawnLocation;
    }

    public CubeID getSpawnCubeID() {
        return this.spawnCubeID;
    }

    public String getWorldname() {
        return this.worldname;
    }
}
