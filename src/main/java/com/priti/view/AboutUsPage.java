package com.priti.view;

import javafx.animation.FadeTransition;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AboutUsPage {

    public void show(Stage stage) {
        Scene aboutScene = getScene(stage);
        stage.setScene(aboutScene);
    }

    public Scene getScene(Stage stage) {
        BorderPane root = new BorderPane();

        // Apply a gradient background using a StackPane wrapper
        Stop[] stops = new Stop[] {
                new Stop(0, Color.web("#A53860")),
                new Stop(1, Color.web("#FFE3EC"))
        };
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        root.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));

        // ========== TOP BAR ==========
// ========== TOP BAR ==========
VBox topBarContainer = new VBox();
topBarContainer.setPadding(new Insets(20, 34, 0, 20));
topBarContainer.setSpacing(10);

// Back Button
Button backButton = new Button("← Back");
backButton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
backButton.setStyle("-fx-background-color: #cc0066; -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 8 16;");
backButton.setOnAction(e -> {
    new com.priti.view.LandingPage().start(stage);
});

// Logos + Title Row
ImageView coreLogo = new ImageView(new Image("/assets/images/c2w_logo.jpeg"));
coreLogo.setFitWidth(110);
coreLogo.setFitHeight(110);

ImageView mjLogo = new ImageView(new Image("/assets/images/final_logo.jpg"));
mjLogo.setFitWidth(110);
mjLogo.setFitHeight(110);
mjLogo.setClip(new Circle(55, 55, 55));

VBox mjLogoBox = new VBox(mjLogo, logoLabel("Maatru-Jeevan"));
mjLogoBox.setAlignment(Pos.CENTER);
mjLogoBox.setSpacing(5);

// Title label
Label title = new Label("Maatruwa-Setu");
title.setFont(Font.font("Georgia", FontWeight.EXTRA_BOLD, 38));
title.setTextFill(Color.web("#cc0066"));
title.setAlignment(Pos.CENTER);

// Spacer box to center the title
Region spacer1 = new Region();
Region spacer2 = new Region();
HBox.setHgrow(spacer1, Priority.ALWAYS);
HBox.setHgrow(spacer2, Priority.ALWAYS);

// Logo row
HBox logoRow = new HBox(40);
logoRow.setAlignment(Pos.CENTER);
logoRow.getChildren().addAll(coreLogo, spacer1, title, spacer2, mjLogoBox);

// Add to container
topBarContainer.getChildren().addAll(backButton, logoRow);


        // ========== HERO TITLE ==========
        // Label title = new Label("Maatruwa-Setu");
        // title.setFont(Font.font("Georgia", FontWeight.EXTRA_BOLD, 44));
        // title.setTextFill(Color.web("#cc0066"));
        // title.setPadding(new Insets(0, 0, 140, 0));
        // title.setAlignment(Pos.CENTER);

        // ========== MAIN GRID ==========
        GridPane mainGrid = new GridPane();
        mainGrid.setHgap(40);
        mainGrid.setVgap(0);
        mainGrid.setPadding(new Insets(10, 50, 12, 80));

        // ---- LEFT COLUMN ----
        ImageView shashiImage = new ImageView(new Image("/assets/images/shashi_sir_image.jpeg"));
        shashiImage.setPreserveRatio(true);
        shashiImage.setSmooth(true);
        shashiImage.setFitWidth(390);
        shashiImage.setFitHeight(450);

        Label shashiLabel = new Label("Prof. Shashi Bagal Sir");
        shashiLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        shashiLabel.setTextFill(Color.web("#99004d"));

        // ➕ Add Personal Note
        Label note = new Label(
                "We are fortunate to have a teacher like Shashi Bagal Sir — not just a teacher, but truly a guru in every sense.\n" +
                "He doesn't just teach us Academic Knowledge; he builds our understanding from the very basics to advanced levels ,"+ 
                "with remarkable clarity and depth.\n"+
                "Beyond academics, he constantly motivates us, shares valuable knowledge beyond the syllabus\n" +
                "and supports us like a family member.\n"+
                "We are truly grateful to have Shashi sir as our Guru."
        );
        note.setWrapText(true);
        note.setFont(Font.font("Segoe UI", 15));
        note.setTextFill(Color.web("#4A235A"));
        note.setTextAlignment(TextAlignment.JUSTIFY);
        note.setMaxWidth(380);

        VBox shashiBox = new VBox(18, shashiImage, shashiLabel, note);
        shashiBox.setAlignment(Pos.TOP_CENTER);

        // ---- RIGHT COLUMN ----
        VBox contentCards = new VBox(28);
        contentCards.setAlignment(Pos.TOP_CENTER);
        contentCards.setPadding(new Insets(0, 0, 0, 4));
        contentCards.setMaxWidth(900);

        VBox intro = card("🌸 Introduction-",
                "Maatru Jeevan is a smart digital assistant designed to support maternal and child health. \n"
                        + "It helps expectant and new mothers throughout pregnancy and early childhood with personalized care, \n"
                        + "reminders, health tracking, and educational resources – all in one platform.");

        VBox goal = card("🎯 Mission-",
                "Our mission is to empower mothers by bridging the healthcare information gap. \n"
                        + "We make timely support accessible to every family and encourage early childhood development.");

        VBox team = horizontalCard("👩‍💼 Meet the Team-", new String[]{
                "Sakshi Sanjay Divekar (BE)",
                "Priti Rohidas Zaware (BE)",
                "Nikita Baburao Burkule (TE)"
        });

        VBox mentors = horizontalCard("🧑‍🏫 Mentors & Support-", new String[]{
                "Prof. Sachin Sir", 
                "Prof. Pramod Sir", 
                "Prof. Shiv Sir",
                "Prof. Subodh Sir",
                "Mentor Guidance - Punam Khedkar"
        });

        contentCards.getChildren().addAll(
                addFade(intro),
                addFade(goal),
                addFade(team),
                addFade(mentors)
        );

        mainGrid.add(shashiBox, 0, 0);
        mainGrid.add(contentCards, 1, 0);
        GridPane.setValignment(shashiBox, VPos.TOP);
        GridPane.setValignment(contentCards, VPos.TOP);

        // ========== SPECIAL THANKS ==========
        VBox thanks = card("💖 Special Thanks-", "Thanks to Core2Web for giving us this opportunity. \n" +
                "We thank everyone who supported and guided us on this journey. \n"
                        + "Your encouragement helped shape Maatru Jeevan into a meaningful initiative.");
        thanks.setMaxWidth(660);
        thanks.setAlignment(Pos.CENTER);
        VBox.setMargin(thanks, new Insets(40, 0, 0, 0));
        addFade(thanks);

        // ========== COMBINE ==========
        VBox pageContent = new VBox(0);
        pageContent.setAlignment(Pos.TOP_CENTER);
        pageContent.setPadding(new Insets(0, 0, 26, 0));
        pageContent.getChildren().addAll(topBarContainer, mainGrid, thanks);


        ScrollPane scrollPane = new ScrollPane(pageContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        root.setCenter(scrollPane);
        return new Scene(root, 1550, 800);
    }

    private Label logoLabel(String text) {
        Label l = new Label(text);
        l.setFont(Font.font("Segoe UI", FontWeight.BOLD, 17));
        l.setTextFill(Color.web("#A53860"));
        l.setAlignment(Pos.CENTER);
        return l;
    }

    private VBox card(String heading, String content) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setMaxWidth(800);
        card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 5, 5);");

        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: #ffe6f0; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 12, 0, 5, 5);"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 5, 5);"));

        Label header = new Label(heading);
        header.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        header.setTextFill(Color.web("#cc0066"));

        Label body = new Label(content);
        body.setFont(Font.font("Segoe UI", 16));
        body.setWrapText(true);
        body.setTextFill(Color.web("#444444"));
        body.setTextAlignment(TextAlignment.JUSTIFY);

        card.getChildren().addAll(header, body);
        return card;
    }

    private VBox horizontalCard(String heading, String[] items) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(20));
        box.setMaxWidth(800);
        box.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 5, 5);");

        box.setOnMouseEntered(e -> box.setStyle("-fx-background-color: #ffe6f0; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 12, 0, 5, 5);"));
        box.setOnMouseExited(e -> box.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 5, 5);"));

        Label title = new Label(heading);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        title.setTextFill(Color.web("#cc0066"));

        VBox itemList = new VBox(5);
        for (String item : items) {
            Label label = new Label("• " + item);
            label.setFont(Font.font("Segoe UI", 16));
            label.setTextFill(Color.web("#333333"));
            itemList.getChildren().add(label);
        }

        box.getChildren().addAll(title, itemList);
        return box;
    }

    private VBox addFade(VBox node) {
        FadeTransition ft = new FadeTransition(Duration.seconds(1.2), node);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        return node;
    }
}