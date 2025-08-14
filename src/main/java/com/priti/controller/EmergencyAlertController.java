package com.priti.controller;

import java.util.Arrays;
import java.util.List;

public class EmergencyAlertController {
    public void sendEmergencyAlert() {
        List<String> familyNumbers = Arrays.asList(
            "+917588798767" // Replace with actual emergency contacts
        );

        String alertMsg = "\uD83D\uDEA8 EMERGENCY ALERT: I need help. Please reach out immediately!";

        Messenger.sendMessageToContacts(familyNumbers, alertMsg);
    }
}

