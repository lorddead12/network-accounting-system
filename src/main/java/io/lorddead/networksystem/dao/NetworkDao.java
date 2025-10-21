package io.lorddead.networksystem.dao;

import io.lorddead.networksystem.model.Network;
import io.lorddead.networksystem.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NetworkDao implements Dao<Network> {

    private final static NetworkDao INSTANCE = new NetworkDao();

    private NetworkDao() {}

    @Override
    public void save(Network network) throws SQLException {
        String INSERT_SQL = "INSERT INTO networks.network (name, description) " +
                            "VALUES(?, ?)";

        try (var connection = ConnectionManager.open()) {
            try (var stmt = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, network.getName());
                stmt.setString(2, network.getDescription());
                stmt.executeUpdate();

                var generatedKeys = stmt.getGeneratedKeys();

                if (generatedKeys.next()) {
                    network.setId(generatedKeys.getLong("id"));
                    network.setCreatedAt(
                            generatedKeys.getTimestamp("created_at").toLocalDateTime());
                } else {
                    throw new SQLException("Creating network failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public void update(Network network) throws SQLException {
        String UPDATE_SQL = "UPDATE networks.network " +
                            "SET name = ?, description = ? " +
                            "WHERE id = ?";

        try (var connection = ConnectionManager.open()) {
            try (var stmt = connection.prepareStatement(UPDATE_SQL)) {
                stmt.setString(1, network.getName());
                stmt.setString(2, network.getDescription());
                stmt.setLong(3, network.getId());

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void delete(Network network) throws SQLException {
        String DELETE_SQL = "DELETE FROM networks.network " +
                            "WHERE id = ?";

        try (var connection = ConnectionManager.open()) {
            try (var stmt = connection.prepareStatement(DELETE_SQL)) {
                stmt.setLong(1, network.getId());
                stmt.executeUpdate();
            }
        }
    }


    public List<Network> getAllModels() throws SQLException {
        String SELECT_ALL_SQL = "SELECT id, name, description, created_at " +
                                "FROM networks.network";

        try (var connection = ConnectionManager.open()) {
            try (var stmt = connection.createStatement()) {
                return fillListNetworks(stmt.executeQuery(SELECT_ALL_SQL));
            }
        }
    }

    public List<Network> getEmptyModels() throws SQLException {
        String SELECT_SQL = "SELECT network.id, network.name, network.description, network.created_at " +
                            "FROM networks.network " +
                            "LEFT JOIN networks.device device on network.id = device.network_id " +
                            "WHERE device.id is null";

        try (var connection = ConnectionManager.open()) {
            try (var stmt = connection.createStatement()) {
                return fillListNetworks(stmt.executeQuery(SELECT_SQL));
            }
        }
    }

    private List<Network> fillListNetworks(ResultSet result) throws SQLException {
        List<Network> networks = new ArrayList<>();
        while (result.next()) {
            networks.add(toModel(result));
        }

        return networks;
    }

    private Network toModel(ResultSet result) throws SQLException {
        return new Network(
                result.getLong("id"),
                result.getString("name"),
                result.getString("description"),
                result.getTimestamp("created_at").toLocalDateTime()
        );
    }

    public static NetworkDao getInstance() {
        return INSTANCE;
    }
}
