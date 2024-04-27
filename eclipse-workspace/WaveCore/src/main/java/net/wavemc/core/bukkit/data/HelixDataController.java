package net.wavemc.core.bukkit.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.wavemc.core.bukkit.WaveBukkit;

import java.util.List;

@AllArgsConstructor @Data
public abstract class HelixDataController<T> {

    private final WaveBukkit plugin;

    public abstract void save(T t);
    public abstract T load(T t);
    public abstract List<T> load();
}
