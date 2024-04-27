package net.wavemc.core.bukkit.warp;

import net.wavemc.core.bukkit.WaveBukkit;
import net.wavemc.core.bukkit.data.HelixDataController;
import net.wavemc.core.bukkit.file.WaveFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import java.util.ArrayList;
import java.util.List;

public class WaveWarpData extends HelixDataController<WaveWarp> {

    private final WaveFile file;

    public WaveWarpData(WaveBukkit plugin) {
        super(plugin);
        file = new WaveFile("warps.yml", plugin.getDataFolder());
    }

    @Override
    public void save(WaveWarp warp) {
        if (!warp.hasLocation()) {
            throw new NullPointerException();
        }
        if (!warp.hasWorld()) {
            throw new NullPointerException();
        }
        try {
            file.getYaml().set("Mundo-" + warp.getName(), serializeW(warp));
            file.getYaml().set("X-" + warp.getName(), serializeX(warp));
            file.getYaml().set("Y-" + warp.getName(), serializeY(warp));
            file.getYaml().set("Z-" + warp.getName(), serializeZ(warp));
            file.getYaml().set("PITCH-" + warp.getName(), serializeP(warp));
            file.getYaml().set("YAW-" + warp.getName(), serializeYW(warp));
            file.save();
        }catch (NullPointerException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public WaveWarp load(WaveWarp warp) {
        try {



            String serializedW = file.getYaml().getString("Mundo-" + warp.getName());
            Bukkit.getConsoleSender().sendMessage("§e§lCORE: §fWarp §e'" + warp.getName() + "' §fcarregado!");
        }catch (Exception exception) {
            exception.printStackTrace();
        }
        return warp;
    }

    @Override
    public List<WaveWarp> load() {
        List<WaveWarp> warps = new ArrayList<>();
        try {
            ConfigurationSection section;
            if ((section = file.getYaml().getConfigurationSection("warps")) == null) {
                return warps;
            }

            section.getKeys(false).forEach(warpName -> {
                WaveWarp warp = new WaveWarp(warpName, null, null);
                warps.add(load(warp));
            });
        }catch (Exception exception) {
            exception.printStackTrace();
        }
        return warps;
    }

    public void delete(WaveWarp warp) {
        ConfigurationSection section;
        if ((section = file.getYaml().getConfigurationSection("warps")) == null) {
            return;
        }

        if (section.contains(warp.getName())) {
            section.set(warp.getName(), null);
        }
        file.save();
    }

    private String serialize(WaveWarp warp) {
        Location location;
        if ((location = warp.getLocation()) == null) {
            throw new NullPointerException();
        }
        return location.getWorld().getName() + " : " + location.getX() + " : " + location.getY() +
                " : " + location.getZ() + " : " + location.getYaw() + " : " + location.getPitch();
    }
    private String serializeW(WaveWarp warp) {
        World location;
        if ((location = warp.getWorldpenis()) == null) {
            throw new NullPointerException();
        }
        return location.getName();
    }
    private Double serializeX(WaveWarp warp) {
        Location location = warp.getLocation();

        return location.getX();
    }
    private Double serializeY(WaveWarp warp) {
        Location location = warp.getLocation();

        return location.getY();
    }
    private Double serializeZ(WaveWarp warp) {
        Location location = warp.getLocation();

        return location.getZ();
    }
    private Float serializeYW(WaveWarp warp) {
        Location location = warp.getLocation();

        return location.getYaw();
    }

    private Float serializeP(WaveWarp warp) {
        Location location = warp.getLocation();

        return location.getPitch();
    }
    private Location deserialize(String serialized) {
        String[] serializedArr = serialized.split(" : ");

        World world = Bukkit.getWorld(serializedArr[0]);
        double x = Double.parseDouble(serializedArr[1]);
        double y = Double.parseDouble(serializedArr[2]);
        double z = Double.parseDouble(serializedArr[3]);
        float yaw = Float.parseFloat(serializedArr[4]);
        float pitch = Float.parseFloat(serializedArr[5]);

        return new Location(world, x, y, z, yaw, pitch);
    }
    private String deserializeW(String serialized) {
        String serializedArr = serialized;

        World world2 = Bukkit.getWorld(serialized);

        return world2.getName();
    }
}
