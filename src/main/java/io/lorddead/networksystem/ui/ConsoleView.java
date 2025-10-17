package io.lorddead.networksystem.ui;

import io.lorddead.networksystem.model.Network;
import io.lorddead.networksystem.service.UserChoice;
import io.lorddead.networksystem.service.ValidationResult;

import java.io.IOException;

public class ConsoleView {
    private final ConsoleWriter out;
    private final ConsoleReader in;

    public ConsoleView(ConsoleWriter writer, ConsoleReader reader) {
        out = writer;
        in = reader;
    }

    public ValidationResult getUserChoice() {
        for (UserChoice action : UserChoice.values()) {
            out.printMessage(action.getCode() + ". " + action.getDescription());
        }

        out.printMessageForInput("Enter code from 0 to 10: ");

        try {
            return new ValidationResult(in.readCode(), true);
        } catch (NumberFormatException e) {
            out.printMessage("\nIncorrect input format. Try again.\n");
            return new ValidationResult(-1, false);
        } catch (IOException e) {
            throw new RuntimeException("Error: can not read data from console.", e);
        }
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

    public void showDisplayInfo(String message) {
        out.printMessage(message);
    }

    public void showDisplayObjectFromDatabase(Object obj) {
        out.printObjectFromDatabase(obj);
    }

    public void close() {
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing resources", e);
        }
    }
}
