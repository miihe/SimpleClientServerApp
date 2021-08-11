package com.javastart;

import com.javastart.com.javastart.dataperson.Account;
import com.javastart.com.javastart.dataperson.Bill;
import com.javastart.exceptions.FailureOfSendNotificationMethodException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Client client = new Client(9991);

        Account account = new Account();
        account.setName("Troye");
        Bill bill = new Bill(200);
        account.setBill(bill);

        try {
            client.sendNotification(1L, "CHECK-1", "params1");
            client.sendNotification(2L, "CHECK-2", "params2");
        } catch (IOException e) {
            throw new FailureOfSendNotificationMethodException("Some problems with writing and sending data from client to server.");
        }

        while (client.changeFunction) {
            client.writeNotification();
        }
        client.transactionService(account);
    }
}
