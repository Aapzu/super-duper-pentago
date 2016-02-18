package fi.aapzu.pentago.app;

import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.logic.Line;
import java.io.IOException;
import java.util.Set;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
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
        
        initTiles();
        initCircles();
        
        game = new Pentago();
        game.setPlayerName(0, "Player0");
        game.setPlayerName(0, "Player1");
        
//        Line line = null;
//        while (line == null) {
//            doTurn();
//            line = game.getLine();
//        }
//        winGame(line);
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
    
    private void initCircles() {
        for(int y = 0; y < 2; y++) {
            for(int x = 0; x < 2; x++) {
                GridPane tile = tiles[y][x];
                Set<Node> circleSet = tile.lookupAll("Circle");
                int i = 0;
                for(Node n : circleSet) {
                    int cX = x*3 + i % 3;
                    int cY = y*3 + i / 3;
                    n.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                        setMarble((Circle)n, cX, cY);
                    });
                    i++;
                }
            }
        }
    }
    
    public void setMarble(Circle circle, int x, int y) {
        try {
            game.setMarble(x, y);
            circles[y][x].setStyle("-fx-fill: #000000");
        } catch (Exception e){}
    }
    
    public void doTurn() {
        primaryStage.setTitle("Pentago - " + game.whoseTurn() + " in turn");
        
    }
}