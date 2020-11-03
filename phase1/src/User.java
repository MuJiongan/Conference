import java.io.Serializable;
import java.util.ArrayList;



public abstract class User implements Serializable {

    /**
     * Stores the userID of the user
     */
    private int userID;
    /**
     * Stores the name of the user
     */
    private String name;
    /**
     * Stores the user name of the user
     */
    private String userName;
    /**
     * Stores the password of the user
     */
    private String passWord;
    /**
     * Stores a list of message IDs received and sent by the user
     */
    private ArrayList<Integer> messagesList;
    /**
     * Stores a list of contact IDs of the user
     */
    private ArrayList<Integer> contactList;
    /**
     * Stores a list of event IDs the user is going to attend
     */
    private ArrayList<Integer> eventsAttend;
    /**
     * The total number of the Users
     */
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
     * Returns the userName of the user
     * @return the userName of the user
     */
    public String getUserName() {
        return userName;
    }

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

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * Returns the shallow copy of messagesList of the user
     * @return shallow copy of messagesList of the user
     */
    public ArrayList<Integer> getMessagesList() {
        return (ArrayList<Integer>) messagesList.clone();
    }

    /**
     * Add a message ID to the messagesList of the user
     * @param messageID to be added to the messagesList of the user
     */
    public void addMessage(int messageID) {
        this.messagesList.add(messageID);
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
     * @param eventID to be added to the eventsAttend list of the user
     */
    public void removeEvent(int eventID) {
        this.eventsAttend.remove(eventID);
    }
}

