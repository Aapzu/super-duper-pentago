package fi.aapzu.pentago.ai;

import fi.aapzu.pentago.ai.heuristics.Heuristics;
import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.util.DynamicArray;

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
        DynamicArray<String> strings = PossibleGamesWithOneMove.get(getSerializationString());
        PentagoNode[] games = new PentagoNode[strings.size()];
        int i = 0;
        for (String s : strings) {
            games[i] = new PentagoNode(s);
            i++;
        }
        return games;
    }

    public String getSerializationString() {
        return serializationString;
    }
}
