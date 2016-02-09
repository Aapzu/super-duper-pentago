
package fi.aapzu.pentago.game;

/**
 * The exception to be thrown, if tried to do something against the rules of Pentago.
 * 
 * @author Aapeli
 */
public class PentagoGameRuleException extends RuntimeException {

    /**
     * Creates a new PentagoGameRuleException.
     */
    public PentagoGameRuleException() {
    }

    /**
     * Creates a new PentagoGameRuleException with the given message.
     * 
     * @param msg the message of the exception
     */
    public PentagoGameRuleException(String msg) {
        super(msg);
    }
}
