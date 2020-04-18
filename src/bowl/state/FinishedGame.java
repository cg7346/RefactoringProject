package bowl.state;

import bowl.events.PinsetterEvent;
import bowl.io.ScoreReport;
import bowl.model.Bowler;
import bowl.model.Party;
import bowl.view.EndGamePrompt;
import bowl.view.EndGameReport;

import java.util.Iterator;
import java.util.Vector;

/**
 * @StatePattern: Concrete State
 * <p>
 * when a game is finished  --> one of the states that a Lane
 * can be in
 */
public class FinishedGame implements ILaneStatus {

    private final Lane lane;
    private Party party;

    // TODO: end game methods

    public FinishedGame(Lane lane, Party party) {
        this.lane = lane;
        this.party = party;
    }

    /**
     * Handles the end game report and then transitions to open lane
     */
    @Override
    public void handleRun() {
        EndGamePrompt egp = new EndGamePrompt(((Bowler) party.getMembers().get(0)).getNickName() + "'s Party");
        int result = egp.getResult();
        egp.distroy();
        lane.status = new OpenLane(lane);

        System.out.println("result was: " + result);

        if (result == 1) {                    // yes, want to play again
            lane.resetScores();
            lane.resetBowlerIterator();

        } else if (result == 2) {// no, dont want to play another game
            Vector printVector;
            EndGameReport egr = new EndGameReport(((Bowler) party.getMembers().get(0)).getNickName() + "'s Party", party);
            printVector = egr.getResult();
            lane.setAssignedParty(false);
            Iterator scoreIt = party.getMembers().iterator();
            party = null;
            lane.setAssignedParty(false);

            lane.publish(lane.lanePublish());

            int myIndex = 0;
            while (scoreIt.hasNext()) {
                Bowler thisBowler = (Bowler) scoreIt.next();
                ScoreReport sr = new ScoreReport(thisBowler, lane.getFinalScores()[myIndex++], lane.getGameNumber());
                sr.sendEmail(thisBowler.getEmail());
                Iterator printIt = printVector.iterator();
                while (printIt.hasNext()) {
                    if (thisBowler.getNick() == printIt.next()) {
                        System.out.println("Printing " + thisBowler.getNick());
                        sr.sendPrintout();
                    }
                }

            }
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
