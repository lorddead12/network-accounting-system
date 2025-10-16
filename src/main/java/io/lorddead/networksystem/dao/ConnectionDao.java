package io.lorddead.networksystem.dao;

import io.lorddead.networksystem.model.Connection;
import io.lorddead.networksystem.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConnectionDao implements Dao<Connection> {

    private static final ConnectionDao INSTANCE = new ConnectionDao();

    private ConnectionDao() {}

    @Override
    public void save(Connection connection) throws SQLException {
        final String SQL =
                "INSERT INTO networks.connection (device_from_id, device_to_id, connection_type, status) " +
                "VALUES (?, ?, ?, ?)";

        try (var connect = ConnectionManager.open()) {
            try (var stmt = connect.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(1, connection.getDeviceFromId());
                stmt.setLong(2, connection.getDeviceToId());
                stmt.setString(3, connection.getConnectionType());
                stmt.setString(4, connection.getStatus());

                stmt.executeUpdate();

                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    connection.setId(generatedKeys.getLong("id"));
                    connection.setCreatedAt(generatedKeys.getTimestamp("created_at").toLocalDateTime());
                } else {
                    throw new SQLException("Failed to create a new record in the database");
                }
            }
        }
    }

    @Override
    public void update(Connection connection) throws SQLException {
        String SQL = "UPDATE networks.connection " +
                     "SET device_from_id = ?, device_to_id = ?, connection_type = ?, status = ? " +
                     "WHERE id = ?";

        try (var connect = ConnectionManager.open()) {
            try (var stmt = connect.prepareStatement(SQL)) {
                stmt.setLong(1, connection.getDeviceFromId());
                stmt.setLong(2, connection.getDeviceToId());
                stmt.setString(3, connection.getConnectionType());
                stmt.setString(4, connection.getStatus());
                stmt.setLong(5, connection.getId());

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public List<Connection> getAllModels() throws SQLException {
        final String SQL =
                "SELECT id, device_from_id, device_to_id, connection_type, status, created_at " +
                "FROM networks.connection";

        try (var connect = ConnectionManager.open()) {
            try (var stmt = connect.createStatement()) {
                return fillListConnections(stmt.executeQuery(SQL));
            }
        }
    }

    private List<Connection> fillListConnections(ResultSet result) throws SQLException {
        List<Connection> connections = new ArrayList<>();
        while (result.next()) {
            connections.add(toModel(result));
        }

        return connections;
    }

    private Connection toModel(ResultSet result) throws SQLException {
        return new Connection.ConnectionBuilder()
                .setId(result.getLong("id"))
                .setDeviceFromId(result.getLong("device_from_id"))
                .setDeviceToId(result.getLong("device_to_id"))
                .setConnectionType(result.getString("connection_type"))
                .setStatus(result.getString("status"))
                .setCreatedAt(result.getTimestamp("created_at").toLocalDateTime())
                .build();
    }

    @Override
    public void delete(Connection connection) throws SQLException {
        final String SQL = "DELETE FROM networks.connection " +
                            "WHERE id = ?";

        try (var connect = ConnectionManager.open()) {
            try (var stmt = connect.prepareStatement(SQL)) {
                stmt.setLong(1, connection.getId());
                stmt.executeUpdate();
            }
        }
    }

    public static ConnectionDao getInstance() {
        return INSTANCE;
    }
}
