

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AttendeeMenu extends UserMenu implements UserController{


    public AttendeeMenu(UserManager um, RoomManager rm, EventManager em, MessageManager mm, User user, ReadWrite gateway){
        super(um, rm, em, mm, user, gateway);
    }

    public ArrayList<Event> eventsTheyCanSignUpFor(){
        ArrayList<Event> eventsTheyCanSignUpFor = new ArrayList<>();
        for (Event event: getEventManager().getEvents()){
            if (canSignUp(event)){
                eventsTheyCanSignUpFor.add(event);
            }
        }
        return eventsTheyCanSignUpFor;
    }

    public boolean cancelEnrollment(int eventID){
        if (getUser().getEventsAttend().contains(eventID)||super.getEventManager().getEventByID(eventID) != null) {
            getUser().removeEvent(eventID);
            getEventManager().removeUserID(getUser().getUserId(), getEventManager().getEventByID(eventID));
            return true;
        }else{
            return false;
        }
    }

    public boolean signUp(int eventID){
        if (getEventManager().getEventByID(eventID) == null ||getUser().getEventsAttend().contains(eventID)){
            return false;
        }else{
            getUser().addEvent(eventID);
            getEventManager().addUserID(getUser().getUserId(), getEventManager().getEventByID(eventID));
            return true;
        }
    }

    public User run(){
        System.out.println("something");
        return null;
    }


    private boolean canSignUp(Event event){
        LocalDateTime startTime = getEventManager().getStartTime(event);
        LocalDateTime endTime = getEventManager().getEndTime(event);
        for (Integer eventToSignUpFor: getUserManager().getEventList(getUser())){
            Event actualEventToSignUpFor = getEventManager().getEventByID(eventToSignUpFor);
            LocalDateTime newStartTime = getEventManager().getStartTime(actualEventToSignUpFor);
            if (newStartTime.isAfter(startTime) && newStartTime.isBefore(endTime)){
                return false;
            }
        }
        return true;
    }



}