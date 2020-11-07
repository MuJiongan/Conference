import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

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
    public ArrayList<User> getUsers(){
        return (ArrayList<User>) users.clone();
    }
    /**
     * add a user to list of users
     * @param user to be added
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
     * @param user the given User object
     * @return the userID corresponding to the user object
     */
    public int getIDByUser(User user){
        return user.getUserId();
    }
    /**
     * add an eventID to the list of all events the user is going to attend
     * @param user the given User object
     * @param eventID ID of the event the user is going to add
     * @custom.precondition
     *       MAKE_SURE_EVENT_VACANCY_IS_GREATER_THAN_ZERO
     * @return true if and only if the user is successfully added to the list
     */
    public boolean addEventID(int eventID, User user){
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
    public String getUsernameById(int userId){
        return getUserByID(userId).getUserName();
    }

    /**
     * remove an eventID from the list of all events the user is going to cancel
     * @param user the given User object
     * @param eventID ID of the event the user is going to cancel
     * @return true if and only if the user is successfully removed from the list
     */
    public boolean removeEventID(int eventID, User user) {
        boolean exists = false;
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
    /**
     * add a messageID to the list of the messageSent
     * @param user the given User object
     * @param messageID ID of the message the user is sending
     * @param receiverID of the receiver of the message
     * @custom.precondition
     * receiverID and messageID have to be valid
     */
    public void addSentMessageID(int messageID, User user, int receiverID){
        user.addSentMessage(receiverID, messageID);
    }
    /**
     * add a messageID to the list of the messageReceived
     * @param user the given User object
     * @param messageID ID of the message the user is receiving
     * @param senderID sender of the message
     * @custom.precondition
     * senderID and messageID have to be valid
     */
    public void addReceivedMessageID(int messageID, User user, int senderID){
        user.addReceivedMessage(senderID, messageID);
    }
    /**
     * return a list of all events the user is going to attend
     * @param user the given User object
     * @return a list of all events the user is going to attend
     */
    public ArrayList<Integer> getEventList(User user){
        return user.getEventsAttend();
    }
    /**
     * return a User object if the User has the correct password, username, and type and return null otherwise
     * @param username the username given by the user
     * @param password the password given by the user
     * @custom.precondition
     * userType.toLowerCase().equals("attendee") || userType.toLowerCase().equals("speaker") || userType.toLowerCase().equals("organizer")
     * @return the user if it has the correct password, username and type and null otherwise
     */
    public User validate(String username, String password){
        for (User user: users){
            if (user.getUserName().equals(username) && user.getPassWord().equals(password)){
                return user;
            }
        }
        return null;
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
     * return the User object given the user ID, null if not found
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
     * return the HashMap receivedMessages given the userID
     * @Precondition user object has to be valid(in one of the UserManager lists)
     * @param user object of which user to access
     * @return the HashMap receivedMessages
     */
    public HashMap<Integer, ArrayList<Integer>> getReceivedMessages(User user){
        return user.getMessagesReceived();
    }
    /**
     * return the HashMap sentMessages given the userID
     * @Precondition user object has to be valid(in one of the UserManager lists)
     * @param user object of which user to access
     * @return the HashMap sentMessages
     */
    public HashMap<Integer, ArrayList<Integer>> getSentMessages(User user){
        return user.getMessagesSent();
    }






}
