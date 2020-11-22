package interfaceAdapters;

import entities.*;
import useCases.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class SpeakerMenu extends UserMenu implements UserController{

    /**
     * Construct an instance of SpeakerMenu with the given Managers
     * @param am the instance of <code>AttendeeManager</code> in the conference
     * @param om the instance of <code>OrganizerManager</code> in the conference
     * @param sm the instance of <code>SpeakerManager</code> in the conference
     * @param rm the instance of <code>RoomManager</code> in the conference
     * @param em the instance of <code>EventManager</code> in the conference
     * @param mm the instance of <code>MessageManager</code> in the conference
     * @param user a instance of <code>User</code> that simulate the user on the keyboard
     */
    public SpeakerMenu(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm, EventManager em, MessageManager mm, User user){
        super(am, om, sm, rm, em, mm, user);
    }

    /**
     * Returns whether the current user can send message to the other user given by ID
     * @param receiverID ID of the other user who is the receiver of the message
     * @return true iff the current user can send message to the other user given by ID
     */
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

    /**
     * Adds the message to the messages hashmaps of both the receiver and the sender, returns true iff successful
     * @param receiverID ID of the other user the current user is sending message to
     * @param messageContent content of the message
     * @return true iff the message is sent successfully
     */
    public boolean sendMessage(int receiverID, String messageContent)
    {
        if (canSend(receiverID))
        {
            Message message = getMessageManager().createMessage(messageContent, this.getUser().getUserId(), receiverID);
            getMessageManager().addMessage(message);
            //Add message to receiver's hashmap
            if (getAttendeeManager().idInList(receiverID)) {
                getAttendeeManager().addMessageID(message.getMessageID(),getAttendeeManager().getUserByID(receiverID), getSpeakerManager().getIDByUser(getUser()));
            }
            else if (getOrganizerManager().idInList(receiverID))
            {
                getOrganizerManager().addMessageID(message.getMessageID(),getOrganizerManager().getUserByID(receiverID), getSpeakerManager().getIDByUser(getUser()));
            }
            //add message to this user's hashmap
            super.sendMessage(receiverID, message);
            getMessageManager().addMessage(message);
            Presenter.print("Messages sent");
            return true;
        }
        else
        {
            System.out.println("Receiver ID doesn't exist or you cannot message them");
            return false;
        }
    }

    /**
     * Adds the message to the messages hashmaps of both all the receivers in the given event and the sender
     * @param eventID ID of the event the user is sending all attendees message to
     * @param content content of the message
     */
    public void messageAll(int eventID, String content)
    {
        Event event = getEventManager().getEventByID(eventID);
        for (int userID: getEventManager().getUserIDs(event))
        {
            sendMessage(userID, content);
            Presenter.print("Messages sent");
        }
    }

    /**
     * Runs the main speaker menu. Print out error message if the input is not a valid option. Print out greetings
     * message when the user logs out.
     * @return null
     */
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
                    runViewMyEvents(); //Edit this menu part
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

    /**
     * Runs the view my events menu. Print out error message if the input is not a valid option.
     */
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
                        int eventID = Integer.parseInt(input2);
                        while (!this.hasMyEvent(eventID)){
                            System.out.println("Please enter a valid option: ");
                            input2 = br.readLine();
                            eventID = Integer.parseInt(input2);
                        }
                        System.out.println("Please type in your message: ");
                        String input3 = br.readLine();
                        this.messageAll(eventID, input3);
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

    /**
     * Runs the view contacts menu. Print out error message if the input is not a valid option.
     */
    public void runViewContacts() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Presenter.print("1. View chat history \n2. Go back to the main menu");
        try {
            String input = br.readLine();
            while (!input.equals("2")) {
                if (input.equals("1")) {
                    Presenter.print("Please enter a friend number: ");
                    String input2 = br.readLine();
                    try {
                        int friendID = Integer.parseInt(input2);
                        while (!this.hasContact(friendID)){
                            System.out.println("Please enter a valid option: ");
                            input2 = br.readLine();
                            friendID = Integer.parseInt(input2);
                        }
                        this.runViewChat(friendID);
                    } catch (NumberFormatException ne) {
                        Presenter.print("Please enter an integer value for the ID!!");
                    } catch (IOException ie) {
                        Presenter.print("Please enter a valid option: ");
                    }
                }
                Presenter.print("1. View chat history \n2. Go back to the main menu");
                input = br.readLine();
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option: ");
        }
    }

    /**
     * Runs view chat. Print out error message if the input is not a valid option.
     * @param receiverID the id of receiver
     */
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
                    this.readAllMessage(receiverID);
                }
                Presenter.viewChat(receiverID, getAttendeeManager().getMessages(getUser()), getMessageManager(), getAttendeeManager()
                        , getOrganizerManager(), getSpeakerManager());
                Presenter.print("1. Send Message \n2. Go Back to Contacts List");
                input = br.readLine();
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option");
        }
    }

    /**
     * Runs the manage account menu. Print out error message if the input is not a valid option.
     */
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
