package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create text
        Text helloText = new Text("Hello World");

        // Layout
        BorderPane root = new BorderPane();
        root.setCenter(helloText);

        // Scene
        Scene scene = new Scene(root, 400, 400);

        // Stage
        primaryStage.setTitle("Hello World App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
