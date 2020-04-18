package bowl.model;

import java.util.HashMap;

/**
 * This class is used by a Lane in order to handle scoring for the Bowlers.
 */
public class ScoreTracker {
    //Map of Bowlers to their current GameScores
    private HashMap<Bowler, GameScore> currentScores;
    //The current frame that the Pinsetter of the Lane is on
    private int frameNumber;

    public ScoreTracker(Party party) {
        currentScores = new HashMap<>();

        //Object type cast preserves original Vector implementation of Party
        for (Object bowler : party.getMembers()) {
            currentScores.put((Bowler) bowler, new GameScore());
        }
        makeNewFrames();

        frameNumber = 0;
    }

    public HashMap<Bowler, GameScore> getCurrentScores() {
        return currentScores;
    }

    /**
     * Get the current Bowlers' scores in array form. Each row of the array is a
     * Bowler, and each column of the array is an array of the Bowlers' frame scores.
     * @return The Bowlers' scores, in a 2d array of integers.
     */
    public int[][] getCurrentScoresAsArray() {
        int[][] result = new int[currentScores.keySet().size()][frameNumber + 1];

        int row = 0;
        for (GameScore gameScore : currentScores.values()) {
            result[row] = gameScore.getFrameScoreArray();
            row++;
        }

        return result;
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
}
