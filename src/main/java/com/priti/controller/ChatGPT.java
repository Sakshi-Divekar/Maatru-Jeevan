package com.priti.controller;
import com.priti.controller.ChatGPT;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class ChatGPT {
    static final String API_KEY = "AIzaSyAxiDkHNG1G3DbAlJT-cwFRkIZjDsg3Bpc";
    
    // System prompt for maternal and child care specialization
    private static final String SYSTEM_PROMPT = "You are a knowledgeable and compassionate maternal and child care assistant. " +
            "Provide accurate, medically sound advice for pregnancy, newborn care, toddler development, " +
            "and parenting. Always recommend consulting a healthcare professional for serious concerns. " +
            "Keep responses clear, supportive, and tailored to the user's needs.";
    
    public static String getAIResponse(String userInput) {
        try {
            System.setProperty("https.protocols", "TLSv1.2");

            URL url = new URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            // Enhanced prompt with system context
             String jsonInput = "{\n" +
                    "  \"contents\": [\n" +
                    "    {\n" +
                    "      \"parts\": [\n" +
                    "        {\n" +
                    "          \"text\": \"" + userInput.replace("\"", "\\\"") + "\"\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";


            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes("utf-8"));
            }
            System.out.println(conn.getResponseCode()+"==================");
            InputStream is = (conn.getResponseCode() == 200)
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }

            JSONObject obj = new JSONObject(response.toString());

            if (obj.has("error")) {
                JSONObject err = obj.getJSONObject("error");
                return "Error: " + err.getString("message");
            }

            JSONArray candidates = obj.getJSONArray("candidates");
            JSONObject firstCandidate = candidates.getJSONObject(0);
            JSONObject content = firstCandidate.getJSONObject("content");
            JSONArray parts = content.getJSONArray("parts");
            JSONObject firstPart = parts.getJSONObject(0);

            return firstPart.getString("text").trim();

        } catch (Exception e) {

            System.out.println("in Excep AI");
            e.printStackTrace();
            return "I'm having trouble answering right now. Please try again later or consult your healthcare provider for urgent matters.";
        }
    }
}