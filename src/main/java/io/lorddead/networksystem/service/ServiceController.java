package io.lorddead.networksystem.service;

import io.lorddead.networksystem.dao.ConnectionDao;
import io.lorddead.networksystem.dao.Dao;
import io.lorddead.networksystem.dao.DeviceDao;
import io.lorddead.networksystem.dao.NetworkDao;
import io.lorddead.networksystem.model.Connection;
import io.lorddead.networksystem.model.Device;
import io.lorddead.networksystem.model.Network;
import io.lorddead.networksystem.ui.ConsoleView;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ServiceController {

    private final ConsoleView view;

    public ServiceController(ConsoleView view) {
        this.view = view;
    }

    public void process() {
        try {
            while (true) {
                var userCode = view.getUserChoice();
                if (!userCode.isValid()) {
                    continue;
                }

                Optional<UserChoice> action = UserChoice.valueOf(userCode.getCode());
                if (action.isEmpty()) {
                    view.showDisplayInfo("\nInvalid code. Please try again.\n");
                } else {
                    var userChoice = action.get();
                    if (userChoice == UserChoice.EXIT) {
                        break;
                    }

                    processChoice(userChoice);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            view.close();
        }
    }

    private void processChoice(UserChoice userChoice) throws SQLException {
        switch (userChoice) {
            case ADD_NETWORK -> {
                Network newNetwork = view.getNetworkFromUser();
                Dao<Network> networkDao = NetworkDao.getInstance();
                networkDao.save(newNetwork);

                view.showDisplayObjectFromDatabase(newNetwork);
            }

            case DELETE_NETWORK -> {
                Dao<Network> networkDao = NetworkDao.getInstance();
                List<Network> networks = networkDao.getEmptyModels();

                if (networks.isEmpty()) {
                    view.showDisplayInfo("\nAll networks have related devices. Remove them first\n");
                    return;
                }

                Network networkToDelete = view.selectOf(networks, "delete network");
                networkDao.delete(networkToDelete);
                view.showDisplayInfo("Was deleted network with id - [" + networkToDelete.getId() + "]\n");
            }

            case UPDATE_NETWORK -> {
                Dao<Network> networkDao = NetworkDao.getInstance();
                List<Network> networks = networkDao.getAllModels();
                Network networkToUpdate = view.selectOf(networks, "update network");
                Network tempNetwork = view.getNetworkFromUser();

                networkToUpdate.setName(tempNetwork.getName());
                networkToUpdate.setDescription(tempNetwork.getDescription());

                networkDao.update(networkToUpdate);
                view.showDisplayInfo("Was updated network: " + networkToUpdate + "\n");
            }

            case ADD_DEVICE -> {
                Dao<Device> dao = DeviceDao.getInstance();
                List<Network> networks = NetworkDao.getInstance().getAllModels();
                Network selectNetwork = view.selectOf(networks, "network for the new device");

                Device device = view.getDeviceFromUser();

                Device newDevice = new Device(selectNetwork.getId(), device.getName(),
                                    device.getIpAddress(), device.getMacAddress(), device.getType(), device.getStatus());

                dao.save(newDevice);
                view.showDisplayObjectFromDatabase(newDevice);
            }

            case UPDATE_DEVICE -> {
                Dao<Device> dao = DeviceDao.getInstance();

                List<Device> devices = dao.getAllModels();
                Device toUpdate = view.selectOf(devices, "update to");
                Device updatedDevice = view.getDeviceFromUser();
                Device update =
                        new Device(
                                toUpdate.getId(),
                                toUpdate.getNetworkId(),
                                updatedDevice.getName(),
                                updatedDevice.getIpAddress(),
                                updatedDevice.getMacAddress(),
                                updatedDevice.getType(),
                                updatedDevice.getStatus(),
                                toUpdate.getCreatedAt());

                dao.update(update);
                view.showDisplayObjectFromDatabase(update);
            }

            case DELETE_DEVICE -> {
                Dao<Device> dao = DeviceDao.getInstance();
                List<Device> devices = dao.getEmptyModels();

                if (devices.isEmpty()) {
                    view.showDisplayInfo("\nAll devices are connected in networks. First, remove the link.\n");
                    return;
                }

                Device deviceToDelete = view.selectOf(devices, "device delete to");
                dao.delete(deviceToDelete);

                view.showDisplayInfo("Was deleted device with id - [" + deviceToDelete.getId() + "]\n");
            }

            case ADD_CONNECTION -> {
                Dao<Connection> dao = ConnectionDao.getInstance();
                List<Device> devices = DeviceDao.getInstance().getAllModels();

                if (devices.size() < 2) {
                    view.showDisplayInfo("Not enough devices");
                    return;
                }

                Device deviceFrom = view.selectOf(devices, "device");
                List<Device> remainedDevices = devices.stream()
                                                      .filter(device -> !device.equals(deviceFrom))
                                                      .toList();

                Device deviceTo = view.selectOf(remainedDevices, "device to");

                Connection connection = view.getConnectionFromUser();
                connection.setDeviceFromId(deviceFrom.getId());
                connection.setDeviceToId(deviceTo.getId());

                dao.save(connection);
                view.showDisplayObjectFromDatabase(connection);
            }

            default -> throw new RuntimeException("Unsupported operation: " + userChoice);
        }
    }
}
