package com.example.model.useCases;

import com.example.model.entities.Room;

import java.io.*;
import java.util.ArrayList;


/**
 * Use Case class that will manage how the entities.Room objects are updated during run-time
 */
public class RoomManager implements Serializable {

    private ArrayList<Room> rooms;

    /**
     * Create a new useCases.RoomManager object
     */
    public RoomManager()
    {
        rooms = new ArrayList<>();
    }

    /**
     * Returns the corresponding room with id. If no room has that id, return null.
     * @param id ID of room we want
     * @return entities.Room with corresponding id. If none exists, return null
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
     * Returns the id of the entities.Room corresponding to the entities.Room object parameter
     * @param roomID The room that we want to get corresponding ID of
     * @return ID of corresponding entities.Room, -1 if entities.Room not found
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
     * Add a new entities.Room to the list of all entities.Room in the conference
     * @param roomID entities.Room to add to list
     * @return True if room was succesfully added, false otherwise
     */
    public boolean addRoom(int roomID)
    {
        Room room = getRoomByID(roomID);
        if (!rooms.contains(room))
        {
            rooms.add(room);
            return true;
        }
        return false;
    }

    /**
     * Get the capacity of a certain entities.Room r
     * @param roomID entities.Room's capacity we want
     * @return the capacity of entities.Room r
     */
    public int getCapacity(int roomID)
    {
        Room room = getRoomByID(roomID);
        return room.getCapacity();
    }

    /**
     * Return the list of event ID's for a certain entities.Room r
     * @param roomID entities.Room's event ID's we want
     * @return list of event ID's for entities.Room r
     */
    public ArrayList<Integer> getSchedule(int roomID)
    {
        Room room = getRoomByID(roomID);
        return room.getEventsScheduled();
    }


    /**
     * Schedules an event in entities.Room r, assumes that event is valid for that specific room
     * @param roomID Represents the room where event will happen
     * @param event Represents the event ID of entities.Event we want to schedule
     */
    public void scheduleEvent (int roomID, int event)
    {
        Room r = getRoomByID(roomID);
        ArrayList<Integer> eventCopy = r.getEventsScheduled();
        if (!eventCopy.contains(event))
        {
            r.addEventID(event);
        }
    }

    /**
     * Return a new room with given features
     * @param name of new room
     * @param capacity of new room
     * @return a new room with given features
     */
    public Room createRoom(String name, int capacity){
        return new Room(capacity, name, rooms.size() + 1);
    }
    /**
     * Get the room name
     * @param roomID .Room
     * @return the name of entities.Room room
     */
    public String getRoomName(int roomID){
        Room room = getRoomByID(roomID);
        return room.getName();
    }
    /**
     * Read the useCases.RoomManager object that was stored in a .ser file
     * @param path String representing the file path
     * @return useCases.RoomManager object read from .ser file
     * @throws ClassNotFoundException is thrown if useCases.RoomManager object is not found
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
     * Write the useCases.RoomManager object to a .ser file to store once program exists
     * @param filePath file to write to
     * @throws IOException is thrown if file we want to write to does not exist
     */
    public void saveToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the useCases.RoomManager
        output.writeObject(this);
        output.close();
    }

    /**
     * Return a list of all rooms
     * @return a list of all rooms
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }
}
