package com.example.model.interfaceAdapters;

import com.example.model.useCases.*;

import java.io.IOException;
import java.io.Serializable;

/**
 * Gateway class to read and write from files
 */
public class ReadWrite implements Serializable {
    private AttendeeManager attendees;
    private OrganizerManager organizers;
    private SpeakerManager speakers;
    private RoomManager rooms;
    private EventManager events;
    private MessageManager messages;

    /**
     * Construct an instance of ReadWrite with newly created Managers
     */
    public ReadWrite()
    {

        attendees = new AttendeeManager();
        organizers = new OrganizerManager();
        speakers = new SpeakerManager();
        rooms = new RoomManager();
        events = new EventManager();
        messages = new MessageManager();
    }

    /**
     * Read stored information and load the stored AttendeeManager
     * @param path filepath of the ser. file that stores nothing if the main method is never ran and store
     *             <code>AttendeeManager</code> by the end of the last running if the main method is ran
     * @return return the instance of <code>AttendeeManager</code> updated based on the given file
     */
    public AttendeeManager readAttendee(String path)
    {
        try
        {
            return attendees.readFromFile(path);
        }
        catch (ClassNotFoundException c) {
            System.out.println("Couldn't find file " + path);
            return attendees;
         }
    }

    /**
     * Read stored information and load the stored OrganizerManager
     * @param path filepath of the ser. file that stores nothing if the main method is never ran and store
     *             <code>OrganizerManager</code> by the end of the last running if the main method is ran
     * @return return the instance of <code>OrganizerManager</code> updated based on the given file
     */
    public OrganizerManager readOrganizer(String path)
    {
        try
        {
            return organizers.readFromFile(path);
        }
        catch (ClassNotFoundException c) {
            System.out.println("Couldn't find file " + path);
            return organizers;
        }
    }

    /**
     * Read stored information and load the stored SpeakerManager
     * @param path filepath of the ser. file that stores nothing if the main method is never ran and store
     *             <code>SpeakerManager</code> by the end of the last running if the main method is ran
     * @return return the instance of <code>SpeakerManager</code> updated based on the given file
     */
    public SpeakerManager readSpeaker(String path)
    {
        try
        {
            return speakers.readFromFile(path);
        }
        catch (ClassNotFoundException c) {
            System.out.println("Couldn't find file " + path);
            return speakers;
        }
    }
    /**
     * Read stored information and load the stored RoomManager
     * @param path filepath of the ser. file that stores nothing if the main method is never ran and store
     *             <code>RoomManager</code> by the end of the last running if the main method is ran
     * @return return the instance of <code>RoomManager</code> updated based on the given file
     */
    public RoomManager readRoom(String path)
    {
        try
        {
            return rooms.readFromFile(path);
        }
        catch (ClassNotFoundException c) {
            System.out.println(c.getMessage());
            return rooms;
        }
    }
    /**
     * Read stored information and load the stored EventManager
     * @param path filepath of the ser. file that stores nothing if the main method is never ran and store
     *             <code>EventManager</code> by the end of the last running if the main method is ran
     * @return return the instance of <code>EventManager</code> updated based on the given file
     */
    public EventManager readEvent(String path)
    {
        try
        {
            return events.readFromFile(path);
        }
        catch (ClassNotFoundException c) {
            System.out.println(c.getMessage());
            return events;
        }
    }
    /**
     * Read stored information and load the stored MessageManager
     * @param path filepath of the ser. file that stores nothing if the main method is never ran and store
     *             <code>MessageManager</code> by the end of the last running if the main method is ran
     * @return return the instance of <code>MessageManager</code> updated based on the given file
     */
    public MessageManager readMessage(String path)
    {
        try
        {
            return messages.readFromFile(path);
        }
        catch (ClassNotFoundException c) {
            System.out.println(c.getMessage());
            return messages;
        }
    }

    /**
     * set Managers of this ReadWrite to the given Managers
     * @param am the given instance of <code>AttendeeManager</code>
     * @param om the given instance of <code>OrganizerManager</code>
     * @param sm the given instance of <code>SpeakerManager</code>
     * @param em the given instance of <code>EventManager</code>
     * @param rm the given instance of <code>RoomManager</code>
     * @param mm the given instance of <code>MessageManager</code>
     */
    public void setManagers(AttendeeManager am, OrganizerManager om, SpeakerManager sm, EventManager em, RoomManager rm, MessageManager mm)
    {
        attendees = am;
        organizers = om;
        speakers = sm;
        events = em;
        rooms = rm;
        messages = mm;
    }
    public void setManagers(AttendeeManager am, OrganizerManager om, SpeakerManager sm)
    {
        attendees = am;
        organizers = om;
        speakers = sm;
    }

    /**
     * store the <code>AttendeeManager</code> in the conference to the given file
     * @param path filepath of the ser. file to store the <code>AttendeeManager</code>
     */
    public void saveAttendees(String path)
    {
        try{
            attendees.saveToFile(path);
        }
        catch (IOException e)
        {
            System.out.println("Couldn't save useCases.AttendeeManager");
        }
    }
    /**
     * store the <code>OrganizerManager</code> in the conference to the given file
     * @param path filepath of the ser. file to store the <code>OrganizerManager</code>
     */
    public void saveOrganizers(String path)
    {
        try{
            organizers.saveToFile(path);
        }
        catch (IOException e)
        {
            System.out.println("Couldn't save useCases.OrganizerManager");
        }
    }
    /**
     * store the <code>SpeakerManager</code> in the conference to the given file
     * @param path filepath of the ser. file to store the <code>SpeakerManager</code>
     */
    public void saveSpeakers(String path)
    {
        try{
            speakers.saveToFile(path);
        }
        catch (IOException e)
        {
            System.out.println("Couldn't save useCases.SpeakerManager");
        }
    }
    /**
     * store the <code>EventManager</code> in the conference to the given file
     * @param path filepath of the ser. file to store the <code>EventManager</code>
     */
    public void saveEvent (String path)
    {
        try{
            events.saveToFile(path);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    /**
     * store the <code>RoomManager</code> in the conference to the given file
     * @param path filepath of the ser. file to store the <code>RoomManager</code>
     */
    public void saveRoom (String path) {
        try{
            rooms.saveToFile(path);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    /**
     * store the <code>MessageManager</code> in the conference to the given file
     * @param path filepath of the ser. file to store the <code>MessageManager</code>
     */
    public void saveMessage (String path)
    {
        try{
            messages.saveToFile(path);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    /**
     * store all Managers in the conference to their corresponding ser file
     */
    public void saveAll(){
        saveAttendees("phase1/src/attendeemanager.ser");
        saveOrganizers("phase1/src/organizermanager.ser");
        saveSpeakers("phase1/src/speakermanager.ser");
        saveMessage("phase1/src/messagemanager.ser");
        saveRoom("phase1/src/roommanager.ser");
        saveEvent("phase1/src/eventmanager.ser");
    }
}
