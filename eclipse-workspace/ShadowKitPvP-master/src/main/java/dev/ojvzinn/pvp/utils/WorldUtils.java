package dev.ojvzinn.pvp.utils;

import dev.ojvzinn.pvp.Main;
import dev.slickcollections.kiwizin.plugin.logger.KLogger;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.util.logging.Level;

public class WorldUtils {

    public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger()).getModule("LOAD_WORLD");
    private String worldFileName;

    public WorldUtils(String worldFileName) {
        this.worldFileName = worldFileName;
    }

    public void load() {
        try {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                WorldCreator wc = WorldCreator.name(worldFileName);
                wc.generateStructures(false);
                wc.generator(VoidChunkGenerator.VOID_CHUNK_GENERATOR);
                World world = wc.createWorld();
                world.setTime(0L);
                world.setStorm(false);
                world.setThundering(false);
                world.setAutoSave(false);
                world.setAnimalSpawnLimit(0);
                world.setWaterAnimalSpawnLimit(0);
                world.setKeepSpawnInMemory(false);
                world.setGameRuleValue("doMobSpawning", "false");
                world.setGameRuleValue("doDaylightCycle", "false");
                world.setGameRuleValue("mobGriefing", "false");
            });
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Cannot load world \"" + worldFileName + "\"", ex);
        }
    }
}
