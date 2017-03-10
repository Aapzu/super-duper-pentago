package fi.aapzu.pentago.ai;

import fi.aapzu.pentago.ai.heuristics.Heuristics;
import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.util.DynamicArray;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Wrapper between serialized Pentago and AlphaBetaPruning.
 */
public class PentagoNode extends Node {

    private final String serializationString;

    /**
     * Creates new instance of the PentagoNode.
     *
     * @param serializationString serialized Pentago
     */
    public PentagoNode(String serializationString) {
        this.serializationString = serializationString;
    }


    @Override
    public int getNodeValue() {
        Pentago game = new Pentago();
        if (game.deserialize(getSerializationString())) {
            return Heuristics.getScore(game, 1 - game.getWhoseTurnIndex());
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
