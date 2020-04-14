package bowl;

import java.util.Vector;

import bowl.model.Alley;
import bowl.model.ControlDesk;
import bowl.view.ControlDeskView;

import java.io.*;

public class Drive {

	public static void main(String[] args) {

		int numLanes = 3;
		int maxPatronsPerParty=5;

		Alley a = new Alley( numLanes );
		ControlDesk controlDesk = a.getControlDesk();

		ControlDeskView cdv = new ControlDeskView( controlDesk, maxPatronsPerParty);
		controlDesk.subscribe( cdv );

	}
}
