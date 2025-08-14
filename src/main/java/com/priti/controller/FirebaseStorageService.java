package com.priti.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;

public class FirebaseStorageService {

public String uploadFile(File file, String destinationPath) {
        try {
            Storage storage = StorageClient.getInstance().bucket().getStorage();
            BlobInfo blobInfo = BlobInfo.newBuilder(StorageClient.getInstance().bucket().getName(), destinationPath)
                    .setContentType("image/jpeg")
                    .build();

            storage.create(blobInfo, new FileInputStream(file));

            return String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media",
                    StorageClient.getInstance().bucket().getName(),
                    destinationPath.replace("/", "%2F"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String uploadPostDeliveryImage(File file, String userEmail) {
    try {
        String fileName = "post_delivery_images/" + userEmail + "_" + System.currentTimeMillis() + ".jpg";
        Storage storage = StorageClient.getInstance().bucket().getStorage();
        BlobInfo blobInfo = BlobInfo.newBuilder(StorageClient.getInstance().bucket().getName(), fileName)
                .setContentType("image/jpeg")
                .build();

        storage.create(blobInfo, new FileInputStream(file));

        return String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media",
                StorageClient.getInstance().bucket().getName(),
                URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()));
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}

 

    
}