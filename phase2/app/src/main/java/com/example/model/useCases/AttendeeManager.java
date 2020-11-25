package com.example.model.useCases;

import com.example.model.entities.Attendee;

import java.io.*;

public class AttendeeManager extends UserManager implements Serializable{

    public AttendeeManager()
    {
        super();
    }
    /**
     * creates a new attendee object and returns it
     * @param name entities.User's real name
     * @param username entities.User's username
     * @param password entities.User's password
     * @param userID entities.User's ID
     * @return the user object that we created
     */

    public Attendee createAttendee(String name, String username, String password, int userID){
        return new Attendee(name, username, password, userID);
    }

    /**
     * Read the useCases.UserManager object that was stored in a .ser file
     * @param path String representing the file path
     * @return useCases.UserManager object read from .ser file
     * @throws ClassNotFoundException is thrown if useCases.UserManager object is not found
     */
    public AttendeeManager readFromFile (String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path); // String path should be "fileName.ser"
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the StudentManager
            AttendeeManager am = (AttendeeManager) input.readObject();
            input.close();
            return am;
        } catch (IOException ex) {
            return new AttendeeManager();
        }
    }
    /**
     * Write the useCases.UserManager object to a .ser file to store once program exists
     * @param filePath file to write to
     * @throws IOException is thrown if file we want to write to does not exist
     */
    public void saveToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the useCases.UserManager
        output.writeObject(this);
        output.close();
    }
}