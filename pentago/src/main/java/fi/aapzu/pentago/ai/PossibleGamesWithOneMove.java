package fi.aapzu.pentago.ai;

import fi.aapzu.pentago.util.DynamicArray;

import java.util.ArrayList;

/**
 * A class to determine the possible games one can get from a game state with a single move
 */
class PossibleGamesWithOneMove {

    private static final char[][] ROTATION_TILE_DIRECTION_POSSIBILITIES = {
        {'0', '0', '1'}, {'0', '0', '2'}, {'0', '1', '1'}, {'0', '1', '2'},
        {'1', '0', '1'}, {'1', '0', '2'}, {'1', '1', '1'}, {'1', '1', '2'},
    };

    private static boolean rotationTileDirectionIsOpposite(char[] first, char[] second) {
        return first[0] == second[0] &&
                first[1] == second[1] &&
                first[2] != '0' &&
                second[2] != '0' &&
                ((first[2] == '1' && second[2] == '2') ||
                        (first[2] == '2' && second[2] == '1'));
    }

    /**
     * Gives all the possible moves after one move
     *
     * @param s serialization String of the Pentago game
     * @return gameStates
     */
    static DynamicArray<String> get(String s) {
        char symbol = s.charAt(s.length() - 1) == '0' ? '1' : '2';
        return getGamesAfterRotationAndMarbleInsertion(s, symbol);
    }

    private static DynamicArray<String> getGamesAfterRotationAndMarbleInsertion(String s, char symbol) {
        DynamicArray<String> games = new DynamicArray<>();
        for (String g : getGamesAfterMarbleInsertion(s, symbol)) {
            char[] chars = g.toCharArray();
            char[] chars2 = new char[]{chars[38], chars[39], chars[40]};
            for (char[] pos : ROTATION_TILE_DIRECTION_POSSIBILITIES) {
                if (!rotationTileDirectionIsOpposite(chars2, pos)) {
                    chars[38] = pos[0];
                    chars[39] = pos[1];
                    chars[40] = pos[2];
                    games.add(new String(chars));
                }
            }
        }
        return games;
    }

    private static String[] getGamesAfterMarbleInsertion(String s, char symbol) {
        ArrayList<String> gamesBeforeRotation = new ArrayList<>();
        for (int i = 0; i < 36; i++) {
            if (s.charAt(i) == '0') {
                char[] chars = s.toCharArray();
                chars[i] = symbol;
                gamesBeforeRotation.add(new String(chars));
            }
        }
        return gamesBeforeRotation.toArray(new String[gamesBeforeRotation.size()]);
    }
}
