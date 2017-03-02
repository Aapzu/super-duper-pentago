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
            int score = value(1, n, alpha, beta, 1, movesAhead);
            n.setAlphaBetaValue(score);
            if (score > bestScore) {
                bestNodeYet = n;
                bestScore = score;
            }
        }
        return bestNodeYet;
    }

    private int value(int meOrOpponent, Node node, Integer alpha, Integer beta, int depth, int maxDepth) {
        int newDepth = depth + 1;

        Node[] children = node.getChildren();
        if (nodes.containsKey(node)) {
            return nodes.get(node);
        }
        if (depth >= maxDepth || children.length == 0) {
            int value = meOrOpponent * node.getNodeValue();
            nodes.put(node, value);
            return value;
        }
        int bestValue = Integer.MIN_VALUE;
        for (Node n : children) {
            int value = value(-meOrOpponent, n, -beta, -alpha, newDepth, maxDepth);
            n.setAlphaBetaValue(value);
            bestValue = Math.max(value, bestValue);
            alpha = Math.max(alpha, value);
            if (alpha >= beta) {
                break;
            }
        }
        return bestValue;
    }

    /**
     * Clears the map of nodes
     */
    public void clear() {
        nodes.clear();
    }
}
