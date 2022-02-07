package com.javastart.server;

import com.javastart.dataperson.Account;

import java.util.ArrayList;
import java.util.List;

public class DataBaseSingletone {
    private static DataBaseSingletone DATABASE;
    private List<Account> accountList = new ArrayList<>();

    private DataBaseSingletone(){

    }

    public static DataBaseSingletone getDATABASE() {
        if(DATABASE == null) {
            DATABASE = new DataBaseSingletone();
        }
        return DATABASE;
    }

    public void addAccountInfo(Account account) {
        accountList.add(account);
    }
}
