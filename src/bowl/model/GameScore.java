package bowl.model;

import java.util.ArrayList;

public class GameScore implements IScore {
    private ArrayList<FrameScore> frameScores;

    public GameScore() {
        frameScores = new ArrayList<>();
    }

    @Override
    public int getScore() {
        return 0;
    }

    public void addScore(int pinsDown) {
        frameScores.get(frameScores.size() - 1).newThrow(pinsDown);
    }

    public void newFrame() {
        frameScores.add(new FrameScore());
    }
}
