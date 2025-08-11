package org.example.pong;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

public class GameController {

    // UI desde FXML
    @FXML private Label contador1;
    @FXML private Label contador2;
    @FXML private Rectangle player1_pos;
    @FXML private Rectangle player2_pos;
    @FXML private Circle pelotaShape;

    // Datos del juego
    private ObservableSet<KeyCode> teclasPresionadas = FXCollections.observableSet();
    private AnimationTimer gameLoop;
    private int puntos1 = 0;
    private int puntos2 = 0;
    private double velX = 3;
    private double velY = 3;

    // Timer
    @FXML private Label tiempo;
    private int segundos = 0;
    private Timeline lineaTiempo;

    // Tamaño del tablero
    private static final double WIDTH = 600;
    private static final double HEIGHT = 500;

    @FXML
    public void initialize() {
        // Centrar pelota
        pelotaShape.setCenterX(WIDTH / 2);
        pelotaShape.setCenterY(HEIGHT / 2);

        // Inicializar marcador
        contador1.setText(String.format("%03d", puntos1));
        contador2.setText(String.format("%03d", puntos2));

        // Timer
        lineaTiempo = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            segundos++;
            int min = segundos / 60;
            int seg = segundos % 60;
            tiempo.setText(String.format("%02d:%02d", min, seg));
        }));
        lineaTiempo.setCycleCount(Timeline.INDEFINITE);
        lineaTiempo.play();

        Platform.runLater(() -> {
            activarControles();
            startGameLoop();
        });
    }

    private void activarControles() {
        var scene = player1_pos.getScene();

        scene.setOnKeyPressed(e -> teclasPresionadas.add(e.getCode()));
        scene.setOnKeyReleased(e -> teclasPresionadas.remove(e.getCode()));
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moverPaletas();
                moverPelota();
            }
        };
        gameLoop.start();
    }

    private void moverPaletas() {
        double paso = 5;

        if (teclasPresionadas.contains(KeyCode.W) && player1_pos.getY() > 0) {
            player1_pos.setY(player1_pos.getY() - paso);
        }
        if (teclasPresionadas.contains(KeyCode.S) && player1_pos.getY() + player1_pos.getHeight() < HEIGHT) {
            player1_pos.setY(player1_pos.getY() + paso);
        }
        if (teclasPresionadas.contains(KeyCode.O) && player2_pos.getY() > 0) {
            player2_pos.setY(player2_pos.getY() - paso);
        }
        if (teclasPresionadas.contains(KeyCode.L) && player2_pos.getY() + player2_pos.getHeight() < HEIGHT) {
            player2_pos.setY(player2_pos.getY() + paso);
        }
    }

    private void moverPelota() {
        pelotaShape.setCenterX(pelotaShape.getCenterX() + velX);
        pelotaShape.setCenterY(pelotaShape.getCenterY() + velY);

        // Rebote vertical
        if (pelotaShape.getCenterY() - pelotaShape.getRadius() <= 0 ||
                pelotaShape.getCenterY() + pelotaShape.getRadius() >= HEIGHT) {
            velY *= -1;
        }

        // Colisión con paletas
        if (pelotaShape.getBoundsInParent().intersects(player1_pos.getBoundsInParent()) && velX < 0) {
            velX *= -1;
        }
        if (pelotaShape.getBoundsInParent().intersects(player2_pos.getBoundsInParent()) && velX > 0) {
            velX *= -1;
        }

        // Punto jugador 1
        if (pelotaShape.getCenterX() - pelotaShape.getRadius() <= 0) {
            puntos2++;
            contador2.setText(String.format("%03d", puntos2));
            resetPelota();
        }

        // Punto jugador 2
        if (pelotaShape.getCenterX() + pelotaShape.getRadius() >= WIDTH) {
            puntos1++;
            contador1.setText(String.format("%03d", puntos1));
            resetPelota();
        }
    }

    private void resetPelota() {
        pelotaShape.setCenterX(WIDTH / 2);
        pelotaShape.setCenterY(HEIGHT / 2);
        velX *= -1; // cambiar dirección al reiniciar
    }
}
