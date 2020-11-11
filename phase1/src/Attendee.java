
import java.io.Serializable;
import java.util.ArrayList;
/**
 * Represents a attendee in the conference.
 */

public class Attendee extends User implements Serializable {
    /**
     * Create an instance of Attendee
     */
    public Attendee(String name, String username, String password, int userID){
        super(name, username, password, userID);
    }

}