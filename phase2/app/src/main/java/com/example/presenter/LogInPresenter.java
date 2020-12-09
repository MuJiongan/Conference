package com.example.presenter;
import android.content.Context;
import com.example.conference.AttendeeMenu;
import com.example.conference.OrganizerMenu;
import com.example.conference.SpeakerMenu;
import com.example.model.interfaceAdapters.ReadWrite;
import com.example.model.useCases.*;
import java.io.Serializable;

public class LogInPresenter implements Serializable {
    private AttendeeManager am;
    private OrganizerManager om;
    private SpeakerManager sm;
    private RoomManager rm;
    private EventManager em;
    private MessageManager mm;
    private int userID;
    private VipManager vipM;
    private VipEventManager vipEvent;

    private View view;

    /**
     * Create the logIn interface on Android device given the View and Context
     * @param view the View that used for drawing content onto the screen of the Android device
     * @param context the context of the application
     */
    public LogInPresenter(View view, Context context) {
        am = ReadWrite.readAttendee(context);
        om = ReadWrite.readOrganizer(context);
        sm = ReadWrite.readSpeaker(context);
        rm = ReadWrite.readRoom(context);
        em = ReadWrite.readEvent(context);
        mm = ReadWrite.readMessage(context);
        vipM = ReadWrite.readVipManager(context);
        vipEvent = ReadWrite.readVipEventManager(context);
        //om.createOrganizer("Jonathan", "chenjo14", "12345678", getNewID());
        this.userID = 0;
        this.view = view;
    }

    /**
     * Change and set the View to new given View
     * @param view the new View
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Return the UserMenu (Controller) for a User account
     * @param username the username of User's account
     * @param password the password of User's account
     * @return the valid Menu for different types of User's account
     */
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
        user = vipM.validate(username, password);
        if (!(user == -100))
        {
            this.userID = user;
            return AttendeeMenu.class;
        }
        return null;
    }

    /**
     * Create an Attendee User Account with name, userName and password
     * @param name name of the Attendee
     * @param userName username for the Attendee Account
     * @param password password for the Attendee Account
     * @return true if and only if the Attendee Account is successfully created
     */
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
        return created;
    }

    /**
     * Create a new User ID
     * @return the new User ID
     */
    public int getNewID(){
        int size = am.getUsers().size() + om.getUsers().size() + sm.getUsers().size() + vipM.getUsers().size();
        return size + 1;
    }

    /**
     * View that push Messages
     */
    public interface View {
        void pushMessage(String info);
    }

    /**
     * Getter of AttendeeManager's instance
     * @return the instance of <code>AttendeeManager</code> in the conference
     */
    public AttendeeManager getAm() {
        return am;
    }

    /**
     * Getter of SpeakerManager's instance
     * @return the instance of <code>SpeakerManager</code> in the conference
     */
    public SpeakerManager getSm() {
        return sm;
    }

    /**
     * Getter of OrganizerManager's instance
     * @return the instance of <code>OrganizerManager</code> in the conference
     */
    public OrganizerManager getOm() {
        return om;
    }

    /**
     * Getter of RoomManager's instance
     * @return the instance of <code>RoomManager</code> in the conference
     */
    public RoomManager getRm() {
        return rm;
    }

    /**
     * Getter of EventManager's instance
     * @return the instance of <code>EventManager</code> in the conference
     */
    public EventManager getEm() {
        return em;
    }

    /**
     * Getter of MessageManager's instance
     * @return the instance of <code>MessageManager</code> in the conference
     */
    public MessageManager getMm()
    {
        return mm;
    }

    /**
     * Getter of User ID
     * @return the User ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Getter of VipManager's instance
     * @return the instance of <code>VipManager</code> in the conference
     */
    public VipManager getVipManager(){
        return vipM;
    }

    /**
     * Getter of VipEventManager's instance
     * @return the instance of <code>VipEventManager</code> in the conference
     */
    public VipEventManager getVipEvent() {
        return vipEvent;
    }

    /**
     * Setter of Managers
     * @param am the instance of <code>AttendeeManager</code> in the conference
     * @param om the instance of <code>OrganizerManager</code> in the conference
     * @param sm the instance of <code>SpeakerManager</code> in the conference
     * @param rm the instance of <code>RoomManager</code> in the conference
     * @param em the instance of <code>EventManager</code> in the conference
     * @param mm the instance of <code>MessageManager</code> in the conference
     * @param vipm the instance of <code>VipManager</code> in the conference
     * @param vipe the instance of <code>VipEventManager</code> in the conference
     */
    public void setManagers(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm,
                            EventManager em, MessageManager mm, VipManager vipm, VipEventManager vipe)
    {
        this.am = am;
        this.om = om;
        this.sm = sm;
        this.rm = rm;
        this.em = em;
        this.mm = mm;
        this.vipM = vipm;
        this.vipEvent = vipe;
    }
}
