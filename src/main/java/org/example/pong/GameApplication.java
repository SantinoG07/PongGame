package org.example.pong;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class GameApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Cargar la fuente
        Font font = Font.loadFont(
                getClass().getResourceAsStream("/org/example/pong/fontpixel.ttf"), 10
        );
        if (font == null) System.out.println("No se pudo cargar la fuente");

        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("Menu.fxml"));
        StackPane root = fxmlLoader.load(); // asumimos que el root es StackPane
        if (font != null) {
            root.setStyle("-fx-font-family: '" + font.getFamily() + "';");
        }

        Scene scene = new Scene(root, 800, 600);

        scene.getStylesheets().add(getClass()
                .getResource("/org/example/pong/estilosmenu.css").toExternalForm()
        );

        stage.setScene(scene);
        stage.setTitle("Pong Game");
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}