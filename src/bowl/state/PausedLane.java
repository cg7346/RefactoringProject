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
     * transmits data for the run method and handles requests
     * to change the state for executing a Lane
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
     * transmits data for receiving an assigned party and
     * changes the state of the party
     * @param event
     */
    @Override
    public void handlePinsetterEvent(PinsetterEvent event) {
        //pass through
    }

    /**
     * transmits data for if a game is paused and changes the
     * state
     */
    @Override
    public void handlePauseGame() {
        //pass through
    }

    /**
     * transmits the data for resuming a game and changes the
     * state
     */
    @Override
    public void handleUnpauseGame() {
        lane.changeStatus(previousStatus);
    }
}
