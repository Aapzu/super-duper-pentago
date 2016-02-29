
package fi.aapzu.pentago.app;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * A eventHandler which works on both clicking and enter pressing.
 * 
 * @author Aapeli
 */
class EnterClickEventHandler implements EventHandler<Event> {

    /**
     * Checks if the key pressed is enter and if so, calls handle().
     * 
     * @param e 
     */
    protected void handleKeyEvent(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            handleEvent();
        }
    }

    /**
     * Handles mouseEvent.
     * 
     * @param e 
     */
    protected void handleMouseEvent(MouseEvent e) {
        handleEvent();
    }

    @Override
    public void handle(Event event) {
        if (event instanceof KeyEvent) {
            handleKeyEvent((KeyEvent) event);
        } else if (event instanceof MouseEvent) {
            handleMouseEvent((MouseEvent) event);
        }
    }

    /**
     * The thing the eventHandler really does.
     */
    protected void handleEvent() {
    }

}