package bowl.state;

import bowl.events.PinsetterEvent;
import bowl.model.Lane;

/**
 * @StatePattern: Concrete State
 * <p>
 * when a game is finished  --> one of the states that a Lane
 * can be in
 */
public class FinishedGame implements ILaneStatus {

    private Lane lane;

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

    }

    /**
     * transmits the data for resuming a game and changes the
     * state
     */
    @Override
    public void handleUnpauseGame() {

    }
}
