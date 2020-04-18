package bowl.model;

import java.util.ArrayList;

/**
 * Represents a bowler's score for a single frame. Each throw
 * is stored as an individual number of pins knocked down.
 */
public class FrameScore {
    //List of the number of pins knocked down on each throw for the frame
    private ArrayList<Integer> ballThrows;
    //Total amount of pins knocked down this frame
    private int score;
    //The count of how many throws to add to the score (for strike/spare)
    private int throwsToAddCount;

    public FrameScore() {
        ballThrows = new ArrayList<>();
        score = 0;
        throwsToAddCount = 0;
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
        score += pinsDown;
    }

    /**
     * A strike has occurred for the frame if all 10 pins were knocked
     * down in one throw. This also means that the scores of the next
     * two throws get added on to this frame.
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
     * means that the scores of the next 1 throw gets added on to this frame.
     * This method is not used on the 10th frame.
     * @return True if there was a strike, false otherwise.
     */
    public boolean hasSpareOccurred() {
        if (hasStrikeOccurred()) {
            return false;
        }

        if (totalPinsDown() >= 10) {
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
     * Get the list of number of pins knocked down per ball throw.
     * @return An arraylist containing each throw for the frame.
     */
    public ArrayList<Integer> getBallThrows() {
        return ballThrows;
    }

    /**
     * Convert the throws for this frame into string form. The array
     * will be of size 2, or 3 for frame 10. Any empty spaces (where
     * a throw was not made) will be filled with an empty string. Strikes
     * are marked with an "X" and spares are marked with a "/".
     * @return An array of Strings.
     */
    public String[] getBallThrowsAsArray() {
        String[] result = new String[ballThrows.size()];

        /* Work in progress */


//        if (hasStrikeOccurred()) {
//            result[0] = ballThrows.get(0);
//            result[1] = -1;
//        } else {
//            for (int i = 0; i < 2; i++) {
//                result[i] = ballThrows.get(i);
//            }
//        }

        return result;
    }
}
