package io.lorddead.networksystem.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReader {
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

    public long readId() {
        try {
            return readCode();
        } catch (NumberFormatException e) {
            return -1;
        } catch (IOException e) {
            throw new RuntimeException("ERROR: Couldn't read data from the console", e);
        }
    }

    public String readFieldString() {
        try {
            return console.readLine();
        } catch (IOException e) {
            throw new RuntimeException("ERROR: Couldn't read data from the console", e);
        }
    }
}
