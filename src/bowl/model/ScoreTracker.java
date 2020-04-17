package bowl.model;

import java.util.HashMap;

public class ScoreTracker {
    private HashMap<Bowler, GameScore> currentScores;
    private int ballNumber;
    private int frameNumber;

    public ScoreTracker(Party party) {
        currentScores = new HashMap<>();

        //Object type cast preserves original Vector implementation of Party
        for (Object bowler : party.getMembers()) {
            currentScores.put((Bowler) bowler, new GameScore());
        }

        frameNumber = -1;

        //System.out.println(currentScores.keySet());
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

        return result;
    }

    private void makeNewFrames(int eventFrame) {
        for (GameScore gameScore : currentScores.values()) {
            if (eventFrame >= 2) {
                gameScore.checkStrikeAndSpare();
            }

            gameScore.newFrame();
        }
    }

    public void newThrow(Bowler bowler, int eventFrame, int pinsDown) {
        if (eventFrame != this.frameNumber) {
            makeNewFrames(eventFrame);
        }

        currentScores.get(bowler).newThrow(pinsDown);
    }
}
