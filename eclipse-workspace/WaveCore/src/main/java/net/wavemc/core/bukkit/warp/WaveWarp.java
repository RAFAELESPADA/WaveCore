package net.wavemc.core.bukkit.warp;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.World;

@Data @AllArgsConstructor
public class WaveWarp {

    private final String name;
    private Location location;
    private World worldpenis;

    public boolean hasLocation() {
        return location != null;
    }

    public boolean hasWorld() {
        return worldpenis != null;
    }

    public void setWorldpenis(World w) {
        worldpenis = w;
    }}
