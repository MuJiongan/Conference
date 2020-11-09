import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;


/**
 * Use Case class that will manage how the Room objects are updated during run-time
 */
public class RoomManager implements Serializable {
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
    public Room getRoomByID(int id)
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
     * @return True if room was succesfully added, false otherwise
     */
    public boolean addRoom(Room room)
    {
        if (!rooms.contains(room))
        {
            rooms.add(room);
            return true;
        }
        return false;
    }

    /**
     * Get the capcity of a certain Room r
     * @param r Room's capacity we want
     * @return the capacity of Room r
     */
    public int getCapacity(Room r)
    {
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

    /**
     * Schedules an event in Room r, assumes that event is valid for that specific room
     * @param r Represents the room where event will happen
     * @param event Represents the event ID of Event we want to schedule
     */
    public void scheduleEvent (Room r, int event)
    {
        ArrayList<Integer> eventCopy = r.getEventsScheduled();
        if (!eventCopy.contains(event))
        {
            r.addEventID(event);
        }
    }

    /**
     * Read the RoomManager object that was stored in a .ser file
     * @param path String representing the file path
     * @return RoomManager object read from .ser file
     * @throws ClassNotFoundException is thrown if RoomManager object is not found
     */
    public RoomManager readFromFile (String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path); // String path should be "fileName.ser"
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the StudentManager
            RoomManager rm = (RoomManager) input.readObject();
            input.close();
            return rm;
        } catch (IOException ex) {
            return new RoomManager();
        }
    }

    /**
     * Write the RoomManager object to a .ser file to store once program exists
     * @param filePath file to write to
     * @throws IOException is thrown if file we want to write to does not exist
     */
    public void saveToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the RoomManager
        output.writeObject(this);
        output.close();
    }



}
