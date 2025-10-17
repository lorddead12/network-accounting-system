package io.lorddead.networksystem.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReader implements AutoCloseable {
    private final BufferedReader console;

    public ConsoleReader() {
        console = new BufferedReader(new InputStreamReader(System.in));
    }

    public int readCode() throws IOException, NumberFormatException {
        String code = console.readLine();
        if (code == null || code.isBlank()) {
            throw new NumberFormatException("Empty input");
        }

        return Integer.parseInt(code);
    }

    public String readFieldString() {
        try {
            return console.readLine();
        } catch (IOException e) {
            throw new RuntimeException("ERROR: Couldn't read data from the console");
        }
    }

    @Override
    public void close() throws IOException {
        console.close();
    }
}
