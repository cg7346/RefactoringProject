package bowl.state;

import bowl.events.PinsetterEvent;

/**
 * @StatePattern: Concrete State
 * <p>
 * open lane --> one of the states that a Lane can be in
 */
public class OpenLane implements ILaneStatus {

    private final Lane lane;

    public OpenLane(Lane lane){
        this.lane = lane;
    }

    // TODO: resetting scores method

    /**
     * transmits data for the run method and handles requests
     * to change the state for executing a Lane
     */
    @Override
    public void handleRun() {
        if (lane.isPartyAssigned()){
            lane.changeStatus(new RegularFrameLane(lane));
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
        //pass through
    }
}
