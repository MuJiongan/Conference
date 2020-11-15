package interfaceAdapters;

import entities.Event;
import entities.Message;
import entities.User;
import useCases.*;

import java.util.ArrayList;

public abstract class UserMenu {
    private AttendeeManager am;
    private OrganizerManager om;
    private SpeakerManager sm;
    private RoomManager rm;
    private EventManager em;
    private MessageManager mm;
    private User user;
    private UserManager currentManager;

    public UserMenu(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm, EventManager em, MessageManager mm, User user){
        this.am = am;
        this.om = om;
        this.sm = sm;
        this.rm = rm;
        this.em = em;
        this.mm = mm;
        this.user = user;

        if (this.am.getUsers().contains(this.user))
        {
           currentManager = this.am;
        }
        else if (this.om.getUsers().contains((this.user)))
        {
           currentManager = this.om;
        }
        else
        {
           currentManager = this.sm;
        }
    }

    // precondition: receiverID has to be valid. Check before you send the message
    public boolean sendMessage(int receiverID, Message message){
        currentManager.addMessageID(mm.getIdByMessage(message), user, receiverID);
        //um.addReceivedMessageID(mm.getIdByMessage(message), um.getUserByID(receiverID), um.getIDByUser(user));
        // Remember to add recevied message in sendMessage extension
        return true;
    }



    public User getUser() {
        return user;
    }

    public EventManager getEventManager() {
        return em;
    }

    public MessageManager getMessageManager() {
        return mm;
    }

    public RoomManager getRoomManager() {
        return rm;
    }

    public AttendeeManager getAttendeeManager()
    {
        return am;
    }

    public OrganizerManager getOrganizerManager()
    {
        return om;
    }
    public SpeakerManager getSpeakerManager()
    {
        return sm;
    }
    public UserManager getCurrentManager() {
        return currentManager;
    }


    public ArrayList<Event> viewMyEvents() {
        ArrayList<Event> eventsTheyAttended = new ArrayList<>();
        for (Integer eventID : getUser().getEventsAttend()) {
            eventsTheyAttended.add(getEventManager().getEventByID(eventID));
        }
        return eventsTheyAttended;
    }

    public ArrayList<Integer> viewMyContactList(){
        return currentManager.getContactList(user);
    }

    public boolean hasContact(int friendID){
        return currentManager.getContactList(user).contains(friendID);
    }

    public boolean hasEvent(int eventID){
        return this.getEventManager().getEvents().contains(getEventManager().getEventByID(eventID));
    }

    public boolean hasMyEvent(int eventID){
        return currentManager.getEventList(user).contains(eventID);
    }

    public void readAllMessage(int friendID) {
        for(Integer messageID:getUser().getMessages().get(friendID)){
            Message message = getMessageManager().getMessageById(messageID);
            if(message.getSenderID() == friendID){
                getMessageManager().changeMessageCondition(messageID);
            }
        }

    }

    public ArrayList<Integer> viewChat(int receiverID){
        return user.getMessages().get(receiverID);
    }

    public int getNewID(){
        int size = getAttendeeManager().getUsers().size() + getOrganizerManager().getUsers().size() + getSpeakerManager().getUsers().size();
        return size + 1;
    }



}
