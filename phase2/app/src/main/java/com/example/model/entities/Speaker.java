package com.example.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a organizer in the conference.
 */
public class Speaker extends User implements Serializable {

    /**
     * Create an instance of Organizer of the given name, username, password and userID
     * @param name name of this Speaker
     * @param username username of this Speaker
     * @param password password of this Speaker
     * @param userID  userID of this Speaker
     */
    public Speaker(String name, String username, String password, int userID){
        super(name, username, password, userID);
    }

}