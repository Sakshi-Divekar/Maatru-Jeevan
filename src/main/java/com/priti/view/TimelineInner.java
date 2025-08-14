package com.priti.view;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.priti.controller.TimelineController;
import com.priti.controller.TimelineGeminiInsightController;
import com.priti.utils.UserSession;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.concurrent.Executors;

public class TimelineInner {
    private final Stage stage;
    private TimelineController controller;
    private GridPane calendarGrid;
    private YearMonth currentMonth;
    private LocalDate pregnancyStartDate;
    private Label startDateLabel;
    private Label monthYearLabel;
    private VBox calendarContainer;
    private Label yearLabel;
    private final LocalDate today = LocalDate.now();
    private VBox infoPanel;
    private TextArea dailyInsightsArea;
    private Label pregnancyDayLabel;
    private Label weekInfoLabel;
    private Label trimesterLabel;
    private Label insightsLabel;

    public TimelineInner(Stage stage, TimelineController controller) {
        this.stage = stage;
        this.controller = controller;
        this.currentMonth = YearMonth.now();
        this.pregnancyStartDate = controller.getStartDate();
    }

    private void fetchPregnancyStartDateFromFirebase() {
        String userEmail = UserSession.getUserEmail();
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("users")
                .document(userEmail)
                .collection("preDeliveryData")
                .document("details");
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                DocumentSnapshot snapshot = docRef.get().get();
                if (snapshot.exists() && snapshot.contains("pregnancyStartDate") && snapshot.contains("dueDate")) {
                    String startDateStr = snapshot.getString("pregnancyStartDate");
                    String dueDateStr = snapshot.getString("dueDate");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate fetchedStartDate = LocalDate.parse(startDateStr, formatter);
                    LocalDate fetchedDueDate = LocalDate.parse(dueDateStr, formatter);
                    Platform.runLater(() -> {
                        this.pregnancyStartDate = fetchedStartDate;
                        controller.updatePregnancyStartDate(fetchedStartDate);
                        controller.setDueDate(fetchedDueDate);
                        startDateLabel.setText("Pregnancy Start Date: " + pregnancyStartDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
                        updateCalendar();
                        updatePregnancyInfoPanel();
                    });
                } else {
                    Platform.runLater(() -> {
                        this.pregnancyStartDate = LocalDate.now();
                        controller.updatePregnancyStartDate(pregnancyStartDate);
                        startDateLabel.setText("Pregnancy Start Date: " + pregnancyStartDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
                        updateCalendar();
                        updatePregnancyInfoPanel();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    startDateLabel.setText("Failed to load start date");
                    this.pregnancyStartDate = LocalDate.now();
                    controller.updatePregnancyStartDate(pregnancyStartDate);
                });
            }
        });
    }

    public Scene getScene() {
        VBox root = new VBox();
        root.setSpacing(24);
        root.setPadding(new Insets(24, 40, 30, 40));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #F5F5F5 0%, #E8F5E9 100%);");

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: linear-gradient(to right, #F5F5F5 0%, #F5F5F5 0%, #E8F5E9 100%);");
        topBar.setPadding(new Insets(10, 20, 10, 20));

        Button backButton = new Button("← Back");
        backButton.setStyle("-fx-font-family: 'Poppins'; " +
                          "-fx-font-weight: bold; " +
                          "-fx-font-size: 16px; " +
                          "-fx-background-color: #ef9dddff; " +
                          "-fx-text-fill: black; " +
                          "-fx-background-radius: 20;");
        backButton.setPrefHeight(44);
        backButton.setPrefWidth(110);
        backButton.setOnAction(e -> {
            PregnancyMonitoringPage previousPage = new PregnancyMonitoringPage(stage);
            stage.setScene(previousPage.getScene());
        });
        topBar.getChildren().add(backButton);

        VBox dateInputSection = buildStartDateSection();
        dateInputSection.setMaxWidth(400);

        HBox mainContent = new HBox();
        mainContent.setSpacing(40);
        mainContent.setAlignment(Pos.TOP_LEFT);

        VBox monthsList = buildMonthsList();
        calendarContainer = buildCalendarContainer();
        infoPanel = buildPregnancyInfoPanel();

        mainContent.getChildren().addAll(monthsList, calendarContainer, infoPanel);

        root.getChildren().addAll(topBar, dateInputSection, mainContent);

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        return new Scene(scrollPane, 1550, 800);
    }

    private VBox buildCalendarContainer() {
        VBox calendarBox = new VBox(16);
        calendarBox.setPadding(new Insets(14, 18, 18, 18));
        calendarBox.setStyle("-fx-background-color: linear-gradient(to bottom, #E3F2FD 0%, #BBDEFB 100%); " +
                "-fx-border-radius: 18; -fx-background-radius: 18; " +
                "-fx-border-color: #64B5F6; -fx-border-width: 2;");
        calendarBox.setPrefWidth(800);
        calendarBox.setAlignment(Pos.TOP_CENTER);

        monthYearLabel = new Label();
        monthYearLabel.setStyle("-fx-font-family: 'Poppins'; " +
                              "-fx-font-weight: bold; " +
                              "-fx-font-size: 22px; " +
                              "-fx-text-fill: black;");
        monthYearLabel.setAlignment(Pos.CENTER);
        monthYearLabel.setMaxWidth(Double.MAX_VALUE);

        calendarGrid = new GridPane();
        calendarGrid.setHgap(11);
        calendarGrid.setVgap(11);
        calendarGrid.setPadding(new Insets(20, 0, 0, 0));
        calendarGrid.setAlignment(Pos.CENTER);

        updateCalendar();

        Label insightsTitle = new Label("Today's Insights");
        insightsTitle.setStyle("-fx-font-family: 'Poppins'; " +
                             "-fx-font-weight: bold; " +
                             "-fx-font-size: 20px; " +
                             "-fx-text-fill: black;");
        insightsTitle.setPadding(new Insets(12, 0, 6, 0));
        insightsTitle.setMaxWidth(Double.MAX_VALUE);
        insightsTitle.setAlignment(Pos.CENTER_LEFT);

        dailyInsightsArea = new TextArea();
        dailyInsightsArea.setWrapText(true);
        dailyInsightsArea.setEditable(false);
        dailyInsightsArea.setStyle("-fx-font-family: 'Poppins'; " +
                                 "-fx-font-size: 15px; " +
                                 "-fx-background-color: #E8F5E9;" +
                                 "-fx-text-fill: black;" +
                                 "-fx-background-radius: 18;" +
                                 "-fx-border-radius: 18;" +
                                 "-fx-border-color: #81C784;" +
                                 "-fx-border-width: 1.8;");
        dailyInsightsArea.setPrefHeight(140);
        dailyInsightsArea.setMaxWidth(Double.MAX_VALUE);

        calendarBox.getChildren().addAll(monthYearLabel, calendarGrid, insightsTitle, dailyInsightsArea);
        return calendarBox;
    }

    private VBox buildStartDateSection() {
        VBox section = new VBox();
        section.setPadding(new Insets(14));
        section.setSpacing(14);
        section.setAlignment(Pos.CENTER_LEFT);
        section.setStyle("-fx-background-color: linear-gradient(to right, #FFE0B2 0%, #FFCC80 100%); " +
                        "-fx-background-radius: 20; " +
                        "-fx-border-radius: 20; " +
                        "-fx-border-color: #FB8C00; " +
                        "-fx-border-width: 2;");

        HBox row = new HBox(14);
        row.setAlignment(Pos.CENTER_LEFT);

        startDateLabel = new Label("Pregnancy Start Date: Loading...");
        startDateLabel.setStyle("-fx-font-family: 'Poppins'; " +
                              "-fx-font-weight: bold; " +
                              "-fx-font-size: 19px; " +
                              "-fx-text-fill: black;");

        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-font-family: 'Poppins'; " +
                          "-fx-font-weight: bold; " +
                          "-fx-font-size: 16px; " +
                          "-fx-background-color: #F57C00; " +
                          "-fx-text-fill: black; " +
                          "-fx-background-radius: 16;");
        editButton.setOnAction(e -> showDatePicker());

        row.getChildren().addAll(startDateLabel, editButton);
        section.getChildren().add(row);
        fetchPregnancyStartDateFromFirebase();
        return section;
    }

    private void showDatePicker() {
        DatePicker datePicker = new DatePicker(pregnancyStartDate != null ? pregnancyStartDate : LocalDate.now());
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isAfter(LocalDate.now()));
            }
        });

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Edit Start Date");
        alert.setHeaderText("Select Pregnancy Start Date");
        alert.getDialogPane().setContent(datePicker);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK && datePicker.getValue() != null) {
                pregnancyStartDate = datePicker.getValue();
                controller.updatePregnancyStartDate(pregnancyStartDate);
                startDateLabel.setText("Pregnancy Start Date: " + pregnancyStartDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
                updateCalendar();
                updatePregnancyInfoPanel();
            }
        });
    }

    private VBox buildPregnancyInfoPanel() {
        VBox infoPanel = new VBox(22);
        infoPanel.setPadding(new Insets(32));
        infoPanel.setSpacing(20);
        infoPanel.setStyle("-fx-background-color: linear-gradient(to bottom, #FCE4EC 0%, #F8BBD0 50%, #F48FB1 100%); " +
                         "-fx-background-radius: 22; " +
                         "-fx-border-radius: 22; " +
                         "-fx-border-width: 2; " +
                         "-fx-border-color: #D81B60;");
        infoPanel.setPrefWidth(360);
        infoPanel.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Pregnancy Tracker");
        title.setStyle("-fx-font-family: 'Poppins'; " +
                      "-fx-font-weight: bold; " +
                      "-fx-font-size: 25px; " +
                      "-fx-text-fill: black;");

        VBox todayBox = new VBox(3);
        todayBox.setAlignment(Pos.CENTER);
        Label todayLabel = new Label("TODAY");
        todayLabel.setStyle("-fx-font-family: 'Poppins'; " +
                           "-fx-font-weight: bold; " +
                           "-fx-font-size: 16px; " +
                           "-fx-text-fill: black;");
        Label dayNumber = new Label(String.valueOf(LocalDate.now().getDayOfMonth()));
        dayNumber.setStyle("-fx-font-family: 'Poppins'; " +
                         "-fx-font-weight: bold; " +
                         "-fx-font-size: 30px; " +
                         "-fx-text-fill: black;");
        todayBox.getChildren().addAll(todayLabel, dayNumber);

        VBox progressBox = new VBox(3);
        progressBox.setAlignment(Pos.CENTER);
        Label weeksLabel = new Label("0 Weeks ①");
        weeksLabel.setStyle("-fx-font-family: 'Poppins'; " +
                          "-fx-font-weight: bold; " +
                          "-fx-font-size: 20px; " +
                          "-fx-text-fill: black;");
        weeksLabel.setId("weeksLabel");
        Label daysLabel = new Label("0 Days");
        daysLabel.setStyle("-fx-font-family: 'Poppins'; " +
                         "-fx-font-weight: normal; " +
                         "-fx-font-size: 16px; " +
                         "-fx-text-fill: black;");
        daysLabel.setId("daysLabel");
        progressBox.getChildren().addAll(weeksLabel, daysLabel);

        Button detailsButton = new Button("Details");
        detailsButton.setStyle("-fx-font-family: 'Poppins'; " +
                             "-fx-font-weight: bold; " +
                             "-fx-font-size: 16px; " +
                             "-fx-padding: 6 18 6 18; " +
                             "-fx-background-color: #D81B60; " +
                             "-fx-text-fill: black; " +
                             "-fx-background-radius: 16;");
        detailsButton.setOnAction(e -> showDailyDetails());

        VBox cardsContainer = new VBox(14);
        cardsContainer.setPadding(new Insets(24, 0, 0, 0));

        VBox pregnancyDayCard = createInfoCard("Pregnancy day", "0", "linear-gradient(to right, #B3E5FC 0%, #4FC3F7 100%)");
        pregnancyDayLabel = (Label) pregnancyDayCard.getChildren().get(1);
        pregnancyDayCard.setId("pregnancyDayCard");

        VBox weekInfoCard = createInfoCard("0 weeks", "Your baby", "linear-gradient(to right, #C8E6C9 0%, #81C784 100%)");
        weekInfoLabel = (Label) weekInfoCard.getChildren().get(0);
        weekInfoCard.setId("weekInfoCard");

        VBox trimesterCard = createInfoCard("First trimester", "Watch-outs", "linear-gradient(to right, #FFE0B2 0%, #FFB74D 100%)");
        trimesterLabel = (Label) trimesterCard.getChildren().get(0);
        trimesterCard.setId("trimesterCard");

        VBox insightsCard = createInfoCard("Today", "Insights", "linear-gradient(to right, #D1C4E9 0%, #9575CD 100%)");
        insightsLabel = (Label) insightsCard.getChildren().get(0);
        insightsCard.setOnMouseClicked(e -> showDailyInsights());
        insightsCard.setId("insightsCard");

        cardsContainer.getChildren().addAll(pregnancyDayCard, weekInfoCard, trimesterCard, insightsCard);

        infoPanel.getChildren().addAll(title, todayBox, progressBox, detailsButton, new Separator(), cardsContainer);
        this.infoPanel = infoPanel;
        return infoPanel;
    }

    private VBox createInfoCard(String title, String value, String bgColor) {
        VBox card = new VBox(7);
        card.setPadding(new Insets(14, 20, 14, 20));
        card.setStyle("-fx-background-color: " + bgColor + "; " +
                     "-fx-background-radius: 18; " +
                     "-fx-border-radius: 18; " +
                     "-fx-border-color: #616161; " +
                     "-fx-border-width: 1.5;");
        card.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-family: 'Poppins'; " +
                          "-fx-font-weight: normal; " +
                          "-fx-font-size: 16px; " +
                          "-fx-text-fill: black;");

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-family: 'Poppins'; " +
                          "-fx-font-weight: bold; " +
                          "-fx-font-size: 18px; " +
                          "-fx-text-fill: black;");

        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
    }

    private void showDailyDetails() {
        if (pregnancyStartDate == null) return;
        long daysPregnant = java.time.temporal.ChronoUnit.DAYS.between(pregnancyStartDate, LocalDate.now());
        int weeks = (int) (daysPregnant / 7);
        int days = (int) (daysPregnant % 7);
        String details = "Pregnancy Progress:\n" +
                "• " + weeks + " weeks and " + days + " days\n" +
                "• " + (280 - daysPregnant) + " days remaining\n\n" +
                "Baby Development:\n" +
                TimelineGeminiInsightController.getDailyInsights(weeks, days);
        dailyInsightsArea.setText(details);
    }

    private void showDailyInsights() {
        if (pregnancyStartDate == null) return;
        long daysPregnant = java.time.temporal.ChronoUnit.DAYS.between(pregnancyStartDate, LocalDate.now());
        int weeks = (int) (daysPregnant / 7);
        int days = (int) (daysPregnant % 7);
        String insights = TimelineGeminiInsightController.getDailyInsights(weeks, days);
        dailyInsightsArea.setText(insights);
    }

    private void updateCalendar() {
        if (calendarGrid == null || controller == null || pregnancyStartDate == null) return;
        calendarGrid.getChildren().clear();

        String monthName = currentMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        int year = currentMonth.getYear();
        monthYearLabel.setText("\uD83D\uDCC5 " + monthName + " " + year);

        String[] daysOfWeek = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
        for (int i = 0; i < daysOfWeek.length; i++) {
            Label dayLabel = new Label(daysOfWeek[i]);
            dayLabel.setStyle("-fx-font-family: 'Poppins'; " +
                            "-fx-font-weight: bold; " +
                            "-fx-font-size: 16px; " +
                            "-fx-text-fill: #4A148C;");
            calendarGrid.add(dayLabel, i, 0);
        }

        LocalDate firstDayOfMonth = currentMonth.atDay(1);
        int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue() % 7;
        int daysInMonth = currentMonth.lengthOfMonth();
        int row = 1;
        int col = dayOfWeek;

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate currentDate = currentMonth.atDay(day);
            VBox dateBox = new VBox(3);
            dateBox.setAlignment(Pos.CENTER);

            Label dayLabel = new Label(String.valueOf(day));
            dayLabel.setPrefSize(44, 44);
            dayLabel.setAlignment(Pos.CENTER);
            dayLabel.setStyle("-fx-font-family: 'Poppins'; " +
                            "-fx-font-weight: bold; " +
                            "-fx-font-size: 16px; " +
                            "-fx-text-fill: black; " +
                            "-fx-background-radius: 50%;");

            if (currentDate.equals(LocalDate.now())) {
                dayLabel.setStyle("-fx-font-family: 'Poppins'; " +
                                 "-fx-font-weight: bold; " +
                                 "-fx-font-size: 16px; " +
                                 "-fx-background-color: #81D4FA; " +
                                 "-fx-background-radius: 50%; " +
                                 "-fx-text-fill: black;");
                Label todayLabel = new Label("Today");
                todayLabel.setStyle("-fx-font-family: 'Poppins'; " +
                                   "-fx-font-size: 11px; " +
                                   "-fx-text-fill: black;");
                dateBox.getChildren().addAll(dayLabel, todayLabel);
            } else if (currentDate.equals(controller.getDueDate())) {
                dayLabel.setStyle("-fx-font-family: 'Poppins'; " +
                                "-fx-font-weight: bold; " +
                                "-fx-font-size: 16px; " +
                                "-fx-background-color: #FFD54F; " +
                                "-fx-background-radius: 50%; " +
                                "-fx-text-fill: black;");
                Label dueLabel = new Label("Due");
                dueLabel.setStyle("-fx-font-family: 'Poppins'; " +
                                "-fx-font-size: 11px; " +
                                "-fx-text-fill: black;");
                dateBox.getChildren().addAll(dayLabel, dueLabel);
            } else if (currentDate.equals(controller.getThirdTrimesterEnd())) {
                dayLabel.setStyle("-fx-font-family: 'Poppins'; " +
                                 "-fx-font-weight: bold; " +
                                 "-fx-font-size: 16px; " +
                                 "-fx-background-color: #6311b4ff; " +
                                 "-fx-background-radius: 50%; " +
                                 "-fx-text-fill: black;");
                Label trimesterLabel = new Label("3rd Tri");
                trimesterLabel.setStyle("-fx-font-family: 'Poppins'; " +
                                      "-fx-font-size: 11px; " +
                                      "-fx-text-fill: black;");
                dateBox.getChildren().addAll(dayLabel, trimesterLabel);
            } else if (currentDate.equals(controller.getSecondTrimesterEnd())) {
                dayLabel.setStyle("-fx-font-family: 'Poppins'; " +
                                 "-fx-font-weight: bold; " +
                                 "-fx-font-size: 16px; " +
                                 "-fx-background-color: #B2EBF2; " +
                                 "-fx-background-radius: 50%; " +
                                 "-fx-text-fill: black;");
                Label trimesterLabel = new Label("2nd Tri");
                trimesterLabel.setStyle("-fx-font-family: 'Poppins'; " +
                                      "-fx-font-size: 11px; " +
                                      "-fx-text-fill: black;");
                dateBox.getChildren().addAll(dayLabel, trimesterLabel);
            } else if (currentDate.equals(controller.getFirstTrimesterEnd())) {
                dayLabel.setStyle("-fx-font-family: 'Poppins'; " +
                                "-fx-font-weight: bold; " +
                                "-fx-font-size: 16px; " +
                                "-fx-background-color: #D1C4E9; " +
                                "-fx-background-radius: 50%; " +
                                "-fx-text-fill: black;");
                Label trimesterLabel = new Label("1st Tri");
                trimesterLabel.setStyle("-fx-font-family: 'Poppins'; " +
                                      "-fx-font-size: 11px; " +
                                      "-fx-text-fill: black;");
                dateBox.getChildren().addAll(dayLabel, trimesterLabel);
            } else if (currentDate.isAfter(pregnancyStartDate) && currentDate.isBefore(LocalDate.now())) {
                dayLabel.setStyle("-fx-font-family: 'Poppins'; " +
                                "-fx-font-weight: bold; " +
                                "-fx-font-size: 16px; " +
                                "-fx-background-color: #FFFDE7; " +
                                "-fx-background-radius: 50%; " +
                                "-fx-text-fill: black;");
                dateBox.getChildren().add(dayLabel);
            } else if ((currentDate.equals(LocalDate.now()) || currentDate.isAfter(LocalDate.now())) && currentDate.isBefore(controller.getDueDate())) {
                dayLabel.setStyle("-fx-font-family: 'Poppins'; " +
                                "-fx-font-weight: bold; " +
                                "-fx-font-size: 16px; " +
                                "-fx-background-color: #E1BEE7; " +
                                "-fx-background-radius: 50%; " +
                                "-fx-text-fill: black;");
                dateBox.getChildren().add(dayLabel);
            } else {
                dayLabel.setStyle("-fx-font-family: 'Poppins'; " +
                                 "-fx-font-weight: bold; " +
                                 "-fx-font-size: 16px; " +
                                 "-fx-background-color: #F8BBD0; " +
                                 "-fx-background-radius: 50%; " +
                                 "-fx-text-fill: black;");
                dateBox.getChildren().add(dayLabel);
            }

            dayLabel.setEffect(new DropShadow(4, Color.web("#FFD54F")));
            calendarGrid.add(dateBox, col, row);
            col++;
            if (col == 7) {
                col = 0;
                row++;
            }
        }
    }

    private void updatePregnancyInfoPanel() {
        if (pregnancyStartDate == null || controller == null) return;

        long daysPregnant = java.time.temporal.ChronoUnit.DAYS.between(pregnancyStartDate, LocalDate.now());
        int weeks = (int) (daysPregnant / 7);
        int days = (int) (daysPregnant % 7);

        Label weeksLabel = (Label) infoPanel.lookup("#weeksLabel");
        Label daysLabel = (Label) infoPanel.lookup("#daysLabel");
        if(weeksLabel != null) {
            weeksLabel.setText(weeks + " Weeks ①");
            weeksLabel.setStyle("-fx-font-family: 'Poppins'; " +
                              "-fx-font-weight: bold; " +
                              "-fx-font-size: 20px; " +
                              "-fx-text-fill: black;");
        }
        if(daysLabel != null) {
            daysLabel.setText(days + " Days");
            daysLabel.setStyle("-fx-font-family: 'Poppins'; " +
                             "-fx-font-weight: normal; " +
                             "-fx-font-size: 16px; " +
                             "-fx-text-fill: black;");
        }

        if(pregnancyDayLabel != null) pregnancyDayLabel.setText(String.valueOf(daysPregnant));
        if(weekInfoLabel != null) weekInfoLabel.setText(weeks + " weeks");

        if(trimesterLabel != null) {
            String trimester = getCurrentTrimester(weeks);
            trimesterLabel.setText(trimester);

            Tooltip tt = new Tooltip();
            tt.setText(TimelineGeminiInsightController.getTrimesterInfo(
                trimester, getTrimesterStartDate(weeks), getTrimesterEndDate(weeks)
            ));
            tt.setStyle("-fx-font-family: 'Poppins'; " +
                      "-fx-font-size: 12px; " +
                      "-fx-text-fill: black;");
            tt.setMaxWidth(300);
            tt.setWrapText(true);
            trimesterLabel.setTooltip(tt);
        }
    }

    private LocalDate getTrimesterStartDate(int weeks) {
        if (weeks < 13) return pregnancyStartDate;
        else if (weeks < 27) return pregnancyStartDate.plusWeeks(13);
        else return pregnancyStartDate.plusWeeks(27);
    }

    private LocalDate getTrimesterEndDate(int weeks) {
        if (weeks < 13) return pregnancyStartDate.plusWeeks(12);
        else if (weeks < 27) return pregnancyStartDate.plusWeeks(26);
        else return pregnancyStartDate.plusWeeks(40);
    }

    private String getCurrentTrimester(int weeks) {
        if (weeks < 13) return "First trimester";
        else if (weeks < 27) return "Second trimester";
        else return "Third trimester";
    }

    private VBox buildMonthsList() {
        VBox monthsBox = new VBox();
        monthsBox.setSpacing(40);
        monthsBox.setPadding(new Insets(23));
        monthsBox.setStyle("-fx-background-color: linear-gradient(to bottom, #DCE775 0%, #CDDC39 100%); " +
                         "-fx-border-radius: 18; " +
                         "-fx-background-radius: 18; " +
                         "-fx-border-width: 1.5; " +
                         "-fx-border-color: #AFB42B;");
        monthsBox.setPrefWidth(180);
        monthsBox.setAlignment(Pos.TOP_CENTER);

        HBox yearBox = new HBox(8);
        yearBox.setAlignment(Pos.CENTER);

        Button prevYear = new Button("<<");
        prevYear.setStyle("-fx-font-family: 'Poppins'; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 12px; " +
                        "-fx-background-color: #AFB42B; " +
                        "-fx-text-fill: black; " +
                        "-fx-background-radius: 10;");
        prevYear.setOnAction(e -> {
            currentMonth = currentMonth.minusYears(1);
            updateCalendar();
            yearLabel.setText("" + currentMonth.getYear());
        });

        yearLabel = new Label("" + currentMonth.getYear());
        yearLabel.setStyle("-fx-font-family: 'Poppins'; " +
                         "-fx-font-weight: bold; " +
                         "-fx-font-size: 17px; " +
                         "-fx-text-fill: black;");

        Button nextYear = new Button(">>");
        nextYear.setStyle("-fx-font-family: 'Poppins'; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 12px; " +
                        "-fx-background-color: #DCE775; " +
                        "-fx-text-fill: black; " +
                        "-fx-background-radius: 10;");
        nextYear.setOnAction(e -> {
            currentMonth = currentMonth.plusYears(1);
            updateCalendar();
            yearLabel.setText("" + currentMonth.getYear());
        });

        yearBox.getChildren().addAll(prevYear, yearLabel, nextYear);
        monthsBox.getChildren().add(yearBox);

        String[] months = { "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December" };

        Color[] monthColors = {
            Color.web("#FFCDD2"), Color.web("#F8BBD0"), Color.web("#E1BEE7"), Color.web("#D1C4E9"),
            Color.web("#C5CAE9"), Color.web("#BBDEFB"), Color.web("#B3E5FC"), Color.web("#B2EBF2"),
            Color.web("#B2DFDB"), Color.web("#C8E6C9"), Color.web("#DCEDC8"), Color.web("#F0F4C3")
        };

        for (int i = 0; i < months.length; i++) {
            Label monthLabel = new Label(months[i]);
            monthLabel.setStyle("-fx-font-family: 'Poppins'; " +
                              "-fx-font-weight: bold; " +
                              "-fx-font-size: 15px; " +
                              "-fx-text-fill: black;");
            int finalI = i;
            monthLabel.setOnMouseClicked(event -> {
                currentMonth = YearMonth.of(currentMonth.getYear(), finalI + 1);
                updateCalendar();
            });

            final String pastel = String.format("#%02X%02X%02X", 
                (int)(monthColors[i].getRed()*255), 
                (int)(monthColors[i].getGreen()*255), 
                (int)(monthColors[i].getBlue()*255)
            );
            monthLabel.setOnMouseEntered(ev -> monthLabel.setStyle("-fx-font-family: 'Poppins'; " +
                                                                 "-fx-font-weight: bold; " +
                                                                 "-fx-font-size: 15px; " +
                                                                 "-fx-background-color: " + pastel + "; " +
                                                                 "-fx-text-fill: black; " +
                                                                 "-fx-background-radius: 8; " +
                                                                 "-fx-underline: true;"));
            monthLabel.setOnMouseExited(ev -> monthLabel.setStyle("-fx-font-family: 'Poppins'; " +
                                                               "-fx-font-weight: bold; " +
                                                               "-fx-font-size: 15px; " +
                                                               "-fx-text-fill: black; " +
                                                               "-fx-underline: false;"));

            monthsBox.getChildren().add(monthLabel);
        }
        return monthsBox;
    }
}