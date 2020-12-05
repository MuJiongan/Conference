package com.example.model.useCases;

import com.example.model.entities.Event;


import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class EventManager implements Serializable{

    /**
     * The list of all events
     */
    private List<Event> events = new ArrayList<>();
    private int numOfCancelledEvents;

    /**
     * Return the start time of the given event
     * @param eventID that will be held
     * @return the start time of the given event
     */
    public LocalDateTime getStartTime(int eventID)
    {
        Event event = getEventByID(eventID);
        return event.getStartTime();
    }

    /**
     * Change the start time of the event to the given time
     * @param eventID which start time will be changed
     * @param time that will be set as start time
     */
    public void setStartTime(int eventID, LocalDateTime time)
    {
        Event event = getEventByID(eventID);
        event.setStartTime(time);
    }

    /**
     * Return the end time of the given event
     * @param eventID that will be held
     * @return the end time of the given event
     */
    public LocalDateTime getEndTime(int eventID)
    {
        Event event = getEventByID(eventID);
        return event.getEndTime();
    }

    /**
     * Change the end time of the event to the given time
     * @param eventID which end time will be changed
     * @param time that will be set as end time
     */
    public void setEndTime(int eventID, LocalDateTime time)
    {
        Event event = getEventByID(eventID);
        event.setEndTime(time);
    }

    /**
     * Returns the string representation of all the events
     * @return the string representation of all the events
     */
    public List<Integer> getEvents() {
        List<Integer> eventIDs = new ArrayList<>();
        for (Event e: events)
        {
            eventIDs.add(getIDByEvent(e));
        }
//        return (List<Integer>) ((ArrayList<Integer>) eventIDs).clone();
        return new ArrayList<>(eventIDs);
    }

    /**
     * add an event to events
     * @param event event to be added
     * @return true if and only if the event is successfully added to the event list
     */
    public boolean addEvent(Event event){
        for (Event value : events) {
            if (event == value) {
                return false;
            }
        }
        events.add(event);
        return true;
    }

    /**
     * remove an event to events
     * @param eventID event to be removed
     * @return true if and only if the event is successfully removed to the event list
     */
    public boolean removeEvent(int eventID){
        Event event = getEventByID(eventID);
        for (Event value : events) {
            if (event == value) {
                events.remove(event);
                return true;
            }
        }
        return false;
    }

    /**
     * return the event object given the event ID
     * @param eventID the given eventID
     * @return the event object corresponding to the ID
     */
    public Event getEventByID(int eventID){
        for (Event event: events){
            if (event.getEventID() == eventID){
                return event;
            }
        }
        return null;
    }
    /**
     * return the vacancy given the event object
     * @param eventID the given entities.Event object
     * @return the vacancy corresponding to the entities.Event object
     */
    public int getVacancy(int eventID){
        Event event = getEventByID(eventID);
        return event.getCapacity() - event.getUserIDs().size();
    }

    /**
     * return the event ID given the event object
     * @param event the given entities.Event object
     * @return the event ID corresponding to the entities.Event object
     */
    public int getIDByEvent(Event event){
        return event.getEventID();
    }

    /**
     * add a userID to the list of all attendees in the event
     * @param userID ID of entities.Attendee to be added
     * @param eventID in which event the attendee is being added
     * @return true if and only if the attendee is successfully added to the list
     */
    public boolean addUserID(int userID, int eventID){
        Event event = getEventByID(eventID);
        for (int i = 0; i<event.getUserIDs().size(); i++){
            if (userID == event.getUserIDs().get(i)){
                return false;
            }
        }
        event.addUserID(userID);
        return true;
    }

    /**
     * add a speakerID to the list of all speakers in the event
     * @param speakerID the ID of entities.Speaker that entities.Event to be added
     * @param eventID in which event the speaker is being added
     * @return true if and only if the speaker is successfully added to the list
     */
    public boolean addSpeakerID(int speakerID, int eventID){
        Event event = getEventByID(eventID);
        for (int i = 0; i<event.getSpeakerIDs().size(); i++){
            if (speakerID == event.getSpeakerIDs().get(i)){
                return false;
            }
        }

        event.addSpeakerID(speakerID);
        return true;
    }

    /**
     * Remove an entities.Attendee's ID from the list of all attendees of the event
     * @param userID ID of entities.Attendee to be removed
     * @param eventID in which event the entities.Attendee is being removed
     * @return true if and only if the entities.Attendee is successfully removed
     */
    public boolean removeUserID(int userID, int eventID) {
        boolean exists = false;
        Event event = getEventByID(eventID);
        for (int i = 0; i < event.getUserIDs().size(); i++) {
            if (userID == event.getUserIDs().get(i)) {
                exists = true;
            }
        }
        if (!exists){
            return false;
        }
        else{
            event.removeUserID(userID);
            return true;
        }
    }

    /**
     * Remove a entities.Speaker's ID from the list of all entities.Speaker of the event
     * @param speakerID ID of entities.Speaker to be removed
     * @param eventID in which event the entities.Speaker is being removed
     * @return true if and only if the entities.Speaker is successfully removed
     */
    public boolean removeSpeakerID(int speakerID, int eventID) {
        Event event = getEventByID(eventID);
        boolean exists = false;
        for (int i = 0; i < event.getSpeakerIDs().size(); i++) {
            if (speakerID == event.getSpeakerIDs().get(i)) {
                exists = true;
            }
        }
        if (!exists){
            return false;
        }
        else{
            event.removeSpeakerID(speakerID);
            return true;
        }
    }

    /**
     * change the room ID to a new ID of a given event
     * @param roomID new entities.Room
     * @param eventID in which event the roomID is being changed
     */
    public void changeRoomID(int roomID, int eventID){
        Event event = getEventByID(eventID);
        event.changeRoomID(roomID);
    }

    /**
     * Read the useCases.EventManager object that was stored in a .ser file
     * @param path String representing the file path
     * @return useCases.EventManager object read from .ser file
     * @throws ClassNotFoundException is thrown if useCases.EventManager object is not found
     */
    public EventManager readFromFile (String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path); // String path should be "fileName.ser"
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the StudentManager
            EventManager em = (EventManager) input.readObject();
            input.close();
            return em;
        } catch (IOException ex) {
            return new EventManager();
        }
    }

    /**
     * return the event name given the event object
     * @param eventID the given entities.Event object
     * @return the event name corresponding to the entities.Event object
     */
    public String getName (int eventID){
        Event event = getEventByID(eventID);
        return event.getName();
    }

    /**
     * Write the useCases.EventManager object to a .ser file to store once program exists
     * @param filePath file to write to
     * @throws IOException is thrown if file we want to write to does not exist
     */
    public void saveToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the useCases.EventManager
        output.writeObject(this);
        output.close();
    }

    /**
     * Return a list of all attendees of the given event
     * @param eventID that attendees will attend
     * @return a list of all attendees of the given entities.Event
     */
    public List<Integer> getUserIDs(int eventID){
        Event event = getEventByID(eventID);
        return  event.getUserIDs();
    }

    /**
     * Return a list of all speakers of the given event
     * @param eventID that speakers will speak
     * @return a list of all speakers of the given entities.Event
     */
    public List<Integer> getSpeakerIDs(int eventID){
        Event event = getEventByID(eventID);
        return  event.getSpeakerIDs();
    }

    /**
     * Return the capacity of the room that holds the given event
     * @param eventID that will be held
     * @return the capacity of the room that holds the given entities.Event
     */
    public int getCapacity(int eventID){
        Event event = getEventByID(eventID);
        return event.getCapacity();
    }

    /**
     * Change the capacity of the room that holds the given event
     * @param eventID that will be held
     * @param capacity the new capacity of the room that hold the given Event
     */
    public void setCapacity(int eventID, int capacity){
        Event event = getEventByID(eventID);
        event.setCapacity(capacity);
    }

    /**
     * Return the ID of the room that holds the given event
     * @param eventID that will be held
     * @return Return the ID of the room that holds the given entities.Event
     */
    public int getRoomID(int eventID){
        Event event = getEventByID(eventID);
        return event.getRoomID();
    }

    /**
     * Return the the number of attendees in the given event
     * @param eventID the we want to know the number of attendees
     * @return Return the number of attendees in the given event
     */
    public int getNumOfAttendee(int eventID){
        Event event = getEventByID(eventID);
        return event.getNumOfAttendee();
    }

    /**
     * Return a new event with given features
     * @param startTime of new event
     * @param endTime of new event
     * @param roomID of new event
     * @param name of new event
     * @param capacity of new event
     * @param ID is the ID of new event
     * @return a new event will given features
     */
    public int createEvent(LocalDateTime startTime, LocalDateTime endTime, int roomID, String name, int capacity, int ID){
        Event event = new Event(startTime, endTime, roomID, name, capacity, ID);
        int id = event.getEventID();
        addEvent(event);
        return id;
    }

    public void setNumOfCancelledEvents() {
        this.numOfCancelledEvents = this.numOfCancelledEvents + 1;
    }

    /**
     * check whether a event in the event list
     * @param eventID of the user
     * @return true if the event list has this event with given event ID
     */
    public boolean idInList(int eventID){
        return events.contains(getEventByID(eventID));
    }

    public int getNumOfCancelledEvents()
    {
        return numOfCancelledEvents;
    }

}
