package net.wavemc.core.storage.provider;

import lombok.Getter;
import net.wavemc.core.bukkit.WaveBukkit;
import net.wavemc.core.storage.Storage;
import net.wavemc.core.storage.StorageConnection;

import java.sql.DriverManager;

public class MySQL extends Storage {

    @Getter private final String host;
    @Getter private final String user;
    @Getter private final String password;
    @Getter private final String database;
    @Getter private final int port;

    public MySQL(String host, String user, String password, String database, int port) {
        super("MySQL");
        this.host = host;
        this.user = user;
        this.password = password;
        this.database = database;
        this.port = port;
    }

    @Override
    public StorageConnection newConnection() {
        StorageConnection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = new StorageConnection(DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=" + WaveBukkit.getInstance().getConfig().getString("UseSSL"),
                    user, password
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
