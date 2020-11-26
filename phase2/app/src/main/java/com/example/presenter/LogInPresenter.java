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

        om.addUser(om.createOrganizer("Jonathan", "chenjo14", "12345678", 1));
        gateway.setManagers(am, om, sm);

    }

    public void setView(View view) {
        this.view = view;
    }

    public boolean validate(UserManager um, String username, String password)
    {
        User user =  um.validate(username, password);
        return !(user == null);
    }
    public Object validate1(String username, String password)
    {
        User user =  am.validate(username, password);
        if (!(user == null))
        {
            return AttendeeMenu.class;
        }
        user = om.validate(username, password);
        if (!(user == null))
        {
            return OrganizerMenu.class;
        }
        user = sm.validate(username, password);
        if (!(user == null))
        {
            return SpeakerMenu.class;
        }
        return null;
    }

    /**
     * Initializes the contacts list of the given new Attendee and add the given new Attendee to the contacts list of
     * other users if they are allowed to contact him.
     * @param newAttendee the Attendee whose contacts list we want to initialize
     */
    public void initializeAttendeeContactsList(Attendee newAttendee){
        for (User attendee: am.getUsers()){
            // Add every attendee to this new attendee's contact list
            am.addToContactsList(newAttendee, am.getIDByUser(attendee));
            // Add this new attendee's to every attendee's contact list
            am.addToContactsList(attendee, am.getIDByUser(newAttendee));

        }
        for (User speaker: sm.getUsers()){

            // Add every speaker to this new attendee's contact list
            am.addToContactsList(newAttendee, sm.getIDByUser(speaker));
        }
        for (User organizer: om.getUsers()){
            // Add this new attendee to each organizer's contact list
            om.addToContactsList(organizer, am.getIDByUser(newAttendee));
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
        Attendee newAccount = am.createAttendee(name, userName, password, getNewID());
        initializeAttendeeContactsList(newAccount);
        am.addUser(newAccount);
        return true;
    }

    public int getNewID(){
        int size = am.getUsers().size() + om.getUsers().size() + sm.getUsers().size();
        //TODO add VIP MANAGER
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
}
