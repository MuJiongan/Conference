import java.io.Serializable;
import java.util.ArrayList;
/**
 * Represents an event in the conference.
 */
public class Event implements Serializable {
    /**
     * The start Time of the event
     */
    private String startTime;
    /**
     * The end Time of the event
     */
    private String endTime;
    /**
     * The roomID of the room where the event takes place
     */
    private int roomID;
    /**
     * The list of all the users IDs in the event
     */
    private ArrayList<Integer> userIDs;
    /**
     * The list of all speakers IDs in the event
     */
    private ArrayList<Integer> speakerIDs;
    /**
     * The name of the event
     */
    private String name;
    /**
     * The total number of the event
     */
    private static int numberOfEvents = 0;
    /**
     * The event ID
     */
    private int eventID;
    /**
     * The event capacity
     */
    private int capacity;


    /**
     * Create a new event object with the given startTime, endTime, roomID, name
     * @param startTime of the event
     * @param endTime  the event
     * @param roomID of the event
     * @param name of the event
     */
    public Event(String startTime, String endTime, int roomID, String name, int capacity){
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomID = roomID;
        this.name = name;
        this.eventID = numberOfEvents;
        this.capacity = capacity;
        numberOfEvents++;
    }

    /**
     * Returns the roomID of the event
     * @return the roomID of the event
     */
    public int getRoomID() {
        return roomID;
    }
    /**
     * Returns the startTime of the event
     * @return the startTime of the event
     */
    public String getStartTime() {
        return startTime;
    }
    /**
     * Returns the endTime of the event
     * @return the endTime of the event
     */
    public String getEndTime() {
        return endTime;
    }
    /**
     * Returns the name of the event
     * @return the name of the event
     */
    public String getName() {
        return name;
    }
    /**
     * Adds an speaker ID to the speakerIDs list
     * @param speakerID to be added in the Event
     */
    public void addSpeakerID(int speakerID) {
        speakerIDs.add(speakerID);
    }
    /**
     * Adds an user ID to the userIDs list
     * @param userID to be added in the Event
     */
    public void addUserID(int userID) {
        userIDs.add(userID);
    }
    /**
     * removes an user ID from the userIDs list
     * @param userID to be removed in the Event
     */
    public void removeUserID(int userID) {
        userIDs.remove(userID);
    }
    /**
     * Returns the shallow copy of UserIDs of the event
     * @return the UserIDs of the event
     */
    public ArrayList<Integer> getUserIDs() {
        return (ArrayList<Integer>) userIDs.clone();
    }
    /**
     * Returns the shallow copy of SpeakerIDs of the event
     * @return the SpeakerIDs of the event
     */
    public ArrayList<Integer> getSpeakerIDs() {
        return (ArrayList<Integer>) speakerIDs.clone();
    }
    /**
     * Returns the eventID of the event
     * @return the eventID of the event
     */
    public int getEventID() {
        return eventID;
    }
    /**
     * removes an speaker ID from the speakerIDs list
     * @param speakerID to be removed in the Event
     */
    public void removeSpeakerID(int speakerID) {
        speakerIDs.remove(speakerID);
    }
    /**
     * change the room ID of the event
     * @param roomID new roomID
     */
    public void changeRoomID(int roomID){
        this.roomID = roomID;
    }

    public int getCapacity() {
        return capacity;
    }
}

