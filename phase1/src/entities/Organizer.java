package entities;

import entities.Attendee;

import java.io.Serializable;

/**
 * Represents a organizer in the conference.
 */

public class Organizer extends Attendee implements Serializable {
    /**
     * Create an instance of entities.Organizer
     */
    public Organizer(String name, String username, String password, int userID){
        super(name, username, password, userID);
    }
}