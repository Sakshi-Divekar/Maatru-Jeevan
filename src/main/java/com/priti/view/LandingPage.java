package com.priti.view;

import com.priti.controller.FirebaseInit;
import com.priti.view.AboutUsPage;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class LandingPage extends Application {
    private Stage primaryStage;
    private Scene landingScene;
    
    public LandingPage() {
        // No-args constructor required by JavaFX
    }
    
    public LandingPage(Stage stage) {
        this.primaryStage = stage;
    }



    @Override
    public void start(Stage stage) {
        primaryStage=stage;
        FirebaseInit.initialize();
    // Create section nodes so they can be scrolled to
StackPane hero = createHeroSection();
VBox featureSection = createFeatureSection();
VBox modulesSection = createModulesSection();
VBox missionSection = createMissionSection();
VBox footer = createFooter();

// SCROLL TARGETS
ScrollPane scrollPane = new ScrollPane();
VBox scrollContent = new VBox();
scrollContent.getChildren().addAll(hero, featureSection, modulesSection, missionSection, footer);
scrollPane.setContent(scrollContent);
scrollPane.setFitToWidth(true);
scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

// NAVIGATION BAR (stable on top)
HBox navbar = createNavbar(scrollPane, hero, featureSection, modulesSection, missionSection);

// Combine navbar + scrollable content
VBox fullPage = new VBox(navbar, scrollPane);
VBox.setVgrow(scrollPane, Priority.ALWAYS);
        // Root VBox
        VBox root = new VBox();
        root.setSpacing(0);

        // 1. HERO SECTION
         hero = createHeroSection();


        // 2. KEY FEATURES
         featureSection = createFeatureSection();

        // 3. MODULES OVERVIEW
         modulesSection = createModulesSection();

        // 4. MISSION
         missionSection = createMissionSection();

        // 5. FOOTER
         footer = createFooter();

        // Add sections to root layout
        root.getChildren().addAll(hero, featureSection, modulesSection, missionSection, footer);

        // SCENE
        Scene landingScene = new Scene(fullPage,1550, 800);

        // Stage setup
        stage.setTitle("Maatru-Jeevan – Empowering Mothers");
        stage.setScene(landingScene);

        stage.show();
    }
public Scene getScene() {
        start(primaryStage);
       
    return landingScene;
}

    


    /** HERO SECTION **/
    private StackPane createHeroSection() {
        StackPane heroPane = new StackPane();
        heroPane.setPadding(new Insets(36, 20, 56, 20));
        heroPane.setMinHeight(360);

        // BACKGROUND GRADIENT
        heroPane.setBackground(new Background(new BackgroundFill(
            new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#A53860")),
                new Stop(1, Color.web("#EC7FA9"))),
            CornerRadii.EMPTY, Insets.EMPTY)));

        // Side image
        ImageView heroImg = new ImageView(new Image("/assets/images/final_logo.jpg", 270, 0, true, true));
        heroImg.setPreserveRatio(true);
        heroImg.setSmooth(true);
        heroImg.setOpacity(0.90);

        // Left: Texts + Button
        VBox texts = new VBox(20);
        texts.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Empowering Mothers at Every Step");
        title.setFont(Font.font("Montserrat", FontWeight.BOLD, 35));
        title.setTextFill(Color.WHITE);

        Label sub = new Label("Smart care tracking, traditional remedies & expert support");
        sub.setFont(Font.font("Roboto", FontWeight.NORMAL, 17));
        sub.setTextFill(Color.WHITE);

        Button login = new Button("Login / Sign Up");
login.setStyle("-fx-background-color: #A53860; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 6 16;");
login.setOnAction(event -> {
    PregnancyRegistrationForm form = new PregnancyRegistrationForm();
    form.show((Stage) login.getScene().getWindow());
});


        texts.getChildren().addAll(title, sub, login);

        HBox heroContent = new HBox(60, texts, heroImg);
        heroContent.setAlignment(Pos.CENTER);
        heroContent.setPadding(new Insets(16, 0, 0, 0));

        heroPane.getChildren().add(heroContent);

        return heroPane;
    }

    /** FEATURE SECTION **/
    private VBox createFeatureSection() {
        VBox box = new VBox();
        box.setPadding(new Insets(38, 0, 27, 0));
        box.setSpacing(18);
        box.setAlignment(Pos.TOP_CENTER);
        box.setStyle("-fx-background-color: #FFF5FA;");

        Label main = new Label("Key Features");
        main.setFont(Font.font("Montserrat", FontWeight.BOLD, 26));
        main.setTextFill(Color.web("#A53860"));
        Label sub = new Label("Smart features designed to assist mothers throughout pregnancy and child care.");
        sub.setFont(Font.font("Roboto", 15));
        sub.setTextFill(Color.web("#555"));

        GridPane grid = new GridPane();
        grid.setHgap(34);
        grid.setVgap(22);
        grid.setPadding(new Insets(10, 0, 0, 0));
        grid.setAlignment(Pos.CENTER);

        String[][] features = {
            {"🤰", "Pregnancy Tracking", "Week-wise updates, baby growth info"},
            {"🍼", "Baby Care Plans", "Milestones, routine care, tips"},
            {"🍎", "Nutrition Guide", "Mother-baby diet charts & supplements"},
            {"📅", "Appointment Reminders", "Doctor visits, scans, tests"},
            {"🌿", "Traditional Tips & Remedies", "Ayurvedic/home remedies for wellness"},
            {"📄", "Legal Docs & Schemes", "Government schemes & document checklist"},
            {"📲", "App-Based Activities", "Daily logs, activity tracking"},
            {"⚠️", "Emergency Info", "Quick-access hospital/doctors info"}
        };
        // 2 rows x 4 columns
        for (int r = 0; r < 2; r++)
            for (int c = 0; c < 4; c++)
                grid.add(createFeatureCard(features[r * 4 + c][0], features[r * 4 + c][1], features[r * 4 + c][2]), c, r);

        box.getChildren().addAll(main, sub, grid);
        return box;
    }

    private VBox createFeatureCard(String emoji, String title, String desc) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(16, 14, 16, 14));
        card.setAlignment(Pos.TOP_CENTER);
        card.setMaxWidth(210);
        card.setMinHeight(100);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, #e0a4c1AA, 6, 0.18, 1, 2);");
        Label icon = new Label(emoji); icon.setFont(Font.font(32));
        Label tit = new Label(title); tit.setFont(Font.font("Montserrat", FontWeight.BOLD, 14));tit.setTextFill(Color.web("#A53860"));
        Label d = new Label(desc);d.setWrapText(true); d.setFont(Font.font("Roboto", 12)); d.setTextAlignment(TextAlignment.CENTER); d.setTextFill(Color.web("#72526a"));

        card.getChildren().addAll(icon, tit, d);
        return card;
    }

    /** MODULES SECTION **/
    private VBox createModulesSection() {
        VBox sec = new VBox();
        sec.setPadding(new Insets(52, 0, 28, 0));
        sec.setStyle("-fx-background-color: white;");
        sec.setAlignment(Pos.TOP_CENTER);
        sec.setSpacing(18);

        Label h = new Label("Modules Offered");
        h.setFont(Font.font("Montserrat", FontWeight.BOLD, 24));
        h.setTextFill(Color.web("#A53860"));
        Label sub = new Label("Each module is tailored to support different phases of motherhood.");
        sub.setFont(Font.font("Roboto", 14));
        sub.setTextFill(Color.web("#444"));

        HBox modulesBox = new HBox(38);
        modulesBox.setAlignment(Pos.CENTER);

        modulesBox.getChildren().addAll(
                createModuleCard("🤰 Pre-Delivery Phase", new String[]{
                        "👩‍🍼 Mother profile", "⏰ Reminders", "🧘‍♀️ Mood/health tracker"}),
                createModuleCard("👶 Post-Delivery Phase", new String[]{
                        "🍼 Baby care tips", "💉 Vaccination", "📔 Digital diary"}),
                createModuleCard("💡 Common Modules", new String[]{
                        "⚠️ Emergency Help", "🍎 Nutrition", "⚖️ Legal Support"})
        );
        sec.getChildren().addAll(h, sub, modulesBox);
        return sec;
    }

    private VBox createModuleCard(String phaseTitle, String[] items) {
        VBox v = new VBox(10);
        v.setAlignment(Pos.TOP_LEFT);
        v.setPadding(new Insets(24, 15, 18, 15));
        v.setMinWidth(220);
        v.setMaxWidth(280);
        v.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #EC7FA9, #FFE6ED);\r\n" + //
                        " -fx-background-radius:15; -fx-effect:dropshadow(two-pass-box,#bc4579BB,4,0.15,2,1);");
        Label t = new Label(phaseTitle);
        t.setFont(Font.font("Montserrat", FontWeight.BOLD, 15));
        t.setTextFill(Color.web("#A53860"));
        VBox list = new VBox(8);
        for (String s : items) {
            Label l = new Label(s);
            l.setFont(Font.font("Roboto", 13));
            l.setTextFill(Color.web("#51354a"));
            list.getChildren().add(l);
        }
        v.getChildren().addAll(t, list);
        return v;
    }

    /** MISSION SECTION **/
    private VBox createMissionSection() {
        VBox v = new VBox();
        v.setPadding(new Insets(52, 0, 48, 0));
        v.setSpacing(0);
        v.setAlignment(Pos.TOP_CENTER);
        v.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #faf2f7, #ffe6ec);");

        Label h = new Label("Our Mission");
        h.setFont(Font.font("Montserrat", FontWeight.BOLD, 24));
        h.setTextFill(Color.web("#A53860"));

        Label p = new Label(
                "Maatru-jeevan is designed to blend the warmth of traditional care with the intelligence of modern health tracking—creating a safe, smart, and holistic journey for every mother and child.\n\n" +
                "To empower every mother with the knowledge, tools, and support needed for a healthy and confident maternal journey.\n" +
                "Maatru-Jeevan bridges technology and care so no mother feels alone or uninformed during this precious phase of life." );
        p.setFont(Font.font("Roboto", 16));
        p.setTextAlignment(TextAlignment.CENTER);
        p.setWrapText(true);
        p.setMaxWidth(700);
        p.setTextFill(Color.web("#594760"));

        // Side or bg image
        ImageView mothers = new ImageView(new Image("/assets/images/i8.jpg", 200, 0, true, true));
        mothers.setPreserveRatio(true);
        mothers.setSmooth(true);
        mothers.setOpacity(0.93);
        mothers.setEffect(new javafx.scene.effect.DropShadow(16, Color.web("#ec7fa9aa")));

        v.getChildren().addAll(h, p, mothers);
        return v;
    }

    /** FOOTER **/
    private VBox createFooter() {
        VBox v = new VBox(8);
        v.setPadding(new Insets(18, 0, 18, 0));
        v.setAlignment(Pos.CENTER);
        v.setStyle("-fx-background-color: #A53860;");
        Label links = new Label("Quick Links: Home");
        links.setFont(Font.font("Roboto",FontWeight.BOLD,13));
        links.setTextFill(Color.web("#ffe6f1"));

        HBox socials = new HBox(25);
        socials.setAlignment(Pos.CENTER);
        socials.getChildren().addAll(
                makeFooterIcon("🌐","Website"),
                makeFooterIcon("📷", "Instagram"),
                makeFooterIcon("📘", "Facebook"),
                makeFooterIcon("✉️", "support@maatrutwasetu.in")
        );
        socials.setStyle("-fx-font-size: 19;");

        Label copy = new Label(
                "© 2025 Maatru-Jeevan. All rights reserved. | Empowering Maternal & Child Care with Technology");
        copy.setFont(Font.font("Roboto", 11.8));
        copy.setTextFill(Color.web("#ffebfc"));

        v.getChildren().addAll(links, socials, copy);
        return v;
    }

    private Label makeFooterIcon(String emoji, String tip) {
        Label l = new Label(emoji);
        l.setTooltip(new Tooltip(tip));
        l.setTextFill(Color.web("#fff"));
        return l;
    }
    private HBox createNavbar(ScrollPane scrollPane, Node hero, Node features, Node modules, Node mission) {
    HBox nav = new HBox(20);
    nav.setPadding(new Insets(14, 28, 14, 28));
    nav.setAlignment(Pos.CENTER_LEFT);
    nav.setStyle("-fx-background-color: #ffffffee; -fx-border-color: #eab1cc; -fx-border-width: 0 0 1 0;");

    Image logoImage = new Image("/assets/images/final_logo.jpg");
    ImageView logoView = new ImageView(logoImage);
    logoView.setFitHeight(30);
    logoView.setPreserveRatio(true);
    
    Label logo = new Label("Maatru-Jeevan");
    logo.setFont(Font.font("Poppins", FontWeight.BOLD, 18));
    logo.setTextFill(Color.web("#A53860"));

    Button btnAbout = new Button("About Us");
        btnAbout.setStyle("-fx-background-color: transparent; -fx-text-fill: #A53860; -fx-font-size: 14px;");
        btnAbout.setOnAction(e -> {
            new com.priti.view.AboutUsPage().show(primaryStage);
        });

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    Button btnHome = makeNavButton("Home", scrollPane, hero);
   
    Button btnFeatures = makeNavButton("Features", scrollPane, features);
    Button btnModules = makeNavButton("Modules", scrollPane, modules);
    Button btnMission = makeNavButton("Mission", scrollPane, mission);
Button login = new Button("Login / Sign Up");
login.setStyle("-fx-background-color: #A53860; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 6 16;");
login.setOnAction(event -> {
    PregnancyRegistrationForm form = new PregnancyRegistrationForm();
    form.show((Stage) login.getScene().getWindow());
});

    login.setStyle("-fx-background-color: #A53860; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 6 16;");

    nav.getChildren().addAll(logoView ,logo, spacer, btnHome,btnAbout, btnFeatures, btnModules, btnMission, login);
    return nav;
}
private Button makeNavButton(String text, ScrollPane scrollPane, Node target) {
    Button btn = new Button(text);
    btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #A53860; -fx-font-size: 14px;");
    btn.setOnAction(e -> {
        // Scroll to target node
        double targetY = target.localToScene(0, 0).getY()
                + scrollPane.getVvalue() * (scrollPane.getContent().getBoundsInLocal().getHeight() - scrollPane.getViewportBounds().getHeight());

        scrollPane.setVvalue(targetY / (scrollPane.getContent().getBoundsInLocal().getHeight() - scrollPane.getViewportBounds().getHeight()));
    });
    return btn;
}
















    
}