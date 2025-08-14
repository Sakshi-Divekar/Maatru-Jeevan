package com.priti.controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.util.List;

public class Messenger {

    public static final String ACCOUNT_SID = "AC351a8526261fd6e4624b41fc4b5a1f4d";
    public static final String AUTH_TOKEN = "8a043e2ae348a4d50a90bcd81669e022";
    public static final String FROM_NUMBER = "+19378284871";  // Twilio number

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public static void sendMessageToContacts(List<String> phoneNumbers, String messageText) {
        for (String toNumber : phoneNumbers) {
            Message message = Message.creator(
                    new PhoneNumber(toNumber),
                    new PhoneNumber(FROM_NUMBER),
                    messageText
            ).create();

            System.out.println("Sent to " + toNumber + ": SID = " + message.getSid());
        }
    }
}