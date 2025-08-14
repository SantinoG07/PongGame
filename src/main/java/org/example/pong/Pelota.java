package org.example.pong;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Circle;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class Pelota {

    private double vel_inicial;
    private double max_vel = 30;
    private double ult_velocidad;

    private final Circle shape;

    private double dx; // velocidad horizontal
    private double dy; // velocidad vertical

    private final double height;
    private final double width;

    private final Player player1;
    private final Player player2;

    private PuntoListener listener; // referencia al que escucha

    private boolean pausa = false;

    public interface PuntoListener {
        void puntoParaJugador1();
        void puntoParaJugador2();
    }

    public void setPuntoListener(PuntoListener listener) {
        this.listener = listener;
    }

    public void velocidad(double vel) {
        vel_inicial = vel;
    }

    public Pelota(Circle shape, double height, double width, Player p1, Player p2) {
        this.shape = shape;
        this.height = height;
        this.width = width;
        this.player1 = p1;
        this.player2 = p2;
    }

    public void startMoving() {
        dx = vel_inicial;
        dy = vel_inicial;

        PauseTransition pausa_ = new PauseTransition(Duration.seconds(1));
        pausa_.setOnFinished(e -> {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(pausa){
                    return;
                }

                mover();

                if(shape.getBoundsInParent().intersects(player1.getShape().getBoundsInParent()) ||
                        shape.getBoundsInParent().intersects(player2.getShape().getBoundsInParent())) {
                    choque();
                }


            }
        };
        timer.start();
        });
        pausa_.play();

    }

    public Circle getShape() {
        return shape;
    }

    private void mover() {

        // Movimiento
        shape.setCenterX(shape.getCenterX() + dx);
        shape.setCenterY(shape.getCenterY() + dy);

        // Colisión con bordes
        if (shape.getCenterY() - shape.getRadius() <= 100 ||
                shape.getCenterY() + shape.getRadius() >= height) {
            dy *= -1; // Rebote vertical
        }

        // Llegar al final

        if (shape.getCenterX() - shape.getRadius() <= 0) { // Punto para el jugador 2
            if (listener != null) listener.puntoParaJugador2();
            reiniciar();
        }

        if (shape.getCenterX() + shape.getRadius() >= width) { // Punto para el jugador 1
            if (listener != null) listener.puntoParaJugador1();
            reiniciar();
        }
    }

    public void choque(){
        // Choque con paletas
        SoundPlayer.playSound("src/main/resources/sonidos/choque.wav");
        dx *= -1;



        if (Math.abs(dx) < max_vel) {
            dx += Math.copySign(0.5, dx);
            dy += Math.copySign(0.5, dy);

            // Si se pasa del limite se queda en la velocidad maxima
            if (Math.abs(dx) > max_vel) {
                dx = Math.copySign(max_vel, dx);
                dy = Math.copySign(max_vel, dy);
            }
        }
    }

    private void reiniciar(){
        // Posición inicial
        shape.setCenterX(width/2);
        shape.setCenterY((height + 100) / 2);

        dx = 0;
        dy = 0;

        // Sonido de anotar
        SoundPlayer.playSound("src/main/resources/sonidos/punto.wav");

        PauseTransition pausa_ = new PauseTransition(Duration.seconds(1));
        pausa_.setOnFinished(e -> {
            dx = Math.copySign(vel_inicial, Math.random() < 0.5 ? -1 : 1);
            dy = Math.copySign(vel_inicial, Math.random() < 0.5 ? -1 : 1);
        });
        pausa_.play();
    }

    public void stopMoving(){
        ult_velocidad = dx;

        dx = 0;
        dy = 0;

        pausa = true;
    }

    public void despausar(){ // Usar para despausar la pelota después de una pausa mid-game
        if (dx == 0) {
            ult_velocidad=vel_inicial;
        }
        dx = ult_velocidad;
        dy = ult_velocidad;

        pausa = false;
    }
}