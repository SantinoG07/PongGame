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

public class GameController implements Pelota.PuntoListener {

    // Elementos del UI
    @FXML
    private Label contador1;
    @FXML
    private Label contador2;

    // Elementos del juego
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

    private int puntos1 = 0;
    private int puntos2 = 0;

    @Override
    public void puntoParaJugador1() {
        puntos1++;
        contador1.setText(String.format("%03d", puntos1));
    }

    @Override
    public void puntoParaJugador2() {
        puntos2++;
        contador2.setText(String.format("%03d", puntos2));
    }

    @FXML
    public void initialize() {

        // Instanciar Jugadores
        player1 = new Player(player1_pos, HEIGHT);
        player2 = new Player(player2_pos, HEIGHT);

        // Instanciar Pelota
        pelota = new Pelota(pelotaShape, HEIGHT, WIDTH, player1, player2);
        pelota.setPuntoListener(this); // ← Se registra para recibir la “señal”

        pelota.velocidad(3);
        pelota.startMoving();

        pelota.pos_inicial(pelota.getShape().getCenterX(), pelota.getShape().getCenterY());

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