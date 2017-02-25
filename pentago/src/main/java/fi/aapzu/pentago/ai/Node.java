package fi.aapzu.pentago.ai;

/**
 * Interface for node used by AlphaBetaPruning.
 */
abstract class Node {

    int alphaBetaValue;

    /**
     * Gives a integer value for the Node.
     *
     * @return value of the Node
     */
    abstract int getNodeValue();

    /**
     * Gives the children of the Node.
     *
     * @return Array of Node's children
     */
    abstract Node[] getChildren();

    int getAlphaBetaValue() {
        return alphaBetaValue;
    }

    void setAlphaBetaValue(int alphaBetaValue) {
        this.alphaBetaValue = alphaBetaValue;
    }
}
