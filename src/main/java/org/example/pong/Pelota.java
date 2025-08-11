package org.example.pong;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Circle;

public class Pelota {

    private double vel_inicial;
    private double max_vel = 30;
    private final Circle shape;

    private double dx; // velocidad horizontal
    private double dy; // velocidad vertical

    private final double height;
    private final double width;

    private double x_inicial;
    private double y_inicial;

    private final Player player1;
    private final Player player2;

    private PuntoListener listener; // referencia al que escucha

    public interface PuntoListener {
        void puntoParaJugador1();
        void puntoParaJugador2();
    }

    public void setPuntoListener(PuntoListener listener) {
        this.listener = listener;
    }

    public void pos_inicial(double x,double y){
        x_inicial = x;
        y_inicial = y;
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
        dy = 6;
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                mover();

                if(shape.getBoundsInParent().intersects(player1.getShape().getBoundsInParent()) ||
                        shape.getBoundsInParent().intersects(player2.getShape().getBoundsInParent())) {
                    choque();
                }
            }
        };
        timer.start();
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
            dx *= 1.1;

            // Si se pasa del limite se queda en 45
            if (Math.abs(dx) > max_vel) {
                dx = Math.copySign(max_vel, dx);
            }
        }
    }

    private void reiniciar(){
        // Posición inicial
        shape.setCenterX(x_inicial);
        shape.setCenterY(y_inicial);

        // Velocidad inicial
        dx = Math.copySign(vel_inicial, dx);

        // Sonido de anotar
        SoundPlayer.playSound("src/main/resources/sonidos/punto.wav");
    }
}