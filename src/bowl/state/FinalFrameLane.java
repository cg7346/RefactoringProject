package bowl.state;

import bowl.events.PinsetterEvent;
import bowl.io.ScoreHistoryFile;
import bowl.model.Bowler;
import bowl.model.Lane;

import java.util.Date;
import java.util.Iterator;

/**
 * @StatePattern: Concrete State
 * <p>
 * final frame of the lane --> one of the states that a Lane
 * can be in
 */
public class FinalFrameLane implements ILaneStatus {

    private Lane lane;
    private Iterator bowlerIterator;
    private Bowler currentBowler;
    private Boolean canThrowAgain;
    private Boolean tenthFameStrike;
    private int ballNumber;
    private int bowlIndex;

    public FinalFrameLane(Lane lane){
        this.lane = lane;
        bowlerIterator = lane.getBowlerIterator();
    }

    // TODO: Scoring Methods

    /**
     * transmits data for the run method and handles requests
     * to change the state for executing a Lane
     */
    @Override
    public void handleRun() {
        int bowlerIndex = 0;
        if (bowlerIterator.hasNext()){
            //TODO: Can we remove this casting?
            currentBowler = (Bowler) bowlerIterator.next();
            canThrowAgain = true;
            tenthFameStrike = false;
            while (canThrowAgain){
                lane.throwBall();
            }
            lane.resetPins();
            bowlerIndex++;
            //send scores out
        }
        //transition to game finished
        lane.changeStatus(new FinishedGame(lane));
    }

    /**
     * transmits data for the assignParty method by assigning
     * members to the specified party
     */
    @Override
    public void handleAssignParty() {

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

        if (totalPins == 10){
            lane.resetPins();
            if (throwNumber == 1){
                lane.enableTenthFrameStrike();
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
     * transmits data for if a game is paused and changes the
     * state
     */
    @Override
    public void handlePauseGame() {
        lane.changeStatus(new PausedLane(lane, this));
    }

    /**
     * transmits the data for resuming a game and changes the
     * state
     */
    @Override
    public void handleUnpauseGame() {
        //pass through
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
