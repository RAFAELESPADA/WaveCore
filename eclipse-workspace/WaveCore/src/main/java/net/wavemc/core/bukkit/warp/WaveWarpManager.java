package net.wavemc.core.bukkit.warp;

import lombok.Getter;
import net.wavemc.core.bukkit.WaveBukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.*;

public class WaveWarpManager {

    @Getter private final List<WaveWarp> warps;
    @Getter private final WaveWarpData data;

    public WaveWarpManager(WaveBukkit plugin) {
        this.warps = new ArrayList<>();
        this.data = new WaveWarpData(plugin);
        warps.addAll(data.load());
    }

    public WaveWarp createWarp(String warpName) {
        return createWarp(warpName, null, null);
    }

    public WaveWarp createWarp(String warpName, Location location, World w) {
        WaveWarp warp = new WaveWarp(warpName, location, w);
        this.warps.add(warp);
        return warp;
    }

    public void deleteWarp(WaveWarp warp) {
        this.warps.remove(warp);
    }

    public Optional<WaveWarp> findWarp(String warpName) {
        return warps.stream().filter(
                warp -> warp.getName().equalsIgnoreCase(warpName)
        ).findFirst();
    }
}