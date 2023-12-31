package net.wavemc.core.storage.provider;

import lombok.Getter;
import net.wavemc.core.storage.Storage;
import net.wavemc.core.storage.StorageConnection;

import java.io.File;
import java.sql.DriverManager;

public class SQLite extends Storage {

    @Getter private final File dataFolder;

    public SQLite(File dataFolder) {
        super("SQLite");
        this.dataFolder = dataFolder;
    }

    @Override
    public StorageConnection newConnection() {
        StorageConnection connection = null;
        try {
            File storageFile = new File(dataFolder, "storage.db");
            String url = "jdbc:sqlite:" + storageFile;

            Class.forName("org.sqlite.JDBC");
            connection = new StorageConnection(DriverManager.getConnection(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
