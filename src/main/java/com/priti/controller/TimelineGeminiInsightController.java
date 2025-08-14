package com.priti.controller;

import com.google.gson.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class TimelineGeminiInsightController {

    private static final String API_KEY = "AIzaSyAxiDkHNG1G3DbAlJT-cwFRkIZjDsg3Bpc";
    private static final String API_URL = 
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;

    public static String getInsights(File imageFile) throws IOException {
        String base64Image = encodeImageToBase64(imageFile);
        String payload = buildJsonPayload(base64Image);
        return sendPostRequest(payload);
    }

    public static String getPregnancyInsights(LocalDate startDate) {
        try {
            long daysPregnant = java.time.temporal.ChronoUnit.DAYS.between(startDate, LocalDate.now());
            int weeks = (int) (daysPregnant / 7);
            int days = (int) (daysPregnant % 7);
            
            String trimester = getTrimesterName(weeks);
            String payload = buildPregnancyPayload(weeks, days, trimester);
            return sendPostRequest(payload);
        } catch (Exception e) {
            return "Error generating pregnancy insights: " + e.getMessage();
        }
    }

    public static String getTrimesterInfo(String trimester, LocalDate startDate, LocalDate endDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
            String dateRange = formatter.format(startDate) + " to " + formatter.format(endDate);
            String payload = buildTrimesterPayload(trimester, dateRange);
            return sendPostRequest(payload);
        } catch (Exception e) {
            return getDefaultTrimesterInfo(trimester, startDate, endDate);
        }
    }

    public static String getDailyInsights(int weeks, int days) {
        try {
            String payload = buildDailyInsightsPayload(weeks, days);
            return sendPostRequest(payload);
        } catch (Exception e) {
            return getDefaultDailyInsights(weeks, days);
        }
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

        int responseCode = conn.getResponseCode();
        if (responseCode >= 400) {
            throw new IOException("API request failed with response code: " + responseCode);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            return parseApiResponse(response.toString());
        }
    }

    private static String parseApiResponse(String response) {
        try {
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            JsonArray candidates = json.getAsJsonArray("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                JsonObject content = candidates.get(0).getAsJsonObject().getAsJsonObject("content");
                JsonArray parts = content.getAsJsonArray("parts");
                return parts.get(0).getAsJsonObject().get("text").getAsString();
            }
            return "No content found in API response";
        } catch (Exception e) {
            return "Error parsing API response: " + e.getMessage();
        }
    }

    private static String encodeImageToBase64(File imageFile) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    private static String getTrimesterName(int weeks) {
        if (weeks < 13) return "First Trimester";
        else if (weeks < 27) return "Second Trimester";
        else return "Third Trimester";
    }

    private static String buildJsonPayload(String base64Image) {
        return String.format("{\"contents\":[{\"parts\":[{\"inline_data\":{\"mime_type\":\"image/jpeg\",\"data\":\"%s\"}},"
            + "{\"text\":\"Extract Expected Delivery Date (EDD), Gestational Age (GA), Fetal Heart Rate (FHR), "
            + "and other key pregnancy-related insights from this medical report image in clear bullet points.\"}]}]}", 
            base64Image);
    }

    private static String buildPregnancyPayload(int weeks, int days, String trimester) {
        return String.format("{\"contents\":[{\"parts\":[{\"text\":\"Generate personalized pregnancy insights for "
            + "a woman who is %d weeks and %d days pregnant, currently in the %s. Provide developmental milestones "
            + "for the baby, health tips for the mother, and things to watch out for. Format as clear bullet points.\"}]}]}",
            weeks, days, trimester);
    }

    private static String buildTrimesterPayload(String trimester, String dateRange) {
        return String.format("{\"contents\":[{\"parts\":[{\"text\":\"Generate detailed information about %s of pregnancy "
            + "(dates: %s). Include baby development milestones, maternal changes, recommended tests, nutrition advice, "
            + "common symptoms, and precautions. Format as clear bullet points with emojis for better readability.\"}]}]}",
            trimester, dateRange);
    }

    private static String buildDailyInsightsPayload(int weeks, int days) {
        return String.format("{\"contents\":[{\"parts\":[{\"text\":\"Generate personalized daily pregnancy insights for "
            + "a woman who is %d weeks and %d days pregnant. Include baby development milestones, health tips for the "
            + "mother, and things to watch out for today. Format as clear bullet points with emojis.\"}]}]}",
            weeks, days);
    }

    private static String getDefaultTrimesterInfo(String trimester, LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String dateRange = formatter.format(startDate) + " to " + formatter.format(endDate);
        
        switch (trimester) {
            case "First Trimester":
                return "First Trimester (" + dateRange + ")\n\n" +
                       "• Baby Development: 🧬 Major organs begin forming\n" +
                       "• Maternal Changes: 🤢 Morning sickness common\n" +
                       "• Tests: 🩺 First ultrasound, blood tests\n" +
                       "• Nutrition: 💊 Folic acid is crucial\n" +
                       "• Symptoms: 😴 Fatigue, breast tenderness\n" +
                       "• Precautions: 🚭 Avoid alcohol/smoking";
                        
            case "Second Trimester":
                return "Second Trimester (" + dateRange + ")\n\n" +
                       "• Baby Development: 👶 Can hear sounds, grows rapidly\n" +
                       "• Maternal Changes: 🤰 Baby bump becomes visible\n" +
                       "• Tests: 📊 Glucose screening, anatomy scan\n" +
                       "• Nutrition: 🥦 Iron-rich foods important\n" +
                       "• Symptoms: 🤗 Energy returns, backaches\n" +
                       "• Precautions: 🏊‍♀️ Safe exercise recommended";
                        
            case "Third Trimester":
                return "Third Trimester (" + dateRange + ")\n\n" +
                       "• Baby Development: 🧠 Brain develops rapidly\n" +
                       "• Maternal Changes: 😫 Increased discomfort\n" +
                       "• Tests: 🩺 Group B strep test, NSTs\n" +
                       "• Nutrition: 🥛 Calcium for bone development\n" +
                       "• Symptoms: 💤 Trouble sleeping, swelling\n" +
                       "• Precautions: 🚨 Watch for preeclampsia signs";
                        
            default:
                return "Trimester information not available";
        }
    }

    private static String getDefaultDailyInsights(int weeks, int days) {
        if (weeks < 13) {
            return "First Trimester Daily Tips:\n" +
                   "• 🧬 Baby's neural tube is forming\n" +
                   "• 🤢 Try ginger for nausea relief\n" +
                   "• 💤 Rest when you feel tired\n" +
                   "• 💧 Stay well hydrated";
        } else if (weeks < 27) {
            return "Second Trimester Daily Tips:\n" +
                   "• 👶 Baby can now hear your voice\n" +
                   "• 🤰 You may feel baby's first kicks\n" +
                   "• 🥦 Eat plenty of leafy greens\n" +
                   "• 🚶‍♀️ Take short walks daily";
        } else {
            return "Third Trimester Daily Tips:\n" +
                   "• 🧠 Baby's brain is developing rapidly\n" +
                   "• 😫 Practice relaxation techniques\n" +
                   "• 🥛 Increase calcium intake\n" +
                   "• 📝 Start preparing for delivery";
        }
    }
}