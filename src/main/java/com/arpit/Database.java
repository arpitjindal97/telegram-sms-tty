package com.arpit;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Database {

    public String amount, pin, dest;

    static String path;


    public static void setAmount(String amount) {

        Database database = getDetails(Database.path);
        database.amount = amount;
        setDetails(database);
    }

    public static void setPin(String pin) {
        Database database = getDetails(Database.path);
        database.pin = pin;
        setDetails(database);
    }

    public static void setDest(String dest) {
        Database database = getDetails(Database.path);
        database.dest = dest;
        setDetails(database);
    }

    public static String getAmount() {
        return getDetails(Database.path).amount;
    }

    public static String getPin() {
        return getDetails(Database.path).pin;
    }

    public static String getDest() {
        return getDetails(Database.path).dest;
    }


    public static Database getDetails(String path) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            if (path == null)
                path = "";

            Database.path = path;

            Database database = objectMapper.readValue(new File(path + "database.json"), Database.class);


            String str = objectMapper.writeValueAsString(database);
            //System.out.println(str);
            return database;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setDetails(Database database) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(Database.path + "database.json"), database);
        } catch (Exception e) {
        }
    }
}
