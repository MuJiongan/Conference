package entities;

import entities.Attendee;

import java.io.Serializable;

/**
 * Represents a organizer in the conference.
 */

public class Organizer extends Attendee implements Serializable {
    /**
     * Create an instance of Organizer with the given name, username, password and userID
     * @param name name of this Organizer
     * @param username username of this Organizer
     * @param password password of this Organizer
     * @param userID userID of this Organizer
     */
    public Organizer(String name, String username, String password, int userID){
        super(name, username, password, userID);
    }
}