import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AttendeeMenu extends UserMenu implements UserController{


    public AttendeeMenu(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm, EventManager em, MessageManager mm, User user){
        super(am, om, sm, rm, em, mm, user);
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

    // receiverID has to be in the user's contact list
    public boolean sendMessage(int receiverID, String messageContent){
            boolean canSend = false;
            Message message = getMessageManager().createMessage(messageContent, this.getUser().getUserId(), receiverID);

            if (getAttendeeManager().idInList(receiverID)) {
                canSend = true;
                getAttendeeManager().addMessageID(message.getMessageID(), getUser(), receiverID);
            }
            else if (getOrganizerManager().idInList(receiverID))
            {
                canSend = true;
                getOrganizerManager().addMessageID(message.getMessageID(), getUser(), receiverID);
            }
            else if (getSpeakerManager().idInList(receiverID)){
                canSend = true;
                getSpeakerManager().addMessageID(message.getMessageID(), getUser(), receiverID);
            }
            if (!canSend){
                Presenter.print("Receiver ID doesn't exist");
                return false;
            }
            else{
                Presenter.print("Messages sent");
                getMessageManager().addMessage(message);
                return true;
            }







    }
    public boolean cancelEnrollment(int eventID){
        if (getUser().getEventsAttend().contains(eventID)||getEventManager().getEventByID(eventID) != null) {
            getUser().removeEvent(eventID);
            getEventManager().removeUserID(getUser().getUserId(), getEventManager().getEventByID(eventID));
            Presenter.print("Cancellation successful!");
            return true;
        }else{
            Presenter.print("Cancellation unsuccessful!");
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
    public ArrayList<Event> viewAllEvents()
    {
        ArrayList<Integer> events = getCurrentManager().getEventList(getUser());
        ArrayList<Event> actualEvents = new ArrayList<>();
        for (Integer eventID: events){
            Event event = getEventManager().getEventByID(eventID);
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

        System.out.println("See you again soon");
        return null;
    }


    private boolean canSignUp(Event event){
        LocalDateTime startTime = getEventManager().getStartTime(event);
        LocalDateTime endTime = getEventManager().getEndTime(event);
        int vacancy = getEventManager().getCapacity(event) - getEventManager().getSpeakerIDs(event).size() - getEventManager().getUserIDs(event).size();
        for (Integer eventToSignUpFor: getCurrentManager().getEventList(getUser())){
            Event actualEventToSignUpFor = getEventManager().getEventByID(eventToSignUpFor);
            LocalDateTime newStartTime = getEventManager().getStartTime(actualEventToSignUpFor);
            LocalDateTime newEndTime = getEventManager().getEndTime(actualEventToSignUpFor);
            if ((newStartTime.isAfter(startTime) && newStartTime.isBefore(endTime) && newEndTime.isBefore(endTime)&& newEndTime.isAfter(startTime)) || vacancy == 0){
                return false;
            }
        }
        return true;
    }



}