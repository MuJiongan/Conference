package com.example.model.interfaceAdapters;

import com.example.model.entities.*;
import com.example.model.useCases.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AttendeeMenu extends UserMenu implements UserController{

    /**
     * Create an instance of AttendeeMenu with the given Managers
     * @param am the instance of <code>AttendeeManager</code> in the conference
     * @param om the instance of <code>OrganizerManager</code> in the conference
     * @param sm the instance of <code>SpeakerManager</code> in the conference
     * @param rm the instance of <code>RoomManager</code> in the conference
     * @param em the instance of <code>EventManager</code> in the conference
     * @param mm the instance of <code>MessageManager</code> in the conference
     * @param userID a instance of <code>User</code> that simulate the user on the keyboard
     */
    public AttendeeMenu(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm, EventManager em, MessageManager mm, int userID){
        super(am, om, sm, rm, em, mm, userID);
    }

    /**
     * return an ArrayList of <code>Event</code> that the attendee on the keyboard can sign up for
     * @return an ArrayList of <code>Event</code> that the attendee on the keyboard can sign up for.
     * The <code>Event</code> in the returned ArrayList satisfy two condition:
     * 1. there is a vacancy in the event
     * 2. the event doesn't conflict with the registered event of the attendee on the keyboard
     */
    public ArrayList<Event> eventsTheyCanSignUpFor(){
        ArrayList<Event> eventsTheyCanSignUpFor = new ArrayList<>();
        for (Event event: getEventManager().getEvents()){
            if (canSignUp(eventID)){
                eventsTheyCanSignUpFor.add(event);
            }
        }
        return eventsTheyCanSignUpFor;
    }

    /**
     * Return true if user can sign up and false otherwise
     * @param eventID event that the user on the keyboard want to sign up now
     * @return return true if there is a vacancy in the give event and the event wanted to sign up now doesn't conflict
     * with the given registered event
     */

    private boolean canSignUp(int eventID){
        LocalDateTime startTime = getEventManager().getStartTime(eventID);
        LocalDateTime endTime = getEventManager().getEndTime(eventID);
        int vacancy = getEventManager().getCapacity(eventID) - getEventManager().getSpeakerIDs(eventID).size()
                - getEventManager().getUserIDs(eventID).size();
        // the event capacity - number of speakers - number of attendees
        for (Integer eventToSignUpFor: getCurrentManager().getEventList(getUser())){

            LocalDateTime newStartTime = getEventManager().getStartTime(eventToSignUpFor);
            LocalDateTime newEndTime = getEventManager().getEndTime(eventToSignUpFor);
            // get startime and endtime of a specific event that user has already signed up
            if (!checkTime(startTime, endTime, newStartTime, newEndTime)|| vacancy == 0){

                return false;
            }
        }
        return true;
    }

    /**
     * Return ture if the event want to sign up now doesn't conflict with the given registered event and false otherwise
     * @param startTime start time of a given event that the user has already signed up
     * @param endTime end time of a given event that the user has already signed up
     * @param newStartTime start time of event that the user want to sign up now
     * @param newEndTime end time of event that the user want to sign up now
     * @return return true if one of four conditions is violate:
     * condition1: the end time of event wanted to sign up is during the event signed up
     * condition2: the start time of event wanted to sign up is during the event signed up
     * condition3: the end time of event wanted to sign up is during the event signed up
     * condition4: the start time of event wanted to sign up is during the event signed up
     */
    private boolean checkTime(LocalDateTime startTime, LocalDateTime endTime, LocalDateTime newStartTime, LocalDateTime newEndTime){
        // endTime is between the newStartTime and newEndTime
        // return true if the endtime is in between
        boolean condition1 = (!endTime.isAfter(newEndTime))&&(!endTime.isBefore(newStartTime));
        // old event ends in the middle of new event or before the start of new event


        boolean condition2 = (!startTime.isAfter(newEndTime))&&(!startTime.isBefore(newStartTime));
        // old event starts in the middle of new events or before new event starts
        boolean condition3 = (!newEndTime.isAfter(endTime))&&(!newEndTime.isBefore(startTime));

        boolean condition4 = (!newStartTime.isAfter(endTime))&&(!newStartTime.isBefore(startTime));



        // if one of the conditions fails, return false
        if (condition1 || condition2 || condition3 || condition4){
            return false;
        }
        return true;
    }

    /**
     * return true if signed up successfully, ana false if not and update the attendee list in the
     * given event and update the contact list of each speaker host the given event
     * @param eventID the id of event that attendee on the keyboard want to sign up for
     * @return return false if one of the condition false:
     * 1. the given event is not in the conference
     * 2. attendee on the keyboard has already signed up for the given event
     * 3. there is no vacancy in the given event
     * 4. the given event is conflicted with an event that the attendee on the keyboard signed up
     */
    public boolean signUp(int eventID){
        if (getEventManager().getEventByID(eventID) == null){
            Presenter.print("Event doesn't exist");
            return false;
        }
        if (getCurrentManager().getEventList(getUser()).contains(eventID)){
            Presenter.print("You already signed up for this event.");
            return false;
        }

        if (getEventManager().getCapacity(eventID) - getEventManager().getUserIDs(eventID).size() <= 0){
            Presenter.print("The event is already full.");
            return false;
        }
        if (!canSignUp(eventID)){
            Presenter.print("You already signed up for an event at that time.");
            return false;

        }
            else{
            getCurrentManager().addEventID(getUser(), eventID);
            getEventManager().addUserID(getUser(), eventID);
            // add the attendee to the speaker's contact list
            Event event = getEventManager().getEventByID(eventID);
            ArrayList<Integer> speakerIDs = getEventManager().getSpeakerIDs(eventID);
            for (Integer speakerID: speakerIDs){


                // Check if the attendee is already in speaker's contact list
                if (!getSpeakerManager().getContactList(speakerID).contains(getUser())){
                getSpeakerManager().addToContactsList(speakerID, getUser());}
            }
            Presenter.print("Successfully signed up!");
            return true;
        }
    }

    /**
     * return true if the message with the given content is successfully sent to the given receiver
     * and false is fail to send the message. Also, if the message is sent successfully, update the information.
     * @param receiverID the id of user that attendee on the keyboard wanted to send message to
     * @param messageContent the content of message that attendee on the keyboard wanted to send
     * @return return false if the given receiver is not an attendee or a speaker in the conference, return true
     * otherwise
     */
    // receiverID has to be in the user's contact list
    public boolean sendMessage(int receiverID, String messageContent){
            boolean canSend = false;
            Message message = getMessageManager().createMessage(messageContent, this.getUser(), receiverID);

            if (getAttendeeManager().idInList(receiverID)) {
                canSend = true;
                getAttendeeManager().addMessageID(message.getMessageID(), getUser(), receiverID);
                getAttendeeManager().addMessageID(message.getMessageID(), getAttendeeManager().getUserByID(receiverID), getAttendeeManager().getIDByUser(getUser()));
                getMessageManager().addMessage(messageID);
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
                int userID = getCurrentManager().getIDByUser(getUser());
                if (!getSpeakerManager().getContactList(speaker).contains(userID)){
                    getSpeakerManager().addToContactsList(speaker, userID);
                }

            }
            if (!canSend){
                Presenter.print("Receiver ID doesn't exist or you cannot message them");
                return false;
            }
            else{
                Presenter.print("Messages sent");
                getMessageManager().addMessage(message);
                return true;
            }
//          information updated:
//          1. update the message list in the <code>MessageManager</code>
//          2. update the message list of the attendee on the keyboard and the message list of receiver
//          3. if the given receiver is a speaker, then add the attendee on the keyboard to the contact list of the
//          given speaker
    }

    /**
     * return true if event is canceled successfully, and false if fails. Also, update the EventsAttend list and event
     * list in the <code>EventManager</code>
     * @param eventID the id of event that the attendee on the keyboard wanted to cancel enrollment
     * @return return true if the given event is canceled successfully, and false if fails.
     */
    public boolean cancelEnrollment(int eventID){
        if (getCurrentManager().getEventList(getUser()).contains(eventID)||getEventManager().getEventByID(eventID) != null) {
            getCurrentManager().removeEventID(getUser(), eventID);
            getEventManager().removeUserID(getUser(), getEventManager().getEventByID(eventID));
            Presenter.print("Cancellation successful!");
            return true;
        }else{
            Presenter.print("Cancellation unsuccessful!");
            return false;
        }
    }

    /**
     * return an ArrayList of all events in the conference
     * @return return an ArrayList of all events in the conference
     */
    public String viewAllEvents()
    {
        List<Integer> eventIDs = getEventManager().getEvents();
        String output ="";
        for (int ID: eventIDs)
        {
            output = output + ID + ".\t" + getEventManager().getName(ID) + "\t" + getEventManager().getStartTime(ID)
                    + "\t" + getEventManager().getEventByID(ID) + "\t" + getRoomManager().getRoomName(getEventManager().getRoomID(ID))
            +"\n";
        }
        return output;
    }

    /**
     * Runs the main attendee menu
     * @return null
     */
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

    /**
     * runs View All Event submenu of the main menu
     */
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
                    while (!hasEvent(index)) {
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

    /**
     * runs View My Events submenu of the main menu
     */
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
    /**
     * run View Contact List submenu of the main menu
     */
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
    /**
     * run View chat history submenu of View Contact List option
     * @param receiverID the id of a user in the system that the attendee on the keyboard choose to see the chat history
     *                   with
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
                   // this.readAllMessage(receiverID);
                }
                Presenter.print("1. Send Message \n2. Go Back to Contacts List");
                input = br.readLine();
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option");
        }
    }
    /**
     * run Manage Account submenu of the main menu
     */
    public void runManageAccount() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Presenter.print("Current name: " + getCurrentManager().getnameById(getUser()));
        Presenter.print("1. Change my name \n2. Go back to the main menu");
        try{
            String input = br.readLine();
            while (!input.equals("2")){
                if (input.equals("1")) {
                    Presenter.print("Please type in your new name here: ");
                    String input2 = br.readLine();
                    getAttendeeManager().setName(getUser(), input2);
                }
                Presenter.print("Current name: " + getCurrentManager().getnameById(getUser()));
                Presenter.print("1. Change my name \n2. Go back to the main menu");
                input = br.readLine();
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option");
        }
    }
}