package fi.aapzu.pentago.app;

import fi.aapzu.pentago.game.Pentago;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
 
public class GUI extends Application {
    
    private Pentago game;
    
    public void startGUI(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Pentago");
        Pane basePane = (Pane)FXMLLoader.load(getClass().getClassLoader().getResource("fxml/Game.fxml"));
        Scene myScene = new Scene(basePane);
        primaryStage.setScene(myScene);
        primaryStage.show();
    }
}