package com.priti.controller;

import java.util.Arrays;
import java.util.List;

public class PostEmergencyAlertController {
    public void sendEmergencyAlert() {
        List<String> familyNumbers = Arrays.asList(
            "+917588798767" // Replace or load from post-delivery settings if needed
        );

        String alertMsg = "🚨 POST-DELIVERY EMERGENCY ALERT: Immediate assistance required!";

        Messenger.sendMessageToContacts(familyNumbers, alertMsg);
    }
}
