package io.lorddead.networksystem.ui;

import io.lorddead.networksystem.model.Connection;
import io.lorddead.networksystem.model.Device;
import io.lorddead.networksystem.model.Model;
import io.lorddead.networksystem.model.Network;
import io.lorddead.networksystem.service.TableChoice;
import io.lorddead.networksystem.service.UserChoice;
import io.lorddead.networksystem.service.ValidationResult;

import java.io.IOException;
import java.util.List;

public class ConsoleView {
    private final ConsoleWriter out;
    private final ConsoleReader in;

    public ConsoleView(ConsoleWriter writer, ConsoleReader reader) {
        out = writer;
        in = reader;
    }

    public ValidationResult getUserChoice(String table) {
        for (UserChoice action : UserChoice.values()) {
            out.printMessage(action.getCode() + ". " + action.getDescription());
        }

        out.printMessageForInput("Enter code action for table " + table + " from 1 to 4: ");

        try {
            int code = in.readCode();
            out.printMessage("");
            return new ValidationResult(code, true);
        } catch (NumberFormatException e) {
            out.printMessage("\nIncorrect input format. Try again.\n");
            return new ValidationResult(-1, false);
        } catch (IOException e) {
            throw new RuntimeException("Error: can not read data from console.", e);
        }
    }

    public ValidationResult getTableChoice() {
        for (TableChoice table : TableChoice.values()) {
            out.printMessage(table.getCode() + ". " + table.getDescription());
        }

        out.printMessageForInput("Enter code from 1 to 4: ");

        try {
            int code = in.readCode();
            out.printMessage("");
            return new ValidationResult(code, true);
        } catch (NumberFormatException e) {
            out.printMessage("\nIncorrect input format. Try again.\n");
            return new ValidationResult(-1, false);
        } catch (IOException e) {
            throw new RuntimeException("Error: can not read data from console.", e);
        }
    }

    public Device getDeviceFromUser() {

        out.printMessageForInput("Enter name device: ");
        String name = in.readFieldString();

        out.printMessageForInput("Enter ip address device: ");
        String ipAddress = in.readFieldString();

        out.printMessageForInput("Enter mac address device: ");
        String macAddress = in.readFieldString();

        out.printMessageForInput("Enter type device: ");
        String type = in.readFieldString();

        out.printMessageForInput("Enter status device: ");
        String status = in.readFieldString();

        out.printBorder(50);

        return new Device(name, ipAddress, macAddress, type, status);
    }

    public Network getNetworkFromUser() {
        out.printBorder(20);

        out.printMessageForInput("Enter name network: ");
        String name = in.readFieldString();

        out.printMessageForInput("Enter description network: ");
        String description = in.readFieldString();

        out.printBorder(20);

        return new Network(name, description);
    }

    public Connection getConnectionFromUser() {
        out.printMessageForInput("Enter connection type: ");
        String connectionType = in.readFieldString();

        out.printMessageForInput("Enter connection status: ");
        String status = in.readFieldString();

        return new Connection.ConnectionBuilder()
                .setConnectionType(connectionType)
                .setStatus(status)
                .build();
    }

    public <T extends Model> T selectOf(List<T> models, String name) {
        out.printBorder(50);
        models.forEach(out::printObjectFromDatabase);

        while (true) {
            out.printMessageForInput("Enter id of " + name + ": ");
            long id = in.readId();

            if (id > 0) {
                for (var model : models) {
                    if (model.getId() == id) {
                        return model;
                    }
                }
            } else {
                out.printMessage("Id was incorrect. try again.");
            }
        }
    }

    public void showDisplayInfo(String message) {
        out.printMessage(message);
    }

    public void showDisplayObjectFromDatabase(Object obj) {
        out.printObjectFromDatabase(obj);
    }

    public void close() {
        out.close();
    }
}
