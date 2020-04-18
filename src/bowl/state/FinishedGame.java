package bowl.state;

import bowl.events.PinsetterEvent;

/**
 * @StatePattern: Concrete State
 * <p>
 * when a game is finished  --> one of the states that a Lane
 * can be in
 */
public class FinishedGame implements ILaneStatus {

    private final Lane lane;

    // TODO: end game methods

    public FinishedGame(Lane lane){
        this.lane = lane;
    }

    /**
     * transmits data for the run method and handles requests
     * to change the state for executing a Lane
     */
    @Override
    public void handleRun() {

    }

    /**
     * transmits data for the assignParty method by assigning
     * members to the specified party
     */
    @Override
    public void handleAssignParty() {

    }

    /**
     * Pass through because we cannot handle events when the
     * the game is over and we should not be getting events anyways
     * @param event the event we will not be handling
     */
    @Override
    public void handlePinsetterEvent(PinsetterEvent event) {
        //pass through
    }

    /**
     * Cannot pause a game while it runs the end game report
     * so pass through
     */
    @Override
    public void handlePauseGame() {
        //pass through
    }

    /**
     * Cannot unpause a game that is not paused
     */
    @Override
    public void handleUnpauseGame() {
        //pass through
    }
}
