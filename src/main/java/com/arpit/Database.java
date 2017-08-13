package com.arpit;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

public class Database
{
    static String amt_username, amt_password;

    static String mqtt_broker, mqtt_username, mqtt_password, mqtt_port, mqtt_topic;

    static String amount, pin, dest;

    static String path;

    public void setAmt_username(String amt_username)
    {
        Database.amt_username = amt_username;
    }

    public void setAmt_password(String amt_password)
    {
        Database.amt_password = URLEncoder.encode(amt_password);
        Database.amt_password = amt_password;
    }

    public void setMqtt_broker(String mqtt_broker)
    {
        Database.mqtt_broker = mqtt_broker;
    }

    public void setMqtt_username(String mqtt_username)
    {
        Database.mqtt_username = mqtt_username;
    }

    public void setMqtt_password(String mqtt_password)
    {
        Database.mqtt_password = mqtt_password;
    }

    public void setMqtt_port(String mqtt_port)
    {
        Database.mqtt_port = mqtt_port;
    }

    public void setMqtt_topic(String mqtt_topic)
    {
        Database.mqtt_topic = mqtt_topic;
    }

    public void setAmount(String amount)
    {
        Database.amount = amount;
    }

    public void setPin(String pin)
    {
        Database.pin = pin;
    }

    public void setDest(String dest)
    {
        Database.dest = dest;
    }

    public String getAmt_username()
    {
        return amt_username;
    }

    public String getAmt_password()
    {
        return amt_password;
    }

    public String getMqtt_broker()
    {
        return mqtt_broker;
    }

    public String getMqtt_username()
    {
        return mqtt_username;
    }

    public String getMqtt_password()
    {
        return mqtt_password;
    }

    public String getMqtt_port()
    {
        return mqtt_port;
    }

    public String getMqtt_topic()
    {
        return mqtt_topic;
    }

    public String getAmount()
    {
        return amount;
    }

    public String getPin()
    {
        return pin;
    }

    public String getDest()
    {
        return dest;
    }


    public static void getDetails(String path) throws IOException
    {

        ObjectMapper objectMapper = new ObjectMapper();

        if(path == null)
            path="";

        Database.path = path;

        Database database = objectMapper.readValue(new File(path+"database.json"), Database.class);

        String str = objectMapper.writeValueAsString(database);
        //System.out.println(str);

    }

    public static void setDetails()
    {
        try
        {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(Database.path+"database.json"), new Database());
        } catch (Exception e)
        {
        }
    }
}
