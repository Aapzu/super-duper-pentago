package fi.aapzu.pentago.ai;

import fi.aapzu.pentago.ai.heuristics.Heuristics;
import fi.aapzu.pentago.game.Pentago;

/**
 * Wrapper between serialized Pentago and AlphaBetaPruning.
 */
public class PentagoNode implements Node {

    private final String serializationString;

    /**
     * Creates new instance of the PentagoNode.
     *
     * @param serializationString serialized Pentago
     */
    public PentagoNode(String serializationString) {
        this.serializationString = serializationString;
    }

    /**
     * Creates new instance of the PentagoNode for given Pentago game.
     *
     * @param game to be used
     */
    public PentagoNode(Pentago game) {
        this(game.serialize());
    }

    @Override
    public int getNodeValue() {
        Pentago game = new Pentago();
        if (game.deserialize(getSerializationString())) {
            return Heuristics.getScore(game, game.getWhoseTurnIndex());
        }
        throw new RuntimeException("Invalid serialization string!");
    }

    @Override
    public Node[] getChildren() {
        String[] strings = PossibleGamesWithOneMove.get(getSerializationString());
        PentagoNode[] games = new PentagoNode[strings.length];
        for (int i = 0; i < strings.length; i++) {
            games[i] = new PentagoNode(strings[i]);
        }
        return games;
    }

    public String getSerializationString() {
        return serializationString;
    }
}
