package bowl.state;

import bowl.events.PinsetterEvent;

/**
 * @StatePattern: State
 * an interface for Lane Frames to use
 */
public interface ILaneStatus {
    /**
     * transmits data for the run method and handles requests
     * to change the state for executing a Lane
     */
    void handleRun();

    /**
     * transmits data for the assignParty method by assigning
     * members to the specified party
     */
    void handleAssignParty();

    /**
     * transmits data for receiving an assigned party and
     * changes the state of the party
     * @param event
     */
    void handlePinsetterEvent(PinsetterEvent event);

    /**
     * transmits data for if a game is paused and changes the
     * state
     */
    void handlePauseGame();

    /**
     * transmits the data for resuming a game and changes the
     * state
     */
    void handleUnpauseGame();

}
