package com.javastart.exceptions;

public class ClientSocketIsCrashedException extends RuntimeException {
    public ClientSocketIsCrashedException(String message) {
        super(message);
    }
}
