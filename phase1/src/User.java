import java.util.ArrayList;
import java.util.List;

/**
 * Represents a User in the conference system
 */
public abstract class User {

    private int userID;
    private String name;
    private String userName;
    private String passWord;
    private List<Integer> messagesList;
    private List<Integer> contactList;
    private List<Integer> eventsAttend;
    private static int numOfUsers = 0;

    /**
     * Constructs an instance of Student based on Strings of information
     * @param name User's real name
     * @param userName User's username
     * @param passWord User's password
     */
    public User(String name, String userName, String passWord) {
        numOfUsers++;
        this.userID = numOfUsers;
        this.name = name;
        this.userName = userName;
        this.passWord = passWord;
        this.messagesList = new ArrayList<>();
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
     * Returns the username of the user
     * @return the username of the user
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the password of the user
     * @return the password of the user
     */
    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * Returns the a list of ids of messages received of the user
     * @return the a list of ids of messages received of the user
     */
    public List<Integer> getMessagesList() {
        return messagesList;
    }

    /**
     * Add a message ID to the message list of the user
     * @param messageID to be added to the message list of the user
     */
    public void addMessage(int messageID) {
        this.messagesList.add(messageID);
    }

    /**
     * Returns the a list of ids of contacts of the user
     * @return the a list of ids of contacts of the user
     */
    public List<Integer> getContactList() {
        return contactList;
    }

    /**
     * Add a contact's userID to the contact list of the user
     * @param contactID to be added to the contact list of the user
     */
    public void addContact(int contactID) {
        this.messagesList.add(contactID);
    }

    /**
     * Returns the a list of ids of events the user is going to attend
     * @return the a list of ids of events the user is going to attend
     */
    public List<Integer> getEventsAttend() {
        return eventsAttend;
    }

    /**
     * Add an event to the event list which the user is going to attend
     * @param eventID to be added to the contact list of the user
     */
    public void addEvent(int eventID) {
        this.eventsAttend.add(eventID);
    }

    /**
     * Remove an event to the event list which the user is going to attend
     * @param eventID to be added to the contact list of the user
     */
    public void removeEvent(int eventID) {
        this.eventsAttend.remove(eventID);
    }
}

