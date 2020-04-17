package bowl.state;

/**
 * @StatePattern: Concrete State
 * <p>
 * when a game is finished  --> one of the states that a Lane
 * can be in
 */
public class FinishedGame implements ILaneStatus {

    // TODO: end game methods

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
     */
    @Override
    public void handleReceivePartyAssigned() {

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
