package bowl.model;

import java.util.ArrayList;

/**
 * Represents a bowler's total score for a game. Maintains
 * a list of FrameScore objects.
 */
public class GameScore {
    private ArrayList<FrameScore> frameScores;
    private ArrayList<FrameScore> strikesAndSpares;

    public GameScore() {
        frameScores = new ArrayList<>();
        strikesAndSpares = new ArrayList<>();
    }

    /**
     * Adds up the scores from each frame.
     * @return The bowler's total score for the game.
     */
    public int getScore() {
        int total = 0;

        for (FrameScore frameScore : frameScores) {
            total += frameScore.getScore();
        }

        return total;
    }

    public void checkStrikeAndSpare() {
        FrameScore currentFrame = getRecentFrameScore();

        if (!(frameScores.size() == 10) && !strikesAndSpares.contains(currentFrame)) {
            if (currentFrame.hasStrikeOccurred() || currentFrame.hasSpareOccurred()) {
                strikesAndSpares.add(currentFrame);
            }
        }

        for (FrameScore checking : strikesAndSpares) {
            int nextFrameCount = 1;
            FrameScore nextFrame;

            try {
                nextFrame = frameScores.get(checking.getFrameNumber() + nextFrameCount);
            } catch (IndexOutOfBoundsException e) {
                //The end of the frameScore list was reached, break the loop
                break;
            }

            while (checking.getThrowsToAdd() != 0) {
                ArrayList<Integer> nextThrowList = nextFrame.getBallThrows();
                for (int pinsDown : nextThrowList) {
                    checking.addNewThrow(pinsDown);
                }

                nextFrameCount++;

                try {
                    nextFrame = frameScores.get(checking.getFrameNumber() + nextFrameCount);
                } catch (IndexOutOfBoundsException e) {
                    //The end of the frameScore list was reached, break the loop
                    break;
                }
            }

            if (checking.getThrowsToAdd() == 0) {
                strikesAndSpares.remove(checking);
            }
        }


        //Adds throws on from the current frame while the counter is not 0
    }

    public int[] getFrameScoreArray() {
        int[] result = new int[frameScores.size()];
        result[0] = frameScores.get(0).getScore();

        for (int i = 1; i < frameScores.size(); i++) {
            result[i] = frameScores.get(i).getScore() + result[i - 1];
        }

        return result;
    }

    /**
     * Get the most recent FrameScore object (being used for the
     * current frame).
     * @return The FrameScore instance.
     */
    public FrameScore getRecentFrameScore() {
        return frameScores.get(frameScores.size() - 1);
    }

    /**
     * Get the entire list of current FrameScores for the bowler.
     * @return An ArrayList with all the FrameScores.
     */
    public ArrayList<FrameScore> getAllFrameScores() {
        return frameScores;
    }

    /**
     * Add a throw to the current frame score (will always be the most
     * recent one in the list of frameScores).
     * @param pinsDown The number of pins knocked down for the throw.
     */
    public void newThrow(int pinsDown) {
        frameScores.get(frameScores.size() - 1).newThrow(pinsDown);
    }

    /**
     * Create a new frame score object when the bowler moves
     * on to the next frame.
     */
    public void newFrame() {
        frameScores.add(new FrameScore(frameScores.size()));
    }
}
