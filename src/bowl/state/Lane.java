
/* $Id$
 *
 * Revisions:
 *   $Log: Lane.java,v $
 *   Revision 1.52  2003/02/20 20:27:45  ???
 *   Fouls disables.
 *
 *   Revision 1.51  2003/02/20 20:01:32  ???
 *   Added things.
 *
 *   Revision 1.50  2003/02/20 19:53:52  ???
 *   Added foul support.  Still need to update laneview and test this.
 *
 *   Revision 1.49  2003/02/20 11:18:22  ???
 *   Works beautifully.
 *
 *   Revision 1.48  2003/02/20 04:10:58  ???
 *   Score reporting code should be good.
 *
 *   Revision 1.47  2003/02/17 00:25:28  ???
 *   Added disbale controls for View objects.
 *
 *   Revision 1.46  2003/02/17 00:20:47  ???
 *   fix for event when game ends
 *
 *   Revision 1.43  2003/02/17 00:09:42  ???
 *   fix for event when game ends
 *
 *   Revision 1.42  2003/02/17 00:03:34  ???
 *   Bug fixed
 *
 *   Revision 1.41  2003/02/16 23:59:49  ???
 *   Reporting of sorts.
 *
 *   Revision 1.40  2003/02/16 23:44:33  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.39  2003/02/16 23:43:08  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.38  2003/02/16 23:41:05  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.37  2003/02/16 23:00:26  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.36  2003/02/16 21:31:04  ???
 *   Score logging.
 *
 *   Revision 1.35  2003/02/09 21:38:00  ???
 *   Added lots of comments
 *
 *   Revision 1.34  2003/02/06 00:27:46  ???
 *   Fixed a race condition
 *
 *   Revision 1.33  2003/02/05 11:16:34  ???
 *   Boom-Shacka-Lacka!!!
 *
 *   Revision 1.32  2003/02/05 01:15:19  ???
 *   Real close now.  Honest.
 *
 *   Revision 1.31  2003/02/04 22:02:04  ???
 *   Still not quite working...
 *
 *   Revision 1.30  2003/02/04 13:33:04  ???
 *   Lane may very well work now.
 *
 *   Revision 1.29  2003/02/02 23:57:27  ???
 *   fix on pinsetter hack
 *
 *   Revision 1.28  2003/02/02 23:49:48  ???
 *   Pinsetter generates an event when all pins are reset
 *
 *   Revision 1.27  2003/02/02 23:26:32  ???
 *   ControlDesk now runs its own thread and polls for free lanes to assign queue members to
 *
 *   Revision 1.26  2003/02/02 23:11:42  ???
 *   parties can now play more than 1 game on a lane, and lanes are properly released after games
 *
 *   Revision 1.25  2003/02/02 22:52:19  ???
 *   Lane compiles
 *
 *   Revision 1.24  2003/02/02 22:50:10  ???
 *   Lane compiles
 *
 *   Revision 1.23  2003/02/02 22:47:34  ???
 *   More observering.
 *
 *   Revision 1.22  2003/02/02 22:15:40  ???
 *   Add accessor for pinsetter.
 *
 *   Revision 1.21  2003/02/02 21:59:20  ???
 *   added conditions for the party choosing to play another game
 *
 *   Revision 1.20  2003/02/02 21:51:54  ???
 *   LaneEvent may very well be observer method.
 *
 *   Revision 1.19  2003/02/02 20:28:59  ???
 *   fixed sleep thread bug in lane
 *
 *   Revision 1.18  2003/02/02 18:18:51  ???
 *   more changes. just need to fix scoring.
 *
 *   Revision 1.17  2003/02/02 17:47:02  ???
 *   Things are pretty close to working now...
 *
 *   Revision 1.16  2003/01/30 22:09:32  ???
 *   Worked on scoring.
 *
 *   Revision 1.15  2003/01/30 21:45:08  ???
 *   Fixed speling of received in Lane.
 *
 *   Revision 1.14  2003/01/30 21:29:30  ???
 *   Fixed some MVC stuff
 *
 *   Revision 1.13  2003/01/30 03:45:26  ???
 *   *** empty log message ***
 *
 *   Revision 1.12  2003/01/26 23:16:10  ???
 *   Improved thread handeling in lane/controldesk
 *
 *   Revision 1.11  2003/01/26 22:34:44  ???
 *   Total rewrite of lane and pinsetter for R2's observer model
 *   Added Lane/Pinsetter Observer
 *   Rewrite of scoring algorythm in lane
 *
 *   Revision 1.10  2003/01/26 20:44:05  ???
 *   small changes
 *
 *
 */

package bowl.state;

import bowl.events.LaneEvent;
import bowl.events.PinsetterEvent;
import bowl.io.ScoreHistoryFile;
import bowl.io.ScoreReport;
import bowl.model.Bowler;
import bowl.model.Party;
import bowl.model.Pinsetter;
import bowl.model.ScoreTracker;
import bowl.observers.ILaneObserver;
import bowl.observers.IPinsetterObserver;
import bowl.view.EndGamePrompt;
import bowl.view.EndGameReport;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class Lane extends Thread implements IPinsetterObserver {
    private Party party;
    private final Pinsetter setter;
    private final ArrayList<ILaneObserver> subscribers;

    private boolean gameIsHalted;

    private boolean partyAssigned;
    private Iterator bowlerIterator;
    private int ball;
    private int bowlIndex;
    private int frameNumber;

    //Game number the lane is on (starts at -1 until party assigned)
    private int gameNumber;

    private Bowler currentThrower;            // = the thrower who just took a throw

    //Keeps track of scoring for all players during the game
    private ScoreTracker scoreTracker;
    ILaneStatus status;

    /**
     * Lane()
     * <p>
     * Constructs a new lane and starts its thread
     *
     * @pre none
     * @post a new lane has been created and its thered is executing
     */
    public Lane() {
        this.status = new OpenLane(this);
        this.setter = new Pinsetter();
        this.subscribers = new ArrayList<>();

        this.gameIsHalted = false;
        this.partyAssigned = false;
        gameNumber = -1;

        this.setter.subscribe(this);
        this.scoreTracker = new ScoreTracker();

        this.start();
    }

    /**
     * run()
     * <p>
     * entry point for execution of this lane
     */
    @Override
    public void run() {

        //this will turn into
        // status.handleRun()
        //try sleep ......

        while (true) {
            status.handleRun();
			try {
				sleep(10);
			} catch (Exception e) {}
		}
	}
	
	/** recievePinsetterEvent()
	 * 
	 * recieves the thrown event from the pinsetter
	 *
	 * @pre none
	 * @post the event has been acted upon if desiered
	 * 
	 * @param pe 		The pinsetter event that has been received.
	 */
	public void receivePinsetterEvent(PinsetterEvent pe) {
	    if (pe.pinsDownOnThisThrow() >= 0){
            status.handlePinsetterEvent(pe);
        }

	}
	
	/**
     * resetBowlerIterator()
     * <p>
     * sets the current bower iterator back to the first bowler
     *
     * @pre the party as been assigned
     * @post the iterator points to the first bowler in the party
     */
	Iterator<Bowler> resetBowlerIterator() {
        bowlerIterator = (party.getMembers()).iterator();
        return bowlerIterator;
    }

    /**
     * resetScores()
     * <p>
     * resets the scoring mechanism, must be called before scoring starts
     *
     * @pre the party has been assigned
     * @post scoring system is initialized
     */
    void resetScores() {
        scoreTracker.newGame(party);
		frameNumber = 0;
		gameNumber++;
	}

	/** assignParty()
	 *
	 * assigns a party to this lane
	 *
	 * @pre none
	 * @post the party has been assigned to the lane
	 *
	 * @param theParty		Party to be assigned
	 */
	public void assignParty( Party theParty ) {
        party = theParty;
        resetBowlerIterator();
        partyAssigned = true;

        //A new party has not done any games yet
        scoreTracker = new ScoreTracker();
        scoreTracker.newGame(theParty);
        gameNumber = -1;

        resetScores();
    }

    /**
     * markScore()
     * <p>
     * Method that marks a bowlers score on the board.
     *
     * @param Cur   The current bowler
     * @param frame The frame that bowler is on
     *              //@param ball		The ball the bowler is on
     * @param score The bowler's score
     */
    public void markScore(Bowler Cur, int frame, int score, int ball, int bowlIndex) {
        this.currentThrower= Cur;
        this.bowlIndex = bowlIndex;
        this.frameNumber = frame;
        this.ball = ball;

        scoreTracker.newThrow(Cur, frame, score);
        publish(lanePublish(this.gameIsHalted));
    }

    /**
     * lanePublish()
     * <p>
     * Method that creates and returns a newly created laneEvent
     *
     * @return The new lane event
     */
    LaneEvent lanePublish(boolean gameIsHalted) {
        this.gameIsHalted = gameIsHalted;
        LaneEvent laneEvent = new LaneEvent(party, bowlIndex, currentThrower, scoreTracker.getCurrentScores(),
                scoreTracker.getAllBowlerFrameThrows(), frameNumber + 1, ball, gameIsHalted);
		return laneEvent;
	}

    /**
     * isPartyAssigned()
     * <p>
     * checks if a party is assigned to this lane
     *
     * @return true if party assigned, false otherwise
     */
    public boolean isPartyAssigned() {
        return partyAssigned;
    }

    /**
     * subscribe
     * <p>
     * Method that will add a subscriber
     *
     * @param adding Observer that is to be added
     */

    public void subscribe(ILaneObserver adding) {
        subscribers.add(adding);
    }

    /**
     * unsubscribe
     * <p>
     * Method that unsubscribes an observer from this object
     *
     * @param removing The observer to be removed
     */

    public void unsubscribe(ILaneObserver removing) {
        subscribers.remove(removing);
    }

    /**
     * publish
     * <p>
     * Method that publishes an event to subscribers
     *
     * @param event Event that is to be published
     */
    public void publish(LaneEvent event) {
        if (subscribers.size() > 0) {
            Iterator eventIterator = subscribers.iterator();

            while (eventIterator.hasNext()) {
                ((ILaneObserver) eventIterator.next()).receiveLaneEvent(event);
            }
        }
    }

    /**
     * Accessor to get this Lane's pinsetter
     *
     * @return A reference to this lane's pinsetter
     */

    public Pinsetter getPinsetter() {
        return setter;
    }

    /**
     * Pause the execution of this game
     */
    public void pauseGame() {
       status.handlePauseGame();
    }

    /**
     * Resume the execution of this game
     */
    public void unPauseGame() {
        //gameIsHalted = false;
        status.handleUnpauseGame();
    }

    /**
     * used to change the status of the game to another state
     * Regular Frame Lane
     * Open Lane
     * Final Frame Lane
     * Paused Game
     * Finished Game
     *
     * @param stat games current status
     */
    void changeStatus(ILaneStatus stat) {
        status = stat;
    }

    /**
     * Used by the pinsetterEvent handler to reset the pins after
     * the 10th frame strike
     */
    void resetPins(){
        setter.resetPins();
    }

    /**
     * Used by the pinsetterEvent handler to reset the setter after
     */
    void resetSetter(){
        setter.reset();
    }

    /**
     * Called by the state to make a PinsetterEvent
     * happen
     */
    void throwBall(){
        setter.ballThrown();
    }

    /**
     * Allows the state to iterate over the bowlers
     *
     * @return An Iterator for Bowlers
     */
    Iterator<Bowler> getBowlerIterator() {
        return bowlerIterator;
    }

    /**
     * Lets the Finished Game frame to set the assigned party to generate end GameReport
     *
     * @param assigned true or false if the game has been assigned
     */
    void setAssignedParty(boolean assigned) {
        this.partyAssigned = assigned;
    }

    /**
     * Allows the Finished Game frame to gather the final scores for the GameReport
     *
     * @return the final scores at the end of the game
     */
    int[][] getFinalScores() {
        return scoreTracker.getAllFinalScores();
    }

    /**
     * Allows the Finished Game frame to get the game number for the Score Report
     *
     * @return the current games' number
     */
    int getGameNumber() {
        return gameNumber;
    }

    Party getParty(){
        return party;
    }

    /**
     * Log the final game score of a bowler in the score tracker, and
     * in the score history file.
     * @param bowler The bowler being logged.
     */
    void insertFinalScore(Bowler bowler){
        scoreTracker.logGameScore(bowler);

        try {
            Date date = new Date();
            String dateString = "" + date.getHours() + ":" + date.getMinutes() + " " + date.getMonth() + "/" + date.getDay() + "/" + (date.getYear() + 1900);
            ScoreHistoryFile.addScore(bowler.getNick(), dateString, String.valueOf(scoreTracker.getGameScore(bowler)));
        } catch (Exception e) {
             System.err.println("Exception in addScore. " + e);
        }
    }
}
