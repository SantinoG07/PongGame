package org.example.pong;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.application.Platform;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableSet;
import javafx.collections.FXCollections;

public class GameController {
    @FXML
    private Rectangle player1_pos;
    @FXML
    private Rectangle player2_pos;
    @FXML
    private Circle pelotaShape;

    private ObservableSet<KeyCode> teclasPresionadas = FXCollections.observableSet();

    private AnimationTimer timer;

    private Pelota pelota;

    private Player player1;
    private Player player2;

    public static int HEIGHT = 500;
    public static int WIDTH = 600;

    @FXML
    public void initialize() {
        pelota = new Pelota(pelotaShape, HEIGHT, WIDTH);

        player1 = new Player(player1_pos, HEIGHT);
        player2 = new Player(player2_pos, HEIGHT);

        pelota.velocidad(3);
        pelota.startMoving();

        Platform.runLater(() -> { // Corre las funciones después de cargar todo lo de Javafx
            activarControles();
            startGameLoop();
        });
    }

    private void activarControles() {
        var scene = player1_pos.getScene();

        scene.setOnKeyPressed(event -> {
            teclasPresionadas.add(event.getCode());
        });

        scene.setOnKeyReleased(event -> {
            teclasPresionadas.remove(event.getCode());
        });
    }

    private void startGameLoop() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (teclasPresionadas.contains(KeyCode.W)) {
                    player1.moveUp();
                }
                if (teclasPresionadas.contains(KeyCode.S)) {
                    player1.moveDown();
                }
                if (teclasPresionadas.contains(KeyCode.O)) {
                    player2.moveUp();
                }
                if (teclasPresionadas.contains(KeyCode.L)) {
                    player2.moveDown();
                }
            }
        };
        timer.start();
    }



}