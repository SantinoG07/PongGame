package org.example.pong;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    // Botones
    @FXML
    private Button jugarButton;
    @FXML
    private Button salirButton;
    @FXML
    private Button inicioButton;

    // Sliders
    @FXML
    private Slider sliderVelocidad;
    @FXML
    private Slider sliderTiempo;
    @FXML
    private Slider sliderGoles;

    // Checkboxes
    @FXML
    private CheckBox checkTiempo;
    @FXML
    private CheckBox checkGoles;

    @FXML
    public void initialize() {
        if (checkTiempo != null && sliderTiempo != null) {
            sliderTiempo.setDisable(!checkTiempo.isSelected());
            checkTiempo.selectedProperty().addListener((obs, oldVal, newVal) -> {
                sliderTiempo.setDisable(!newVal);
                if (!newVal) sliderTiempo.setValue(0);
            });
        }

        if (checkGoles != null && sliderGoles != null) {
            sliderGoles.setDisable(!checkGoles.isSelected());
            checkGoles.selectedProperty().addListener((obs, oldVal, newVal) -> {
                sliderGoles.setDisable(!newVal);
                if (!newVal) sliderGoles.setValue(0);
            });
        }
    }

    @FXML
    private void jugarJuego() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        GameController gameController = fxmlLoader.getController();

        int velocidad = (int) sliderVelocidad.getValue();
        int tiempo = checkTiempo.isSelected() ? (int) sliderTiempo.getValue() : 0;
        int goles = checkGoles.isSelected() ? (int) sliderGoles.getValue() : 0;

        // Pasa los valores de los sliders
        gameController.setParametros(
                velocidad,
                tiempo,
                goles
        );

        Stage stage = (Stage) jugarButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Pong Game");
        stage.show();
    }

    @FXML
    private void subMenu() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MenuI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());


        Stage stage = (Stage) inicioButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Pong Game");
        stage.show();
    }

    @FXML
    private void salirAplicacion() {
        System.exit(0);
    }

    public void pausarg() {
        SoundPlayer.stopSound();
    }

    public void salirmenu(ActionEvent actionEvent) {
    }
}