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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
    private List<Node> rotateButtons;
    private Label helpLabel;
    private Label errorLabel;



    void startGUI(String[] args) {
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
        TextField whitePlayerName = (TextField) (baseScene.getRoot().lookup("#whitePlayerName"));
        TextField blackPlayerName = (TextField) (baseScene.getRoot().lookup("#blackPlayerName"));
        Button startButton = (Button) (baseScene.getRoot().lookup("#startButton"));

        EnterClickEventHandler handler = new EnterClickEventHandler() {
            @Override
            public void handleEvent() {
                try {
                    game.addHumanPlayer(!whitePlayerName.getText().equals("") ? whitePlayerName.getText() : "White");
                    game.addBot(!blackPlayerName.getText().equals("") ? blackPlayerName.getText() : "Black");
                    loadGame();
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        whitePlayerName.addEventHandler(KeyEvent.KEY_PRESSED, handler);
        blackPlayerName.addEventHandler(KeyEvent.KEY_PRESSED, handler);
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
    }

    private void loadGame() throws IOException {
        replaceSceneContent("fxml/Game.fxml");

        rotateButtons = new ArrayList<>();
        rotateButtons.addAll(baseScene.getRoot().lookupAll(".counterClockwise"));

        helpLabel = (Label) (baseScene.getRoot().lookup("#helpLabel"));
        errorLabel = (Label) (baseScene.getRoot().lookup("#errorLabel"));

        initTiles();
        initCircles();
        addCircleClickHandlers();

        readyToSetMarble();
    }

    private void initTiles() {
        Set<Node> tileSet = baseScene.getRoot().lookupAll(".tile");
        int i = 0;
        for (Node n : tileSet) {
            final int x = i % 2;
            final int y = i / 2;
            tiles[y][x] = (GridPane) (n);
            Node clockwise = n.lookup("#clockwise" + x + y);
            Node counterClockwise = n.lookup("#counterClockwise" + x + y);
            if (clockwise != null && counterClockwise != null) {
                rotateButtons.add(clockwise);
                rotateButtons.add(counterClockwise);
                clockwise.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                    if (game.getAllowedToRotate()) {
                        rotateTile(x, y, Direction.CLOCKWISE);
                    }
                });
                counterClockwise.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                    if (game.getAllowedToRotate()) {
                        rotateTile(x, y, Direction.COUNTER_CLOCKWISE);
                    }
                });
            }
            i++;
        }
    }

    private void initCircles() {
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                GridPane tile = tiles[y][x];
                Set<Node> circleSet = tile.lookupAll("Circle");
                int i = 0;
                for (Node n : circleSet) {
                    int cX = x * 3 + i % 3;
                    int cY = y * 3 + i / 3;
                    circles[cY][cX] = (Circle) n;
                    i++;
                }
            }
        }
    }

    private void addCircleClickHandlers() {
        for (int y = 0; y < circles.length; y++) {
            for (int x = 0; x < circles[0].length; x++) {
                final int cX = x;
                final int cY = y;
                circles[y][x].addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                    if (!game.getAllowedToRotate()) {
                        setMarble(cX, cY);
                    }
                });
            }
        }
    }

    private void fillCircles() {
        Marble[][] mArr = game.getBoard().toMarbleArray();
        for (int y = 0; y < mArr.length; y++) {
            for (int x = 0; x < mArr[0].length; x++) {
                if (mArr[y][x] != null) {
                    switch (mArr[y][x].getSymbol()) {
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
        baseScene.getRoot().lookup("AnchorPane").getStyleClass().add("readyToSetMarble");
        baseScene.getRoot().lookup("AnchorPane").getStyleClass().remove("readyToRotate");
        fillCircles();
        for (Node n : rotateButtons) {
            n.setVisible(false);
        }
        helpLabel.setText(game.whoseTurn() + " - Click where you'd want to put the marble");
    }

    private void readyToRotate() {
        baseScene.getRoot().lookup("AnchorPane").getStyleClass().add("readyToRotate");
        baseScene.getRoot().lookup("AnchorPane").getStyleClass().remove("readyToSetMarble");
        fillCircles();
        for (Node n : rotateButtons) {
            n.setVisible(true);
        }
        helpLabel.setText(game.whoseTurn() + " - Click the button on the tile you want to rotate");
    }

    private void setMarble(int x, int y) {
        try {
            game.addMarble(x, y);
            readyToRotate();
        } catch (Exception e) { }
    }

    private void rotateTile(int x, int y, Direction d) {
        try {
            game.rotateTile(x, y, d);
            errorLabel.setText("");
            Line line = game.checkLines();
            if (line != null) {
                winGame(line);
            } else if (game.isEven()) {
                evenGame();
            } else {
                readyToSetMarble();
            }
        } catch (Exception e) {
            if (e instanceof PentagoGameRuleException) {
                errorLabel.setText("Illegal direction!");
            }
        }
    }

    private void evenGame() throws IOException {
        replaceSceneContent("fxml/GameOver.fxml");

        initTiles();
        initCircles();
        fillCircles();

        helpLabel.setText("Even! No more possible moves. Start a new game from the main menu!");

        initMainMenu();
    }

    private void winGame(Line line) throws IOException {
        replaceSceneContent("fxml/GameOver.fxml");

        initTiles();
        initCircles();
        fillCircles();

        for (Integer[] coords : line.getCoordinates()) {
            int x = coords[0];
            int y = coords[1];
            circles[y][x].strokeWidthProperty().setValue(5);
            circles[y][x].strokeProperty().setValue(Paint.valueOf("#ffd700"));
            String color;
            if (line.getSymbol().equals(Symbol.O)) {
                color = "#FFFFFF";
            } else {
                color = "#000000";
            }
            circles[y][x].setFill(Paint.valueOf(color));
        }

        Text text = new Text(line.getPlayer() + " won!");
        text.setFont(new Font("Arial", 72));
        text.setFill(Paint.valueOf("#FF0000"));
        TextFlow textFlow = (TextFlow) (baseScene.getRoot().lookup("#winText"));
        textFlow.setVisible(true);
        textFlow.getChildren().add(text);
        initMainMenu();
    }

    /**
     * Binds the main manu button to change the view to the main menu.
     */
    public void initMainMenu() {
        ((Button) (baseScene.getRoot().lookup("#mainMenuButton"))).addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            try {
                game.clear();
                loadStartMenu();
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * Changes the view to a fxml view by the parameter 'fxml'.
     * 
     * @param fxml the url of the fxml file
     * @return the page created
     * @throws IOException if the fxml file is not found
     */
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
