package org.example.pong;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.text.Font;

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
    private Pane menu;

    private Font pixelFont;

    @FXML
    public void initialize() {
        // Cargar la fuente una sola vez
        pixelFont = Font.loadFont(getClass().getResourceAsStream("/org/example/pong/fontpixel.ttf"), 10);
        if (pixelFont == null) {
            System.out.println("No se pudo cargar la fuente");
        }

     
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
        Parent root = fxmlLoader.load();

        // Aplicar la fuente al root
        if (pixelFont != null) {
            root.setStyle("-fx-font-family: '" + pixelFont.getFamily() + "';");
        }

        GameController gameController = fxmlLoader.getController();

        int velocidad = (int) sliderVelocidad.getValue();
        int tiempo = checkTiempo.isSelected() ? (int) sliderTiempo.getValue() : 0;
        int goles = checkGoles.isSelected() ? (int) sliderGoles.getValue() : 0;

        gameController.setParametros(velocidad, tiempo, goles);

        Stage stage = (Stage) jugarButton.getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/org/example/pong/estilosmenu.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Pong Game");
        stage.show();
    }


    @FXML
    private void subMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MenuI.fxml"));
        Parent root = fxmlLoader.load();

        // Aplicar la fuente Pixel
        if (pixelFont != null) {
            root.setStyle("-fx-font-family: '" + pixelFont.getFamily() + "';");
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/org/example/pong/estilosmenu.css").toExternalForm());

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

}
