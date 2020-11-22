package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public abstract class User implements Serializable {
    private int userID;
    private String name;
    private String userName;
    private String passWord;
    private HashMap<Integer, ArrayList<Integer>> message;
    private ArrayList<Integer> contactList;
    private ArrayList<Integer> eventsAttend;

    /**
     * Constructs an instance of Student based on the given name, userName, password and userID
     * @param name User's real name
     * @param userName User's username
     * @param passWord User's password
     * @param userID User's userID
     */
    public User(String name, String userName, String passWord, int userID) {

        this.userID = userID;
        this.name = name;
        this.userName = userName;
        this.passWord = passWord;
        this.message = new HashMap<>();
        this.contactList = new ArrayList<>();
        this.eventsAttend = new ArrayList<>();
    }

    /**
     * Returns the userID of the user
     * @return the userID of the user
     */
    public int getUserId() {
        return userID;
    }

    /**
     * Returns the name of the user
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * change name to the given new name
     * @param newName of the user
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Returns the userName of the user
     * @return the userName of the user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * change the username to the given new username
     * @param userName of the user
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the passWord of the user
     * @return the passWord of the user
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * change the password to the given new password
     * @param passWord of the user
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * Returns the shallow copy of the HashMap message
     * @return shallow copy of message
     */
    public HashMap<Integer, ArrayList<Integer>> getMessages() {
        return (HashMap<Integer, ArrayList<Integer>>) message.clone();
    }
    /**
     * Add a message ID to the user's hashmap
     * @param userID receiver ID
     * @param messageID message ID
     */
    public void addMessage(int userID, int messageID) {
        if (!message.containsKey(userID)){
            ArrayList<Integer> newList = new ArrayList<>();
            message.put(userID, newList);
        }
        this.message.get(userID).add(messageID);

    }
    /**
     * Returns the shallow copy of contactList of the user
     * @return shallow copy of contactList of the user
     */
    public ArrayList<Integer> getContactList() {
        return (ArrayList<Integer>) contactList.clone();
    }

    /**
     * Add a contact's userID to the contactList of the user
     * @param contactID to be added to the contactList of the user
     */
    public void addContact(int contactID) {
        this.contactList.add(contactID);
    }

    /**
     * Returns the shallow copy of eventsAttend list of the user
     * @return shallow copy of eventsAttend list of the user
     */
    public ArrayList<Integer> getEventsAttend() {
        return (ArrayList<Integer>) eventsAttend.clone();
    }

    /**
     * Add an event to the eventsAttend list of the user
     * @param eventID to be added to the eventsAttend list of the user
     */
    public void addEvent(int eventID) {
        this.eventsAttend.add(eventID);
    }

    /**
     * Remove an event to the eventsAttend list of the user
     * @param eventID to be removed from the eventsAttend list of the user
     */
    public void removeEvent(int eventID) {
        Integer eventId = eventID;
        this.eventsAttend.remove(eventId);
    }
    /**
     * Add the userid to the contactList of this user
     * @param userID the id of the user that this user can message to and receive messages from
     */
    public void addToContactsList(int userID)
    {
        contactList.add(userID);
    }
    /**
     * change the name of this user to the given name
     * @param name new name that this user want to set
     */
    public void userSetName(String name)
    {
        this.name = name;
    }
}