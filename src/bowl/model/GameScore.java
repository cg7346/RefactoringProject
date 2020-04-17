package bowl.model;

import java.util.ArrayList;

/**
 * Represents a bowler's total score for a game. Maintains
 * a list of FrameScore objects.
 */
public class GameScore {
    private ArrayList<FrameScore> frameScores;

    public GameScore() {
        frameScores = new ArrayList<>();
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

    /**
     * Get the most recent FrameScore object (being used for the
     * current frame).
     * @return The FrameScore instance.
     */
    public FrameScore getCurrentFrameScore() {
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
        frameScores.add(new FrameScore());
    }
}
