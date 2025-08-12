package org.example.pong;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import java.io.IOException;
import java.sql.Time;

public class GameController implements Pelota.PuntoListener {

    @FXML
    private Label contador1;
    @FXML
    private Label contador2;
    @FXML
    private Label contadorLabel;
    @FXML
    private Rectangle player1_pos;
    @FXML
    private Rectangle player2_pos;
    @FXML
    private Circle pelotaShape;

    private Pelota pelota;

    private Player player1;
    private Player player2;

    private ObservableSet<KeyCode> teclasPresionadas = FXCollections.observableSet();
    private AnimationTimer gameLoop;


    private int puntos1 = 0;
    private int puntos2 = 0;

    private double vel_inicial;

    // Timer
    @FXML
    private Label tiempo;
    @FXML
    private Label overtime;
    private int segundos = 0;
    private Timeline lineaTiempo;
    @FXML
    private Pane menu;



    // Finalizadores del Juego

    private int minuto_final = 0;
    private int maximo_puntaje = 0;

    // Tamaño del tablero
    private static final double WIDTH = 600;
    private static final double HEIGHT = 500;

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

    public void setParametros(int velocidad, int minuto, int goles) {
        if(velocidad == 1){
            vel_inicial = 3;
        } else if (velocidad == 2){
            vel_inicial = 4.5;
        } else {
            vel_inicial = 6;
        }

        minuto_final = minuto;
        maximo_puntaje = goles;


        // Instanciar Pelota
        pelota = new Pelota(pelotaShape, HEIGHT, WIDTH, player1, player2);
        pelota.setPuntoListener(this); // Se registra para recibir la señal

        pelota.velocidad(vel_inicial);
        pelota.startMoving();

        // Timer
        lineaTiempo = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            segundos++;
            int min = segundos / 60;
            int seg = segundos % 60;
            tiempo.setText(String.format("%02d:%02d", min, seg));
        }));
        lineaTiempo.setCycleCount(Timeline.INDEFINITE);
        lineaTiempo.play();
    }

    @FXML
    public void initialize() {

        overtime.setVisible(false);

        // Instanciar Jugadores
        player1 = new Player(player1_pos, HEIGHT);
        player2 = new Player(player2_pos, HEIGHT);

        // Inicializar marcador
        contador1.setText(String.format("%03d", puntos1));
        contador2.setText(String.format("%03d", puntos2));

        Platform.runLater(() -> { // Corre las funciones después de cargar todo lo de Javafx
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
                pausar();

                if (maximo_puntaje > 0) {
                    if (puntos1 >= maximo_puntaje) {
                        pausar_fisica();
                        System.out.print("Jugador 1 ganó");
                    } else if (puntos2 >= maximo_puntaje) {
                        pausar_fisica();
                        System.out.print("Jugador 2 Ganó");
                    }
                }

                if (minuto_final > 0) {
                    int minutos_jugados = segundos / 60;
                    if (minutos_jugados >= minuto_final) {

                        if (puntos1 > puntos2){
                            pausar_fisica();
                            System.out.print("Jugador 1 ganó");
                        } else if (puntos2 > puntos1){
                            pausar_fisica();
                            System.out.print("Jugador 2 ganó");
                        } else {
                            overtime.setVisible(true);
                            maximo_puntaje = puntos1+1;
                        }

                    }
                }
            }
        };
        gameLoop.start();
    }

    private void pausar_fisica(){
        gameLoop.stop();
        pelota.stopMoving();
        lineaTiempo.stop();
    }

    @FXML
    private void volverAlMenuPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Parent menuPrincipalRoot = loader.load();

            Scene escenaActual = menu.getScene();

            escenaActual.setRoot(menuPrincipalRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean juegoPausado = false;

    private void pausar() {
        if (teclasPresionadas.contains(KeyCode.ESCAPE)) {
            if (!juegoPausado) {
                pausar_fisica();
                menu.setVisible(true);
                juegoPausado = true;
            } else {
                contadorR();
            }
        }
    }


    private Timeline countdownTimeline;
    private int segundosRestantes;
    public void contadorR(){
        menu.setVisible(false);
        segundosRestantes = 5;
        contadorLabel.setText("Reanudando en: " + segundosRestantes + " s");
        contadorLabel.setVisible(true);
        countdownTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            segundosRestantes--;
            if (segundosRestantes > 0) {
                contadorLabel.setText("Reanudando en: " + segundosRestantes + " s");
            } else {
                contadorLabel.setVisible(false);
                juegoPausado = false;
                gameLoop.start();
                pelota.despausar();  //ERRORRR
                lineaTiempo.play();
            }
        }));
        countdownTimeline.setCycleCount(segundosRestantes);
        countdownTimeline.play();

    }


    private void moverPaletas() {

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

}
