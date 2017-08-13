package com.arpit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class Main
{

    static Logger log = LoggerFactory.getLogger(Main.class.getName());

    public static void main(String[] args)
    {

        RuntimeMXBean rt = ManagementFactory.getRuntimeMXBean();
        String pid = rt.getName();
        MDC.put("PID", pid.substring(0, pid.indexOf("@")));

        log.info("Starting bot");

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        log.info("Registering Bots");
        try
        {
            botsApi.registerBot(new MySMSBot());
        } catch (TelegramApiException e)
        {
            e.printStackTrace();
        }

        log.info("Fetching details from database.json");
        try
        {
            Database.getDetails(args[0]);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        //WebController.startServer();
    }
}