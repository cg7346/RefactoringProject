package bowl.model;

import java.util.HashMap;

/**
 * This class is used by a Lane in order to handle scoring for the Bowlers.
 */
public class ScoreTracker {
    //Map of Bowlers to their GameScores (scores for the current game)
    private HashMap<Bowler, GameScore> currentScores;
    //Map of Bowlers to their FinalScores (scores for multiple games)
    private HashMap<Bowler, FinalScore> finalScores;
    //The current frame that the Pinsetter of the Lane is on
    private int frameNumber;

    public ScoreTracker() {
        currentScores = new HashMap<>();
        finalScores = new HashMap<>();
        frameNumber = 0;
    }

    /**
     * Set up the score tracker for a new game by assigning a
     * new GameScore object to each Bowler. Each Bowler also
     * gets a new FinalScore object if they do not have one already.
     * @param party The party at the Lane.
     */
    public void newGame(Party party) {
        //Object type cast preserves original Vector implementation of Party
        for (Object bowler : party.getMembers()) {
            currentScores.put((Bowler) bowler, new GameScore());

            if (finalScores.isEmpty()) {
                finalScores.put((Bowler) bowler, new FinalScore());
            }
        }

        frameNumber = 0;
    }

    /**
     * Go through each Bowler's GameScore object and add a new FrameScore to
     * their lists of FrameScores.
     */
    private void makeNewFrames() {
        for (GameScore gameScore : currentScores.values()) {
            gameScore.newFrame();
        }
    }

    /**
     * A bowler has thrown the ball.
     * @param bowler The bowler who threw.
     * @param eventFrame The frame number that the Pinsetter of the Lane is on.
     * @param pinsDown The amount of pins knocked down for the throw.
     */
    public void newThrow(Bowler bowler, int eventFrame, int pinsDown) {
        if (eventFrame != this.frameNumber) {
            this.frameNumber = eventFrame;
            makeNewFrames();
        }

        currentScores.get(bowler).newThrow(pinsDown);
    }

    /**
     * Get the current Bowlers' scores in array form. Each row of the array represents a
     * Bowler, and each column of the array is an array of the Bowlers' frame scores.
     * The method returns an array to follow the setup of the
     * original LaneView implementation.
     * @return The Bowlers' scores, in a 2d array of integers.
     */
    public int[][] getCurrentScores() {
        int[][] result = new int[currentScores.keySet().size()][frameNumber + 1];

        int row = 0;
        for (GameScore gameScore : currentScores.values()) {
            result[row] = gameScore.getFrameScoreArray();
            row++;
        }

        return result;
    }

    /**
     * Get all the string representations of the throws made in each
     * frame for every bowler. Each row of the array represents a Bowler, and each
     * column of the array is an array of the string representations.
     * The method returns an array to follow the setup of the
     * original LaneView implementation.
     * @return The string representations, in a 2d array.
     */
    public String[][] getAllBowlerFrameThrows() {
        String[][] result = new String[currentScores.keySet().size()][frameNumber + 1];

        int row = 0;
        for (GameScore gameScore : currentScores.values()) {
            result[row] = gameScore.getFramesThrowsArray();
            row++;
        }

        return result;
    }

    public void logGameScore(Bowler bowler) {
        GameScore toLog = currentScores.get(bowler);
        finalScores.get(bowler).addNewGameScore(toLog);
    }

    /**
     * Get the final score of a bowler for the current game.
     * @param bowler The bowler being checked.
     * @return The score of the bowler.
     */
    public int getGameScore(Bowler bowler) {
        return currentScores.get(bowler).getScore();
    }

    public int[][] getAllFinalScores() {
        int[][] result = new int[finalScores.keySet().size()][frameNumber + 1];

        int row = 0;
        for (Bowler bowler : finalScores.keySet()) {
            result[row] = finalScores.get(bowler).getScores();
        }

        return result;
    }
}
