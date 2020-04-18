package bowl.state;

import bowl.events.PinsetterEvent;

/**
 * @StatePattern: Concrete State
 * <p>
 * when a lane is paused --> one of the states that a Lane can
 * be in
 */
public class PausedLane implements ILaneStatus {

    private final Lane lane;
    private ILaneStatus previousStatus;

    public PausedLane(Lane lane, ILaneStatus previousStatus){
        this.lane = lane;
        this.previousStatus = previousStatus;
    }
    /**
     * Simply pass through as it waits for the game to be unpaused
     */
    @Override
    public void handleRun() {
        //we can put in another wait block or just leave it
    }

    /**
     * transmits data for the assignParty method by assigning
     * members to the specified party
     */
    @Override
    public void handleAssignParty() {

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
        lane.publish(lane.lanePublish(false));
        lane.changeStatus(previousStatus);
    }
}
