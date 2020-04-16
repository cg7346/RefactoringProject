package bowl.model;

import java.util.ArrayList;

public class FrameScore implements IScore {
    private ArrayList<Integer> ballThrows;
    private boolean strike;
    private boolean spare;
    private int score;

    public FrameScore() {
        ballThrows = new ArrayList<>();
    }

    @Override
    public int getScore() {
        return score;
    }

    /**
     * Adds to the score of this frame based on the pins downed during
     * the next frame if this one was a strike or a spare. A strike means
     * the total amount of pins knocked down on the next frame gets added
     * to this frame's score. A spare means the number of pins knocked down
     * on the first throw of the next frame gets added to this frame's score.
     * @param nextFrame The FrameScore of the frame after this one.
     */
    public void addScoreFromNextFrame(FrameScore nextFrame) {
        if (this.strike) {
            score += nextFrame.totalPinsDown();
        }

        if (this.spare) {
            score += nextFrame.getBallThrows().get(0);
        }
    }

    public void newThrow(int pinsDown) {
        ballThrows.add(pinsDown);
        score += pinsDown;

        if (totalPinsDown() == 10) {
            if (ballThrows.size() == 1) {
                strike = true;
            } else if (ballThrows.size() == 2) {
                spare = true;
            }
        }
    }

    public boolean hasStrikeOccurred() {
        return strike;
    }

    public boolean hasSpareOccurred() {
        return spare;
    }

    /**
     * Get the total number of pins knocked down for the frame
     * @return
     */
    public int totalPinsDown() {
        int total = 0;

        for (int pinsDown : ballThrows) {
            total += pinsDown;
        }

        return total;
    }

    public ArrayList<Integer> getBallThrows() {
        return ballThrows;
    }
}
