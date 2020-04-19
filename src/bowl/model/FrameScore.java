package bowl.model;

import java.util.ArrayList;

/**
 * Represents a bowler's score for a single frame. Each throw
 * is stored as an individual number of pins knocked down.
 */
public class FrameScore {
    //List of the number of pins knocked down on each throw for the frame
    private ArrayList<Integer> ballThrows;
    //The representations of the ball throws in string form
    private ArrayList<String> ballThrowsStrings;
    //Total amount of pins knocked down this frame
    private int score;
    //The count of how many throws to add to the score (for strike/spare)
    private int throwsToAddCount;
    //The frame number this score represents (0-9)
    private final int frameNumber;

    public FrameScore(int frameNumber) {
        ballThrows = new ArrayList<>();
        ballThrowsStrings = new ArrayList<>();
        score = 0;
        throwsToAddCount = 0;
        this.frameNumber = frameNumber;
    }

    /**
     * Get the total number of pins knocked down this frame.
     * @return The score for the frame.
     */
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
        newThrowToString(pinsDown);
        score += pinsDown;
    }

    /**
     * Add a new throw to the list of string representations for the ball throws.
     * A throw that results in a strike is represented with "X", and a throw that
     * results in a spare is represented with "/". On any frame before the 10th frame,
     * a strike has an empty string preceding it to match the format for bowling scoring
     * tables.
     * @param pinsDown The amount of pins knocked down for the new throw.
     */
    private void newThrowToString(int pinsDown) {
        if (frameNumber < 9) {
            if (pinsDown == 10) {
                ballThrowsStrings.add("");
                ballThrowsStrings.add("X");
                return;
            }
        } else if (frameNumber == 9) {
            if (pinsDown == 10) {
                ballThrowsStrings.add("X");
                return;
            }
        }

        if (hasSpareOccurred()) {
            ballThrowsStrings.add("/");
        } else {
            ballThrowsStrings.add(String.valueOf(pinsDown));
        }
    }

    /**
     * A strike has occurred for the frame if all 10 pins were knocked
     * down in one throw. This also means that the scores of the next
     * 2 throws get added on to this frame.
     * This method is not used on the 10th frame.
     * @return True if there was a strike, false otherwise.
     */
    public boolean hasStrikeOccurred() {
        if (ballThrows.get(0) == 10) {
            throwsToAddCount = 2;
            return true;
        }

        return false;
    }

    /**
     * A spare has occurred for the frame if all 10 pins were knocked
     * down in two throws, and if a strike has not occurred. This also
     * means that the score of the next 1 throw gets added on to this frame.
     * @return True if there was a strike, false otherwise.
     */
    public boolean hasSpareOccurred() {
        if (totalPinsDown() == 10 && ballThrows.size() == 2) {
            throwsToAddCount = 1;
            return true;
        }

        return false;
    }

    /**
     * Add on to the score for this frame and decrement the amount of
     * future throw scores to be added to the frame. This method is only
     * used when this frame was a strike or spare.
     * @param pinsDown The amount of pins down for the throw.
     */
    public void addNewThrowScore(int pinsDown) {
        score += pinsDown;
        throwsToAddCount--;
    }

    /**
     * Get the amount of throw scores to be added to this frame.
     * @return The amount of throws to add.
     */
    public int getThrowsToAddCount() {
        return throwsToAddCount;
    }

    /**
     * Get the total number of pins knocked down for the frame.
     * @return The total number of pins downed.
     */
    public int totalPinsDown() {
        int total = 0;

        for (int pinsDown : ballThrows) {
            total += pinsDown;
        }

        return total;
    }

    /**
     * Get the string representations of the ball throws for the frame.
     * @return The string representations in an ArrayList.
     */
    public ArrayList<String> getBallThrowsStrings() {
        return ballThrowsStrings;
    }
}
