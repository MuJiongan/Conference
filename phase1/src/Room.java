import java.io.Serializable;
import java.util.ArrayList;
/**
 * Represents a room in the conference.
 * A room is where the events will occur and has a capacity limit for number of people.
 *
 */
public class Room implements Serializable {
    /**
     * The total number of rooms in the conference
     */
    private static int numberOfRooms = 0;
    /**
     * The maximum number of people in the room at one time
     */
    private int capacity;
    /**
     * Stores the events that will occur in the room
     */
    private ArrayList<Integer> eventsScheduled;
    /**
     * Stores the id of the Room
     */
    private int roomID;

    /**
     * Create a new Room object with the given capacity
     * @param capacity maximum capcity of the room
     */
    public Room(int capacity)
    {
        this.capacity = capacity;
        eventsScheduled = new ArrayList<>();
        roomID = numberOfRooms;
        numberOfRooms++;
    }
    /**
     * Returns the maximum capacity of the room.
     * @return the maximum capacity of the room
     */
    public int getCapacity()
    {
        return capacity;
    }
    /**
     * Returns ID value of the room
     * @return ID value of the room
     */
    public int getRoomID()
    {
        return roomID;
    }

    /**
     * Adds an event ID to the eventsScheduled list
     * @param eventID ID of an event to be scheduled in the Room
     */
    public void addEventID(int eventID)
    {
        eventsScheduled.add(eventID);
    }
    /**
     * Removes an event ID to the eventsScheduled list
     * @param eventID ID of an event to be removed from the schedule for Room
     */
    public void removeEventID(int eventID)
    {
        Integer eventId = eventID;
        eventsScheduled.remove(eventId);
    }

    /**
     * Returns a shallow copy of getEventsScheduled
     * @return shallow copy of getEventsScheduled
     */
    public ArrayList<Integer> getEventsScheduled() {
        return (ArrayList<Integer>) eventsScheduled.clone();
    }
}
