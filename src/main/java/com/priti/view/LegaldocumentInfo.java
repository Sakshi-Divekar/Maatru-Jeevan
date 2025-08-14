package com.priti.view;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LegaldocumentInfo {

    private Stage stage;

    public LegaldocumentInfo(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #FFE7EB, #DFF6FF);");

        VBox header = createHeader();
        VBox headerContainer = new VBox(header);
        headerContainer.setPadding(new Insets(0, 0, 25, 0));
        root.setTop(headerContainer);

        FlowPane documentsPane = createDocumentsPane();
        ScrollPane scrollPane = new ScrollPane(documentsPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        root.setCenter(scrollPane);

        HBox footer = createFooter();
        root.setBottom(footer);

        // Create a StackPane to layer the back button on top of the BorderPane
        StackPane layeredPane = new StackPane(root);

        // Back Button setup (floating top-left)
        Button backButton = new Button("\u2190 Back");
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        backButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #4B6587; -fx-border-color: #4B6587; -fx-border-radius: 5; -fx-background-radius: 5;");
        backButton.setOnAction(e -> {
            RecoveryTrackerPage recoveryPage = new RecoveryTrackerPage(stage);
            stage.setScene(recoveryPage.getScene());
        });

        StackPane.setAlignment(backButton, Pos.TOP_LEFT);
        StackPane.setMargin(backButton, new Insets(20, 0, 0, 20)); // 20px from top and left
        layeredPane.getChildren().add(backButton);

        return new Scene(layeredPane, 1550, 800);
    }

    private VBox createHeader() {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(25));
        header.setStyle("-fx-background-color: #BEE1E6; -fx-background-radius: 0 0 20 20;");

        Text title = new Text("Legal Documents for Your Newborn");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 34));
        title.setFill(Color.web("#2C3E50"));

        Text subtitle = new Text("Complete Checklist for New Parents");
        subtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        subtitle.setFill(Color.web("#4B6587"));

        header.getChildren().addAll(title, subtitle);
        return header;
    }

    private FlowPane createDocumentsPane() {
        FlowPane pane = new FlowPane();
        pane.setPadding(new Insets(0, 25, 25, 25));
        pane.setHgap(25);
        pane.setVgap(25);
        pane.setAlignment(Pos.TOP_CENTER);

        String[] titles = {
                "Birth Certificate", "Social Security Number", "Health Insurance",
                "Will & Guardianship", "Trust Fund", "Passport",
                "Medical Consent", "Childcare Agreement", "Citizenship Docs"
        };

        String[] descriptions = {
                "Official proof of identity and citizenship. Required for school, passports, and benefits.",
                "Needed for tax benefits, bank accounts, and government services.",
                "Ensures medical coverage for vaccinations and emergencies.",
                "Appoints a guardian and ensures inheritance rights.",
                "Secures the child's financial future for education.",
                "Required for international travel.",
                "Authorizes caregivers for medical decisions.",
                "Defines terms if hiring a nanny.",
                "For dual citizenship or residency status."
        };

        String[] cardColors = {
                "#FDE2E4", "#E2F0CB", "#FFF1E6", "#D0E8F2", "#F3D1F4",
                "#E4F9F5", "#FFF4E6", "#E3FDFD", "#F8F8FF"
        };

        for (int i = 0; i < titles.length; i++) {
            VBox card = createDocumentCard(titles[i], descriptions[i], cardColors[i % cardColors.length]);
            applyInitialScaleTransition(card, i * 100);  // Apply initial scale animation with staggered delay
            pane.getChildren().add(card);
        }

        return pane;
    }

    private VBox createDocumentCard(String title, String description, String color) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.TOP_LEFT);
        card.setPrefSize(290, 200);
        card.setStyle("-fx-background-color: " + color + ";" +
                "-fx-background-radius: 18;" +
                "-fx-border-radius: 18;" +
                "-fx-border-color: #ddd;" +
                "-fx-border-width: 1.5;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 4);");

        Text titleText = new Text(title);
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titleText.setFill(Color.web("#2C3E50"));

        Text descText = new Text(description);
        descText.setFont(Font.font("Arial", 14));
        descText.setFill(Color.web("#4B6587"));
        descText.setWrappingWidth(250);

        Button addButton = new Button("Done");
        addButton.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        addButton.setStyle("-fx-background-color: #4B6587; -fx-text-fill: white; -fx-background-radius: 8;");
        addButton.setOnAction(e -> {
            addButton.setText("✅ Completed");
            addButton.setDisable(true);
            addButton.setStyle("-fx-background-color: #A8DF8E; -fx-text-fill: #2B2B2B;");
        });

        card.getChildren().addAll(titleText, descText, addButton);

        // Hover Animation
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(240), card);
        scaleIn.setToX(1.1);
        scaleIn.setToY(1.1);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(240), card);
        scaleOut.setToX(1.1);
        scaleOut.setToY(1.1);

        card.setOnMouseEntered(e -> scaleIn.playFromStart());
        card.setOnMouseExited(e -> scaleOut.playFromStart());

        return card;
    }

    private void applyInitialScaleTransition(VBox card, int delayMillis) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(400), card);
        scaleTransition.setFromX(0.8);
        scaleTransition.setFromY(0.8);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.setDelay(Duration.millis(delayMillis));
        scaleTransition.play();
    }

    private HBox createFooter() {
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(20));
        footer.setStyle("-fx-background-color: #BEE1E6; -fx-background-radius: 20 20 0 0;");
        return footer;
    }
}