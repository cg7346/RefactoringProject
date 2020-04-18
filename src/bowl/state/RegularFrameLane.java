package bowl.state;

import bowl.events.PinsetterEvent;
import bowl.model.Bowler;
import bowl.model.Lane;

import java.util.Iterator;

/**
 * @StatePattern: Concrete State
 * <p>
 * regular frame lane --> one of the states that a Lane can be in
 */
public class RegularFrameLane implements ILaneStatus {

    // TODO: Scoring methods
    private Lane lane;
    private Iterator bowlerIterator;
    private Bowler currentBowler;
    private Boolean canThrowAgain;
    //private Boolean tenthFameStrike;
    private int ballNumber;

    public RegularFrameLane(Lane lane){
        this.lane = lane;
        this.bowlerIterator = lane.getBowlerIterator();
    }

    /**
     * transmits data for the run method and handles requests
     * to change the state for executing a Lane
     */
    @Override
    public void handleRun() {
        if (bowlerIterator.hasNext()){
            //TODO: Can we remove this casting?
            currentBowler = (Bowler) bowlerIterator.next();
            canThrowAgain = true;
            //tenthFameStrike = false;
            while (canThrowAgain){
                lane.throwBall();
            }
            lane.resetPins();
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
     * transmits data for receiving an assigned party and
     * changes the state of the party
     * @param event
     */
    @Override
    public void handlePinsetterEvent(PinsetterEvent event) {
        int pinsDown = event.pinsDownOnThisThrow();
        int throwNumber = event.getThrowNumber();

        System.out.println("Pins down: " + pinsDown);
        lane.markScore(pinsDown);
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
        lane.changeStatus(new PausedLane(lane));
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
