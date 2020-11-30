package com.example.presenter;

import android.content.Context;
import com.example.conference.AttendeeMenu;
import com.example.conference.OrganizerMenu;
import com.example.conference.SpeakerMenu;
import com.example.model.entities.*;
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
    private int userID;
    private VipManager vipM;
    private VipEventManager vipEvent;
    //VIP Manager private variable


    private View view;

    public LogInPresenter(View view, Context context) {
        //https://stackoverflow.com/questions/14768191/how-do-i-read-the-file-content-from-the-internal-storage-android-app
        am = ReadWrite.readAttendee(context);
        om = ReadWrite.readOrganizer(context);
        sm = ReadWrite.readSpeaker(context);
        rm = ReadWrite.readRoom(context);
        em = ReadWrite.readEvent(context);
        mm = ReadWrite.readMessage(context);
        vipM = ReadWrite.readVipManager(context);
        vipEvent = ReadWrite.readVipEventManager(context);
        om.createOrganizer("Jonathan", "chenjo14", "12345678", getNewID());
        this.userID = 0;

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

    public EventManager getEm() {
        return em;
    }
    public MessageManager getMm()
    {
        return mm;
    }
    public int getUserID() {
        return userID;
    }
    public VipManager getVipManager(){
        return vipM;
    }

    public void setManagers(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm,
                            EventManager em, MessageManager mm)
    {
        this.am = am;
        this.om = om;
        this.sm = sm;
        this.rm = rm;
        this.em = em;
        this.mm = mm;
    }
}
