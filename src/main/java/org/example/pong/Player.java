package org.example.pong;

import javafx.scene.shape.Rectangle;

public class Player {

    private Rectangle shape;
    private final double speed = 5; // pixeles por movimiento
    private final double screenHeight;

    public Player(Rectangle shape, double height){

        this.shape = shape;
        this.screenHeight = height;

    }

    public void moveUp() {
        double newY = shape.getY() - speed;
        if (newY >= 0) {
            shape.setY(newY);
        }
    }

    public void moveDown() {
        double newY = shape.getY() + speed;
        if (newY + shape.getHeight() + 100 <= screenHeight) {
            shape.setY(newY);
        }
    }

    public Rectangle getShape() {
        return shape;
    }

}
