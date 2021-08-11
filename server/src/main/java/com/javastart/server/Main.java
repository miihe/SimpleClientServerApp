package com.javastart.server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(9991);
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
