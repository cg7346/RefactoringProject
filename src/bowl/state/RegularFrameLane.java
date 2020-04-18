package bowl.state;

import bowl.events.PinsetterEvent;
import bowl.model.Bowler;

import java.util.Iterator;

/**
 * @StatePattern: Concrete State
 * <p>
 * regular frame lane --> one of the states that a Lane can be in
 */
public class RegularFrameLane implements ILaneStatus {

    // TODO: Scoring methods
    private final Lane lane;
    private final Iterator bowlerIterator;
    private Bowler currentBowler;
    private Boolean canThrowAgain;
    //private Boolean tenthFameStrike;
    private int ballNumber;
    private int frameNumber;

    public RegularFrameLane(Lane lane) {
        this.lane = lane;
        this.bowlerIterator = lane.getBowlerIterator();
        this.frameNumber = 0;
    }

    /**
     * transmits data for the run method and handles requests
     * to change the state for executing a Lane
     */
    @Override
    public void handleRun() {
        if (bowlerIterator.hasNext()){
            //TODO: Can we remove this casting? I don't think so
            currentBowler = (Bowler) bowlerIterator.next();
            canThrowAgain = true;
            //tenthFameStrike = false;
            while (canThrowAgain){
                lane.throwBall();
            }
            lane.resetPins();
        }
        frameNumber++;
        if (frameNumber == 9){
            lane.changeStatus(new FinalFrameLane(lane));
        }
    }

    /**
     * transmits data for the assignParty method by assigning
     * members to the specified party
     */
    @Override
    public void handleAssignParty() {

    }

    /**
     * Handles the Pinsetter Event sent when a ball is thrown
     * It will mark the score
     * @param event
     */
    @Override
    public void handlePinsetterEvent(PinsetterEvent event) {
        int pinsDown = event.pinsDownOnThisThrow();
        int throwNumber = event.getThrowNumber();

        System.out.println("Pins down: " + pinsDown);
        lane.markScore(currentBowler, frameNumber, pinsDown);
        if (pinsDown >= 0){
            if (pinsDown == 10 || throwNumber == 2){
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
        // pass through
    }
}
