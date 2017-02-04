package fi.aapzu.pentago.ai;

/**
 * The core element of AI. Forms a game tree and tries to find the best possible move at the moment for the Bot.
 */
public class AlphaBetaPruning {

    /**
     * Calculates the best move.
     *
     * @param movesAhead how many moves ahead is calculated before implying heuristics
     * @return move the best move
     */
    public static Node getBest(Node node, int movesAhead) {
        Node bestNodeYet = node;
        int bestScore = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        for (Node n : node.getChildren()) {
            int score = maxValue(n, alpha, beta, 1, movesAhead);
            if (score > bestScore) {
                bestNodeYet = n;
                bestScore = score;
            }
        }
        return bestNodeYet;
    }

    private static int maxValue(Node node, int alpha, int beta, int depth, int maxDepth) {
        Node[] children = node.getChildren();
        if (depth >= maxDepth || children.length == 0) {
            return node.getNodeValue();
        }
        int value = Integer.MIN_VALUE;
        for (Node n : children) {
            value = Math.max(value, minValue(n, alpha, beta, depth++, maxDepth));
            if (value > beta) {
                return value;
            }
            alpha = Math.max(alpha, value);
        }
        return value;
    }

    private static int minValue(Node node, int alpha, int beta, int depth, int maxDepth) {
        Node[] children = node.getChildren();
        if (depth >= maxDepth || children.length == 0) {
            return node.getNodeValue();
        }
        int value = Integer.MAX_VALUE;
        for(Node n : children) {
            value = Math.min(value, maxValue(n, alpha, beta, depth++, maxDepth));
            if (value < alpha) {
                return value;
            }
            beta = Math.min(beta, value);
        }
        return value;
    }
}
