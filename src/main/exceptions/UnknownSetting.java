package main.exceptions;

public class UnknownSetting extends RuntimeException {
    public UnknownSetting(final String message) {
        super(message);
    }
}
