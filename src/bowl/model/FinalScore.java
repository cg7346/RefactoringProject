package bowl.model;

import java.util.ArrayList;

/**
 * This class holds a bowler's multiple game scores during
 * a single bowling session.
 */
public class FinalScore {
    //The previous scores that the bowler has gotten in each game
    private ArrayList<GameScore> gameScores;

    public FinalScore() {
        gameScores = new ArrayList<>();
    }

    /**
     * Add a new game score to the list of previous game scores.
     * @param gameScore The GameScore that the bowler just finished with.
     */
    public void addNewGameScore(GameScore gameScore) {
        gameScores.add(gameScore);
    }

    /**
     * Get all the game scores for the bowler during the session.
     * @return The scores, in an array.
     */
    public int[] getScores() {
        int[] result = new int[gameScores.size()];

        int i = 0;
        for (GameScore gameScore : gameScores) {
            result[i] = gameScore.getScore();
            i++;
        }

        return result;
    }
}
