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
    private Iterator bowlerIterator;
    private Bowler currentBowler;
    private Boolean canThrowAgain;
    private int ballNumber;
    private int bowlIndex;
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
        System.out.println("Current Frame: " + frameNumber);
        bowlIndex = 0;
        if (bowlerIterator.hasNext()){
            //TODO: Can we remove this casting? I don't think so
            currentBowler = (Bowler) bowlerIterator.next();
            System.out.println("Current bowler " + currentBowler.getNickName());
            canThrowAgain = true;
            ballNumber = 0;
            while (canThrowAgain){
                lane.throwBall();
                ballNumber++;
            }
            lane.resetSetter();
            bowlIndex++;
        }else {
            frameNumber++;
            bowlerIterator = lane.resetBowlerIterator();
            bowlIndex = 0;
        }
        if (frameNumber == 9){
            lane.changeStatus(new FinalFrameLane(lane));
        }
    }

    /**
     * Handles the Pinsetter Event sent when a ball is thrown
     * It will mark the score then check if the bowler's turn is done
     * @param event the PinsetterEvent sent when a ball is thrown
     */
    @Override
    public void handlePinsetterEvent(PinsetterEvent event) {
        int pinsDown = event.pinsDownOnThisThrow();
        int throwNumber = event.getThrowNumber();

        System.out.println("Pins down: " + pinsDown + " Throw " + throwNumber);
        lane.markScore(currentBowler, frameNumber, pinsDown,
                ballNumber, bowlIndex);

        // if all the pins were knocked down or this is their second ball
        // they cannot throw again
        if (pinsDown == 10){
            canThrowAgain = false;
        }else if (throwNumber == 2){
            canThrowAgain = false;
        }
    }

    /**
     * Transitions into the PausedLane state
     */
    @Override
    public void handlePauseGame() {
        lane.publish(lane.lanePublish(true));
        lane.changeStatus(new PausedLane(lane, this));
    }

    /**
     * Cannot unpause an ongoing game, so just pass through
     */
    @Override
    public void handleUnpauseGame() {
        // pass through
    }
}
