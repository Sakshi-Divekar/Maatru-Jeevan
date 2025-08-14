package com.priti.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// MothersWellnessPage.java
public class MothersWellnessPage extends Application {
    @Override
    public void start(Stage stage) {
        Label label = new Label("Mother's Health & Wellness Page");
        Scene scene = new Scene(new StackPane(label), 800, 600);
        stage.setScene(scene);
        stage.setTitle("Mother's Health & Wellness");
        stage.show();
    }
}

