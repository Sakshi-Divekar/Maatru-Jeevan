package com.priti.controller;

import com.google.gson.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class LabInsightController {
    private static final String API_KEY = "AIzaSyAxiDkHNG1G3DbAlJT-cwFRkIZjDsg3Bpc";
    private static final String API_URL =
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;

    public static String getLabInsights(File imageFile) throws IOException {
        String base64Image = encodeImageToBase64(imageFile);
        String payload = buildJsonPayload(base64Image);
        String responseOfLabReport = sendPostRequest(payload);
        return parseInsights(responseOfLabReport);
    }

    private static String encodeImageToBase64(File imageFile) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    private static String buildJsonPayload(String base64Image) {
        return "{\n" +
                "  \"contents\": [\n" +
                "    {\n" +
                "      \"parts\": [\n" +
                "        {\n" +
                "          \"inline_data\": {\n" +
                "            \"mime_type\": \"image/jpeg\",\n" +
                "            \"data\": \"" + base64Image + "\"\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"text\": \"Analyze this lab report. Extract test names, result values, reference ranges, and any abnormal findings in bullet points.\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    private static String sendPostRequest(String jsonInput) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonInput.getBytes("utf-8"));
        }

        InputStream is = conn.getResponseCode() < 400 ? conn.getInputStream() : conn.getErrorStream();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    private static String parseInsights(String responseOfLabReport) {
        try {
            JsonObject json = JsonParser.parseString(responseOfLabReport).getAsJsonObject();
            JsonArray candidates = json.getAsJsonArray("candidates");
            if (candidates != null && candidates.size() > 0) {
                JsonObject content = candidates.get(0).getAsJsonObject().getAsJsonObject("content");
                JsonArray parts = content.getAsJsonArray("parts");
                return parts.get(0).getAsJsonObject().get("text").getAsString();
            } else {
                return "No insights found.";
            }
        } catch (Exception e) {
            return "API Error: " + e.getMessage();
        }
    }
}
