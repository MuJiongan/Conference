package com.example.model.useCases;

import com.example.model.entities.Room;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Use Case class that will manage how the entities.Room objects are updated during run-time
 */
public class RoomManager implements Serializable {

    private List<Room> rooms;

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
     * @param room The room that we want to get corresponding ID of
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
     * check whether a room in the rooms list
     * @param roomID of the user
     * @return true if the room list has this room with given room ID
     */
    public boolean idInList(int roomID){
        return rooms.contains(getRoomByID(roomID));
    }

    /**
     * Add a new entities.Room to the list of all entities.Room in the conference
     * @param room entities.Room to add to list
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
     * Schedules an event in entities.Room r, assumes that event is valid for that specific room
     * @param roomID Represents the room where event will happen
     * @param event Represents the event ID of entities.Event we want to schedule
     */
    public void scheduleEvent (int roomID, int event)
    {
        Room r = getRoomByID(roomID);
        List<Integer> eventCopy = r.getEventsScheduled();
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
    public int createRoom(String name, int capacity){
        Room room = new Room(capacity, name, rooms.size() + 1);
        addRoom(room);
        return room.getRoomID();
    }

    /**
     * Remove the Event from Room list
     * @param roomID id of room where the Event held
     * @param eventID id of Event to be removed
     */
    public void removeEventID(int roomID, int eventID){
        getRoomByID(roomID).removeEventID(eventID);
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
     * Return a list of all room ID's
     * @return a list of all room ID's
     */
    public List<Integer> getRooms() {
        List<Integer> roomIDs = new ArrayList<>();
        for (Room room: rooms)
        {
            roomIDs.add(getIDbyRoom(room));
        }
        return roomIDs;
    }

    /**
     * return the number of rooms in the conference
     * @return return the number of rooms in the conference
     */
    public int getNumOfRooms(){
        return rooms.size();
    }
}
