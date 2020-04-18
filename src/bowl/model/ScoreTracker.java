package bowl.model;

import java.util.Arrays;
import java.util.HashMap;

public class ScoreTracker {
    private HashMap<Bowler, GameScore> currentScores;
    private Lane lane;
    private int ballNumber;
    private int frameNumber;

    public ScoreTracker(Party party, Lane lane) {
        currentScores = new HashMap<>();
        this.lane = lane;

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

    public int[][] getCurrentScoresAsArray() {
        int[][] result = new int[currentScores.keySet().size()][frameNumber + 1];

        int row = 0;
        for (GameScore gameScore : currentScores.values()) {
            result[row] = gameScore.getFrameScoreArray();
            row++;
        }

        System.out.println(Arrays.deepToString(result));

        return result;
    }

    private void makeNewFrames() {
        for (GameScore gameScore : currentScores.values()) {
            if (frameNumber != 0) {
                gameScore.checkStrikeAndSpare();
            }

            gameScore.newFrame();
        }
    }

    public void newThrow(Bowler bowler, int eventFrame, int pinsDown) {
        if (eventFrame != this.frameNumber) {
            this.frameNumber = eventFrame;
            makeNewFrames();
        }

        currentScores.get(bowler).newThrow(pinsDown);
        //lane.scoreMarked();
    }
}
