import java.util.ArrayList;
public class AttendeeMenu implements UserController{
    private UserManager um;
    private RoomManager rm;
    private EventManager em;
    private MessageManager mm;
    private User user;

    public AttendeeMenu(UserManager um, RoomManager rm, EventManager em, MessageManager mm, User user){
        this.um = um;
        this.rm = rm;
        this.em = em;
        this.mm = mm;
        this.user = user;
    }

    public ArrayList<Event> allEvents(){
        return em.getEvents();
    }

//    public ArrayList<Event> eventsTheyCanSignUpFor(){
//        ArrayList<Event> eventsTheyCanSignUpFor = new ArrayList<>();
//        for (Event event: em.getEvents()){
//            if (canSignUp(event.getEventID())){
//                eventsTheyCanSignUpFor.add(event);
//            }
//        }
//        return eventsTheyCanSignUpFor;
//    }

    public boolean cancelEnrollment(int eventID){
        if (user.getEventsAttend().contains(eventID)||em.getEventByID(eventID) != null) {
            user.removeEvent(eventID);
            em.removeUserID(user.getUserId(), em.getEventByID(eventID));
            return true;
        }else{
            return false;
        }
    }

//    public boolean sendMessage(int receiverID){
//
//    }

//    public boolean viewMessage(){
//    // viewMessage
//    }

    public boolean signUp(int eventID){
        if (em.getEventByID(eventID) == null ||user.getEventsAttend().contains(eventID)){
            return false;
        }else{
            user.addEvent(eventID);
            em.addUserID(user.getUserId(), em.getEventByID(eventID));
            return true;
        }
    }

    public User run(){
        System.out.println("something");
        return null;
    }


//    private boolean canSignUp(int EventID){
//
//    }



}
