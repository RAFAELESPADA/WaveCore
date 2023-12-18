package dev.ojvzinn.pvp.game.object.config;

import dev.slickcollections.kiwizin.utils.CubeID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.IOException;

public class ArenaConfig extends KitPvPConfig {

    private CubeID spawnCubeID;

    public ArenaConfig(String fileName) throws IOException {
        super(fileName);
        if (!this.getYamlConfig().getString("spawn-cube-id").isEmpty()) {
            this.spawnCubeID = new CubeID(this.getYamlConfig().getString("spawn-cube-id"));
        } else {
            World firstWorld = Bukkit.getWorlds().get(0);
            Location spawnLocation = firstWorld.getSpawnLocation();
            this.spawnCubeID = new CubeID(spawnLocation.clone().add(10, 0, 10), spawnLocation.clone().add(-10, 10, -10));
        }
    }

    @Override
    public void reload() {
        this.spawnCubeID = new CubeID(this.getYamlConfig().getString("spawn-cube-id"));
    }

    public CubeID getSpawnCubeID() {
        return this.spawnCubeID;
    }
}
