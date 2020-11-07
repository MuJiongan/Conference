import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AttendeeMenu extends UserMenu implements UserController{


    public AttendeeMenu(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm, EventManager em, MessageManager mm, User user, ReadWrite gateway){
        super(am, om, sm, rm, em, mm, user, gateway);
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
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserPropertiesIterator prompts = new UserPropertiesIterator();
        ArrayList<String> inputs = new ArrayList<>();
        System.out.println("1. View All Events \n2. View your Events \n3. Message User \n4. Message Users" +
                "in Event \n5. Enter New Room \n6.Schedule Speaker \n7.Exit");
        try{
            String input = br.readLine();
            while (!input.equals("7"))
            {
                if (input.equals("1"))
                {
                    this.viewAllEvents();
                }
                System.out.println("1. View All Events \n2. View your Events \n3. Message User \n4. Message Users" +
                        "in Event \n5. Enter New Room \n6.Schedule Speaker \n7.Exit");
                input = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Please enter a valid option");
            return null;
        }
        this.saveInfo();
        System.out.println("See you again soon");
        return null;
    }


    private boolean canSignUp(Event event){
        LocalDateTime startTime = getEventManager().getStartTime(event);
        LocalDateTime endTime = getEventManager().getEndTime(event);
        for (Integer eventToSignUpFor: getCurrentManager().getEventList(getUser())){
            Event actualEventToSignUpFor = getEventManager().getEventByID(eventToSignUpFor);
            LocalDateTime newStartTime = getEventManager().getStartTime(actualEventToSignUpFor);
            if (newStartTime.isAfter(startTime) && newStartTime.isBefore(endTime)){
                return false;
            }
        }
        return true;
    }



}