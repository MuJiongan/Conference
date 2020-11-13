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

    public boolean signUp(int eventID){
        if (getEventManager().getEventByID(eventID) == null){
            Presenter.print("event doesn't exist");
            return false;
        }
        if (getUser().getEventsAttend().contains(eventID)){
            Presenter.print("You already signed up for this event.");
            return false;
        }else{
            getUser().addEvent(eventID);
            getEventManager().addUserID(getUser().getUserId(), getEventManager().getEventByID(eventID));
            // add the attendee to the speaker's contact list
            Event event = getEventManager().getEventByID(eventID);
            ArrayList<Integer> speakerIDs = getEventManager().getSpeakerIDs(event);
            for (Integer speakerID: speakerIDs){
                User speaker = getSpeakerManager().getUserByID(speakerID);
                Integer userID = getCurrentManager().getIDByUser(getUser());
                // Check if the attendee is already in speaker's contact list
                if (!getSpeakerManager().getContactList(speaker).contains(userID)){
                getSpeakerManager().addToContactsList(speaker, userID);}
            }
            Presenter.print("Successfully signed up!");
            return true;
        }
    }

    // receiverID has to be in the user's contact list
    public boolean sendMessage(int receiverID, String messageContent){
            boolean canSend = false;
            Message message = getMessageManager().createMessage(messageContent, this.getUser().getUserId(), receiverID);

            if (getAttendeeManager().idInList(receiverID)) {
                canSend = true;
                getAttendeeManager().addMessageID(message.getMessageID(), getUser(), receiverID);
                getAttendeeManager().addMessageID(message.getMessageID(), getAttendeeManager().getUserByID(receiverID), getAttendeeManager().getIDByUser(getUser()));
                getMessageManager().addMessage(message);
            }
//            else if (getOrganizerManager().idInList(receiverID))
//            {
//                canSend = true;
//                getOrganizerManager().addMessageID(message.getMessageID(), getUser(), receiverID);
//                getMessageManager().addMessage(message);
//            } Attendees cant message organizers
            else if (getSpeakerManager().idInList(receiverID)){
                canSend = true;
                getAttendeeManager().addMessageID(message.getMessageID(), getUser(), receiverID);
                getSpeakerManager().addMessageID(message.getMessageID(), getSpeakerManager().getUserByID(receiverID), getAttendeeManager().getIDByUser(getUser()));
                getMessageManager().addMessage(message);
                // Add the attendee to speaker's contact list (if doesn't exist)
                User speaker = getSpeakerManager().getUserByID(receiverID);
                Integer userID = getCurrentManager().getIDByUser(getUser());
                if (!getSpeakerManager().getContactList(speaker).contains(userID)){
                    getSpeakerManager().addToContactsList(speaker, userID);
                }

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

    public ArrayList<Event> viewAllEvents()
    {
        return getEventManager().getEvents();
    }
    public User run(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Presenter.printAttendeeMenu();
        try{
            String input = br.readLine();
            while (!input.equals("5"))
            {
                if (input.equals("1"))
                {
                    Presenter.viewAllEvents(viewAllEvents(), getEventManager(), getRoomManager());
                    runViewAllEvents();
                }
                else if (input.equals("2"))
                {
                    Presenter.viewMyEvents(viewMyEvents(), getEventManager(), getRoomManager());
                    runViewMyEvents();
                }
                else if (input.equals("3"))
                {
                    Presenter.viewContacts(getAttendeeManager().getContactList(getUser()), getAttendeeManager(), getOrganizerManager(), getSpeakerManager());
                    runViewContacts();
                }
                else if (input.equals("4"))
                {
                    runManageAccount();
                }
                Presenter.printAttendeeMenu();
                input = br.readLine();
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option");
            return null;
        }

        Presenter.print("See you again soon");
        return null;
    }
    public void runViewAllEvents() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Presenter.print("1. Sign up for event\n2. Go back to the main menu");
        try{
            String input = br.readLine();
            while (!input.equals("2")) {
                if (input.equals("1")) {
                    Presenter.print("Please enter an event number: ");
                    String input2 = br.readLine();
                    int index = Integer.parseInt(input2);
                    while (hasEvent(index)) {
                        Presenter.print("Please enter a valid option: ");
                        input2 = br.readLine();
                        index = Integer.parseInt(input2);
                    }
                    signUp(index);

                }
                Presenter.print("1. Sign up for event\n2. Go back to the main menu");
                input = br.readLine();
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option: ");
        }
        catch (NumberFormatException n) {
            Presenter.print("Please enter an integer value for the ID!!");
        }
    }
    public void runViewMyEvents() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Presenter.print("1. Cancel Event\n2. Go back to the main menu");
        try{
            String input = br.readLine();
            while (!input.equals("2")) {
                if (input.equals("1")) {
                    Presenter.print("Please enter an event number: ");
                    String input2 = br.readLine();
                    int index = Integer.parseInt(input2);
                    while (!hasMyEvent(index)) {
                        Presenter.print("Please enter a valid option: ");
                        input2 = br.readLine();
                        index = Integer.parseInt(input2);
                    }
                    cancelEnrollment(index);
                }
                Presenter.print("1. Cancel Event\n2. Go back to the main menu");
                input = br.readLine();
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option: ");
        }
        catch (NumberFormatException n) {
            Presenter.print("Please enter an integer value for the ID!!");
        }
    }

    public void runViewContacts() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Presenter.print("1. View chat history \n2. Go back to the main menu");
        try {
            String input = br.readLine();
            while (!input.equals("2")) {
                if (input.equals("1")) {
                    Presenter.print("Please enter a friend number: ");
                    int index = Integer.parseInt(br.readLine());
                    runViewChat(index);
                }
                Presenter.print("1. View chat history \n2. Go back to the main menu");
                input = br.readLine();
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option: ");
        } catch (NumberFormatException n) {
            Presenter.print("Please enter an integer value for the ID");
        }
    }
    public void runViewChat(int receiverID) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Presenter.viewChat(receiverID, getAttendeeManager().getMessages(getUser()), getMessageManager(), getAttendeeManager()
        , getOrganizerManager(), getSpeakerManager());
        Presenter.print("1. Send Message \n2. Go Back to Contacts List");
        try{
            String input = br.readLine();
            while (!input.equals("2")){
                if (input.equals("1")) {
                    Presenter.print("Please type your message here: ");
                    String input2 = br.readLine();
                    sendMessage(receiverID, input2);
                   // this.readAllMessage(receiverID);
                }
                Presenter.print("1. Send Message \n2. Go Back to Contacts List");
                input = br.readLine();
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option");
        }
    }
    public void runManageAccount() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Presenter.print("Current name: " + this.getUser().getName());
        Presenter.print("1. Change my name \n2. Go back to the main menu");
        try{
            String input = br.readLine();
            while (!input.equals("2")){
                if (input.equals("1")) {
                    Presenter.print("Please type in your new name here: ");
                    String input2 = br.readLine();
                    getAttendeeManager().setName(getUser(), input2);
                }
                Presenter.print("Current name: " + this.getUser().getName());
                Presenter.print("1. Change my name \n2. Go back to the main menu");
                input = br.readLine();
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option");
        }
    }
}