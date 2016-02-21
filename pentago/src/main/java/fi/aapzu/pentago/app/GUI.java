package fi.aapzu.pentago.app;

import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.game.PentagoGameRuleException;
import fi.aapzu.pentago.logic.Direction;
import fi.aapzu.pentago.logic.Line;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;
import static fi.aapzu.pentago.logic.marble.Symbol.O;
import static fi.aapzu.pentago.logic.marble.Symbol.X;
import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
    private Label errorLabel;
    
    protected void startGUI(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Pentago");
        primaryStage.show();
                
        game = new Pentago();
        loadStartMenu();
    }
    
    private void loadStartMenu() throws IOException {
        replaceSceneContent("fxml/StartMenu.fxml");
        
        Button startButton = (Button)(baseScene.getRoot().lookup("#startButton"));
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            try {
                TextField whitePlayerName = (TextField)(baseScene.getRoot().lookup("#whitePlayerName"));
                TextField blackPlayerName = (TextField)(baseScene.getRoot().lookup("#blackPlayerName"));
                game.setPlayerName(0, !whitePlayerName.getText().equals("") ? whitePlayerName.getText() : "Player 0");
                game.setPlayerName(1, !blackPlayerName.getText().equals("") ? blackPlayerName.getText() : "Player 1");
                loadGame();
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private void loadGame() throws IOException {
        replaceSceneContent("fxml/Game.fxml");
        
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
        errorLabel = (Label)(baseScene.getRoot().lookup("#errorLabel"));
        
        initTiles();
        initCircles();
        initRotating();
        addCircleClickHandlers();
        
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
                    i++;
                }
            }
        }
    }
    
    private void addCircleClickHandlers() {
        for(int y = 0; y < circles.length; y++) {
            for(int x = 0; x < circles[0].length; x++) {
                final int cX = x;
                final int cY = y;
                circles[y][x].addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                    if(!game.getAllowedToRotate()) {
                        setMarble(cX, cY);
                    }
                });
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
    
    private void setMarble(int x, int y) {
        try {
            game.setMarble(x, y);
            readyToRotate();
        } catch (Exception e){
            System.out.println(e);
        }
    }
    
    private void rotateTile(int x, int y, Direction d) {
        try {
//            RotateTransition rt = new RotateTransition(Duration.millis(1000), tiles[y][x]);
//            rt.setByAngle(90);
//            int rate;
//            if(d == Direction.CLOCKWISE) {
//                rate = 1;
//            } else {
//                rate = -1;
//            }
//            rt.setRate(rate);
//            rt.play();
            
            game.rotateTile(x, y, d);
            errorLabel.setText("");
            Line line = game.getLine();
            if(line != null) {
                winGame(line);
            } else {
                game.nextTurn();
                readyToSetMarble();
            }
        } catch (Exception e){
            if(e instanceof PentagoGameRuleException) {
                errorLabel.setText("Illegal direction!");
            }
        }
    }
        
    private void winGame(Line line) throws IOException {
        replaceSceneContent("fxml/WinScreen.fxml");
        
        initTiles();
        initCircles();
        fillCircles();
        
        for(Integer[] coords : line.getCoordinates()) {
            int x = coords[0];
            int y = coords[1];
            circles[y][x].strokeWidthProperty().setValue(5);
            circles[y][x].strokeProperty().setValue(Paint.valueOf("#ffd700"));
            String color;
            if(line.getSymbol().equals(Symbol.O))
                color = "#FFFFFF";
            else 
                color = "#000000";
            circles[y][x].setFill(Paint.valueOf(color));
        }
        
        Text text = new Text(game.whoseTurn() + " won!");
        text.setFont(new Font("Arial", 72));
        text.setFill(Paint.valueOf("#FF0000"));
        TextFlow textFlow = (TextFlow)(baseScene.getRoot().lookup("#winText"));
        textFlow.setVisible(true);    
        textFlow.getChildren().add(text);
        
        ((Button)(baseScene.getRoot().lookup("#mainMenuButton"))).addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            try {
                game.clear();
                loadStartMenu();
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private Pane replaceSceneContent(String fxml) throws IOException {
        Pane page = (Pane) FXMLLoader.load(getClass().getClassLoader().getResource(fxml));
        baseScene = primaryStage.getScene();
        if (baseScene == null) {
            baseScene = new Scene(page);
            primaryStage.setScene(baseScene);
        } else {
            baseScene.setRoot(page);
        }
        primaryStage.sizeToScene();
        
        return page;
    }
}
