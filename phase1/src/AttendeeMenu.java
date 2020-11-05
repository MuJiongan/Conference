
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserPropertiesIterator prompts = new UserPropertiesIterator();
        ArrayList<String> inputs = new ArrayList<>();
        System.out.println("1. View All Events \n2. View your Events \n3. Message User \n4. Message Users" +
                "in Event \n5. Enter New Room \n6. Create Speaker Account \n7.Schedule Speaker \n8.Exit");
        try{
            String input = br.readLine();
            while (!input.equals("8"))
            {
                if (input.equals("1"))
                {
                    this.viewAllEvents();
                }
                System.out.println("1. View All Events \n2. View your Events \n3. Message User \n4. Message Users" +
                        "in Event \n5. Enter New Room \n6. Create Speaker Account \n7.Schedule Speaker \n8.Exit");
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


//    private boolean canSignUp(int EventID){
//
//    }



}
