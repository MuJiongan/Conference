import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

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

    // precondition: receiverID has to be valid. Check before you send the message
    public boolean sendMessage(int receiverID, String messageContent){
       Message message = mm.createMessage(messageContent, user.getUserId(), receiverID);
       mm.addMessage(message);
       um.addSentMessageID(mm.getIdByMessage(message), user, receiverID);
       um.addReceivedMessageID(mm.getIdByMessage(message), um.getUserByID(receiverID), um.getIDByUser(user));
       return true;
    }

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
        return null;
    }

    public User getUser() {
        return user;
    }

    public EventManager getEm() {
        return em;
    }

    public MessageManager getMm() {
        return mm;
    }

    public RoomManager getRm() {
        return rm;
    }

    public UserManager getUm() {
        return um;
    }


    public HashMap<Integer, ArrayList<Integer>> viewMessage(){
    System.out.println("Here we view all the received messages");
    return um.getReceivedMessages(user);
  }
}