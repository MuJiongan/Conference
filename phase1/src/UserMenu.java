import java.util.ArrayList;
import java.util.HashMap;

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
    public boolean sendMessage(int receiverID, String messageContent){
        Message message = mm.createMessage(messageContent, user.getUserId(), receiverID);
        mm.addMessage(message);
        currentManager.addSentMessageID(mm.getIdByMessage(message), user, receiverID);
        //um.addReceivedMessageID(mm.getIdByMessage(message), um.getUserByID(receiverID), um.getIDByUser(user));
        // Remember to add recevied message in sendMessage extension
        return true;
    }

    public ArrayList<Event> viewAllEvents()
    {
        ArrayList<Integer> events = currentManager.getEventList(user);
        ArrayList<Event> actualEvents = new ArrayList<>();
        for (Integer eventID: events){
            Event event = em.getEventByID(eventID);
            actualEvents.add(event);
        }
//        String message = "Here is your schedule:\n";
//        for (int x : events)
//        {
//            Event event = em.getEventByID(x);
//            message = message + " " +em.getStartTime(event) + " "+ em.getEndTime(event) + " " + em.getName(event) +"\n";
//        }
//        System.out.println(message);

        return actualEvents;
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

    public HashMap<Integer, ArrayList<Integer>> viewMessage(){
        System.out.println("Here we view all the received messages");
        return currentManager.getMessages(user);
    }


    public ArrayList<Event> viewMyEvents() {
        ArrayList<Event> eventsTheyAttended = new ArrayList<>();
        for (Integer eventID : getUser().getEventsAttend()) {
            eventsTheyAttended.add(getEventManager().getEventByID(eventID));
        }
        return eventsTheyAttended;
    }

    public ArrayList<Event> viewMyContacts() {
        ArrayList<Event> contacts = new ArrayList<>();
        for (Integer userID : getUser().getContactList()) {
            contacts.add(getEventManager().getEventByID(userID));
        }
        return contacts;
        // presenter print number before each name
    }


    public void readAllMessage(int friendID) {
        for(Integer messageID:getUser().getMessages().get(friendID)){
            Message message = getMessageManager().getMessageById(messageID);
            if(message.getSenderID() == friendID){
                getMessageManager().changeMessageCondition(messageID);
            }
        }

    }

    public ArrayList<Message> viewChat(int receiverID){
        ArrayList<Message> chatHistory = new ArrayList<>();
        for(Integer messageID:getUser().getMessages().get(receiverID)){
            chatHistory.add(getMessageManager().getMessageById(messageID));
        }
        return chatHistory;
    }

    public void messageAll(Integer eventID, String message){
        Event event = em.getEventByID(eventID);
        for(Integer userid: event.getUserIDs()){
            sendMessage(userid, message);
        }
    }


}
