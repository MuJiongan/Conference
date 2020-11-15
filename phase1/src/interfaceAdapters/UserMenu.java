package interfaceAdapters;

import entities.Event;
import entities.Message;
import entities.User;
import useCases.*;

import java.util.ArrayList;

public abstract class UserMenu {

    /**
     * Store the AttendeeManager
     */
    private AttendeeManager am;
    /**
     * Store the OrganizerManager
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
     * Store the current user
     */
    private User user;
    /**
     * Store the current Manager of the current user
     */
    private UserManager currentManager;

    /**
     * Construct an instance of UserMenu based on given information
     * @param am the AttendeeManager
     * @param om the OrganizerManager
     * @param sm the SpeakerManager
     * @param rm the RoomManager
     * @param em the EventManager
     * @param mm the MessageManager
     * @param user the current user
     */
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

    /**
     * Add the given message to the receiver given the receiverID
     * @param receiverID ID of the user who receives the message
     * @param message the message content
     * @return true iff the message is successfully sent
     * @custom.precondition receiverID has to be valid. Check before sending the message.
     */
    public boolean sendMessage(int receiverID, Message message){
        currentManager.addMessageID(mm.getIdByMessage(message), user, receiverID);
        //um.addReceivedMessageID(mm.getIdByMessage(message), um.getUserByID(receiverID), um.getIDByUser(user));
        // Remember to add recevied message in sendMessage extension
        return true;
    }

    /**
     * Return the current user
     */
    public User getUser() {
        return user;
    }

    /**
     * Return EventManager
     */
    public EventManager getEventManager() {
        return em;
    }

    /**
     * Return MessageManager
     */
    public MessageManager getMessageManager() {
        return mm;
    }

    /**
     * Return RoomManager
     */
    public RoomManager getRoomManager() {
        return rm;
    }

    /**
     * Return AttendeeManager
     */
    public AttendeeManager getAttendeeManager()
    {
        return am;
    }

    /**
     * Return OrganizerManager
     */
    public OrganizerManager getOrganizerManager()
    {
        return om;
    }

    /**
     * Return SpeakerManager
     */
    public SpeakerManager getSpeakerManager()
    {
        return sm;
    }

    /**
     * Return currentManager
     */
    public UserManager getCurrentManager() {
        return currentManager;
    }

    /**
     * Return list of all Events the user is going to attend
     * @return list of all Events the user is going to attend
     */
    public ArrayList<Event> viewMyEvents() {
        ArrayList<Event> eventsTheyAttended = new ArrayList<>();
        for (Integer eventID : getUser().getEventsAttend()) {
            eventsTheyAttended.add(getEventManager().getEventByID(eventID));
        }
        return eventsTheyAttended;
    }

    /**
     * Return whether the given friend's ID is in the contact list of the current user
     * @param friendID ID of the user that is going to be checked
     * @return true iff the given friend's ID is in the contact list of the current user
     */
    public boolean hasContact(int friendID){
        return currentManager.getContactList(user).contains(friendID);
    }

    /**
     * Return whether the given event's ID is in the list of all events
     * @param eventID ID of the event that is going to be checked
     * @return true iff the given event's ID is in the list of all events
     */
    public boolean hasEvent(int eventID){
        return this.getEventManager().getEvents().contains(getEventManager().getEventByID(eventID));
    }

    /**
     * Return whether the given event's ID is in the event list of the current user
     * @param eventID ID of the event that is going to be checked
     * @return true iff the given event's ID is in the event list of the current user
     */
    public boolean hasMyEvent(int eventID){
        return currentManager.getEventList(user).contains(eventID);
    }

    /**
     * Set all messages receiving from the friend given by ID as already read
     * @param friendID ID of the friend that the current user is chatting to
     */
    public void readAllMessage(int friendID) {
        for(Integer messageID:getUser().getMessages().get(friendID)){
            Message message = getMessageManager().getMessageById(messageID);
            if(message.getSenderID() == friendID){
                getMessageManager().changeMessageCondition(messageID);
            }
        }

    }

    /**
     * Return the next ID that is going to be assigned to the new User created
     * @return the next ID that is going to be assigned to the new User created
     */
    public int getNewID(){
        int size = getAttendeeManager().getUsers().size() + getOrganizerManager().getUsers().size() + getSpeakerManager().getUsers().size();
        return size + 1;
    }



}
