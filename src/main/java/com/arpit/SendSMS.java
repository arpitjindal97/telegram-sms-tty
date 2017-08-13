package com.arpit;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by arpit on 8/6/17.
 */
public class SendSMS
{
    SerialPort selectedPort;

    SendSMS()
    {
        SerialPort[] list = SerialPort.getCommPorts();

        for (int i = 0; i < list.length; i++)
        {
            selectedPort = list[i];
            if (Testing.test(selectedPort))
                return;
        }
        selectedPort = null;
    }

    SendSMS(SerialPort selectedPort)
    {
        this.selectedPort = selectedPort;
    }

    public boolean sendOne(String num,String message)
    {

        selectedPort.openPort();

        //String message = (String)newJFrame.jTable1.getValueAt(i,2);
        if (num.equals("") || message.equals(""))
            return false;
        boolean result = sendThis(num, message);

        selectedPort.closePort();
        if (result)
        {
            return true;
        } else
        {
            return false;
        }


    }

    boolean sendThis(String num, String message)
    {
        try
        {
            InputStream inputStream = selectedPort.getInputStream();
            OutputStream outputStream = selectedPort.getOutputStream();
            String str = "AT+CSCS=\"GSM\"" + (char) 13;
            outputStream.write(str.getBytes());
            //System.out.println(str);

            if (!waitForOK(inputStream))
                return false;

            str = "AT+CMGF=1" + (char) 13;
            outputStream.write(str.getBytes());
            //System.out.println(str);

            if (!waitForOK(inputStream))
                return false;

            str = "AT+CMGS=\"" + num + "\"" + (char) 13;
            outputStream.write(str.getBytes());
            //System.out.println(str);
            Thread.sleep(500);
            str = message + (char) 26 + (char) 26;
            outputStream.write(str.getBytes());
            //System.out.println(str);

            if (!waitForOK(inputStream))
                return false;

        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    boolean waitForOK(InputStream inputStream) throws IOException
    {
        String str = "";
        int time = 30000;
        while (time > 0 && (!str.contains("OK")))
        {
            //while (selectedPort.bytesAvailable() == 0)
            try
            {
                Thread.sleep(500);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            //System.out.println("reached here");

            while (selectedPort.bytesAvailable() != 0)
            {
                char ch = (char) inputStream.read();
                str += ch;

            }
            System.out.print(str);
            time -= 500;
        }
        return str.contains("OK");
    }

}
