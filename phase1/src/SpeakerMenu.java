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
        UserPropertiesIterator prompts = new UserPropertiesIterator();
        ArrayList<String> inputs = new ArrayList<>();
        Presenter.printSpeakermenu();
        try{
            String input = br.readLine();
            while (!input.equals("3"))
            {
                if (input.equals("1"))
                {
                    this.runViewMyEvents();
                }
                else if (input.equals("2")){
                    this.runViewContacts();
                }
                Presenter.printSpeakermenu();
                input = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Please enter a valid option");
            return null;
        }
        System.out.println("See you again soon");
        return null;
    }

    public User runViewMyEvents() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserPropertiesIterator prompts = new UserPropertiesIterator();
        ArrayList<String> inputs = new ArrayList<>();
        this.viewMyEvents();
        System.out.println("1. Message all attendees in one event \n2. Message one attendee in event\n2. Go back to the main menu");
        try{
            String input = br.readLine();
            while (!input.equals("2")){
                if (input.equals("1")) {
                    System.out.println("Please enter an event number: ");
                    String input2 = br.readLine();
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
                }
                System.out.println("1. Message all attendees in one event \n2. Go back to the main menu");
                input = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Please enter a valid option: ");
            return null;
        }
        catch (NumberFormatException n) {
            Presenter.print("Please enter an integer value for the ID!!");
            return null;
        }
        return null;
    }

    public User runViewContacts() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserPropertiesIterator prompts = new UserPropertiesIterator();
        ArrayList<String> inputs = new ArrayList<>();
        this.viewMyContacts();
        System.out.println("1. View chat history \n2. Go back to the main menu");
        try{
            String input = br.readLine();
            while (!input.equals("2")){
                if (input.equals("1")) {
                    Presenter.print("Please enter a friend number: ");
                    String input2 = br.readLine();
                    int index = Integer.parseInt(input2) - 1;
                    while (index <= 0 || index >= this.viewMyContacts().size()){
                        Presenter.print("Please enter a valid option: ");
                        input2 = br.readLine();
                        index = Integer.parseInt(input2) - 1;
                    }
                    int receiverID = super.getUser().getContactList().get(index);
                    this.runViewChat(receiverID);
                }
                System.out.println("1. View chat history \n2. Go back to the main menu");
                input = br.readLine();
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option: ");
            return null;
        } catch (NumberFormatException n) {
            Presenter.print("Please enter an integer value for the ID");
            return null;
        }
        return null;
    }

    public User runViewChat(int receiverID) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserPropertiesIterator prompts = new UserPropertiesIterator();
        ArrayList<String> inputs = new ArrayList<>();
        this.viewChat(receiverID);
        System.out.println("1. Send Message \n2. Exit");
        try{
            String input = br.readLine();
            while (!input.equals("2")){
                if (input.equals("1")) {
                    Presenter.print("Please type your message here: ");
                    String input2 = br.readLine();
                    this.sendMessage(receiverID, input2);
                    this.readAllMessage(receiverID);
                    this.viewChat(receiverID);
                }
                System.out.println("1. Send Message \n2. Exit");
                input = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Please enter a valid option");
            return null;
        }
        return null;
    }
}
