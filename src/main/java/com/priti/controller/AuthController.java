package com.priti.controller;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class AuthController {
    private final Firestore db;

    public AuthController() {
        FirebaseInit.initialize();
        this.db = FirestoreClient.getFirestore();
    }

    public boolean registerUser(String username, String email, String password, String stage) {
        try {
            // Check if user already exists
            DocumentSnapshot document = db.collection("users").document(email).get().get();
            if (document.exists()) {
                return false;
            }

            // Create user data
            Map<String, Object> userData = new HashMap<>();
            userData.put("username", username);
            userData.put("email", email);
            userData.put("password", password);
            userData.put("stage", stage);
            userData.put("createdAt", FieldValue.serverTimestamp());

            // Add to Firestore
            ApiFuture<WriteResult> future = db.collection("users").document(email).set(userData);
            future.get();
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Object> loginUser(String email, String password) {
        Map<String,Object> data=new HashMap<>();
        try {
            DocumentSnapshot document = db.collection("users").document(email).get().get();
            if (document.exists()) {
                String storedPassword = document.getString("password");
                String userEmail = document.getString("email");
                data.put("password", storedPassword);
                data.put("email", userEmail);
                if (password.equals(storedPassword)) {
                    return document.getData();
                }
            }
            return null;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updatePreDeliveryData(String userEmail, Map<String, Object> data) {
        try {
            data.put("updatedAt", FieldValue.serverTimestamp());
            ApiFuture<WriteResult> future = db.collection("users").document(userEmail)
                    .collection("preDeliveryData").document("details").set(data);
            future.get();
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Object> getPreDeliveryData(String userEmail) {
        try {
            DocumentSnapshot document = db.collection("users").document(userEmail)
                    .collection("preDeliveryData").document("details").get().get();
            return document.exists() ? document.getData() : new HashMap<>();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String uploadSonographyImage(File file, String userEmail) throws IOException {
    String fileName = "sonography/" + userEmail + "/" + UUID.randomUUID() + "_" + file.getName();
    StorageClient.getInstance().bucket().create(fileName, new FileInputStream(file), "image/jpeg");

    // Get public URL (or generate signed URL if needed)
    return "https://firebasestorage.googleapis.com/v0/b/" +
            FirebaseApp.getInstance().getOptions().getStorageBucket() +
            "/o/" + URLEncoder.encode(fileName, "UTF-8") + "?alt=media";
}

public static void saveSonographyImageUrl(String userEmail, String imageUrl) {
    Firestore db = FirestoreClient.getFirestore();
    DocumentReference docRef = db.collection("users").document(userEmail)
            .collection("preDeliveryData").document("details");

    docRef.update("sonographyImages", FieldValue.arrayUnion(imageUrl));
}


    public Map<String, Object> getUserData(String email) {
        try {
            DocumentSnapshot document = db.collection("users").document(email).get().get();
            return document.exists() ? document.getData() : null;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

public Map<String, Object> getPostDeliveryData(String email) {
    try {
        DocumentSnapshot document = db.collection("users").document(email)
                .collection("postDeliveryData").document("details").get().get();
        return document.exists() ? document.getData() : new HashMap<>();
    } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
        return null;
    }
}

public boolean updatePostDeliveryData(String userEmail, Map<String, Object> data) {
    try {
        // Get existing data first
        DocumentSnapshot document = db.collection("users").document(userEmail)
                .collection("postDeliveryData").document("details").get().get();
        
        // If document exists, preserve the existing imageUrl if not being updated
        if (document.exists() && document.contains("imageUrl") && !data.containsKey("imageUrl")) {
            data.put("imageUrl", document.getString("imageUrl"));
        }
        
        // Add/update timestamp
        data.put("updatedAt", FieldValue.serverTimestamp());
        
        // Set the data
        ApiFuture<WriteResult> future = db.collection("users").document(userEmail)
                .collection("postDeliveryData").document("details").set(data, SetOptions.merge());
        future.get();
        return true;
    } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
        return false;
    }
}





public boolean saveInsightresponseOfSonographyReport(String userEmail, String responseOfSonographyReport, String stage) {
    try {
        String collection = stage.equalsIgnoreCase("Pre-Delivery") ? "preDeliveryData" : "postDeliveryData";

        DocumentReference docRef = db.collection("users")
                                     .document(userEmail)
                                     .collection(collection)
                                     .document("details");

        Map<String, Object> update = new HashMap<>();
        update.put("responseOfSonographyReport", responseOfSonographyReport);
        update.put("updatedAt", FieldValue.serverTimestamp());

        docRef.update(update).get(); // This line applies the update
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

public boolean saveInsightresponseOfLabReport(String userEmail, String responseOfLabReport, String stage) {
    try {
        String collection = stage.equalsIgnoreCase("Pre-Delivery") ? "preDeliveryData" : "postDeliveryData";

        DocumentReference docRef = db.collection("users")
                                     .document(userEmail)
                                     .collection(collection)
                                     .document("details");

        Map<String, Object> update = new HashMap<>();
        update.put("responseOfLabReport", responseOfLabReport);
        update.put("updatedAt", FieldValue.serverTimestamp());

        docRef.update(update).get(); // Apply the update
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}



// Updated existing methods to remove profileImageUrl references:

public String getPreDeliveryImageUrl(String userEmail) {
    try {
        DocumentSnapshot document = db.collection("users")
                .document(userEmail)
                .collection("preDeliveryData")
                .document("details")
                .get()
                .get();

        if (document.exists() && document.contains("imageUrl")) {
            return document.getString("imageUrl");
        }
    } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
    }
    return null;
}

public boolean updatePreDeliveryImageUrl(String userEmail, String imageUrl) {
    try {
        DocumentReference docRef = db.collection("users").document(userEmail)
                .collection("preDeliveryData").document("details");

        Map<String, Object> update = new HashMap<>();
        update.put("imageUrl", imageUrl);
        docRef.update(update);
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

// Updated upload method for both cases:
public String uploadImage(File file, String userEmail, String stage) throws IOException {
    String folder = stage.equalsIgnoreCase("Pre-Delivery") ? "pre" : "post";
    String fileName = "profile_images/" + folder + "/" + userEmail + ".jpg";
    
    StorageClient.getInstance().bucket().create(fileName, new FileInputStream(file), "image/jpeg");
    
    return "https://firebasestorage.googleapis.com/v0/b/" +
            FirebaseApp.getInstance().getOptions().getStorageBucket() +
            "/o/" + URLEncoder.encode(fileName, "UTF-8") + "?alt=media";
}



/**
 * Gets the post-delivery profile image URL
 */
public String getPostDeliveryImageUrl(String userEmail) {
    try {
        DocumentSnapshot document = db.collection("users")
                .document(userEmail)
                .collection("postDeliveryData")
                .document("details")
                .get()
                .get();

        if (document.exists() && document.contains("imageUrl")) {
            return document.getString("imageUrl");
        }
    } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
    }
    return null;
}

/**
 * Updates the post-delivery profile image URL
 */
public boolean updatePostDeliveryImageUrl(String userEmail, String imageUrl) {
    try {
        DocumentReference docRef = db.collection("users").document(userEmail)
                .collection("postDeliveryData").document("details");

        Map<String, Object> update = new HashMap<>();
        update.put("imageUrl", imageUrl);
        update.put("updatedAt", FieldValue.serverTimestamp());
        docRef.update(update).get();
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


public String uploadPostDeliveryImage(File file, String userEmail) throws IOException {
    String fileName = "post_delivery_images/" + userEmail + "_" + System.currentTimeMillis() + ".jpg";
    StorageClient.getInstance().bucket().create(fileName, new FileInputStream(file), "image/jpeg");
    
    return String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media",
            StorageClient.getInstance().bucket().getName(),
            URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()));
}


public boolean saveLabReport(String userEmail, String fileName, 
                           String downloadUrl, String insight, 
                           String stage) {
    try {
        String collection = stage.equalsIgnoreCase("Pre-Delivery") ? "preDeliveryData" : "postDeliveryData";
        
        DocumentReference docRef = db.collection("users")
                                   .document(userEmail)
                                   .collection(collection)
                                   .document("details");

        // Create or update the labReports array
        Map<String, Object> updates = new HashMap<>();
        updates.put("labReports", FieldValue.arrayUnion(
            Map.of(
                "fileName", fileName,
                "downloadUrl", downloadUrl,
                "insight", insight,
                "uploadedAt", FieldValue.serverTimestamp()
            )
        ));

        docRef.update(updates).get();
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


public boolean addLabReport(String userEmail, Map<String, Object> reportData, String stage) {
    try {
        String collection = stage.equalsIgnoreCase("Pre-Delivery") ? "preDeliveryData" : "postDeliveryData";
        DocumentReference docRef = db.collection("users")
                                   .document(userEmail)
                                   .collection(collection)
                                   .document("details");

        // Create a nested map for lab reports if it doesn't exist
        Map<String, Object> update = new HashMap<>();
        update.put("labReports." + reportData.get("fileName"), reportData);
        update.put("updatedAt", FieldValue.serverTimestamp());

        docRef.update(update).get();
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

public void saveLabReportImageUrl(String userEmail, String imageUrl, String stage) {
    try {
        String collection = stage.equalsIgnoreCase("Pre-Delivery") ? "preDeliveryData" : "postDeliveryData";

        DocumentReference docRef = db.collection("users")
                .document(userEmail)
                .collection(collection)
                .document("details");

        Map<String, Object> update = new HashMap<>();
        update.put("labReportImages", FieldValue.arrayUnion(imageUrl));
        update.put("updatedAt", FieldValue.serverTimestamp());

        docRef.update(update).get();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}

