
import java.util.ArrayList;

public class AttendeeMenu extends UserMenu implements UserController{


    public AttendeeMenu(UserManager um, RoomManager rm, EventManager em, MessageManager mm, User user, ReadWrite gateway){
        super(um, rm, em, mm, user, gateway);
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
        if (getUser().getEventsAttend().contains(eventID)||super.getEvents().getEventByID(eventID) != null) {
            getUser().removeEvent(eventID);
            getEvents().removeUserID(getUser().getUserId(), getEvents().getEventByID(eventID));
            return true;
        }else{
            return false;
        }
    }

    public boolean signUp(int eventID){
        if (getEvents().getEventByID(eventID) == null ||getUser().getEventsAttend().contains(eventID)){
            return false;
        }else{
            getUser().addEvent(eventID);
            getEvents().addUserID(getUser().getUserId(), getEvents().getEventByID(eventID));
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
