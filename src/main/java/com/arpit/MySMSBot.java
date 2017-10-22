package com.arpit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class MySMSBot extends TelegramLongPollingBot {
    public SendSMS sendSMS;
    static Logger log = LoggerFactory.getLogger(MySMSBot.class.getName());

    MySMSBot() {
        super();
        log.info("Creating SMS Instance");
        sendSMS = new SendSMS();
        if (sendSMS.selectedPort == null) {
            log.info("Port not recognized");
            System.exit(1);
        }
        log.info("Port Desc : " + sendSMS.selectedPort.getDescriptivePortName());
        log.info("Port Number : " + sendSMS.selectedPort.getSystemPortName());
    }

    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            if (checkSpecial(message_text, chat_id))
                return;

            send("Processing with amount " + Database.getAmount(), chat_id);

            log.info("Recieved " + message_text);

            message_text = "RC 91" + message_text + " " + Database.getAmount() + " " + Database.getPin();

            boolean result = sendSMS.sendOne(Database.getDest(), message_text);

            if (result) {
                send("Done", chat_id);

            } else {
                send("Some error occured", chat_id);

            }
        }
    }


    public String getBotUsername() {
        return "Arpit_SMS_Bot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "412386556:AAHawYQpPaNLFdnpckQsYr7c3YdBQZEXy0I";
    }

    public void send(String message_text, long chat_id) {
        SendMessage message = new SendMessage()
                .setChatId(chat_id)
                .setText(message_text);
        try {
            sendMessage(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public boolean checkSpecial(String str, long chat_id) {
        StringBuilder builder = new StringBuilder(str);


        if (str.contains("setAmount=") && str.substring("setAmount=".length() - 1, str.length()).length() > 0) {

            Database.setAmount(builder.substring(builder.indexOf("=") + 1, builder.length()));

            send("Amount=" + Database.getAmount(), chat_id);

        } else if (str.contains("setPin=") && str.substring("setPin=".length() - 1, str.length()).length() > 0) {
            Database.setPin(builder.substring(builder.indexOf("=") + 1, builder.length()));
            send("Pin=" + Database.getPin(), chat_id);
        } else if (str.contains("setDest=") && str.substring("setDest=".length() - 1, str.length()).length() > 0) {
            Database.setDest(builder.substring(builder.indexOf("=") + 1, builder.length()));
            send("Dest=" + Database.getDest(), chat_id);
        } else if (str.contains("getAmount") && str.length() == "getAmount".length()) {
            send("Amount=" + Database.getAmount(), chat_id);
        } else if (str.contains("getPin") && str.length() == "getPin".length()) {
            send("Pin=" + Database.getPin(), chat_id);
        } else if (str.contains("getDest") && str.length() == "getDest".length()) {
            send("Dest=" + Database.getDest(), chat_id);
        } else
            return false;

        return true;
    }
}