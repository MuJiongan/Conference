import java.util.ArrayList;

public abstract class UserMenu {
    private UserManager um;
    private RoomManager rm;
    private EventManager em;
    private MessageManager mm;
    private User user;

    public UserMenu(UserManager um, RoomManager rm, EventManager em, MessageManager mm, User user){
        this.um = um;
        this.rm = rm;
        this.em = em;
        this.mm = mm;
        this.user = user;
    }
//    public boolean sendMessage(int receiverID, String messageContent){
//        Message message = mm.createMessage(messageContent, user.getUserId(), receiverID);
//        mm.addMessage(message);
//
//
//    }
    public ArrayList<Event> viewAllEvents()
    {
        ArrayList<Integer> events = um.getEventList(user);
        String message = "Here is your schedule:\n";
        for (int x : events)
        {
            Event event = em.getEventByID(x);
            message = message + " " +em.getStartTime(event) + " "+ em.getEndTime(event) + " " + em.getName(event) +"\n";
        }
        System.out.println(message);
        return em.getEvents();
    }

//    public boolean viewMessage(){
//    // viewMessage
//    }
}
