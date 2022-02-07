package com.javastart;

import com.javastart.dataperson.*;
import com.javastart.dataperson.Adjustment;
import com.javastart.dataperson.Payment;
import com.javastart.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Это клиент для отправки сообщений на приложение сервера, пример операции оплаты и пополнения счета
 */
public class Client {
    private final Logger log = LoggerFactory.getLogger(Client.class);
    private final DataOutputStream toServer;
    private final ObjectOutputStream toServerObject;
    private final ObjectInputStream fromServer;
    protected boolean changeFunction = true;
    Scanner scanner = new Scanner(System.in);

    public Client(int port) {
        try {
            Socket socket = new Socket("localhost", port);
            toServer = new DataOutputStream(socket.getOutputStream());
            toServerObject = new ObjectOutputStream(socket.getOutputStream());
            fromServer = new ObjectInputStream(socket.getInputStream());
            log.info("Created the client-socket with properties: " + socket);
        } catch (IOException e) {
            throw new ClientSocketIsCrashedException("Error when creating clients socket, or client-server connection streams.");
        }

    }

    public void sendNotification(Long external_id, String message, String extra_params) throws IOException {
        toServer.writeBytes(external_id + "\n" + message + "\n" + extra_params + "\n");
        log.info("Method sendNotification is send message.");
    }

    public void writeNotification() {
        try {
            log.info("Start writeNotification method...");
            System.out.println("Enter id: ");
            String externalId = scanner.next();
            System.out.println("Enter the message: ");
            String message = scanner.next();
            System.out.println("Enter extra params: ");
            String extraParams = scanner.next();
            if (externalId.equals("exit") || message.equals("exit") || extraParams.equals("exit")) {
                changeFunction = false;
            }
            toServer.writeBytes(externalId + "\n" + message + "\n" + extraParams + "\n");
            log.info("Message from client has been sent to the server.");
        } catch (IOException e) {
            throw new ClientSocketIsCrashedException("Error when transmitting data (messages) to the server.");
        }
    }

    public void transactionService(Account account) {
        try {
            int paymentOrAdjustment;
            do {
                System.out.println("Please, enter the number of operation: \n 1. Pay \n 2. Adjustment");
                if (!scanner.hasNextInt()) {
                    throw new IncorrectException("It was entered not a number. Enter the operation number: 1 - pay, 2 - adjustment.");
                }
                paymentOrAdjustment = scanner.nextInt();
            } while (paymentOrAdjustment < 1 | paymentOrAdjustment > 2);
            toServerObject.writeInt(paymentOrAdjustment);
            if (paymentOrAdjustment == 1) {
                Payment payment = new Payment();
                do {
                    System.out.println("Please, enter the sum of payment: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Please, enter the correct sum of your payment:");
                        scanner.next();
                    }
                    payment.setPay(scanner.nextInt());
                } while (payment.getPay() <= 0);
                account.getBill().setAmount(account.getBill().getAmount() - payment.getPay());
                toServerObject.writeObject(account);
                toServerObject.writeObject(payment);
                String statusOfTransaction = (String) fromServer.readObject();
                System.out.println(statusOfTransaction);
            } else {
                Adjustment adjustment = new Adjustment();
                do {
                    System.out.println("Please, enter the sum of adjustment: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Please, enter the correct sum of your adjustment:");
                        scanner.next();
                    }
                    adjustment.setAdjustment(scanner.nextInt());
                } while (adjustment.getAdjustment() <= 0);
                account.getBill().setAmount(account.getBill().getAmount() + adjustment.getAdjustment());
                toServerObject.writeObject(account);
                toServerObject.writeObject(adjustment);
                String statusOfTransaction = (String) fromServer.readObject();
                System.out.println(statusOfTransaction);
            }
            toServerObject.flush();
            toServerObject.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new ClientSocketIsCrashedException("Error when transmitting payment or adjustment data, or client-server connection streams.");
        }
    }
}
