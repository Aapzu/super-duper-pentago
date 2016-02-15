package fi.aapzu.pentago.app;

 
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import fi.aapzu.pentago.game.Pentago;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
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
        
       Browser browser = new Browser();
       BrowserView browserView = new BrowserView(browser);
       StackPane pane = new StackPane();
       pane.getChildren().add(browserView);
       Scene scene = new Scene(pane, 700, 500);
       primaryStage.setScene(scene);
       primaryStage.show();
       browser.loadURL("http://www.google.com");
    }
}