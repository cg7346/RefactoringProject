/* ControlDeskObserver.java
 *
 *  Version
 *  $Id$
 * 
 *  Revisions:
 * 		$Log$
 * 
 */

/**
 * Interface for classes that observe control desk events
 *
 */

package bowl.observers;

import bowl.events.ControlDeskEvent;

public interface ControlDeskObserver {

	public void receiveControlDeskEvent(ControlDeskEvent ce);

}
