import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;


public class SpeakerMenu extends UserMenu implements UserController{


    public SpeakerMenu(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm, EventManager em, MessageManager mm, User user){
        super(am, om, sm, rm, em, mm, user);
    }
    private boolean canSend(int receiverID)
    {
        ArrayList<Integer> talks = this.getSpeakerManager().getEventList(this.getUser());
        for (int x: talks)
        {
            Event talk = this.getEventManager().getEventByID(x);
            ArrayList<Integer> people = this.getEventManager().getUserIDs(talk);
            if (people.contains(receiverID))
            {
                return true;
            }
        }
        return false;
    }

    public boolean sendMessage(int receiverID, String messageContent)
    {
        if (canSend(receiverID))
        {
            Message message = getMessageManager().createMessage(messageContent, this.getUser().getUserId(), receiverID);
            getMessageManager().addMessage(message);
            //Add message to receiver's hashmap
            if (getAttendeeManager().idInList(receiverID)) {
                getAttendeeManager().addMessageID(message.getMessageID(), getUser(), receiverID);
            }
            else if (getOrganizerManager().idInList(receiverID))
            {
                getOrganizerManager().addMessageID(message.getMessageID(), getUser(), receiverID);
            }
            //add message to this user's hashmap
            super.sendMessage(receiverID, message);
            getMessageManager().addMessage(message);
            Presenter.print("Messages sent");
            return true;
        }
        else
        {
            System.out.println("Please enter a valid userID");
            return false;
        }
    }
    public void messageAll(int eventID, String content)
    {
        Event event = getEventManager().getEventByID(eventID);
        for (int userID: event.getUserIDs())
        {
            sendMessage(userID, content);
        }
    }

    @Override
    public User run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Presenter.printSpeakerMenu();
        try{
            String input = br.readLine();
            while (!input.equals("4"))
            {
                if (input.equals("1"))
                {
                    this.runViewMyEvents(); //Edit this menu part
                }
                else if (input.equals("2")){
                    Presenter.viewContacts(getSpeakerManager().getContactList(getUser()), getAttendeeManager(), getOrganizerManager(), getSpeakerManager());
                    runViewContacts();
                }
                else if (input.equals("3"))
                {
                    runManageAccount();
                }
                Presenter.printSpeakerMenu();
                input = br.readLine();
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option");
            return null;
        }
        Presenter.print("See you again soon");
        return null;
    }

    public void runViewMyEvents() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Presenter.viewMyEvents(viewMyEvents(), getEventManager(), getRoomManager());
        System.out.println("1. Message all attendees in one event \n2. Go back to the main menu");
        try{
            String input = br.readLine();
            while (!input.equals("2")){
                if (input.equals("1")) {
                    System.out.println("Please enter an event number: ");
                    String input2 = br.readLine();
                    try {
                        int index = Integer.parseInt(input2) - 1;
                        while (index <= 0 || index >= this.viewMyEvents().size()){
                            System.out.println("Please enter a valid option: ");
                            input2 = br.readLine();
                            index = Integer.parseInt(input2) - 1;
                        }
                        int eventID = getUser().getEventsAttend().get(index);
                        System.out.println("Please type in your message: ");
                        String input3 = br.readLine();
                        this.messageAll(eventID, input3);
                        System.out.println("Message Sent");
                    } catch (NumberFormatException ne) {
                        Presenter.print("Please enter an integer value for the ID!!");
                    } catch (IOException ie) {
                        Presenter.print("Please enter a valid option: ");
                    }
                }
                System.out.println("1. Message all attendees in one event\n2. Go back to the main menu");
                input = br.readLine();
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option: ");
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
                    getSpeakerManager().setName(getUser(), input2);
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
