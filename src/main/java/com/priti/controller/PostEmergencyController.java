package com.priti.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.priti.model.UserEmergency;
import com.priti.model.Hospital;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class PostEmergencyController {
    private final Firestore db;

    public PostEmergencyController() {
        this.db = FirestoreClient.getFirestore();
    }

    public Map<String, Object> getEmergencyData(String userId) throws ExecutionException, InterruptedException {
        Map<String, Object> result = new HashMap<>();

        DocumentReference userRef = db.collection("users").document(userId);
        DocumentSnapshot userSnap = userRef.get().get();

        if (userSnap.exists()) {
            UserEmergency user = userSnap.toObject(UserEmergency.class);
            result.put("user", user);
        }

        DocumentReference addressRef = db
            .collection("users")
            .document(userId)
            .collection("postDeliveryData")
            .document("details");

        DocumentSnapshot addressSnap = addressRef.get().get();

        if (addressSnap.exists()) {
            String address = addressSnap.getString("address");
            result.put("address", address);
        } else {
            result.put("address", "Address not available");
        }

        return result;
    }

    private ObservableList<Hospital> getHospitalsByDistrict(String district) throws ExecutionException, InterruptedException {
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
