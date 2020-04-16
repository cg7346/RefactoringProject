package bowl.model;

import java.util.HashMap;

public class ScoreTracker {
    private HashMap<Bowler, GameScore> currentScores;
    private int ballNumber;
    private int frameNumber;

    public ScoreTracker(Party party) {
        currentScores = new HashMap<>();

        for (Object bowler : party.getMembers()) {
            currentScores.put((Bowler) bowler, new GameScore());
        }

        System.out.println(currentScores.keySet());
    }

    public HashMap<Bowler, GameScore> getCurrentScores() {
        return currentScores;
    }
}
