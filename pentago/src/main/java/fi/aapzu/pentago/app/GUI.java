package fi.aapzu.pentago.app;

import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.logic.Direction;
import fi.aapzu.pentago.logic.Line;
import fi.aapzu.pentago.logic.marble.Marble;
import static fi.aapzu.pentago.logic.marble.Symbol.O;
import static fi.aapzu.pentago.logic.marble.Symbol.X;
import java.io.IOException;
import java.util.Set;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
 
public class GUI extends Application {
    
    private Pentago game;
    private GridPane[][] tiles = new GridPane[2][2];
    private Circle[][] circles = new Circle[6][6];
    private Scene baseScene;
    private Stage primaryStage;
    
    private ButtonBar rotateButtonBar; 
    private ChoiceBox directionChoiceBox;
    private ChoiceBox tileChoiceBox;
    private Button rotateButton;
    
    private Label helpLabel;
    
    public void startGUI(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Pentago");
        Pane basePane = (Pane)FXMLLoader.load(getClass().getClassLoader().getResource("fxml/Game.fxml"));
        baseScene = new Scene(basePane);
        primaryStage.setScene(baseScene);
        primaryStage.show();
        
        rotateButtonBar = (ButtonBar)(baseScene.getRoot().lookup("#rotateButtonBar"));
        rotateButton = (Button)(rotateButtonBar.lookup("#rotateButton"));
        
        directionChoiceBox = (ChoiceBox)(baseScene.getRoot().lookup("#directionChoiceBox"));
        directionChoiceBox.setItems(FXCollections.observableArrayList(
            "Clockwise", "Counter Clockwise")
        );
        directionChoiceBox.getSelectionModel().selectFirst();
        
        tileChoiceBox = (ChoiceBox)(baseScene.getRoot().lookup("#tileChoiceBox"));
        tileChoiceBox.setItems(FXCollections.observableArrayList(
            1, 2, 3, 4)
        );
        tileChoiceBox.getSelectionModel().selectFirst();
        
        helpLabel = (Label)(baseScene.getRoot().lookup("#helpLabel"));
        
        initTiles();
        initCircles();
        initRotating();
        
        game = new Pentago();
        
        game.setPlayerName(0, "Player0");
        game.setPlayerName(1, "Player1");
        
        readyToSetMarble();
    }
    
    private void initTiles() {
        Set<Node> tileSet = baseScene.getRoot().lookupAll(".tile");
        int i = 0;
        for(Node n : tileSet) {
            final int x = i % 2;
            final int y = i / 2;
            tiles[y][x] = (GridPane)(n);
            i++;
        }
    }
    
    private void initRotating() {
        rotateButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            if(game.getAllowedToRotate()) {
                final int x = ((int)tileChoiceBox.getValue() - 1) % 2;
                final int y = ((int)tileChoiceBox.getValue() - 1) / 2;
                final Direction d = directionChoiceBox.getValue().equals("Clockwise") ? Direction.CLOCKWISE : Direction.COUNTER_CLOCKWISE;
                rotateTile(x, y, d);
            }
        });
    }
    
    private void initCircles() {
        for(int y = 0; y < 2; y++) {
            for(int x = 0; x < 2; x++) {
                GridPane tile = tiles[y][x];
                Set<Node> circleSet = tile.lookupAll("Circle");
                int i = 0;
                for(Node n : circleSet) {
                    int cX = x*3 + i % 3;
                    int cY = y*3 + i / 3;
                    circles[cY][cX] = (Circle)n;
                    n.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                        if(!game.getAllowedToRotate()) {
                            setMarble(cX, cY);
                        }
                    });
                    i++;
                }
            }
        }
    }
    
    private void fillCircles() {
        Marble[][] mArr = game.getBoard().toMarbleArray();
        for(int y = 0; y < mArr.length; y++) {
            for(int x = 0; x < mArr[0].length; x++) {
                if(mArr[y][x] != null) {
                    switch(mArr[y][x].getSymbol()) {
                        case X:
                            circles[y][x].setStyle("-fx-fill:#000000");
                            break;
                        case O:
                            circles[y][x].setStyle("-fx-fill:#FFFFFF");
                            break;
                    }
                } else {
                    circles[y][x].setStyle("-fx-fill:rgba(0,0,0,0)");
                }
            }
        }
    }
    
    private void readyToSetMarble() {
        fillCircles();
        rotateButtonBar.setVisible(false);
        helpLabel.setText(game.whoseTurn() + " - Click where you'd want to put the marble");
    }
    
    private void readyToRotate() {
        fillCircles();
        rotateButtonBar.setVisible(true);
        helpLabel.setText(game.whoseTurn() + " - First select the direction and then the tile you want to rotate");
    }
    
    public void setMarble(int x, int y) {
        try {
            game.setMarble(x, y);
            readyToRotate();
        } catch (Exception e){
            System.out.println(e);
        }
    }
    
    public void rotateTile(int x, int y, Direction d) {
        try {
            game.rotateTile(x, y, d);
            Line line = game.getLine();
            if(line != null) {
                winGame();
            } else {
                readyToSetMarble();
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
        
    private void winGame() {
        System.out.println(game.whoseTurn() + " won!");
    }
}