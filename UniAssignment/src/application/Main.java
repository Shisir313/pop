package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.text.Font;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // --- Login scene ---
        GridPane loginGrid = new GridPane();
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);
        loginGrid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Please Log In");
        sceneTitle.setFont(Font.font(20));
        loginGrid.add(sceneTitle, 0, 0, 2, 1);

        Label userLabel = new Label("Username:");
        loginGrid.add(userLabel, 0, 1);

        TextField userTextField = new TextField();
        loginGrid.add(userTextField, 1, 1);

        Label pwLabel = new Label("Password:");
        loginGrid.add(pwLabel, 0, 2);

        PasswordField pwBox = new PasswordField();
        loginGrid.add(pwBox, 1, 2);

        Button loginButton = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(loginButton);
        loginGrid.add(hbBtn, 1, 4);

        final Text messageText = new Text();
        loginGrid.add(messageText, 1, 6);

        Scene loginScene = new Scene(loginGrid, 400, 300);

        // --- Home scene (shown after successful login) ---
        BorderPane homeRoot = new BorderPane();
        Text welcomeText = new Text();
        welcomeText.setFont(Font.font(18));
        homeRoot.setCenter(welcomeText);
        Button logoutButton = new Button("Logout");
        BorderPane.setAlignment(logoutButton, Pos.TOP_RIGHT);
        homeRoot.setTop(logoutButton);
        Scene homeScene = new Scene(homeRoot, 400, 300);

        // --- Event handlers ---
        loginButton.setOnAction(e -> {
            String username = userTextField.getText();
            String password = pwBox.getText();

            if (username == null || username.trim().isEmpty()) {
                messageText.setText("Please enter username");
                return;
            }
            if (password == null || password.trim().isEmpty()) {
                messageText.setText("Please enter password");
                return;
            }

            // Simple hardcoded authentication for demo purposes
            if (username.equals("admin") && password.equals("admin")) {
                welcomeText.setText("Welcome, " + username + "!");
                messageText.setText("");
                primaryStage.setScene(homeScene);
            } else {
                messageText.setText("Invalid credentials");
            }
        });

        logoutButton.setOnAction(e -> {
            userTextField.clear();
            pwBox.clear();
            messageText.setText("");
            primaryStage.setScene(loginScene);
        });

        // --- Stage setup ---
        primaryStage.setTitle("Login");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}