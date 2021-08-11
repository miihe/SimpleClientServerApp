package com.javastart.server;

import com.javastart.com.javastart.dataperson.Account;
import com.javastart.com.javastart.dataperson.Adjustment;
import com.javastart.com.javastart.dataperson.Payment;
import com.javastart.server.exceptions.BufferReaderNotReadyException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Класс сервера
 */
public class Server {
    private ServerSocket serverSocket;
    private BufferedReader reader;
    private ObjectInputStream inputObject;
    private ObjectOutputStream outputObject;
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        createConnection(port);
        Socket clientSocket = serverSocket.accept();
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        inputObject = new ObjectInputStream(clientSocket.getInputStream());
        outputObject = new ObjectOutputStream(clientSocket.getOutputStream());
        startListener();
    }

    private void createConnection(int port) {
        try {
            serverSocket = new ServerSocket(port, 10000);
            System.out.println("Server starts");
        } catch (IOException e) {
            System.out.println("Can't create connection");
            e.printStackTrace();
        }
    }

    private void startListener() {
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    if (reader.ready()) {
                        readCommand();
                    }
                } catch (IOException e) {
                    throw new BufferReaderNotReadyException("BufferReader not ready to read input information from client.");
                }
            }
        });
        t1.start();
    }

    private void readCommand() {
        String externalId = "";
        String message = "";
        String extraParams = "";
        int paymentOrAdjustment = 0;
        try {
            externalId = reader.readLine();
            message = reader.readLine();
            extraParams = reader.readLine();
            System.out.println(externalId + " " + message + " " + extraParams);
        } catch (IOException e) {
            System.out.println("Can't parse task, got message: " + e.getMessage());
        }
        if (externalId.equals("exit") || message.equals("exit") || extraParams.equals("exit")) {
            try {
                paymentOrAdjustment = inputObject.readInt();
            } catch (IOException e) {
                System.err.println("Client enter the incorrect number of transaction operation.");
            }
            if (paymentOrAdjustment == 1) {
                readPayment();
            } else if (paymentOrAdjustment == 2) {
                readAdjustment();
            }
        }
    }

    private void readPayment() {
        try {
            Account account = (Account) inputObject.readObject();
            Payment payment = (Payment) inputObject.readObject();
            System.out.println(payment + " by " + account);
            outputObject.writeObject("Payment is successfuly!");
            DataBaseSingletone.getDATABASE().addAccountInfo(account);
            inputObject.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Can't parse task, got message: " + e.getMessage());
        }
    }

    private void readAdjustment() {
        try {
            Account account = (Account) inputObject.readObject();
            Adjustment adjustment = (Adjustment) inputObject.readObject();
            System.out.println(adjustment + " by " + account);
            outputObject.writeObject("Adjustment is successfuly!");
            DataBaseSingletone.getDATABASE().addAccountInfo(account);
            inputObject.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Can't parse task, got message: " + e.getMessage());
        }
    }
}

