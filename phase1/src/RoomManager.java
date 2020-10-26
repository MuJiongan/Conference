import java.util.ArrayList;

/**
 * Use Case class that will manage how the Room objects are updated during run-time
 */
public class RoomManager {
    /**
     * Stores all the instances of room in the conference
     */
    private ArrayList<Room> rooms;

    /**
     * Create a new RoomManager object
     */
    public RoomManager()
    {
        rooms = new ArrayList<>();
    }

    /**
     * Returns the corresponding room with id. If no room has that id, return null.
     * @param id ID of room we want
     * @return Room with corresponding id. If none exists, return null
     */
    public Room getRoombyID(int id)
    {
        //Invariant: Assume that we enter enter a valid id
        for (Room r: rooms)
        {
            if (r.getRoomID() == id)
            {
                return r;
            }
        }
        return null;
    }

    /**
     * Returns the id of the Room corresponding to the Room object parameter
     * @param room The room that we want to get corresponding ID of
     * @return ID of corresponding Room, -1 if Room not found
     */
    public int getIDbyRoom(Room room)
    {
        for (Room r: rooms)
        {
            if (r== room)
            {
                return r.getRoomID();
            }
        }
        return -1;
    }

    /**
     * Add a new Room to the list of all Room in the conference
     * @param room Room to add to list
     */
    public void addRoom(Room room)
    {
        if (!rooms.contains(room))
        rooms.add(room);
    }

    /**
     * Get the capcity of a certain Room r
     * @param r Room's capacity we want
     * @return the capacity of Room r
     */
    public int getCapacity(Room r)
    {
        //Ask About THis
        return r.getCapacity();
    }

    /**
     * Return the list of event ID's for a certain Room r
     * @param r Room's event ID's we want
     * @return list of event ID's for Room r
     */
    public ArrayList<Integer> getSchedule(Room r)
    {
        return r.getEventsScheduled();
    }

    public void addEvent (Room r, int event)
    {
        ArrayList<Integer> eventCopy = r.getEventsScheduled();
        if (!eventCopy.contains(event))
        {
            r.addEventID(event);
        }
    }
//Ask about calling entity methoDs in the conroller


}
