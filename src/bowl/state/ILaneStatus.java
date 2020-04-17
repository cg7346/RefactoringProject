package bowl.state;

/**
 * @StatePattern: State
 * an interface for Lane Frames to use
 */
public interface ILaneStatus {
    void handleRun();

    void handleAssignParty();

    void handleReceivePartyAssigned();

    void handlePauseGame();

    void handleUnpauseGame();

}
