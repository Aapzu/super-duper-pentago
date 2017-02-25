package fi.aapzu.pentago.ai;

import java.util.HashMap;

/**
 * The core element of AI. Forms a game tree and tries to find the best possible move at the moment for the Bot.
 */
public class AlphaBetaPruning {

    private final HashMap<Node, Integer> nodes;

    /**
     * Creates new instance of AlphaBetaPruning.
     */
    public AlphaBetaPruning() {
        nodes = new HashMap<>();
    }

    /**
     * Calculates the best move.
     *
     * @param node       the node to begin the calculation from
     * @param movesAhead how many moves ahead is calculated before implying heuristics
     * @return the best move
     */
    public Node getBest(Node node, int movesAhead) {
        Node bestNodeYet = node;
        int bestScore = Integer.MIN_VALUE;
        Integer alpha = Integer.MIN_VALUE;
        Integer beta = Integer.MAX_VALUE;
        Node[] children = node.getChildren();
        for (Node n : children) {
            int score = maxValue(n, alpha, beta, 1, movesAhead);
            n.setAlphaBetaValue(score);
            if (score > bestScore) {
                bestNodeYet = n;
                bestScore = score;
            }
        }
        return bestNodeYet;
    }

    private int value(boolean minValue, Node node, Integer alpha, Integer beta, int depth, int maxDepth) {
        int newDepth = depth + 1;

        Node[] children = node.getChildren();
        if (nodes.containsKey(node)) {
            return nodes.get(node);
        }
        if (depth >= maxDepth || children.length == 0) {
            int value = node.getNodeValue();
            nodes.put(node, value);
            return value;
        }
        int value = minValue ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        for (Node n : children) {
            value = minValue ?
                    Math.min(value, maxValue(n, alpha, beta, newDepth, maxDepth)) :
                    Math.max(value, minValue(n, alpha, beta, newDepth, maxDepth));
            n.setAlphaBetaValue(value);
            if ((minValue && value > beta || value < alpha)) {
                return value;
            }
            if (minValue) {
                beta = Math.min(beta, value);
            } else {
                alpha = Math.max(alpha, value);
            }
        }
        return value;
    }

    private int maxValue(Node node, Integer alpha, Integer beta, int depth, int maxDepth) {
        return value(false, node, alpha, beta, depth, maxDepth);
    }

    private int minValue(Node node, Integer alpha, Integer beta, int depth, int maxDepth) {
        return value(true, node, alpha, beta, depth, maxDepth);
    }

    /**
     * Clears the map of nodes
     */
    public void clear() {
        nodes.clear();
    }
}
