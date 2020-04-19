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
    private final Iterator<Bowler> bowlerIterator;
    private Bowler currentBowler;
    private Boolean canThrowAgain;
    private int ballNumber;
    private int bowlIndex;
    private boolean tenthFrameStrike;

    public FinalFrameLane(Lane lane) {
        this.lane = lane;
        bowlerIterator = lane.getBowlerIterator();
    }

    /**
     * Lets the bowler
     */
    @Override
    public void handleRun() {
        bowlIndex = 0;
        while (bowlerIterator.hasNext()){
            //TODO: Can we remove this casting?
            currentBowler = (Bowler) bowlerIterator.next();
            canThrowAgain = true;
            ballNumber = 0;
            while (canThrowAgain){
                lane.throwBall();
                ballNumber++;
            }
            tenthFrameStrike = false;
            lane.resetSetter();
            lane.insertFinalScore(currentBowler);
            bowlIndex++;

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
                tenthFrameStrike = true;
            }
        }
        if (totalPins != 10 && (throwNumber == 2 && !tenthFrameStrike)){
            canThrowAgain = false;
        }
        if (throwNumber == 3){
            canThrowAgain = false;
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
}

