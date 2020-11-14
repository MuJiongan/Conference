import java.util.ArrayList;
import java.util.HashMap;

public abstract class UserMenu {

    /**
     * Stores the AttendeeManager
     */
    private AttendeeManager am;
    /**
     * Stores the OrganizerManager
     */
    private OrganizerManager om;
    /**
     * Stores the SpeakerManager
     */
    private SpeakerManager sm;
    /**
     * Stores the RoomManager
     */
    private RoomManager rm;
    /**
     * Stores the EventManager
     */
    private EventManager em;
    /**
     * Stores the MessageManager
     */
    private MessageManager mm;
    /**
     * Stores the current user
     */
    private User user;
    /**
     * Stores the current Manager of the current user
     */
    private UserManager currentManager;

    /**
     * Constructs an instance of UserMenu based on given information
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
     * Adds the given message to the receiver given the receiverID
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
     * Returns the current user
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns EventManager
     */
    public EventManager getEventManager() {
        return em;
    }

    /**
     * Returns MessageManager
     */
    public MessageManager getMessageManager() {
        return mm;
    }

    /**
     * Returns RoomManager
     */
    public RoomManager getRoomManager() {
        return rm;
    }

    /**
     * Returns AttendeeManager
     */
    public AttendeeManager getAttendeeManager()
    {
        return am;
    }

    /**
     * Returns OrganizerManager
     */
    public OrganizerManager getOrganizerManager()
    {
        return om;
    }

    /**
     * Returns SpeakerManager
     */
    public SpeakerManager getSpeakerManager()
    {
        return sm;
    }

    /**
     * Returns currentManager
     */
    public UserManager getCurrentManager() {
        return currentManager;
    }

    /**
     * Returns list of all Events the user is going to attend
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
     * Returns whether the given friend's ID is in the contact list of the current user
     * @param friendID ID of the user that is going to be checked
     * @return true iff the given friend's ID is in the contact list of the current user
     */
    public boolean hasContact(int friendID){
        return currentManager.getContactList(user).contains(friendID);
    }

    /**
     * Returns whether the given event's ID is in the list of all events
     * @param eventID ID of the event that is going to be checked
     * @return true iff the given event's ID is in the list of all events
     */
    public boolean hasEvent(int eventID){
        return this.getEventManager().getEvents().contains(getEventManager().getEventByID(eventID));
    }

    /**
     * Returns whether the given event's ID is in the event list of the current user
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
     * Returns the next ID that is going to be assigned to the new User created
     * @return the next ID that is going to be assigned to the new User created
     */
    public int getNewID(){
        int size = getAttendeeManager().getUsers().size() + getOrganizerManager().getUsers().size() + getSpeakerManager().getUsers().size();
        return size + 1;
    }



}
