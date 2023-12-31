package net.wavemc.core.bukkit.warp;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;

@Data @AllArgsConstructor
public class WaveWarp {

    private final String name;
    private Location location;

    public boolean hasLocation() {
        return location != null;
    }
}
