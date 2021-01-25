package main.exceptions;

public class SettingsNotFound extends RuntimeException {
    public SettingsNotFound(final String message) {
        super(message);
    }
}
