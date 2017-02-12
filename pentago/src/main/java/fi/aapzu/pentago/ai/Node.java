package fi.aapzu.pentago.ai;

/**
 * Interface for node used by AlphaBetaPruning.
 */
public interface Node {

    /**
     * Gives a integer value for the Node.
     *
     * @return value of the Node
     */
    int getNodeValue();

    /**
     * Gives the children of the Node.
     *
     * @return Array of Node's children
     */
    Node[] getChildren();
}
