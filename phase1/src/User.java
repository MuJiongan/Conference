import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


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
     * Stores a HashMap with key of the receiver's ID and value of message ID sent by this user
     */
    private HashMap<Integer, ArrayList<Integer>> messageSent;
    /**
     * Stores a HashMap with key of the sender's ID and value of message ID received by this user
     */
    private HashMap<Integer, ArrayList<Integer>> messageReceived;
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
        this.messageSent = new HashMap<>();
        this.messageReceived = new HashMap<>();
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
     * Returns the shallow copy of the HashMap messageSent
     * @return shallow copy of messageSent
     */
    public HashMap<Integer, ArrayList<Integer>> getMessagesSent() {
        return (HashMap<Integer, ArrayList<Integer>>) messageSent.clone();
    }

    /**
     * Returns the shallow copy of the HashMap messageReceived
     * @return shallow copy of messageReceived
     */
    public HashMap<Integer, ArrayList<Integer>> getMessagesReceived() {
        return (HashMap<Integer, ArrayList<Integer>>) messageReceived.clone();
    }

    /**
     * Add a message ID to the receiverID's list
     * @param receiverID receiver ID
     * @param messageID message ID
     */
    public void addSentMessage(int receiverID, int messageID) {
        if (!messageSent.containsKey(receiverID)){
            ArrayList<Integer> newList = new ArrayList<>();
            messageSent.put(receiverID, newList);
        }
        this.messageSent.get(receiverID).add(messageID);

    }
    /**
     * Add a message ID to the receiverID's list
     * @param senderID sender ID
     * @param messageID message ID
     */
    public void addReceivedMessage(int senderID, int messageID) {
        if (!messageReceived.containsKey(senderID)){
            ArrayList<Integer> newList = new ArrayList<>();
            messageReceived.put(senderID, newList);
        }
        this.messageReceived.get(senderID).add(messageID);
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

