/**
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

package bowl.io;

public class PreviousScore {

    private String nick;
    private String date;
    private String score;

    public PreviousScore(String nick, String date, String score ) {
		this.nick=nick;
		this.date=date;
		this.score=score;
    }

    public String getNickName() {
        return nick;  
    }

	public String getDate() {
		return date;
	}
	
	public String getScore() {
		return score;
	}

	public String toString() {
		return nick + "\t" + date + "\t" + score;
	}

}
