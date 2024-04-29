package net.wavemc.core.storage;

import lombok.AllArgsConstructor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class StorageConnection implements AutoCloseable{

    private final Connection connection;

    @Override
    public void close() {
        try {
            if (hasConnection()) {
                connection.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public boolean hasConnection() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public void execute(String s) throws SQLException {
        if (hasConnection()) {
            connection.prepareStatement(s).execute();
        }
    }

    public ResultSet query(String s) throws SQLException {
        if (!hasConnection()) {
            throw new SQLException("connection is closed");
        }
        return connection.prepareStatement(s).executeQuery();
    }
}
