/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.aapzu.pentago.app;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Aapeli
 */
public class GameController implements Initializable {

    @FXML 
    private GridPane tile0;
    @FXML
    private GridPane tile1;
    @FXML 
    private GridPane tile2;
    @FXML 
    private GridPane tile3;
    
    private GridPane[] tiles;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tiles = new GridPane[]{tile0, tile1, tile2, tile3};
        for(int i = 0; i < tiles.length; i++) {
            final int j = i;
            tiles[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("Tile"+j+" clicked!");
                }
            });
        }
        
    }  
    
}
