package entities;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Represents a attendee in the conference.
 */

public class Attendee extends User implements Serializable {
    /**
     * Create an instance of entities.Attendee with the given name, username, password and userID
     * @param name name of this Attendee
     * @param username username of this Attendee
     * @param password password of this Attendee
     * @param userID  userID of this Attendee
     */
    public Attendee(String name, String username, String password, int userID){
        super(name, username, password, userID);
    }

}