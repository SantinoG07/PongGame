package org.example.pong;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private Button jugarButton;
    @FXML
    private Button configurarButton;
    @FXML
    private Button salirButton;

    @FXML
    private void jugarJuego() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());


        Stage stage = (Stage) jugarButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Pong Game");
        stage.show();

        GameController gameController = fxmlLoader.getController();
        gameController.initialize();
    }

    @FXML
    private void salirAplicacion() {
        System.exit(0);
    }
}
