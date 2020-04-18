package bowl.state;

import bowl.events.PinsetterEvent;

import java.sql.SQLOutput;

/**
 * @StatePattern: Concrete State
 * <p>
 * when a lane is paused --> one of the states that a Lane can
 * be in
 */
public class PausedLane implements ILaneStatus {

    private final Lane lane;
    private final ILaneStatus previousStatus;

    public PausedLane(Lane lane, ILaneStatus previousStatus){
        this.lane = lane;
        this.previousStatus = previousStatus;
    }
    /**
     * Simply pass through as it waits for the game to be unpaused
     */
    @Override
    public void handleRun() {
        System.out.println("PausedGame");
        //we can put in another wait block or just leave it
    }

    /**
     * Pass through because we cannot handle events when paused
     * and we should not be getting events anyways
     * @param event the event we will not be handling
     */
    @Override
    public void handlePinsetterEvent(PinsetterEvent event) {
        //pass through
    }

    /**
     * Cannot pause a game that is already paused,
     * so just pass through
     */
    @Override
    public void handlePauseGame() {
        //pass through
    }

    /**
     * Set the state back to the previous state, hopefully
     * it will pick up where it left off with its variables
     * intact
     */
    @Override
    public void handleUnpauseGame() {
        System.out.println("UNPAUSE GAME");
        lane.publish(lane.lanePublish(false));
        lane.changeStatus(previousStatus);
    }
}
