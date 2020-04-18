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
    //The frame number in the bowling game
    private int frameNumber;
    //The count of how many throws to add to the score (for strike/spare)
    private int throwsToAdd;

    public FrameScore(int frameNumber) {
        ballThrows = new ArrayList<>();
        score = 0;
        this.frameNumber = frameNumber;
        throwsToAdd = 0;
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
     * two throws get added on to this frame. This method excludes the 10th frame.
     * @return True if there was a strike, false otherwise.
     */
    public boolean hasStrikeOccurred() {
        if (ballThrows.get(0) == 10) {
            throwsToAdd = 2;
            return true;
        }

        return false;
    }

    /**
     * A spare has occurred for the frame if all 10 pins were knocked
     * down in two throws. This also means that the scores of the next
     * throw gets added on to this frame. This method excludes the 10th frame.
     * @return True if there was a strike, false otherwise.
     */
    public boolean hasSpareOccurred() {
        int totalDown = 0;

        for (Integer pinsDown : ballThrows) {
            totalDown += pinsDown;
        }

        if (totalDown >= 10) {
            throwsToAdd = 1;
            return true;
        }

        return false;
    }

    public void addNewThrow(int pinsDown) {
        if (throwsToAdd != 0) { //TODO: Remove conditional
            score += pinsDown;
            throwsToAdd--;
        }else {
            //throw new RuntimeException("Fuck");
        }
    }

    public int getThrowsToAdd() {
        return throwsToAdd;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

//    /**
//     * Get the total number of pins knocked down for the frame
//     * @return The total number of pins downed
//     */
//    public int totalPinsDown() {
//        int total = 0;
//
//        for (int pinsDown : ballThrows) {
//            total += pinsDown;
//        }
//
//        return total;
//    }

    /**
     * Get the array of number of pins knocked down per ball throw.
     * @return An arraylist containing each throw for the frame
     */
    public ArrayList<Integer> getBallThrows() {
        return ballThrows;
    }

    public String[] getBallThrowsAsArray() {
        String[] result = new String[ballThrows.size()];



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

//    /**
//     * Adds to the score of this frame based on the pins downed during
//     * the next frame if this one was a strike or a spare. A strike means
//     * the total amount of pins knocked down on the next frame gets added
//     * to this frame's score. A spare means the number of pins knocked down
//     * on the first throw of the next frame gets added to this frame's score.
//     * @param nextFrame The FrameScore of the frame after this one.
//     */
//    public void addScoreFromNextFrame(FrameScore nextFrame) {
//        if (this.hasStrikeOccurred()) {
//            score += nextFrame.totalPinsDown();
//        }
//
//        if (this.hasSpareOccurred()) {
//            score += nextFrame.getBallThrows().get(0);
//        }
//    }
}
