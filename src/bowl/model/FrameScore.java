package bowl.model;

import java.util.ArrayList;

public class FrameScore implements IScore {
    //List of the pins knocked down on each throw for the frame
    private ArrayList<Integer> ballThrows;
    //Total amount of pins knocked down this frame
    private int score;

    public FrameScore() {
        ballThrows = new ArrayList<>();
    }

    @Override
    public int getScore() {
        return score;
    }

    /**
     * Add a number to the list of pins knocked down per throw, and
     * add it to the total amount of downed pins for this frame (score).
     * @param pinsDown The amount of pins knocked down.
     */
    public void newThrow(int pinsDown) {
        ballThrows.add(pinsDown);
        score += pinsDown;
    }

    /**
     * A strike has occurred for the frame if all 10 pins were knocked
     * down in one throw.
     * @return True if there was a strike, false otherwise.
     */
    public boolean hasStrikeOccurred() {
        return totalPinsDown() == 10 && ballThrows.size() == 1;
    }

    /**
     * A spare has occurred for the frame if all 10 pins were knocked
     * down in two throws.
     * @return True if there was a strike, false otherwise.
     */
    public boolean hasSpareOccurred() {
        return totalPinsDown() == 10 && ballThrows.size() == 2;
    }

    /**
     * Get the total number of pins knocked down for the frame
     * @return The total number of pins downed
     */
    public int totalPinsDown() {
        int total = 0;

        for (int pinsDown : ballThrows) {
            total += pinsDown;
        }

        return total;
    }

    /**
     * Get the array of number of pins knocked down per ball throw
     * @return
     */
    public ArrayList<Integer> getBallThrows() {
        return ballThrows;
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
        if (this.hasStrikeOccurred()) {
            score += nextFrame.totalPinsDown();
        }

        if (this.hasSpareOccurred()) {
            score += nextFrame.getBallThrows().get(0);
        }
    }
}
