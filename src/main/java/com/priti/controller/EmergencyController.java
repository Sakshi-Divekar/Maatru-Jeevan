package com.priti.controller;

import com.priti.model.UserEmergency;
import com.priti.model.Hospital;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class EmergencyController {
    private final Firestore db;

    public EmergencyController() {
        this.db = FirestoreClient.getFirestore();
    }

    public Map<String, Object> getEmergencyData(String userId) throws ExecutionException, InterruptedException {
    Map<String, Object> result = new HashMap<>();

    Firestore db = FirestoreClient.getFirestore();

    // 1. Get UserEmergency from "users" collection
    DocumentReference userRef = db.collection("users").document("sneha1@gmail.com");
    DocumentSnapshot userSnap = userRef.get().get();

    if (userSnap.exists()) {
        UserEmergency user = userSnap.toObject(UserEmergency.class);
        result.put("user", user);
    }

    // 2. Get address from preDelivery -> details
    DocumentReference addressRef = db
        .collection("users")
        .document("sneha1@gmail.com")
        .collection("preDeliveryData")
        .document("details"); // or use dynamic ID if needed

    DocumentSnapshot addressSnap = addressRef.get().get();

    if (addressSnap.exists()) {
        String address = addressSnap.getString("address");
        result.put("address", address);  // <- Plain string
    } else {
        result.put("address", "Address not available");
    }

    return result;
}



    private UserEmergency getUserData(String userId) throws ExecutionException, InterruptedException {
        System.out.println("Fetching user data for userId: " + userId);


        DocumentReference docRef = db.collection("users").document(userId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        
        if (document.exists()) {
            return document.toObject(UserEmergency.class);
        }
        return null;
    }

    private ObservableList<Hospital> getHospitalsByDistrict(String district) throws ExecutionException, InterruptedException {
        System.out.println("Querying hospitals in district: " + district);
        ObservableList<Hospital> hospitals = FXCollections.observableArrayList();
        
        Query query = db.collection("hospitals")
                        .whereEqualTo("address", district)
                        .orderBy("name");
        
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            Hospital hospital = document.toObject(Hospital.class);
            hospital.setId(document.getId());
            hospitals.add(hospital);
        }
        
        return hospitals;
    }
}