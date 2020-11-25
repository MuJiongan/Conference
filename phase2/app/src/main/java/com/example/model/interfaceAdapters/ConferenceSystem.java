package com.example.model.interfaceAdapters;

import com.example.model.entities.User;
import com.example.model.useCases.*;

import java.io.Serializable;

public class ConferenceSystem implements Serializable {

    /**
     * Store the AttendeeManager
     */
    private AttendeeManager am;
    /**
     * Store the Organizer Manager
     */
    private OrganizerManager om;
    /**
     * Store the SpeakerManager
     */
    private SpeakerManager sm;
    /**
     * Store the RoomManager
     */
    private RoomManager rm;
    /**
     * Store the EventManager
     */
    private EventManager em;
    /**
     * Store the MessageManager
     */
    private MessageManager mm;
    /**
     * Store the gateway
     */
    private ReadWrite gateway;


    /**
     * Construct an instance of ConferenceSystem
     */
    public ConferenceSystem(){
        gateway = new ReadWrite();
        am = gateway.readAttendee("phase1/src/attendeemanager.ser");
        om = gateway.readOrganizer("phase1/src/organizermanager.ser");
        sm = gateway.readSpeaker("phase1/src/speakermanager.ser");
        rm = gateway.readRoom("phase1/src/roommanager.ser");
        em = gateway.readEvent("phase1/src/eventmanager.ser");
        mm = gateway.readMessage("phase1/src/messagemanager.ser");
        gateway.setManagers(am, om, sm, em, rm, mm);
    }

    /**
     * Returns a new created instance of Organizer with updated contact list containing all existing attendees and
     * speakers
     * @param name name of the organizer
     * @param username username of the organizer
     * @param password password of the organizer
     * @return a new created instance of Organizer with updated contact list containing all existing attendees and
     * speakers
     */
    // This method also handles the contact list
    public User createOrganizerAccount(String name, String username, String password){
        User organizer = om.createOrganizer(name, username, password, getNewID());
        om.addUser(organizer);
        // Initiate the contact list
        for (User attendee: am.getUsers()){
            // add every attendee to this organizer's contact list
            om.addToContactsList(organizer, am.getIDByUser(attendee));
        }
        for (User speaker: sm.getUsers()){
            // add every speaker to this organizer's contact list
            om.addToContactsList(organizer, sm.getIDByUser(speaker));
        }
        // However, there is still more things we need to check
        // When organizer sends a message to a speaker or an attendee, add this organizer to their contact list (done)
        return organizer;

    }

    /**
     * Return the next ID that is going to be assigned to the new User created
     * @return the next ID that is going to be assigned to the new User created
     */
    public int getNewID(){
        int size = am.getUsers().size() + om.getUsers().size() + sm.getUsers().size();
        return size + 1;
    }

    /**
     * Runs the main speaker menu. When the user is logging in the system, check the specific type of user, and
     * direct to corresponding menu. Print error message if the account cannot be found.
     */
    public void run()
    {
        UserController current = new LogInSystem(am, om, sm);
        boolean iterate = true;
        while (iterate) {
            User new_user = current.run();
            if (new_user != null) {
                iterate = false;
                if (am.getUsers().contains(new_user)) {
                   current = new AttendeeMenu(am, om, sm, rm, em, mm, new_user);
                   current.run();
                   gateway.saveAll();

                }
                else if (sm.getUsers().contains(new_user)) {
                    current = new SpeakerMenu(am, om, sm, rm, em, mm, new_user);
                    current.run();
                    gateway.saveAll();
                }
                else if (om.getUsers().contains(new_user))
                {
                    current = new OrganizerMenu(am, om, sm, rm, em, mm, new_user);
                    current.run();
                    gateway.saveAll();
                }
            } else {
                System.out.println("Error please try again");
            }
        }
    }

}
