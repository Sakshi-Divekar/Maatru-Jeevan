package com.priti.controller;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.IOException;
import java.io.InputStream;

public class FirebaseInit {
    private static boolean initialized = false;

    public static void initialize() {
        if (initialized) return;

        try {
            // Try loading from resources/firebase/ directory
            InputStream serviceAccount = FirebaseInit.class.getClassLoader()
                    .getResourceAsStream("firebase/serviceAccountKey.json");

            if (serviceAccount == null) {
                // Try alternative path if first attempt fails
                serviceAccount = FirebaseInit.class.getClassLoader()
                        .getResourceAsStream("serviceAccountKey.json");
                
                if (serviceAccount == null) {
                    throw new RuntimeException("Firebase serviceAccountKey.json not found. " +
                            "Please ensure the file is in src/main/resources/firebase/ directory.");
                }
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId("maatrutwa-setu")
                    .setStorageBucket("maatrutwa-setu.firebasestorage.app")
                    .setDatabaseUrl("https://maatrutwa-setu.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
            initialized = true;

        } catch (IOException e) {
            throw new RuntimeException("Failed to read service account key: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Firebase: " + e.getMessage());
        }
    }
}