package entities;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Represents a room in the conference.
 * A room is where the events will occur and has a capacity limit for number of people.
 *
 */
public class Room implements Serializable {
    /**
     * The maximum number of people in the room at one time
     */
    private int capacity;
    /**
     * Stores the events that will occur in the room
     */
    private ArrayList<Integer> eventsScheduled;
    /**
     * Stores the id of the entities.Room
     */
    private int roomID;
    private String name;

    /**
     * Create a new entities.Room object with the given capacity
     * @param capacity maximum capacity of this room
     * @param name name of the room
     * @param roomID roomID of the room
     */
    public Room(int capacity, String name, int roomID)
    {
        this.capacity = capacity;
        eventsScheduled = new ArrayList<>();
        this.roomID = roomID;
        this.name = name;
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
     * @param eventID ID of an event to be scheduled in the entities.Room
     */
    public void addEventID(int eventID)
    {
        eventsScheduled.add(eventID);
    }
    /**
     * Removes an event ID to the eventsScheduled list
     * @param eventID ID of an event to be removed from the schedule for entities.Room
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


    /**
     * Returns the name of the room
     * @return the name of the room
     */
    public String getName() {
        return name;
    }
}

