package com.example.presenter;

import com.example.conference.AttendeeMenu;
import com.example.conference.OrganizerMenu;

import com.example.conference.SpeakerMenu;
import com.example.model.entities.Attendee;
import com.example.model.entities.User;
import com.example.model.interfaceAdapters.LogInSystem;
import com.example.model.interfaceAdapters.ReadWrite;

import com.example.model.useCases.*;

import java.io.Serializable;

public class LogInPresenter implements Serializable {
    /**
     * Store the AttendeeManager
     */
    private AttendeeManager am;
    private OrganizerManager om;
    private SpeakerManager sm;
    private RoomManager rm;
    private EventManager em;
    private MessageManager mm;
    private ReadWrite gateway;
    private int userID;
    //VIP Manager private variable


    private View view;

    public LogInPresenter(View view) {
        gateway = new ReadWrite();
        //https://stackoverflow.com/questions/14768191/how-do-i-read-the-file-content-from-the-internal-storage-android-app
        am = gateway.readAttendee("src/main/java/com/example/model/useCases/attendeemanager.ser");
        om = gateway.readOrganizer("src/main/java/com/example/model/useCases/organizermanager.ser");
        sm = gateway.readSpeaker("src/main/java/com/example/model/useCases/speakermanager.ser");
        rm = gateway.readRoom("src/main/java/com/example/model/useCases/roommanager.ser");
        em = gateway.readEvent("src/main/java/com/example/model/useCases/eventanager.ser");
        mm = gateway.readMessage("src/main/java/com/example/model/useCases/messagemanager.ser");
        // om.addUser(om.createOrganizer("Jonathan", "chenjo14", "12345678", 1).getUserId());
        gateway.setManagers(am, om, sm);

    }

    public void setView(View view) {
        this.view = view;
    }

    public Object validate(String username, String password)
    {
        int user =  am.validate(username, password);

        if (!(user == -100))
        {
            this.userID = user;
            return AttendeeMenu.class;
        }
        user = om.validate(username, password);
        if (!(user == -100))
        {
            this.userID = user;
            return OrganizerMenu.class;
        }
        user = sm.validate(username, password);
        if (!(user == -100))
        {
            this.userID = user;
            return SpeakerMenu.class;
        }
        return null;
    }

    /**
     * Initializes the contacts list of the given new Attendee and add the given new Attendee to the contacts list of
     * other users if they are allowed to contact him.
     * @param userID the Attendee whose contacts list we want to initialize
     */
    public void initializeAttendeeContactsList(int userID){

        for (int attendee: am.getUserIDs()){
            // Add every attendee to this new attendee's contact list
            am.addToContactsList(userID, attendee);
            // Add this new attendee's to every attendee's contact list
            am.addToContactsList(attendee, userID);

        }
        for (int speaker: sm.getUserIDs()){
            // Add every speaker to this new attendee's contact list
            am.addToContactsList(userID, speaker);
        }
        for (int organizer: om.getUserIDs()){
            // Add this new attendee to each organizer's contact list
            om.addToContactsList(organizer, userID);
        }

    }
    public boolean createAttendeeAccount(String name, String userName, String password){
        // can't be empty
        if (name.equals("") || userName.equals("") || password.equals("")){
            view.pushMessage("Some field is empty! Please fill out everything");
            return false;
        }
        // check if username exists
        if (am.hasUserName(userName) || sm.hasUserName(userName) || om.hasUserName(userName)){
            view.pushMessage("Username already exists. Please enter another one!");
            return false;
        }
        int newID = getNewID();
        boolean created = am.createUser(name, userName, password, newID);
        if (created)
        {
            initializeAttendeeContactsList(newID);
            return true;
        }
        return false;
    }

    public int getNewID(){
        int size = am.getUsers().size() + om.getUsers().size() + sm.getUsers().size();
        //TODO: add VIP MANAGER
        return size + 1;
    }

    public interface View {
        void pushMessage(String info);
    }

    public AttendeeManager getAm() {
        return am;
    }

    public SpeakerManager getSm() {
        return sm;
    }

    public OrganizerManager getOm() {
        return om;
    }

    public RoomManager getRm() {
        return rm;
    }

    public MessageManager getMm() {
        return mm;
    }

    public EventManager getEm() {
        return em;
    }

    public int getUserID() {
        return userID;
    }
}
