package com.example.model.useCases;

import com.example.model.entities.Attendee;
import com.example.model.entities.User;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class UserManager implements Serializable{
    /**
     * The list of all attendees
     */
    private ArrayList<User> users;


    public UserManager()
    {
        users = new ArrayList<>();
    }
    /**
     * Returns the shallow copy of all users in the list
     * @return the shallow copy of all users in a list
     */
    public List<User> getUsers(){
        return (ArrayList<User>) users.clone();
    }
    /**
     * Returns the id's of the users
     * @return a list of the id's of all the users
     */
    public List<Integer> getUserIDs(){
        List<Integer> allIDs = new ArrayList<>();
        for (User user: getUsers())
        {
            allIDs.add(user.getUserId());
        }
        return allIDs;
    }

    /**
     * add a user to list of users
     * @param user the ID of the user to be added
     * @return true if and only if the user is successfully added to the list
     */
    public boolean addUser(User user){

        for (User person: users){
            if(person == user){
                return false;
            }
        }
        users.add(user);
        return true;
    }

    /**
     * return the userID given the user object
     * @param user the given entities.User object
     * @return the userID corresponding to the user object
     */
    public int getIDByUser(User user){
        return user.getUserId();
    }
    /**
     * add an eventID to the list of all events the user is going to attend
     * @param userID the given User object
     * @param eventID ID of the event the user is going to add
     * @return true if and only if the user is successfully added to the list
     */
    public boolean addEventID(int eventID, int userID){
        User user = getUserByID(userID);
        for (int i = 0; i<user.getEventsAttend().size(); i++){
            if (eventID == user.getEventsAttend().get(i)){
                return false;
            }
        }
        user.addEvent(eventID);
        return true;
    }
    /**
     * return the username  given the userid
     * @param userId the given userid
     * @return the username corresponding to the user id
     */
    public String getnameById(int userId){
        return getUserByID(userId).getName();
    }

    /**
     * remove an eventID from the list of all events the user is going to cancel
     * @param userID the given entities.User object
     * @param eventID ID of the event the user is going to cancel
     * @return true if and only if the user is successfully removed from the list
     */
    public boolean removeEventID(int eventID, int userID) {
        User user = getUserByID(userID);
        boolean exists = false;
        if (user != null) {
            for (int i = 0; i < user.getEventsAttend().size(); i++) {
                if (eventID == user.getEventsAttend().get(i)) {
                    exists = true;
                }
            }
            if (!exists) {
                return false;
            } else {
                user.removeEvent(eventID);
                return true;
            }
        }
        return false;
    }
    /**
     * add a messageID to the messages hashmap for user
     * @param userID the given entities.User object
     * @param messageID ID of the message the user is sending
     * @param otherID the other user's ID the user is chatting to
     */
    public void addMessageID(int messageID, int userID, int otherID){
        User user = getUserByID(userID);
        user.addMessage(otherID, messageID);
    }
    /**
     * return a list of all events the user is going to attend
     * @param userID the given entities.User object
     * @return a list of all events the user is going to attend
     */
    public ArrayList<Integer> getEventList(int userID){
        User user = getUserByID(userID);
        return user.getEventsAttend();
    }
    /**
     * return a entities.User object if the entities.User has the correct password, username, and type and return null otherwise
     * @param username the username given by the user
     * @param password the password given by the user
     * @return the userID if it has the correct password, username and type and -100 otherwise
     */
    public int validate(String username, String password){
        for (User user: users){
            if (user.getUserName().equals(username) && user.getPassWord().equals(password)){
                return getIDByUser(user);
            }
        }
        return -100;
    }

    public boolean hasUserName(String username)
    {
        for (User user: users){
            if (user.getUserName().equals(username)){
                return true;
            }
        }
        return false;
    }

    /**
     * return the entities.User object given the user ID, null if not found
     * @param userID the given attendeeID
     * @return the userID object corresponding to the ID, null if not found
     */
    public User getUserByID(int userID){
        for (User user: users)
        {
            if (user.getUserId() == userID)
            {
                return user;
            }
        }
        return null;
    }
    /**
     * return the HashMap Messages given the userID
     * @param userID object of which user to access
     * @return the HashMap Messages
     */
    public HashMap<Integer, ArrayList<Integer>> getMessages(int userID){
        User user = getUserByID(userID);
        return user.getMessages();
    }

    /**
     * check whether a user in the user lists
     * @param userID of the user
     * @return true if the user lists has this user with given user ID
     */
    public boolean idInList(int userID){
        return users.contains(getUserByID(userID));
    }

    /**
     * change the user's name to the given name
     * @param userID who will change name
     * @param name of user
     */
    public void setName(int userID, String name)
    {
        User user = getUserByID(userID);
        user.userSetName(name);
    }

    /**
     * return the ID of the user that just got created
     * @param name user's name
     * @param userName user's username
     * @param password user's password
     * @param ID user's id
     * @return a list of users' IDs that in the user's contact list
     */
    public boolean createUser(String name, String userName, String password, int ID)
    {
        User user = new User(name, userName, password, ID);
        if (addUser(user))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Delete a message ID from the user's hashmap
     * @param userID the given entities.User object
     * @param friendID friend ID
     * @param messageID message ID
     */
    public boolean deleteMessage(int userID, int friendID, int messageID) {
        return getUserByID(userID).deleteMessage(friendID, messageID);
    }

    /**
     * Add a message ID to the user's archived messages list
     * @param userID the given entities.User object
     * @param friendID friendID to whom the user is sending message to
     * @param messageID message ID
     */
    public boolean addArchivedMessage(int userID, int friendID, int messageID){
        return getUserByID(userID).addArchivedMessage(friendID, messageID);
    }
}
