/* ControlDeskEvent.java
 *
 *  Version:
 *  		$Id$
 * 
 *  Revisions:
 * 		$Log$
 * 
 */

/**
 * Class that represents control desk event
 *
 */

package bowl.events;

import java.util.*;

public class ControlDeskEvent {

	/** A representation of the wait queue, containing party names */
	private ArrayList<String> partyQueue;

    /**
     * Contstructor for the ControlDeskEvent
     *
     * @param partyQueue	a Vector of Strings containing the names of the parties in the wait queue
     *
     */

	public ControlDeskEvent( ArrayList<String> partyQueue ) {
		this.partyQueue = partyQueue;
	}

    /**
     * Accessor for partyQueue
     * @param key the key of the vertex being looked for.
     *
     * @return a Vector of Strings representing the names of the parties in the wait queue
     *
     */

	public ArrayList<String> getPartyQueue() {
		return partyQueue;
	}

}
