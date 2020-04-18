package bowl.state;

import bowl.events.PinsetterEvent;
import bowl.model.Bowler;

import java.util.Iterator;

/**
 * @StatePattern: Concrete State
 * <p>
 * final frame of the lane --> one of the states that a Lane
 * can be in
 */
public class FinalFrameLane implements ILaneStatus {

    private final Lane lane;
    private final Iterator bowlerIterator;
    private Bowler currentBowler;
    private Boolean canThrowAgain;
    private int ballNumber;
    private int bowlIndex;

    public FinalFrameLane(Lane lane) {
        this.lane = lane;
        bowlerIterator = lane.getBowlerIterator();
    }

    // TODO: Scoring Methods

    /**
     * Lets the bowler
     */
    @Override
    public void handleRun() {
        bowlIndex = 0;
        if (bowlerIterator.hasNext()){
            //TODO: Can we remove this casting?
            currentBowler = (Bowler) bowlerIterator.next();
            canThrowAgain = true;
            ballNumber = 0;
            lane.setTenthFrameStrike(false);
            while (canThrowAgain){
                lane.throwBall();
                ballNumber++;
            }
            lane.resetPins();
            bowlIndex++;
            //send scores out
        }
        //transition to game finished
        lane.changeStatus(new FinishedGame(lane, lane.getParty()));
    }

    /**
     * transmits data for receiving an assigned party and
     * changes the state of the party
     * @param event
     */
    @Override
    public void handlePinsetterEvent(PinsetterEvent event) {
        int throwNumber = event.getThrowNumber();
        int totalPins = event.totalPinsDown();

        lane.markScore(currentBowler, 9,
                event.pinsDownOnThisThrow(), ballNumber, bowlIndex);
        if (totalPins == 10){
            lane.resetPins();
            if (throwNumber == 1){
                lane.setTenthFrameStrike(true);
            }
        }
        else{
            if (throwNumber == 2 && !lane.isTenthFrameStrike()){
                lane.disableThrow();
            }
            if (throwNumber == 3){
                lane.disableThrow();
            }
        }
    }

    /**
     * Transitions into the PausedLane state
     */
    @Override
    public void handlePauseGame() {
        lane.changeStatus(new PausedLane(lane, this));
    }

    /**
     * Cannot unpause an ongoing game, so just pass through
     */
    @Override
    public void handleUnpauseGame() {
        lane.publish(lane.lanePublish(true));
    }

    private void insertFinalScore(int bowlIndex) {
        //we need to make a method in the lane class that does this
        //and call it here, the lane should know the game number already
//        if (frameNumber == 9) {
//            finalScores[bowlIndex][gameNumber] = cumulScores[bowlIndex][9];
//            try {
//                Date date = new Date();
//                String dateString = "" + date.getHours() + ":" + date.getMinutes() + " " + date.getMonth() + "/" + date.getDay() + "/" + (date.getYear() + 1900);
//                ScoreHistoryFile.addScore(currentThrower.getNick(), dateString, new Integer(cumulScores[bowlIndex][9]).toString());
//            } catch (Exception e) {
//                System.err.println("Exception in addScore. " + e);
//            }
//        }
    }
}
