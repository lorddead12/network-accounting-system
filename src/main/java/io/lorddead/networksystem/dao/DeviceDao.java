package io.lorddead.networksystem.dao;

import io.lorddead.networksystem.model.Device;
import io.lorddead.networksystem.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DeviceDao implements Dao<Device> {

    private static final DeviceDao INSTANCE = new DeviceDao();

    private DeviceDao() {}

    @Override
    public void save(Device device) throws SQLException {
        String INSERT_SQL = "INSERT INTO networks.device(network_id, name, ip_address, mac_address, type, status) " +
                            "VALUES (?, ?, ?, ?, ?, ?)";

        try (var connection = ConnectionManager.open()) {
            try (var stmt = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(1, device.getNetworkId());
                stmt.setString(2, device.getName());
                stmt.setString(3, device.getIpAddress());
                stmt.setString(4, device.getMacAddress());
                stmt.setString(5, device.getType());
                stmt.setString(6, device.getStatus());
                stmt.executeUpdate();

                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    device.setId(generatedKeys.getLong("id"));
                    device.setCreatedAt(generatedKeys.getTimestamp("created_at").toLocalDateTime());
                } else {
                    throw new SQLException("Creating device failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public void update(Device device) throws SQLException {
        String UPDATE_SQL = "UPDATE networks.device " +
                            "SET network_id = ?, name = ?, ip_address = ?, mac_address = ?, type = ?, status = ? " +
                            "WHERE id = ?";

        try (var connection = ConnectionManager.open()) {
            try (var stmt = connection.prepareStatement(UPDATE_SQL)) {
                stmt.setLong(1, device.getNetworkId());
                stmt.setString(2, device.getName());
                stmt.setString(3, device.getIpAddress());
                stmt.setString(4, device.getMacAddress());
                stmt.setString(5, device.getType());
                stmt.setString(6, device.getStatus());
                stmt.setLong(7, device.getId());
                stmt.executeUpdate();
            }
        }
    }

    public List<Device> getEmptyModels() throws SQLException {
        String SELECT_NOT_CONNECTION_DEVICE_SQL =
                "SELECT  dev.id, dev.network_id, dev.name, dev.ip_address ,dev.mac_address , " +
                         "dev.type, dev.status, dev.created_at " +
                "FROM networks.device AS dev " +
                "WHERE NOT EXISTS(SELECT 1 " +
                "FROM networks.connection AS con " +
                "WHERE con.device_from_id  = dev.id " +
                "OR con.device_to_id = dev.id)";

        try (var connection = ConnectionManager.open()) {
            try (var stmt = connection.createStatement()) {
                return fillListModels(stmt.executeQuery(SELECT_NOT_CONNECTION_DEVICE_SQL));
            }
        }
    }

    @Override
    public List<Device> getAllModels() throws SQLException {
        String SELECT_ALL_SQL = "SELECT id, network_id, name, ip_address, mac_address, type, status, created_at " +
                                "FROM networks.device";

        try (var connection = ConnectionManager.open()) {
            try (var stmt = connection.createStatement()) {
                return fillListModels(stmt.executeQuery(SELECT_ALL_SQL));
            }
        }
    }

    private List<Device> fillListModels(ResultSet result) throws SQLException {
        List<Device> devices = new ArrayList<>();
        while (result.next()) {
            devices.add(toModel(result));
        }

        return devices;
    }

    private Device toModel(ResultSet result) throws SQLException {
        long id = result.getLong("id");
        long networkId = result.getLong("network_id");
        String name = result.getString("name");
        String ipAddress = result.getString("ip_address");
        String macAddress = result.getString("mac_address");
        String type = result.getString("type");
        String status = result.getString("status");
        var createdAt = result.getTimestamp("created_at").toLocalDateTime();

        return new Device(id, networkId, name, ipAddress, macAddress, type, status, createdAt);
    }

    @Override
    public void delete(Device device) throws SQLException {
        String DELETE_SQL = "DELETE FROM networks.device " +
                            "WHERE id = ?";

        try (var connection = ConnectionManager.open()) {
            try (var stmt = connection.prepareStatement(DELETE_SQL)) {
                stmt.setLong(1, device.getId());
                stmt.executeUpdate();
            }
        }
    }

    public static DeviceDao getInstance() {
        return INSTANCE;
    }
}
