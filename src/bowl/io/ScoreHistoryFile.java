/**
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

package bowl.io;

import java.util.*;

import java.io.*;

public class ScoreHistoryFile {

	private static String SCOREHISTORY_DAT = "SCOREHISTORY.DAT";

	public static void addScore(String nick, String date, String score)
		throws IOException, FileNotFoundException {

		String data = nick + "\t" + date + "\t" + score + "\n";

		RandomAccessFile out = new RandomAccessFile(SCOREHISTORY_DAT, "rw");
		out.skipBytes((int) out.length());
		out.writeBytes(data);
		out.close();
	}

	public static ArrayList<PreviousScore> getScores(String nick)
		throws IOException, FileNotFoundException {
		ArrayList<PreviousScore> scores = new ArrayList<>();

		try (BufferedReader in = new BufferedReader(new FileReader(SCOREHISTORY_DAT))) {
			String data;
			while ((data = in.readLine()) != null) {
				// File format is nick\tfname\te-mail
				String[] scoredata = data.split("\t");
				//"Nick: scoredata[0] Date: scoredata[1] Score: scoredata[2]
				if (nick.equals(scoredata[0])) {
					scores.add(new PreviousScore(scoredata[0], scoredata[1], scoredata[2]));
				}
			}
			return scores;
		}
	}

}
