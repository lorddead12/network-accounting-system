package io.lorddead.networksystem.ui;

import java.io.PrintWriter;

public class ConsoleWriter implements AutoCloseable {
    private final PrintWriter out;

    public ConsoleWriter() {
        out = new PrintWriter(System.out, true);
    }

    public void printObjectFromDatabase(Object obj) {
        out.println(obj);
        out.println();
    }

    public void printMessage(String message) {
        out.println(message);
    }

    public void printMessageForInput(String message) {
        out.print(message);
        out.flush();
    }

    public void printBorder(int lengthBorder) {
        out.println("-".repeat(lengthBorder));
    }

    @Override
    public void close() {
        out.close();
    }
}
