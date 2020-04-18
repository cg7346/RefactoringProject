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
     * simply check if a party has been assigned, and if
     * so transition into a regular frame game
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
     * Pass through because we cannot handle events that
     * should not be happening
     * @param event the PinsetterEvent we will not be handling
     */
    @Override
    public void handlePinsetterEvent(PinsetterEvent event) {
        //pass through
    }

    /**
     * Cannot pause game that has yet to start,
     * so just pass through
     */
    @Override
    public void handlePauseGame() {
        //pass through
    }

    /**
     * Cannot unpause game that has yet to start,
     * so just pass through
     */
    @Override
    public void handleUnpauseGame() {
        //pass through
    }
}
