package org.example.pong;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class GameController {
    @FXML
    private Line red;
    @FXML
    private Rectangle player1;
    @FXML
    private Rectangle player2;
    @FXML
    private Pane root;
    @FXML
    private Circle pelota;
    @FXML
    private Rectangle lateralI;
    @FXML
    private Rectangle lateralD;
    @FXML
    private Rectangle superior;
    @FXML
    private Rectangle inferior;

    @FXML
    public void initialize() {
        // Bind actualiza constantemente, set es fijo
        player1.layoutXProperty().set(20);
        player1.layoutYProperty().bind(root.heightProperty().divide(2).subtract(player1.heightProperty().divide(2)));

        player2.layoutXProperty().bind(root.widthProperty().subtract(player2.getWidth() + 20));
        player2.layoutYProperty().bind(root.heightProperty().divide(2).subtract(player2.heightProperty().divide(2)));

        pelota.layoutXProperty().bind(root.widthProperty().divide(2));
        pelota.layoutYProperty().bind(root.heightProperty().divide(2));

        lateralI.setWidth(10);
        lateralD.setWidth(10);
        lateralI.layoutXProperty().set(0);
        lateralD.layoutXProperty().bind(root.widthProperty().subtract(lateralD.getWidth()));

        lateralI.heightProperty().bind(root.heightProperty());
        lateralD.heightProperty().bind(root.heightProperty());

        lateralI.setLayoutY(0);
        lateralD.setLayoutY(0);

        superior.setHeight(10);
        inferior.setHeight(10);
        superior.layoutYProperty().set(0);
        inferior.layoutYProperty().bind(root.heightProperty().subtract(inferior.getHeight()));

        superior.widthProperty().bind(root.widthProperty());
        inferior.widthProperty().bind(root.widthProperty());

        superior.setLayoutX(0);
        inferior.setLayoutX(0);

        red.layoutXProperty().bind(root.widthProperty().divide(2));

        red.startYProperty().set(0);
        red.endYProperty().bind(root.heightProperty());

        red.startXProperty().set(0);
        red.endXProperty().set(0);



        Controles();
    }

    public void Controles(){
        root.setOnKeyPressed(event ->{
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
        root.setFocusTraversable(true);
        root.requestFocus();
    }

}