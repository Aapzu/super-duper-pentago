package fi.aapzu.pentago.app;
 
import fi.aapzu.pentago.game.Pentago;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
 
public class GUI extends Application {
    
    private Pentago game;
    
    public void startGUI() {
        launch();
    }
    
    public void startGUI(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        game = new Pentago();
        
        Scene scene = new Scene(new Group());
        
        primaryStage.setTitle("Pentago");
        
        Label label = new Label("Pentago");
        label.setFont(new Font("Arial", 20));
        

        
        StackPane root = new StackPane();
        
        for(int i = 0; i < game.getBoard().getSideLength(); i++) {
            root.getChildren().add(new Label("moi"));
        }
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}