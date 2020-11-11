import java.io.Serializable;
import java.util.ArrayList;
/**
 * Represents a organizer in the conference.
 */

public class Organizer extends Attendee implements Serializable {
    /**
     * Create an instance of Organizer
     */
    public Organizer(String name, String username, String password, int userID){
        super(name, username, password, userID);
    }
}