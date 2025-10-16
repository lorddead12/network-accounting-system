package io.lorddead.networksystem.dao;

import io.lorddead.networksystem.model.Connection;
import io.lorddead.networksystem.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ConnectionDao implements Dao<Connection> {

    private static final ConnectionDao INSTANCE = new ConnectionDao();

    private final String INSERT_SQL = "INSERT INTO networks.connection " +
                                      "(device_from_id, device_to_id, connection_type, status) " +
                                      "VALUES (?, ?, ?, ?)";


    private ConnectionDao() {}

    @Override
    public void save(Connection connection) throws SQLException {
        try (var connect = ConnectionManager.open()) {
            try (var stmt = connect.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
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

    }

    @Override
    public List<Connection> getAllModels() throws SQLException {
        return List.of();
    }

    @Override
    public void delete(Connection connection) throws SQLException {

    }

    public static ConnectionDao getInstance() {
        return INSTANCE;
    }
}
