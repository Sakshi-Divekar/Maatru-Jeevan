package com.priti.controller;

import com.google.gson.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeminiVitalsInsightController {
    private static final String API_KEY = "AIzaSyAxiDkHNG1G3DbAlJT-cwFRkIZjDsg3Bpc";
    private static final String API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;

    public static String getInsightsFromLab(String labReport) {
        return getInsightsFromText("From the following LAB report, summarize pregnancy-related vitals in bullet points:\n" + labReport);
    }

    public static String getInsightsFromSonography(String sonographyReport) {
        return getInsightsFromText("From the following SONOGRAPHY report, extract key fetal and maternal observations in bullet points:\n" + sonographyReport);
    }

    private static String getInsightsFromText(String promptText) {
        try {
            String payload = buildTextPayload(promptText);
            String rawResponse = sendPostRequest(payload);
            return parseInsight(rawResponse);
        } catch (Exception e) {
            return "AI Processing Error: " + e.getMessage();
        }
    }

    private static String buildTextPayload(String reportData) {
        return "{\n" +
                "  \"contents\": [\n" +
                "    {\n" +
                "      \"parts\": [\n" +
                "        {\n" +
                "          \"text\": \"" + reportData.replace("\"", "'") + "\"\n" +
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
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
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

    private static String parseInsight(String json) {
        try {
            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
            JsonArray candidates = obj.getAsJsonArray("candidates");
            if (candidates != null && candidates.size() > 0) {
                JsonObject content = candidates.get(0).getAsJsonObject().getAsJsonObject("content");
                JsonArray parts = content.getAsJsonArray("parts");
                return parts.get(0).getAsJsonObject().get("text").getAsString();
            }
        } catch (Exception e) {
            return "Parsing Error: " + e.getMessage();
        }
        return "No insights available.";
    }
}
