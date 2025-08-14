package com.priti.view;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Desktop;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class BabyCareProducts {
    private Scene scene;
    private VBox productContainer;
    private Map<String, HBox> sectionContent;

    // Cart box container and ScrollPane
    private VBox cartBox;
    private ScrollPane cartScrollPane;
    private VBox cartSection; // cart panel

    private Map<String, CartItem> cartItems = new HashMap<>();

    public BabyCareProducts(Stage stage) {
        BorderPane root = new BorderPane();

        // Gradient background
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #a18cd1, #fbc2eb, #84fab0);");
        root.setPadding(new Insets(20));

        Button backButton = new Button("← Back");
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        backButton.setStyle("-fx-background-color: #d1c4e9; -fx-text-fill: #20102a; -fx-background-radius: 8;");
        backButton.setOnAction(e -> {
            BabyCarePage babyCarePage = new BabyCarePage(stage);
            stage.setScene(babyCarePage.getScene());
        });

        HBox backBox = new HBox(backButton);
        backBox.setAlignment(Pos.TOP_LEFT);
        backBox.setPadding(new Insets(0, 0, 16, 0));

        Label title = new Label("Shop Baby Products ");
        title.setFont(Font.font("Arial Rounded MT Bold", FontWeight.EXTRA_BOLD, 35));
        title.setTextFill(Color.web("#191633")); // very dark purple

        HBox sectionButtons = new HBox(30);
        sectionButtons.setAlignment(Pos.CENTER);

        String[] sections = {"Nutrition & Feeding", "Bath & Baby Care", "Skin & Hair Care", "Clothing & Accessories", "Health & Safety", "Toys & Learning"};

        sectionContent = new HashMap<>();
        for (String section : sections) {
            Button sectionBtn = new Button(section);
            sectionBtn.setStyle("-fx-background-color: #b39ddb; -fx-text-fill: #1a1130; -fx-font-weight: bold; -fx-background-radius: 80;");
            sectionBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
            sectionBtn.setOnAction(e -> showSection(section));
            sectionButtons.getChildren().add(sectionBtn);
        }

        productContainer = new VBox(20);
        productContainer.setAlignment(Pos.TOP_CENTER);

        ScrollPane productScrollPane = new ScrollPane(productContainer);
        productScrollPane.setFitToWidth(true);
        productScrollPane.setStyle(
                "-fx-background: transparent; " +
                        "-fx-background-color: transparent; " +
                        " -fx-padding: 0;"
        );

        // CART
        cartBox = new VBox(10);
        cartBox.setAlignment(Pos.TOP_LEFT);
        cartBox.setPadding(new Insets(10));
        cartBox.setStyle("-fx-background-color: transparent;");

        Label emptyCartLabel = new Label("Your cart is empty");
        emptyCartLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
        emptyCartLabel.setTextFill(Color.web("#1a1130")); // very dark
        cartBox.getChildren().add(emptyCartLabel);

        cartScrollPane = new ScrollPane(cartBox);
        cartScrollPane.setFitToWidth(true);
        cartScrollPane.setPrefViewportHeight(180);
        cartScrollPane.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-background-insets: 0; " +
                        "-fx-padding: 0; " +
                        "-fx-background: transparent;"
        );
        cartScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        cartScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Cart Header (OUTSIDE ScrollPane)
        Label cartTitle = new Label("Your Cart-");
        cartTitle.setFont(Font.font("Arial Rounded MT Bold", FontWeight.EXTRA_BOLD, 24));
        cartTitle.setTextFill(Color.web("#1a1130"));
        cartTitle.setPadding(new Insets(0, 0, 8, 0));

        cartSection = new VBox(8, cartTitle, cartScrollPane);
        cartSection.setAlignment(Pos.TOP_LEFT);
        cartSection.setPadding(new Insets(8, 0, 0, 0));
        cartSection.setPrefWidth(400);
        cartSection.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #ede7f6, #d1c4e9);" +
                        "-fx-background-radius: 17; " +
                        "-fx-border-radius: 17; " +
                        "-fx-border-color: #39246a;" +
                        "-fx-border-width: 2;" +
                        "-fx-effect: dropshadow(gaussian, rgba(50,30,100,0.14), 7, 0, 0, 2);"
        );

        VBox mainContent = new VBox(20, backBox, title, sectionButtons, productScrollPane, cartSection);
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setPadding(new Insets(10));
        mainContent.setPrefWidth(1220);

        root.setCenter(mainContent);

        this.scene = new Scene(root, 1550, 800);

        initializeSections();
        showSection(sections[0]);
    }

    public Scene getScene() {
        return scene;
    }

    private void initializeSections() {
        sectionContent.put("Nutrition & Feeding", new HBox(32,
                createCard("Feeding Bottle", "₹150", "5% OFF",
                        "https://www.amazon.in/Bunniez-SS-Bottle-Silicone-Cleaning/dp/B0DQJMQRX3/ref=sr_1_1_sspa?crid=3GP8U2FDOX17V...",
                        "https://www.flipkart.com/kidbea-baby-feeding-bottle-bpa-free-anti-colic-plastic-free-250-ml/p/itm171230d05504f...",
                        "/assets/images/p1.jpg"),
                createCard("Baby Cereal", "₹300", "Buy 1 Get 1",
                        "https://www.amazon.in/AamumBanana-Porridge-Nutrient-Rich-Homemade-Ingredients/dp/B0FBS5C218/ref=sr_1_1_sspa?crid=80KWVNKRFWZ0...",
                        "https://www.flipkart.com/nestle-nestum-baby-cereal-rice-vegetables-from-8-24-months-bib-pack/p/itm31e8807ded99a...",
                        "/assets/images/P2.jpg"),
                createCard("Baby Sipper", "₹199", "Combo Offer",
                        "https://www.amazon.in/Rabbit-Premium-Sipper-Bottle-Silicone/dp/B09HTV57K2/ref=sr_1_1_sspa?crid=C1YOGPEGN0ZI...",
                        "https://www.flipkart.com/luvlap-baby-bite-resistant-soft-silicone-weighted-straw-sipper-cup-handle-300-ml/p/itm2e70ece044ee5...",
                        "/assets/images/p3.jpg"),
                createCard("Silicone Spoon Set", "₹129", "20% OFF",
                        "https://www.amazon.in/Cubkins-Babies-Silicone-Washable-Reusable/dp/B0DQGQQZ29/ref=sr_1_3_sspa?crid=663UOWXD6RW9...",
                        "https://www.flipkart.com/maaghi-stylish-portable-cutlery-set-case-fork-spoon-yellow-stainless-steel/p/itm36b4305f299ff...",
                        "/assets/images/p4.jpg")
        ));
        sectionContent.put("Bath & Baby Care", new HBox(30,
                createCard("Baby Lotion", "₹299", "10% OFF", "https://www.amazon.in/Himalaya-Sensitive-Phthalates-Synthetic-Dermatologically/dp/B0F47Z9Z4M/ref=sr_1_2_sspa?crid=2N2KDWVH3B4WU&dib=eyJ2IjoiMSJ9.95IJMSYFaE5GmX_d2IAITsARWdHOK2oOqmPNti3j_qNeVnohIsiFLdgMU_QnBPkGrAgX-0WbYgL0PrIMWakS-jiZEOPzPRx60JEpNqtuAVN3A2YLPKDb_BFy8PHtgRjlPG93lhPH1edFvwVPUbStDjbg3eRJo4dqILRr2xzxGLNwYyvIXsrUBASjENC1HToYcvInBxCsH20jU2ZJPO_EDr4_x7-EFFjD-uv_1vz5JR_NynmRYwpWecrZMnPGOKI9DfJnfbPCD3Qwd4rUke4f2CC0WTpfU3w-6fP-0ibfdVc.KDbW_WnW5SHNzqfWypvz1_wyQqVQdb6g8NJejyxoT2w&dib_tag=se&keywords=baby%2Blotion&qid=1753333862&sprefix=baby%2Blotion%2Caps%2C333&sr=8-2-spons&sp_csd=d2lkZ2V0TmFtZT1zcF9hdGY&th=1", "https://www.flipkart.com/sebamed-baby-lotion/p/itmdajdzhb4vzwqg?pid=MSCEXFTYPY4HQEHR&lid=LSTMSCEXFTYPY4HQEHR5V44UN&marketplace=FLIPKART&q=baby+lotion&store=kyh%2Fpl5%2F8q6&srno=s_1_2&otracker=search&otracker1=search&fm=Search&iid=en_57p1CLT2NgfLRkwKyLMGSHp4zINwgMLfAsQt6oOUBDFIfzK0nDMTyOd2MgaTE_lgKOVKwoSyagfkvLF-Cw0UTIQEIsITtCzc4bHaOMTqL08%3D&ppt=sp&ppn=sp&ssid=sya1jsxohs0000001753333927283&qH=999a82931c3475e3", "/assets/images/p5.jpg"),
                createCard("Baby Soap", "₹149", "Flat ₹20 OFF", "https://www.amazon.in/Mamaearth-Moisturizing-Baby-Bathing-Oatmeal/dp/B07Q2B5Z9D/ref=sxin_14_pa_sp_search_thematic_sspa?content-id=amzn1.sym.739e670d-dfb3-4be0-9815-d8c5c0372e07%3Aamzn1.sym.739e670d-dfb3-4be0-9815-d8c5c0372e07&crid=1KX89Z2IBCHWG&cv_ct_cx=baby+soap&keywords=baby+soap&pd_rd_i=B07Q2B5Z9D&pd_rd_r=86e1abe3-83a2-439d-8acb-2fe6013c910c&pd_rd_w=gOIpO&pd_rd_wg=7f7OR&pf_rd_p=739e670d-dfb3-4be0-9815-d8c5c0372e07&pf_rd_r=GPJBQ2XED114TRNQP5DR&qid=1753334013&sbo=RZvfv%2F%2FHxDF%2BO5021pAnSA%3D%3D&sprefix=baby+soap%2Caps%2C297&sr=1-2-66673dcf-083f-43ba-b782-d4a436cc5cfb-spons&sp_csd=d2lkZ2V0TmFtZT1zcF9zZWFyY2hfdGhlbWF0aWM&psc=1", "https://www.flipkart.com/himalaya-extra-moisturizing-baby-soap/p/itmf48tyrytgcz5d?pid=SOPEUBZRWAGG5NR8&lid=LSTSOPEUBZRWAGG5NR8U02I6L&marketplace=FLIPKART&q=baby+soap&store=kyh%2Fpl5%2Fkjr&srno=s_1_1&otracker=search&otracker1=search&fm=Search&iid=en_5F9DQk76_Q2XlxXWs9S2ad_gLx31piZCn07QEqztftRQjzWzZUHrEanqHkAHOGQWj_CZCP3VesqSRe_sg-30PE4IsYyWu-Pj9cxFjFAoaLk%3D&ppt=pp&ppn=pp&ssid=5i2lfep5xc0000001753334118361&qH=a12a121f26c8e69f", "/assets/images/p6.jpg"),
                createCard("Baby Diapers", "₹249", "Buy 2 Get 1", "https://www.amazon.in/Teddyy-Baby-Diapers-Medium-Count/dp/B09ZHTRS6N/ref=sr_1_1_sspa?crid=FX6P0UR6IXML&dib=eyJ2IjoiMSJ9.8X2CGrGUfUx8N7ix4BHhNbp1wsPOm4PbRnfCodKKSh67qEDUDsEqJWd_-jZ7DVOS2SG80kFifRZM1U0DxvLW-rVVoNKMwBpPlu66hghh6hD79lTxu6MoswPt6cjMq9g_iBuG_tvYi0rAtMkjRmnH5irwRnrzA5KXBOxYmJtxdSClcNVstBMuRntXxLtLMSzU5FopVt6CydIGKfepeYJ7uljJ6O4FnMVSBpey-e5qFdbV9k5C1-tCOP_vz7BUXBVNH3zUWT6HzRWXLfbe7VY8Knm78HZXbaEDXgKerOs7sIY.mkcCINvtVr9_-4Y8mh2bkjVmOjzSYu-mL3oTKYrzpEs&dib_tag=se&keywords=baby%2Bdiapers&qid=1753334165&s=baby&sprefix=baby%2Bdiapers%2Cbaby%2C320&sr=1-1-spons&sp_csd=d2lkZ2V0TmFtZT1zcF9hdGY&th=1", "https://www.flipkart.com/little-angel-easy-dry-diaper-pants-12-hrs-absorption-medium-size-7-12-kgs-m/p/itmb6057dce10b38?pid=DPRGGJVMMRDSKZEV&lid=LSTDPRGGJVMMRDSKZEVN1RVSP&marketplace=FLIPKART&q=baby+diapers&store=kyh%2Ffdp%2Fyvf&srno=s_1_1&otracker=search&otracker1=search&fm=Search&iid=en_u9vBrgIByMGq7LyLs_uKOEZOUtW0T8caDnUo2MHzjZey9f1FVoKAaeiq8WHHB-fWHAymTCay9VcfKfboQMJb_k4IsYyWu-Pj9cxFjFAoaLk%3D&ppt=sp&ppn=sp&ssid=0cx21vysm80000001753334218593&qH=e8008f64cb8db04c", "/assets/images/p7.jpg"),
                createCard("Baby Wipes", "₹99", "Flat ₹10 OFF", "https://www.amazon.in/Littles-Soft-Cleansing-Baby-Wipes/dp/B088TZC4B6/ref=sr_1_1_sspa?crid=3CZSDGG7DF4EI&dib=eyJ2IjoiMSJ9.BfiBr25A5_Z3JqFDyPxHGgHX9wphO774BW6XqratoPGWOtW70EFWUl37tgvAaLgQ-f9oEIPqc2SRKxcMfNZzoPwg9PZpEcrcyKVcQby68oc8aZUuuZdI_Oun7-UK6g_Gck8-WlK_-3SYSfLc1ENEv3wbmpUOUKjIAEnZPxYtlxEjzzY62YzfVa4H9dZsjhb4oPkUtij_4X-rKLXYNPQs7KqaS33fNynDR0a5yC-W9ewWS8K6d_wwH_lr32sxs004DzhaSqRiQJD9Zkvfky70CQzs4Qj75qVDzHgz4w3wPcc.bbFti1FnIwf2u3z15xZd7tYR9wbP7Zeh2LhNj3MurW4&dib_tag=se&keywords=baby%2Bwipes&qid=1753334279&s=baby&sprefix=baby%2Bwi%2Cbaby%2C1187&sr=1-1-spons&sp_csd=d2lkZ2V0TmFtZT1zcF9hdGY&th=1", "https://www.flipkart.com/cub-care-baby-wipes-aloe-vera-jojoba-oil-vitamin-e-paraben-free/p/itmef8cb71899ff1?pid=WIPGQ6AFTR6FYVEJ&lid=LSTWIPGQ6AFTR6FYVEJSDWXBK&marketplace=FLIPKART&q=baby+wipes&store=kyh%2Ffdp%2Fkvq&srno=s_1_1&otracker=search&otracker1=search&fm=Search&iid=en_fEHQTrCAv5P1EWdi8sFAOijH1CJ01VZaKmCWP43tov9TMtcy33ThYwP8AXH56XeiVBN8s4-A0Bbu8KC0ZwElPPUFjCTyOHoHZs-Z5_PS_w0%3D&ppt=sp&ppn=sp&ssid=tjiv1ncyf40000001753334459018&qH=2a92a853a070ee65", "/assets/images/p8.jpg")));

        sectionContent.put("Skin & Hair Care", new HBox(30,
                createCard("Baby Shampoo", "₹159", "No Tears Formula", "https://www.amazon.in/Mamaearth-Gentle-Cleansing-Natural-Shampoo/dp/B077HZHB45/ref=sr_1_1_sspa?crid=1ZYCCRJLK2Z30&dib=eyJ2IjoiMSJ9.VwZRL2EGCY67uwzNaHplT2vKul4sqoasgw6vuaCnwbIk6ouqbZlvUSW1I8BheGvrpbox94CU_Et5OG_SIG9PypknjIm7f1U_-1RnVtFmZ6TxZZrxYPfQVHS5ExeiK9NfnXDOEpaGB0e_bY3KAss92C5ANHIPpGVUgn_wjX86yFcvsfuGhVWpyu_if1fAj-bKnnmvv6P11-LtnFgBjlKgwUIY6F6hDHXbrltTfCfIZnLTTZBUCJ0HcTdO-tjBuH6IGLRS4ACJktHYWmzCcELLwRI-N8MCugTi_rjJNxJR0XE.h8PhGhJkY8qRotdky8ANl5SGLdTT7W2fmSWcPyi2Euw&dib_tag=se&keywords=baby%2Bshampoo&qid=1753334526&s=baby&sprefix=baby%2Bshampoo%2Cbaby%2C347&sr=1-1-spons&sp_csd=d2lkZ2V0TmFtZT1zcF9hdGY&th=1", "https://www.flipkart.com/parachute-advansed-baby-shampoo-kids-tear-free-tender-coconut-water-virgin-oil/p/itm739937d84431c?pid=SMPGSF99YCGDVKH6&lid=LSTSMPGSF99YCGDVKH6PXMWDF&marketplace=FLIPKART&q=baby+shampoo&store=kyh%2Fpl5%2Fckx&srno=s_1_1&otracker=search&otracker1=search&fm=Search&iid=en_SWnPEUHadUvppoN_KO9zvNQNG1sPe7t9ir5S0b6aeN9IoUr7cGwpncOZG2ejP52scDxNYWkiu59T6_GaLFBunvUFjCTyOHoHZs-Z5_PS_w0%3D&ppt=sp&ppn=sp&ssid=lt388edm9s0000001753334584097&qH=7ec79ba5264fc8fa", "/assets/images/p9.jpg"),
                createCard("Baby Powder", "₹129", "Free Travel Pack", "https://www.amazon.in/Johnsons-19401882-Baby-Powder-400g/dp/B00412TCQG/ref=sr_1_1_sspa?crid=15NXJK9ZWDFBH&dib=eyJ2IjoiMSJ9.Y4no0WzYBhVH3SsxpsCD6RXbOU-jxpPr_n8ZKAhZt7psFqYoaH4e14lHKCHVea4RuiqtquCLuf2yKrzrmZO1LIOdsryKu44Gr9j4vhvkUYDb_-8Ci7ZALw5Pn88ClWg9SibM1h5iO_ub9OfXYvdFJZJuUAr67MLjkSWv-KfhlZJoMyi0A3JiY_uVzR8ai9S0JBweEwELxrHcdQVIqj8wlWff8DDG9MbbKplRGP01Ites29h82-dlfC9zGvSlb8I9DIh-W9litGvt6EBHU_b1a4y3aqpUb-FaPAZkusGaWjY.GaC4zs5hxxp_1IUdsBUkqPeLeTLmlta2MYpjvGLM51o&dib_tag=se&keywords=baby+powder&qid=1753334654&s=baby&sprefix=baby+powder%2Cbaby%2C307&sr=1-1-spons&sp_csd=d2lkZ2V0TmFtZT1zcF9hdGY&psc=1", "https://www.flipkart.com/softsens-baby-powder-200g-pack-2/p/itm882e24eae1a8b?pid=TLCFAFJTWYXFGKVR&lid=LSTTLCFAFJTWYXFGKVRFNKNK5&marketplace=FLIPKART&q=baby+powder&store=kyh%2Fpl5%2Fa0b&srno=s_1_1&otracker=search&otracker1=search&fm=Search&iid=en_ShGeOHjuVVc8PUE5qFsWd4ztjqaWM4j6GxB8z3B-SMcCmAIAxX6xgZ_aMP9FKezkk1G6yeXe14A1Hcu9vgyAhg%3D%3D&ppt=sp&ppn=sp&ssid=s3iow4q3740000001753334701402&qH=c7716f8b4d55be0a", "/assets/images/p10.jpg"),
                createCard("Baby Cream", "₹199", "Moisture Lock", "https://www.amazon.in/Aveeno-pediatrician-Hypoallergenic-fragrance-free-paraben-free/dp/B0DCVLFTVL/ref=sr_1_1_sspa?crid=LNSQ191QXQDO&dib=eyJ2IjoiMSJ9.52sSrzkQAZrawyKYCswlj9hPMFEcKBUWsZSXIr-R3FJ-0WrhQ9AXpi22lmCclmfeU_j5Y7MxIkHrEE5WSxkSHr8d_pjSqJdU7JB3ophi5nFR7kf6IW_I4DBWvd2Q9oUaiW10ZtT87DtLPmmr2NBOpGX8LiJq9GoKV2-7DQ11HMUSeFvn9jl9k7z3gOR8ax15spAk9zH4VPTDayAKV_mCqr1T12xQRpHCsyh9oYoiT-6buHBvVTXBsMcvxbBPGM4qTCcdfX2Zd81ka0L6nSIOj2Lk0VAQOyNNxd9zr8B7eYk.7W6wtvTyTtBsFHTWdrTADZhrXcTjL_d5gEwpr0-XUII&dib_tag=se&keywords=baby%2Bcream&qid=1753334768&s=baby&sprefix=baby%2Bcrea%2Cbaby%2C317&sr=1-1-spons&sp_csd=d2lkZ2V0TmFtZT1zcF9hdGY&th=1", "https://www.flipkart.com/softsens-baby-cream-milk-shea-butter/p/itmeuzhhdpeacvcy?pid=MSCEUZHHY9SA3KJ8&lid=LSTMSCEUZHHY9SA3KJ8T0STQG&marketplace=FLIPKART&q=baby+cream&store=kyh%2Fpl5%2F8q6&srno=s_1_1&otracker=search&otracker1=search&fm=Search&iid=en_xdLNcN8umscCrCGV2g-FRKoobtkWC266OPWF6ViB5n9KHEXKQIcrusWbNoBw8PVGeh4AokduDhcOBCRF-cnG1g%3D%3D&ppt=sp&ppn=sp&ssid=wuoam0rir40000001753334803203&qH=1eb92be915ce6dc2", "/assets/images/p11.jpg"),
                createCard("Hair Oil", "₹179", "Mild & Natural", "https://www.amazon.in/Himalaya-Baby-Hair-Oil-200/dp/B085W98FZC/ref=sr_1_2_sspa?crid=3QG9NW9U3GHD9&dib=eyJ2IjoiMSJ9.5zY1uzB2ge7br3gvqxslxtN_uwyzcSDAOXXuypogs7fVuChHNYQB3jn5HCgo200FJEHffX9fw8ckpTTCrKSs_qIJyNyOs75fd0z6i5bUxAWpE4SITQhky97nR0DuxSkrgv-prn5MTHv6V9WhNOtZTu5bYrp8OndQ2LIROkQWIl6z5zb7O5LmLPydjzIL5CTZ4Sl9lvgcx7RJSx5V6uLYrF8HqFlX4ylBpOtabUnGmgvG7epMMDgKVPfuNi5Ahcq1rLJHWi82G4r0-oFSge6VtEPE6vbxfWM8Mul_1tonDE8.U5Mrsdc9DNBiEDMY011ktdRNpHPuU_jmODPLSIA3DXw&dib_tag=se&keywords=kids%2Bhair%2Boil&qid=1753334861&s=baby&sprefix=kids%2Bhair%2Boil%2Cbaby%2C302&sr=1-2-spons&sp_csd=d2lkZ2V0TmFtZT1zcF9hdGY&th=1", "https://www.flipkart.com/mikami-kids-hair-oil/p/itmd82b2872baaef?pid=HOLGG5ZTVVYRGXUT&lid=LSTHOLGG5ZTVVYRGXUTVIJ6EA&marketplace=FLIPKART&q=kids+hair+oil&store=kyh%2Fpl5%2Fxhj&srno=s_1_4&otracker=search&otracker1=search&fm=Search&iid=ef096c65-33f3-4cab-a8d6-6566aeb5c0e9.HOLGG5ZTVVYRGXUT.SEARCH&ppt=sp&ppn=sp&ssid=yesihudfs00000001753334909822&qH=1f570d6d413d1f0d", "/assets/images/p12.jpg")));

        sectionContent.put("Clothing & Accessories", new HBox(30,
                createCard("Romper Set", "₹459", "Extra ₹50 OFF", "https://www.amazon.in/EIO-Multi-Color-Cotton-Romper-Jumpsuti/dp/B0DWFLDZ8K/ref=sr_1_5?crid=3O6IF9PFZODLH&dib=eyJ2IjoiMSJ9.TymDyq61DB09qFqC30iuLAIEs9uO3EnTdzL7t2s9fLTSyArK7r0lFhjyFkk7Y8wEhRo6zgFfVMNmRvZBmL6evQM308uzyUaJR7Qd-1T__5ACpYfD5aTxB8HxlpdcLUV9-uf-QXadkMicqrwFdzvNMAilWz-tIF39yCBdnckpFxeQToE-zaBIqERhZ0I4gtxXs5STj8iPAFasxxVM2dUW8BpFGgUX4EAU5Df34F_V9gPeYh6SRbh9K83PHhvdIoeZFP51bwIVOzbpE3elu6NNRMOoAYgy2886HqD02bApzEo.IaZD8pQEGLh7rOMAXIn4_nySAedq61sTpSRMcojYV0s&dib_tag=se&keywords=romper+set&qid=1753334985&s=baby&sprefix=romper+set%2Cbaby%2C327&sr=1-5", "https://www.flipkart.com/kidbee-romper-boys-girls-casual-printed-striped-solid-cotton-blend/p/itmad4d48800f791?pid=KDRGYDGAGHQRQPVK&lid=LSTKDRGYDGAGHQRQPVK667NKV&marketplace=FLIPKART&q=romper+set&store=clo&srno=s_1_1&otracker=search&otracker1=search&fm=Search&iid=en_57QwMa7vVgLD8VRgy7y5ruEuNa-jhVQ-plYsxqchGgwYSBR3HJNnSSxvcWaSkGB41W5RR2mAkfRM87nFh3ThgA%3D%3D&ppt=pp&ppn=pp&ssid=cdxym56o680000001753335019155&qH=f76f37aeae0d2d53", "/assets/images/p13.jpg"),
                createCard("Mittens & Booties", "₹199", "Winter Offer", "https://www.amazon.in/Baby-Eli-Newborn-Premium-Mitten-Booty/dp/B0BQRP2K77/ref=sxin_14_pa_sp_search_thematic_sspa?content-id=amzn1.sym.9b258dad-f808-4e5e-8df5-9b8946a31daf%3Aamzn1.sym.9b258dad-f808-4e5e-8df5-9b8946a31daf&crid=1959H1K0HH7EL&cv_ct_cx=mittens+and+booties&keywords=mittens+and+booties&pd_rd_i=B0BQRP2K77&pd_rd_r=9ed71bd1-8432-4dc5-a903-31bc7fcfbe3b&pd_rd_w=388de&pd_rd_wg=90Mgy&pf_rd_p=9b258dad-f808-4e5e-8df5-9b8946a31daf&pf_rd_r=VPBD3JK9FQ6DK6FP7A9Z&qid=1753335102&s=baby&sbo=RZvfv%2F%2FHxDF%2BO5021pAnSA%3D%3D&sprefix=mittens+and+booties%2Cbaby%2C304&sr=1-1-883a54c7-f466-4d42-997c-6d482a360a1a-spons&sp_csd=d2lkZ2V0TmFtZT1zcF9zZWFyY2hfdGhlbWF0aWM&psc=1", "https://www.flipkart.com/luvlap-100-cotton-baby-caps-mittens-booties-combo-set-0-6-months-pack-2/p/itm73ef8a9662e05?pid=KPBFYE2UMZ6E2Z3R&lid=LSTKPBFYE2UMZ6E2Z3RB7RXMZ&marketplace=FLIPKART&q=mittens+and+booties&store=kyh&srno=s_1_1&otracker=search&otracker1=search&fm=Search&iid=en_NPC2EySlSuUn5letde4oaBGHhpnBlOjAySkaReUnL8IBlpoxXnknTPpvGuowViODi4-b_3gkNDgnholKnmAnjw%3D%3D&ppt=sp&ppn=sp&ssid=gk7oefvhhs0000001753335195675&qH=4a45f9a456d713d7", "/assets/images/p14.jpg"),
                createCard("Cotton Caps", "₹129", "Pack of 3", "https://www.amazon.in/Aashiya-Trades-Unisex-j01_Red_0-Months-12/dp/B09BG2G5RZ/ref=sr_1_4_sspa?crid=22LCWKFK8RLZB&dib=eyJ2IjoiMSJ9.gWLF-Lse_l2CuPE6JuKojUiVWTr1XZ6tIpH1w78XR3dAIwHZ67a2YHxdDDrFo4LITZqMke06h-Wo7K9DYiwtXpIXeVU4C1UyLCHzakrNtSt0KfaUOc8Wi1hGCitsHMUCCmQuQDcCHxOj2VwAQE0sSfYnR0JjvCizp0UlFHSbuaHU_xyYQWNzEJo21ZwZ-oH_loTt___ho-FajmgQT_bNxtXXUl5zxbhq85Cn-vfgJZzrjLZjMNBDvToUbWJBUUrAUkvHJtM5Zlyp6EzlZilyHcEC-oNiErc9ve95p4FvwLU.SC92Jqbj-rXyJVzzEDIJ40CijtRogufFuJ3tfi2ZDJ8&dib_tag=se&keywords=cotton+caps+for+kids&qid=1753335280&sprefix=cotton+caps+for+kid%2Caps%2C353&sr=8-4-spons&sp_csd=d2lkZ2V0TmFtZT1zcF9hdGY&psc=1", "https://www.flipkart.com/romzen-kids-cap/p/itm30204a5f5eac3?pid=KICH9TV5CFH2GGE3&lid=LSTKICH9TV5CFH2GGE3STJLFU&marketplace=FLIPKART&q=cotton+caps+for+kids&store=clo%2Fqd8%2Fy9u%2Fc32&srno=s_1_1&otracker=search&otracker1=search&fm=Search&iid=en_VXE_AgxCfPBU2KJJro8CG_S6qJZgm2fV1xu8ZDIu-k3_ObzmTZB5yJitxgmH5vGnpjfXxd6_MtN0TKsFm2n88Q%3D%3D&ppt=sp&ppn=sp&ssid=ryr3n9wl800000001753335324203&qH=63432adedfe4accb", "/assets/images/p15.jpg"),
                createCard("Baby Blanket", "₹549", "Free Gift", "https://www.amazon.in/OYO-BABY-Hooded-Blanket-Wrapper/dp/B0BL3ZL5JW/ref=sr_1_4_sspa?crid=3KKVYJEM0IYU0&dib=eyJ2IjoiMSJ9.tSx0RxgjJz1-y_pvd5xpw4pijjX6Q6RHZekovPXfcwsiS9fPSZKZzoGzWq7ebs72yrLIIlUGKZW1a2T0TPlV51JQTXAkdtJzhXT_hIfxyNhbvbzXJFOznBrAxljYT1sa50ZEqjpljxMAlY-fTy3YAiByMXCpck82_h-c739GFb7moK738PknGaepfTd83-6RP28MOn__-23g-PWJFU9Fm8swagGaw0ION_CYmPt2FQY01H43U5oGrKPA823beQM5ojTvqgAxMz0chPGRLCJC_bZITRY1vJsv3zUH9deIrhM.QylRSWD5xvEqSDLc9rmeep85oyNuqjN_geqnDelU1U4&dib_tag=se&keywords=baby%2Bblanket&qid=1753335378&sprefix=baby%2Bblanket%2Caps%2C300&sr=8-4-spons&sp_csd=d2lkZ2V0TmFtZT1zcF9hdGY&th=1", "https://www.flipkart.com/brandonn-printed-crib-mink-blanket-ac-room/p/itm05f4b85165406?pid=BLAGG6JDHHYHPVRM&lid=LSTBLAGG6JDHHYHPVRMD7QT6N&marketplace=FLIPKART&q=baby+blanket&store=kyh%2F7j3%2Fw47&srno=s_1_1&otracker=search&otracker1=search&fm=Search&iid=en_DN-tcHHCKfmr5LDPZWdqXJzqHP0Io2aKRZ0IZLoSVMfSuahLdDJIF9T2NOFwC4Hd5ukO3x0lsm8cSs9a1oRiow%3D%3D&ppt=sp&ppn=sp&ssid=92uui9uxts0000001753335437066&qH=11e8c706fe0ecf5c", "/assets/images/p16.jpg")));

        sectionContent.put("Health & Safety", new HBox(30,
                createCard("Thermometer", "₹299", "Fast Read", "https://www.amazon.in/SYNOCARE-Waterproof-Thermometer-Measurement-Temperature/dp/B0DPQP2N2T/ref=sr_1_4_sspa?crid=22442TW67MA36&dib=eyJ2IjoiMSJ9.RS5NtGYg9FGWr9tr9wEFCqt7PePl-1Agu6382ktdlKLuaQJeqWpigvq5XBIO47ECOklLNb-gN7iZ2QOc86Fg3AoyK1uZ9u4RIGiuDkjfy65w4a0foEHUxpjF5RTa4jaQ_lbGOA7F1lyeECjQHNQppR8MPvHr_Vm0aM_GLAI0cBKO870bsG2vNv3x5jMoa01iQpM5sfSpuUIsDeLETGEFxLMmbHq2rq-JXBX4FRrx_iKwMGCeY8egEUsPfz6HRf1L0W8SRs27qdjf7tmWtIPgkp3moMxkrgF21H9G3zMaLF0.gJ9BrqUm5OmSyx-IWB4GwR_stxiU8psHqXg7Lva-SKM&dib_tag=se&keywords=thermometer&qid=1753335531&sprefix=thermometer%2Caps%2C293&sr=8-4-spons&sp_csd=d2lkZ2V0TmFtZT1zcF9hdGY&th=1", "https://www.flipkart.com/tata-1mg-t1mg-dt03-flexible-tip-digital-thermometer-one-touch-operation-child-adult/p/itm9fd74d2fc539e?pid=DTHH2TTQQHGZHPAK&lid=LSTDTHH2TTQQHGZHPAKYGELP1&marketplace=FLIPKART&q=thermometer&store=zlw%2Fnyl%2Fbvv%2Fh7c&srno=s_1_1&otracker=search&otracker1=search&fm=Search&iid=en__8qTculujL-nDgWVcAH56Bi_RwKTYjv1FUK8OYRKCcK3MeLj0dxJMcFmayj2mxXn78KC498FJHVfeBDtogFJmA%3D%3D&ppt=sp&ppn=sp&ssid=ask0w9jzbk0000001753335569940&qH=b6a657fe9f0e8ae3", "/assets/images/p17.jpg"),
                createCard("Nasal Aspirator", "₹199", "Safe Design", "https://www.amazon.in/Pigeon-Nose-Cleaner-Blister-Pack/dp/B0063BML16/ref=sr_1_1_sspa?crid=1KX0G4G3AJWOQ&dib=eyJ2IjoiMSJ9.V0InSe_clIfitKY3Fch5lNTStFOpS-WdmN7xhpUr_8r0Vja8wmD5IngXDy4FNyMdEgKZJ5fDI9JktD6pGIpqU7MV3UziwDJh8wKNJ8e3_eaakaNwwJknHQnNybSSS1OXY8OjgP1zUgp3L6Fv1y0jd-RTdcbmYh7Ylg1uI_qqXdXbFxFPy2tMg-HdklSiVb3OrGk0Nere6MXPj_aBq2DVb2iawF7AdjeFkYGDOp1RsuT0RIBdufnVQcTXlJUZL59opdmDNS3pd3UGCOyZ8dsKK4RqIFFYrIKAVWb5UMwbC4Y.niBlR2yXLzHFHIYSevQbOjCHSASviGsXcWopYFWQrHs&dib_tag=se&keywords=nasal%2Baspirator%2Bfor%2Bbaby%2Bnewborn&qid=1753335631&sprefix=nasal%2B%2Caps%2C366&sr=8-1-spons&sp_csd=d2lkZ2V0TmFtZT1zcF9hdGY&th=1", "https://www.flipkart.com/crescent-nose-cleaner-nasal-aspirator-0-month-babies-safety-nozzle-manual-aspirator/p/itm12e12a69072ab?pid=NASGV77JZZRQFT9Z&lid=LSTNASGV77JZZRQFT9Z4SNEDZ&marketplace=FLIPKART&q=nasal+aspirator+for+baby&store=kyh%2Fg83%2Fxf5&srno=s_1_2&otracker=search&otracker1=search&fm=Search&iid=en_lzcvNyCOXaoMP4yrnGs0VwNc1vd2MsKE9lou19vk081End8FtcyyhKTfvr2rCgIpT3JllHHW-OwjRha7Z8J2Kw%3D%3D&ppt=sp&ppn=sp&ssid=j55980ttr40000001753335681163&qH=0a9219fafc154027", "/assets/images/p18.jpg"),
                createCard("First Aid Kit", "₹499", "Essential Items", "https://www.amazon.in/Plastic-Medicine-Emergency-Portable-Medical/dp/B0F6LHS4H6/ref=sr_1_3_sspa?crid=1M7EFE5AK75T0&dib=eyJ2IjoiMSJ9.83uxN71ACFeFaDtp6xrT_Z4CcTOBkcTJL5S8djHhM_U2zadjoDVWp6B5mPgg_XXqpy-uJBsh3oZ_H0dlkhTHg7ZC_QgJ15SPtCIzkQfWgFTAa3c7G_uo8wgyuItlDzhfv-pyQIgSRJwzHZiapOp0dd9_qLl1fShjzTWvAUNk9d5nRIn4KUphQKewvFq7aEtYZEuijqSIHGQPzrM-zYD0KE4MMRJeLpfnoEc6VfrUrPKxeRR052oC-uaM3EKDApgxYPel7y8pZJjBSV7M0lPlGy27P-COLSag9sD4g2O38G4.lYygjfWnFDQPIBQhyySkpUMGtZp7I3rJAjZe4N6ltyk&dib_tag=se&keywords=first%2Baid%2Bkit&qid=1753335743&sprefix=first%2Baid%2Bki%2Caps%2C382&sr=8-3-spons&sp_csd=d2lkZ2V0TmFtZT1zcF9hdGY&th=1", "https://www.flipkart.com/jaish-advertising-first-aid-kit-box-emergency-medical-box-first-box-home/p/itmc64a6ce006229?pid=FAKH4GTZRGQNVD64&lid=LSTFAKH4GTZRGQNVD64YEJVEP&marketplace=FLIPKART&q=first+aid+kit&store=search.flipkart.com&srno=s_1_2&otracker=search&otracker1=search&fm=Search&iid=en_zPQSn1Va15KEXhAVh00TtmvM84KrC_o07W9cWyrLHLNlxtGdlDQLn_bVzTQkxoqTp6eqQDnWScZCWSBSCGVoeg%3D%3D&ppt=pp&ppn=pp&ssid=1tgaczj2hc0000001753335790448&qH=94e48378ecf888f6", "/assets/images/p19.jpg"),
                createCard("Corner Guard", "₹129", "4 pcs Pack", "https://www.amazon.in/AMAZARA-Proofing-Pre-Taped-Protectors-Furniture/dp/B07HQDWMV4/ref=sr_1_6?crid=31IFHUZLK9R9L&dib=eyJ2IjoiMSJ9.V1sAnfdnlAgo2CDtBiaVim8J3QXvGjDQvgSII09QpRhbSMJaxfyIz5j-fysoBj0RFieMhTg7Ig0phWC-AJ8_GpR9mmNNq0d03TGCySTJbSzcDRpZJ6SCXl-ABVGfecX9Q1nobzIu3VZXRiv_DIb6iMSEIUef5zIaRStdkkTNJ_rK0QGy7szJO6bkvGVMvcj1upmTtL0wRHop92YIaKFOE1WdDcgKTrzyusd92fu1ab2tciwpSI_YwQUE5-PBE0uk3r7kw7W4uE_q6QjM37rqj9yqlt9dyR5Rm2XEqfaz_L0.fJ5DOvg9uKkGE2KxVU9_hUOfiEI5x9kQyqgz6nEEu0s&dib_tag=se&keywords=corner%2Bguard%2Bfor%2Bbaby%2Bsafety&qid=1753335870&sprefix=corner%2Bguard%2B%2Caps%2C333&sr=8-6&th=1", "https://www.flipkart.com/klapavriksha-pack-12-silicon-corner-protector-table-guards-baby-safety/p/itm54fbb1a571602?pid=BPFH4NG6YDJHGXRQ&lid=LSTBPFH4NG6YDJHGXRQRWDICM&marketplace=FLIPKART&q=corner+guard+for+baby+safety&store=kyh%2Fvxo%2Fppd&srno=s_1_1&otracker=search&otracker1=search&fm=Search&iid=en_UwR1FPGCIBWpfa02yQHl2MWLdmY5dMQEOQ9p4Kuopid2y0j0rGY2E_4tW2dxQGrTgJJ9Yfp5mtEF2mwx-0FgGw%3D%3D&ppt=sp&ppn=sp&ssid=za3ihl89q80000001753335949994&qH=6a535f154f19a74a", "/assets/images/p20.jpg")));

        sectionContent.put("Toys & Learning", new HBox(30,
                createCard("Rattle Toy", "₹99", "Colorful", "https://www.amazon.in/Rabbit-Spinster-Friction-Windmill-Interactive/dp/B0DVPSLKN2/ref=sr_1_2_sspa?crid=KCT1COZJFHW7&dib=eyJ2IjoiMSJ9.PfW-O-ax3WwHCWMcgx15WIr1qz1TBdcRaMR29z06hx0FfMTyee9PiaqUlcJ9fVq41s45Ap2jBgMORJXgNYq4_t0BYOtgyszrD_dyKEyuqvwQG3BnY1ZcLeVT4c-b9MccSqyH-8XyX-xApLyPjcANhjp-YU1_IAM-Cc-L8ZPG7QDC2sC34m-JJUlvxrlF496DtUJFPQJ5v_tb5MkreY6-FylN2OUubzetiLLP1f9WwUHUryJMDaU9ACeHI2wPBR3IqvLqRQulKIAJXU9NDNnjoh96Wkcr3BnzgPLpAdLsLGg.gy_KetQQuWBEJ_78MTUaTEQ3YYjHk_xa7rn0_o9pJJA&dib_tag=se&keywords=rattle%2Btoys%2Bfor%2B1%2Byear%2Bold%2Bbaby&qid=1753336015&sprefix=rattle%2Btoy%2Caps%2C396&sr=8-2-spons&sp_csd=d2lkZ2V0TmFtZT1zcF9hdGY&th=1", "https://www.flipkart.com/babygo-rotating-giraffe-musical-rattle-cot-mobile-cradle-bed-jhoomer/p/itm8214d31d23db7?pid=BBRGUGPVF5NZC8GK&lid=LSTBBRGUGPVF5NZC8GKAVQDU4&marketplace=FLIPKART&q=rattle+toys&store=tng%2Ffkw%2F37y&srno=s_1_2&otracker=search&otracker1=search&fm=Search&iid=en_thpJeQpm-_D4_o08bKXljHxcWK2--MKOeaJncmXZSR3mrl7QggObfVv2huy1gUXjZsCHe_h2Q-dM2piCM2-4Iw%3D%3D&ppt=sp&ppn=sp&ssid=b4khkqrqio0000001753336063002&qH=eb8f165250c0bf81", "/assets/images/p21.jpg"),
                createCard("Stacking Rings", "₹149", "Motor Skills", "https://www.amazon.in/TOYZTREND-Stacking-Educational-Multicolour-Construction/dp/B06ZYMFPX6/ref=sr_1_5?crid=1RTQARSTKE3IO&dib=eyJ2IjoiMSJ9.y0f1eQ9Rn5IfdlyQrQWU_Tfq2VyTu1LPl3CjiYioM1kI4LKk3L0hmF4lijw_ZmWPoL2zr-kCctbRC5w3apQrkNBZjU4czjjjkg-TgOwO1z4Xm8NqNtz8dsoM_t69C5wKk_6Ce9gV0ZCispthIQMgIT9Kd3S51bolnqA1wyq6rIjlyABoYW5NvJxI86QYuX9xR2kLhNr1-ZfVoeiJuMDR4DABiuVAMXScsUMbLkaEHR8Vg-2v0OvCGZgjJ5io0unuvW7NJuLlOlPXz4btraAmhjZH0iORS00V10VtrYhsvqA.9KYRAmJiEqgRwjRN7i2ci0S7suOFZAJ5fhiljsEX2Rw&dib_tag=se&keywords=stacking+rings+for+kids&qid=1753336140&sprefix=stacking+rins%2Caps%2C336&sr=8-5", "https://www.flipkart.com/aexoniz-toys-blocks-kids-house-construction-building-boys-windows-toy-set-smooth-rounded-edged-diy-combo-plastic-house-72-piece-multicolor/p/itm5e88bbf4854fe?pid=STYG7S4GR5WJE3WM&lid=LSTSTYG7S4GR5WJE3WMLNNAOC&marketplace=FLIPKART&q=stacking+toys&store=tng%2Ffkw%2Fxon&srno=s_1_6&otracker=AS_QueryStore_OrganicAutoSuggest_1_9_na_na_na&otracker1=AS_QueryStore_OrganicAutoSuggest_1_9_na_na_na&fm=search-autosuggest&iid=en_LTmsL0W12jDV0QoLVyq6ywt9uvDN7bMazsntTHTYEf93aZV5mSzIt-UUFEY-29o1KvBZJUoke6yDEl9BIOwokA%3D%3D&ppt=sp&ppn=sp&ssid=okjw58qmk00000001753336176649&qH=ed52f6d42ec7bccd", "/assets/images/p22.jpg"),
                createCard("Soft Book", "₹199", "Cloth Pages", "https://www.amazon.in/Firstcry-Intellibaby-6-Book-Set-Recommended/dp/B0DDQ3HNCM/ref=sr_1_5?crid=32MY3B6FVFFKO&dib=eyJ2IjoiMSJ9.GoRv4NHD4NSbx-N-nirQHyd2-6JrN04DFgqaf0moiKLGTMmxBjK2Xbmq66WXf3J0zs2y3gCEJgaJs7ohI82kwxiTvaQDddbNz-hH0liUTrvND_kH_A_sUxV5ZeF6umBUPXgdSaPjwXB-RkXpT3TSjMw7wPvcGU_4DdsjPD_zXJyQ3KQMUV9-iU9aFCqgD2_zuvVCyAfIgy62FA45Lnd2cdrgUgXe3su33IivUXNSIDFcc0kZ5tY6lYtJKLrQQw4wJDPO-RDm1D_VYbpmGEp184g6tyLdocfhCV_PNeTsP7I.9mYXN3oHD5wmRBRkX3tKzIvI0ep-sYeebGVp4-cuAdE&dib_tag=se&keywords=soft+books+for+babies&qid=1753336253&sprefix=soft+book%2Caps%2C339&sr=8-5", "https://www.flipkart.com/handiworks-my-art-craft-book-2/p/itmce47f77bd76a9?pid=9788193669945&lid=LSTBOK9788193669945LXCNRZ&marketplace=FLIPKART&q=soft+books&store=search.flipkart.com&srno=s_1_3&otracker=search&otracker1=search&fm=Search&iid=ec371173-66ee-45e0-b1b0-c2b689a7a996.9788193669945.SEARCH&ppt=pp&ppn=pp&ssid=h3wkf02x1c0000001753336292833&qH=081ad2083c044f17", "/assets/images/p23.jpg"),
                createCard("Musical Mat", "₹499", "Sound Play", "https://www.amazon.in/Cable-Musical-Keyboard-Piano-Gym/dp/B07TCPBJK6/ref=sr_1_4_sspa?crid=1OD1EZ2L4NH3R&dib=eyJ2IjoiMSJ9.BBXqLQV9Ky5P-oI88F_wDXiy3f7DZFBz5iEKZvtZRR8GivJdMaewb19Rj4kN2kBIBL-SUKKl3z7jF-oc6pxVdtNf2FxXv5HykptNSjDG8O8ONlvdEQZyQ7lu0V1AcisofdqMpPljzbhynn83ok7YE5pPQvfZSRrKIL8jy2XqNaX6g1OfTcDkz4XJmqHnUWXQmDnw35OfSWGy3a_NTywF3HVFYGICtWeRzjlGU-YcY4TDYK6bCfyptFAuRwYSHmoGiuBtQxEC-24Nvcn6LHiBlPHEq1UEuVXzXyhC3DN4cWw.FgDYM_FO30qEZ2aiX-pjl2cfSDIkzvzg2MaTzBNxNrs&dib_tag=se&keywords=musical%2Bmat&qid=1753336371&sprefix=musical%2Bmat%2Caps%2C367&sr=8-4-spons&sp_csd=d2lkZ2V0TmFtZT1zcF9hdGY&th=1", "https://www.flipkart.com/vidgy-5-1-indian-musical-keyboard-mat-baby-piano-gym-fitness-rack-pink/p/itma07c8747f2560?pid=CTYGR287MMYCZNW5&lid=LSTCTYGR287MMYCZNW5ZLSQTP&marketplace=FLIPKART&q=musical+mat&store=search.flipkart.com&srno=s_1_1&otracker=search&otracker1=search&fm=search-autosuggest&iid=en_DGH6nWiwfk_PVsuePC6e-QQun1fKgWh5A3RS4dKM-jTOsAtnHRyyPaJMtm02q8hyXjkrcR81pejPpIWUoHO8VA%3D%3D&ppt=sp&ppn=sp&ssid=hz1fvvm2w00000001753336412226&qH=012f3399880d9306", "/assets/images/p24.jpg")));

        // More sections if needed
    }

    private void showSection(String section) {
        productContainer.getChildren().clear();
        HBox box = sectionContent.get(section);
        if (box != null) {
            box.setAlignment(Pos.CENTER);
            productContainer.getChildren().add(box);
        }
    }

    private VBox createCard(String name, String price, String promo, String amazonUrl, String flipkartUrl, String imageUrl) {
        final double IMG_WIDTH = 120;
        final double IMG_HEIGHT = 90; // Reduced height for a "shorter" card
        ImageView imgView = new ImageView(new Image(imageUrl, IMG_WIDTH, IMG_HEIGHT, true, true));
        imgView.setFitWidth(IMG_WIDTH);
        imgView.setFitHeight(IMG_HEIGHT);
        imgView.setSmooth(true);

        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
        nameLabel.setTextFill(Color.web("#191633")); // darkest purple
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(205);
        nameLabel.setAlignment(Pos.CENTER);

        Label priceLabel = new Label(price);
        priceLabel.setFont(Font.font("Arial Black", FontWeight.BOLD, 17));
        priceLabel.setTextFill(Color.web("#1a1232"));

        Label promoLabel = new Label(promo);
        promoLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        promoLabel.setTextFill(Color.web("#290c33")); // very dark
        promoLabel.setWrapText(true);
        promoLabel.setMaxWidth(180);
        promoLabel.setAlignment(Pos.CENTER);

        Button cartBtn = new Button("Add to Cart");
        cartBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        cartBtn.setStyle("-fx-background-color:#b39ddb; -fx-text-fill:#1a1232; -fx-background-radius:11;");

        Button buyNowBtn = new Button("Buy Now");
        buyNowBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        buyNowBtn.setStyle("-fx-background-color:#7e57c2; -fx-text-fill:#fafafa; -fx-background-radius:11;");

        buyNowBtn.setOnAction(e -> showBuyOptions(flipkartUrl, amazonUrl));
        cartBtn.setOnAction(e -> addToCart(name, price, promo, amazonUrl, flipkartUrl));

        VBox card = new VBox(9, imgView, nameLabel, priceLabel, promoLabel, cartBtn, buyNowBtn);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(7, 13, 7, 13)); // less padding, less height
        card.setStyle("-fx-background-color: #f381b2ff; -fx-background-radius: 14; -fx-effect: dropshadow(gaussian, rgba(120, 80, 180, 0.18), 5,0,0,2); -fx-border-color:#7c43bd; -fx-border-width:1; -fx-border-radius:13;");
        card.setPrefWidth(220); // Increased width
        card.setMaxWidth(220);

        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(180), card);
        scaleIn.setToX(1.045); scaleIn.setToY(1.045);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(180), card);
        scaleOut.setToX(1.0); scaleOut.setToY(1.0);

        card.setOnMouseEntered(e -> scaleIn.playFromStart());
        card.setOnMouseExited(e -> scaleOut.playFromStart());

        return card;
    }

    private void addToCart(String name, String price, String promo, String amazonUrl, String flipkartUrl) {
        if (cartItems.containsKey(name)) {
            CartItem item = cartItems.get(name);
            item.quantity++;
            item.updateDisplay();
        } else {
            CartItem newItem = new CartItem(name, price, promo, amazonUrl, flipkartUrl);
            cartItems.put(name, newItem);
            cartBox.getChildren().add(newItem.container);
        }
        cartBox.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().equals("Your cart is empty"));
    }

    private class CartItem {
        String name;
        String price;
        String promo;
        String amazonUrl;
        String flipkartUrl;
        int quantity = 1;

        HBox container;
        Label qtyLabel;
        Label priceLabel;

        public CartItem(String name, String price, String promo, String amazonUrl, String flipkartUrl) {
            this.name = name;
            this.price = price;
            this.promo = promo;
            this.amazonUrl = amazonUrl;
            this.flipkartUrl = flipkartUrl;

            Label nameLabel = new Label(name);
            nameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
            nameLabel.setTextFill(Color.web("#191633")); // dark
            nameLabel.setPrefWidth(122);

            qtyLabel = new Label("Qty: " + quantity);
            qtyLabel.setFont(Font.font("Arial", 12));
            qtyLabel.setTextFill(Color.web("#33215d"));
            qtyLabel.setMinWidth(45);

            priceLabel = new Label(price);
            priceLabel.setFont(Font.font("Arial Black", FontWeight.BOLD, 13));
            priceLabel.setTextFill(Color.web("#191633"));
            priceLabel.setMinWidth(60);

            Button removeBtn = new Button("Remove");
            removeBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
            removeBtn.setStyle("-fx-background-color: #f8bbd0; -fx-text-fill: #2e003e; -fx-background-radius: 9;");
            removeBtn.setOnAction(e -> removeFromCart());

            Button buyBtn = new Button("Buy");
            buyBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
            buyBtn.setStyle("-fx-background-color: #5e35b1; -fx-text-fill: #fff; -fx-background-radius: 9;");
            buyBtn.setOnAction(e -> showBuyOptions(flipkartUrl, amazonUrl));

            container = new HBox(6, nameLabel, qtyLabel, priceLabel, buyBtn, removeBtn);
            container.setAlignment(Pos.CENTER_LEFT);
            container.setPadding(new Insets(7, 0, 7, 0));
            container.setStyle("-fx-background-color: #ea5897ff; -fx-background-radius: 9;");
        }

        public void updateDisplay() {
            qtyLabel.setText("Qty: " + quantity);
        }

        private void removeFromCart() {
            cartBox.getChildren().remove(container);
            cartItems.remove(name);
            if (cartItems.isEmpty()) {
                Label emptyCartLabel = new Label("Your cart is empty");
                emptyCartLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
                emptyCartLabel.setTextFill(Color.web("#1a1130"));
                cartBox.getChildren().add(emptyCartLabel);
            }
        }
    }

    private void showBuyOptions(String flipkartUrl, String amazonUrl) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Buy Now");
        alert.setHeaderText("Choose a website to buy from:");
        ButtonType flipkart = new ButtonType("Flipkart");
        ButtonType amazon = new ButtonType("Amazon");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(flipkart, amazon, cancel);
        alert.showAndWait().ifPresent(response -> {
            if (response == flipkart) openWebpage(flipkartUrl);
            else if (response == amazon) openWebpage(amazonUrl);
        });
    }

    private void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}