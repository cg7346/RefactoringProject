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

    /**
     * Put the total score of each frame from the list of FrameScores
     * into an array.
     * @return The total score of each frame, as an array of integers.
     */
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
     * Get all the string representations of the throws made in each frame.
     * @return The string representations, in an array.
     */
    public String[] getFramesThrowsArray() {
        ArrayList<String> resultList = new ArrayList<>();

        for (FrameScore frameScore : frameScores) {
            resultList.addAll(frameScore.getBallThrowsStrings());
        }

        String[] result = new String[resultList.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = resultList.get(i);
        }

        return result;
    }

    /**
     * Create a new frame score object when the bowler moves
     * on to the next frame.
     */
    public void newFrame() {
        frameScores.add(new FrameScore(frameScores.size()));
    }

    /**
     * Add a throw to the current frame score (will always be the most
     * recent one in the list of FrameScores), and to any previous frames
     * that were a strike or spare. If a strike or spare has now occurred
     * in this frame, it gets added to the list of frames that can have
     * future throw scores added.
     * @param pinsDown The number of pins knocked down for the throw.
     */
    public void newThrow(int pinsDown) {
        FrameScore currentFrameScore = getRecentFrameScore();
        currentFrameScore.newThrow(pinsDown);

        if (currentFrameScore.hasStrikeOccurred() || currentFrameScore.hasSpareOccurred()) {
            strikesAndSpares.add(currentFrameScore);
        }

        addToStrikesAndSpares(pinsDown);
    }

    /** Add the amount of pins downed for a throw to the scores of previous
     * frames that had a strike or a spare, only if they can still have a
     * number of throws to add.
     */
    private void addToStrikesAndSpares(int pinsDown) {
        FrameScore currentFrameScore = getRecentFrameScore();

        if (!strikesAndSpares.isEmpty()) {
            for (FrameScore toAddScore : strikesAndSpares) {
                if (toAddScore.getThrowsToAddCount() != 0 && !toAddScore.equals(currentFrameScore)) {
                    toAddScore.addNewThrowScore(pinsDown);
                }
            }
        }
    }
}
