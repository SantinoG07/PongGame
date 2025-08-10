package org.example.pong;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("Game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle("Pong Game");
        stage.setScene(scene);
        stage.show();

        GameController controller = fxmlLoader.getController();
        controller.Controles();
    }

    public static void main(String[] args) {
        launch();
    }
}