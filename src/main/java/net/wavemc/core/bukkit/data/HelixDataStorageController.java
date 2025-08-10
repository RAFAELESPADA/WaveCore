package net.wavemc.core.bukkit.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.wavemc.core.bukkit.WaveBukkit;
import net.wavemc.core.storage.StorageConnection;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor @Data
public abstract class HelixDataStorageController<T> {

    private final WaveBukkit plugin;

    public abstract void save(T t) throws IOException;
    public abstract T load(T t, StorageConnection storageConnection);
    public abstract List<T> load();
}
