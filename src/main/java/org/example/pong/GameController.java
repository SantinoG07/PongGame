package org.example.pong;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

public class GameController {
    @FXML
    private Rectangle player1;
    @FXML
    private Rectangle player2;

    public void Controles(){
        player1.getScene().setOnKeyPressed((KeyEvent event) ->{
            int paso = 20;
            if(event.getCode()== KeyCode.O) {
                player1.setY(player1.getY() - paso);
            } else if (event.getCode()==KeyCode.L){
                player1.setY(player1.getY()+paso);
            }
            else if(event.getCode()== KeyCode.W) {
                player2.setY(player2.getY() - paso);
            } else if (event.getCode()==KeyCode.S){
                player2.setY(player2.getY()+paso);
            }
        });
    }

}