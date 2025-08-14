package com.priti.view;

import java.time.LocalDate;

import com.priti.controller.TimelineController;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class PregnancyMonitoringPage {
    private static final String ACCENT = "#A53860";
    private static final String VITALS = "#a682b8";
    private static final String LAB = "#e8ae55";
    private static final String TIMELINE = "#d58b95";
    private static final String TEXT_DARK = "#462344";

    private final Stage stage;
    private final StackPane rootPane = new StackPane();

    public PregnancyMonitoringPage(Stage stage) {
        this.stage = stage;
        rootPane.setStyle("-fx-background-color: linear-gradient(to bottom, #fff5f8 0%, #ffe4f3 100%);");
        rootPane.getChildren().add(buildTrackerMenu());
    }



    public Scene getScene() {
        return new Scene(rootPane, 1550, 800);
    }

    private VBox buildTrackerMenu() {
        VBox wrapper = new VBox(40);
        wrapper.setAlignment(Pos.TOP_CENTER);
        wrapper.setPadding(new Insets(52, 30, 54, 30));
        wrapper.setMaxWidth(1100);

        Label title = new Label("Pregnancy Tracker");
        title.setFont(Font.font("Arial Rounded MT Bold", FontWeight.EXTRA_BOLD, 44));
        title.setTextFill(Color.web(ACCENT));
        title.setStyle("-fx-effect: dropshadow(gaussian, #fff3fc99, 0,0,0,3);");
        VBox.setMargin(title, new Insets(0, 0, 32, 0));

        HBox phaseRow = new HBox(38);
        phaseRow.setAlignment(Pos.CENTER);
        phaseRow.getChildren().addAll(
                phaseCard("📅 Timeline", TIMELINE, "/assets/images/timeline.jpeg", "timeline"),
                phaseCard("🧭 Sonography", ACCENT, "/assets/images/sonography.jpeg", "sonography"),
                phaseCard("🧪 Labs", LAB, "/assets/images/labb.jpeg", "labs"),
                phaseCard("❤️ Vitals", VITALS, "/assets/images/vitals.jpeg", "vitals")
        );

        Button backBtn = new Button("⬅ Back");
        backBtn.setPrefWidth(210);
        backBtn.setFont(Font.font("Arial Rounded MT Bold", 19));
        backBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #A53860b0, #d17e97);" +
                "-fx-background-radius: 27;"
        );
        backBtn.setTextFill(Color.WHITE);
        backBtn.setOnAction(e -> {
            PreHomePage home = new PreHomePage();
            stage.setScene(home.getScene(stage));
        });
        VBox.setMargin(backBtn, new Insets(46,0,0,0));

        wrapper.getChildren().addAll(title, phaseRow, backBtn);
        return wrapper;
    }

    private StackPane phaseCard(String phase, String color, String imgPath, String type) {
        StackPane pane = new StackPane();
        pane.setPrefSize(240, 390);

        Rectangle bg = new Rectangle(240, 390);
        bg.setArcWidth(64);
        bg.setArcHeight(64);
        bg.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.web(color + "32")),
            new Stop(1, Color.WHITE)));
        bg.setStroke(Color.web(color + "99"));
        bg.setStrokeWidth(3.5);
        bg.setEffect(new DropShadow(24, Color.web(color+"44")));

        ImageView icon = new ImageView(new Image(imgPath));
        icon.setFitWidth(96);
        icon.setFitHeight(96);
        Circle circ = new Circle(60,60,60);
        icon.setClip(circ);

        StackPane iconPane = new StackPane(icon);
        iconPane.setPadding(new Insets(32, 0, 12, 0));

        Label phLabel = new Label(phase);
        phLabel.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 23));
        phLabel.setTextFill(Color.web(color));
        phLabel.setPadding(new Insets(0,0,12,0));

        Label desc = new Label(phaseDescription(phase));
        desc.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        desc.setTextFill(Color.web(TEXT_DARK + "DD"));
        desc.setWrapText(true);
        desc.setAlignment(Pos.TOP_CENTER);
        desc.setMaxWidth(186);

        Button exploreBtn = new Button("Explore ➔");
        exploreBtn.setFont(Font.font("Arial Rounded MT Bold", 16));
        exploreBtn.setStyle(
                "-fx-background-color: " + color + ";" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 18;"
        );
        exploreBtn.setOnAction(e -> openInnerPage(type));

        VBox content = new VBox(2, iconPane, phLabel, desc, exploreBtn);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(14, 16, 14, 16));
        VBox.setMargin(exploreBtn, new Insets(16, 0, 8, 0));

        pane.getChildren().addAll(bg, content);

        pane.setOnMouseEntered(e -> {
            pane.setScaleX(1.045);
            pane.setScaleY(1.045);
            bg.setFill(new LinearGradient(0,0,1,1,true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web(color + "66")), new Stop(1, Color.WHITE)));
        });

        pane.setOnMouseExited(e -> {
            pane.setScaleX(1.0);
            pane.setScaleY(1.0);
            bg.setFill(new LinearGradient(0,0,1,1,true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web(color + "32")), new Stop(1, Color.WHITE)));
        });

        pane.setStyle("-fx-cursor: hand;");

        return pane;
    }

    private String phaseDescription(String phase) {
        switch (phase) {
            case "📅 Timeline":
                return "Pregnancy journey: trimesters, milestones, doctor visits, personal notes.";
            case "🧭 Sonography":
                return "View all fetal sonography: growth, amniotic fluid, placenta health, EDD milestones.";
            case "🧪 Labs":
                return "Lab reports: CBC, HGB, thyroid, glucose, urinalysis & more.";
            case "❤️ Vitals":
                return "Mother's vitals: BP, weight, pulse, O2, nutrition and ongoing wellness trends.";
        }
        return "";
    }

    private void openInnerPage(String type) {
        Scene innerScene = null;
        switch (type) {
            case "timeline":
    TimelineController timelineController = new TimelineController(LocalDate.now());
    innerScene = new TimelineInner(stage, timelineController).getScene();
    break;


            case "sonography":
                innerScene = new SonographyInner(stage).getScene();
                break;
            case "labs":
                innerScene = new LabInner(stage).getScene();
                break;
            case "vitals":
                innerScene = new VitalInner(stage).getScene();
                break;
        }
        if (innerScene != null) {
            stage.setScene(innerScene);
        }
    }

    
}