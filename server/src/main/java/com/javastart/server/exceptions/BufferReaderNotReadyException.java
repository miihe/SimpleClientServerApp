package com.javastart.server.exceptions;

public class BufferReaderNotReadyException extends RuntimeException {
    public BufferReaderNotReadyException(String message) {
        super(message);
    }
}
