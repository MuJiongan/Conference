package com.example.model.interfaceAdapters;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.model.entities.*;
import com.example.model.useCases.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class OrganizerMenu extends AttendeeMenu implements UserController{
    /**
     * Create an instance of OrganizerMenu with the given Managers
     * @param am the instance of <code>AttendeeManager</code> in the conference
     * @param om the instance of <code>OrganizerManager</code> in the conference
     * @param sm the instance of <code>SpeakerManager</code> in the conference
     * @param rm the instance of <code>RoomManager</code> in the conference
     * @param em the instance of <code>EventManager</code> in the conference
     * @param mm the instance of <code>MessageManager</code> in the conference
     * @param userID a instance of <code>User</code> that simulate the user on the keyboard
     */
    public OrganizerMenu(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm, EventManager em,
                         MessageManager mm, int userID){
        super(am, om, sm, rm, em, mm, userID);
    }

    /**
     * send Message with the given content to all
     * @param current current is a <code>AttendeeManager</code> if the organizer on the keyboard choose to message
     *                all attendees , and is a <code>SpeakerManager</code> if the organizer on the keyboard choose
     *                to message all speakers
     * @param content the given content of message the organizer on the keyboard wanted to send
     */
    public void messageAll(UserManager current, String content)
    {
        for (User user: current.getUsers()){
            sendMessage(current.getIDByUser(user), content);
        }

    }

    /**
     * return true if message with the given content is sent successfully, and update the information.
     * @param receiverID the id of user that attendee on the keyboard wanted to send message to
     * @param messageContent the content of message that attendee on the keyboard wanted to send
     * @return return true if message with the given content is sent successfully
     */
    // Need to override because when an organizer sends a message, we might add that organizer to the receiver's
    // contact list
    @Override
    public boolean sendMessage(int receiverID, String messageContent) {
        if (super.sendMessage(receiverID, messageContent)){
            int organizerID = getUser();
            // If this receiver is a speaker, add the organizer to the speaker's contact list
            if (getSpeakerManager().idInList(receiverID)){

                // Check if the organizer already exists in the speaker's contact list
                if (!getSpeakerManager().getContactList(receiverID).contains(organizerID)){
                getSpeakerManager().addToContactsList(receiverID, organizerID);}
            }
            // If this receiver is an attendee, add the organizer to the attendee's contact list
            else if (getAttendeeManager().idInList(receiverID)){

                // Check if the organizer already exists in the speaker's contact list
                if (!getAttendeeManager().getContactList(receiverID).contains(organizerID)){
                    getAttendeeManager().addToContactsList(receiverID, organizerID);}
            }
            // Don't know about the organizer. I assumed organizers aren't allowed to message each other
            return true;
        }
        else{
            return false;
        }
//        updated information:
//        1. add the given message to the receiver given the receiverID
//        2. If this receiver is a speaker, add the organizer to the speaker's contact list (if not added already)
//           If this receiver is an attendee, add the organizer to the attendee's contact list (if not added already)
//           If this receiver is an organizer, do nothing
//
    }

    /**
     * Enter new Rooms into the System
     * @param name name of the room wanted to create
     * @param capacity capacity of the room wanted to create
     */
    public void enterRoom(String name, int capacity){
        getRoomManager().createRoom(name, capacity);
        Presenter.print("Room Succesfully added");
        //Maybe we need to check duplicate names
    }


    /**
     * Create new Speaker account
     * @param name name of the speaker wanted to create
     * @param username username of the speaker wanted to create
     * @param password password of the speaker wanted to create
     * @return true if new Speaker account successfully created
     */
    public boolean createSpeaker(String name, String username, String password) {

        Speaker s = getSpeakerManager().createSpeaker(name, username, password, getNewID());
        // can't initialize contact list because the speaker has no talks to give for now
        // add this speaker to organizers and attendees contact list
        boolean added = getSpeakerManager().addUser(s);
        int speakerID = getSpeakerManager().getIDByUser(s);
        if (added){
            // Add the speaker to the attendee's contact list
            for (User attendee: getAttendeeManager().getUsers()){
                getAttendeeManager().addToContactsList(getAttendeeManager().getIDByUser(attendee), speakerID);
            }
            // Add the speaker to the organizer's contact list
            for (User organizer: getOrganizerManager().getUsers()){
                getOrganizerManager().addToContactsList(getOrganizerManager().getIDByUser(organizer), speakerID);
            }
            Presenter.print("Speaker successfully created");
            return true;
        }
        else{
            Presenter.print("Something went wrong!");
            return false;
        }


    }
//    /**
//     * Schedule Speaker to an existing Event
//     * @param speaker the Speaker to be scheduled
//     * @param event the Event that the Speaker scheduled for
//     * @return true if and only if time and room are both available and
//     *                             speakerID could be successfully added to the existing Event
//     */
//    public boolean scheduleSpeakerToEvent(Speaker speaker, Event event){
//        if(availableAtTime(speaker, event.getStartTime(), event.getEndTime())
//                && availableInRoom(speaker, event.getRoomID(), event.getStartTime(), event.getEndTime())
//                && getEventManager().addSpeakerID(speaker.getUserId(), event)){
//            getSpeakerManager().addEventID(event.getEventID(), speaker);
//            // initialize the speaker's contact list
//            for(Integer userIDInEvent: getEventManager().getUserIDs(event)){
//                if (!getSpeakerManager().getContactList(speaker).contains(userIDInEvent)){
//                getSpeakerManager().addToContactsList(speaker, userIDInEvent);}
//            }
//            return true;
//        }
//        return false;
//    }
    /**
     * Schedule Speaker to a new Event
     * @param speakerID the Speaker who is to be scheduled
     * @param startTime the LocalDateTime of start time of Event
     * @param endTime the LocalDateTime of end time of Event
     * @param roomID ID of room that this Event is scheduled in
     * @param capacity maximum number of attendees allowed in this Event
     * @param name event's name
     * @return true if speakerID successfully added to the new Event
     */
    public boolean scheduleSpeakerToEvent(int speakerID, LocalDateTime startTime, LocalDateTime endTime,
                                          int roomID, String name, int capacity){
        User speaker = getSpeakerManager().getUserByID(speakerID);
        Room room = getRoomManager().getRoomByID(roomID);
        if (room == null){
            Presenter.print("RoomID doesn't exist.");
            return false;
        }
        if (speaker == null){
            Presenter.print("Speaker ID doesn't exist.");
            return false;
        }
        if (!haveEnoughCapacity(roomID, capacity)){
            Presenter.print("Room doesn't have enough capacity.");
            return false;
        }
        int eventID = createEvent(startTime, endTime, roomID, name, capacity);

        if (!availableAtTime(speaker, startTime, endTime)){ //sm.availale at time (speakerID, starttime, endtime)
            getEventManager().removeEvent(eventID);
            Presenter.print("The speaker is not available at the time");
            return false;
        }
        if (!availableInRoom(speaker, roomID, startTime, endTime)){
            getEventManager().removeEvent(eventID);
            Presenter.print("The room you entered is occupied at the time");
            return false;
        }
        if (!getEventManager().addSpeakerID(speakerID, eventID)){
            getEventManager().removeEvent(eventID);
            Presenter.print("You can't add this speaker. The event already has a speaker");
            return false;

        }

        getSpeakerManager().addEventID(eventID, speakerID);

        getRoomManager().scheduleEvent(roomID, eventID);
        Presenter.print("New Event Scheduled");
        return true;

    }


    /**
     * Check whether a Speaker is available to be scheduled at specific time (avoiding double-booking a speaker)
     * @param speaker the Speaker who is to be scheduled
     * @param startTime the LocalDateTime of start time of Event
     * @param endTime the LocalDateTime of end time of Event
     * @return true if Speaker is available to speak at specific time
     */
    public boolean availableAtTime(User speaker, LocalDateTime startTime, LocalDateTime endTime){
        for(Integer eventID : getSpeakerManager().getEventList(speaker.getUserId())){
            LocalDateTime existingStartTime = getEventManager().getStartTime(eventID);
            LocalDateTime existingEndTime = getEventManager().getEndTime(eventID);
            if(!checkTime(startTime, endTime, existingStartTime, existingEndTime))
            return false;
        }
        return true;
    }


    /**
     * Check whether event conflict with the given period of time
     * @param startTime start time of 1st pair of time
     * @param endTime end time of 1st pair of time
     * @param newStartTime start time of 2nd pair of time
     * @param newEndTime end time of 2nd pair of time
     * @return true if and only if no conflict occur
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean checkTime(LocalDateTime startTime, LocalDateTime endTime, LocalDateTime newStartTime,
                              LocalDateTime newEndTime){
        // endTime is between the newStartTime and newEndTime
        // return true if the endtime is in between
        boolean condition1 = (!endTime.isAfter(newEndTime))&&(!endTime.isBefore(newStartTime));

        boolean condition2 = (!startTime.isAfter(newEndTime))&&(!startTime.isBefore(newStartTime));
        boolean condition3 = (!newEndTime.isAfter(endTime))&&(!newEndTime.isBefore(startTime));
        boolean condition4 = (!newStartTime.isAfter(endTime))&&(!newStartTime.isBefore(startTime));


        // if one of the conditions fails, return false
        if (condition1 || condition2 || condition3 || condition4){
            return false;
        }
        return true;
    }


    /**
     * Check whether a Speaker is available to be scheduled in specific room (avoiding double-booking a room)
     * @param speaker the Speaker who is to be scheduled
     * @param roomID ID of room that this Event is scheduled in
     * @param startTime start time of event
     * @param endTime end time of event
     * @return true if Speaker is available to speak in specific room
     */
    public boolean availableInRoom(User speaker, int roomID, LocalDateTime startTime, LocalDateTime endTime){
        ArrayList<Integer> events = getRoomManager().getRoomByID(roomID).getEventsScheduled();
        for(Integer eventID : events) {
            LocalDateTime existingStartTime = getEventManager().getStartTime(eventID);
            LocalDateTime existingEndTime = getEventManager().getEndTime(eventID);
            if(!checkTime(startTime, endTime, existingStartTime, existingEndTime)) return false;
        }
        return true;
    }


    /**
     * Check whether the given Event already has a Speaker (other than the given Speaker we want to add)
     * @param speaker the given Speaker who is to be scheduled
     * @param eventID ID of the Event to be checked
     * @return true if and only if the Event has Speaker other than the given Speaker
     */
    private boolean hasSpeaker(User speaker, int eventID){
        if(getEventManager().getEventByID(eventID).getSpeakerIDs().size() == 0) return false;
        for(Integer speakerID : getEventManager().getEventByID(eventID).getSpeakerIDs()){
            if(speakerID != speaker.getUserId()) return true;
        }
        return false;
    }


    /**
     * Create a new Event and return it
     * @param startTime the LocalDateTime of start time of Event
     * @param endTime the LocalDateTime of end time of Event
     * @param roomID ID of room that this Event is scheduled in
     * @param capacity maximum number of attendees allowed in this Event
     * @param name event's name
     * @return the Event object we created
     */
    public int createEvent(LocalDateTime startTime, LocalDateTime endTime, int roomID, String name, int capacity){
        return getEventManager().createEvent(startTime, endTime, roomID, name, capacity);
    }

    public boolean haveEnoughCapacity(int roomID, int capacity){
        int roomCapacity = getRoomManager().getRoomByID(roomID).getCapacity();
        return roomCapacity >= capacity;
    }

    /**
     * Reschedule an Event with given startTime, endTime and roomID
     * @param eventID the id of Event to be rescheduled
     * @param startTime new start time that the event will be rescheduled at
     * @param endTime new end time that the event will be rescheduled at
     * @param roomID id of new room that the event will be rescheduled in
     */
    public void rescheduleEvent(int eventID, LocalDateTime startTime, LocalDateTime endTime, int roomID){
        getEventManager().setStartTime(eventID, startTime);
        getEventManager().setEndTime(eventID, endTime);
        getEventManager().changeRoomID(roomID, eventID);
    }

    /**
     * Change the Capacity of Event
     * @param eventID the id of Event to be rescheduled
     * @param capacity the new capacity of the Event
     */
    public void changeCapacity(int eventID, int capacity){
        getEventManager().setCapacity(eventID, capacity);
    }

    /**
     * Cancel Event
     *  - Remove the EventID from all User signed up for the Event
     *  - Remove all the UserID from the Event
     *  - Remove the EventID from Room that the Event held
     *  - Remove EventID from the list events
     * @param eventID id of Event to be cancelled
     */
    public void cancelEvent(int eventID){
        removeEventFromUser(eventID);
        removeUserFromEvent(eventID);
        removeEventFromRoom(eventID);
        getEventManager().removeEvent(eventID);
    }

    /**
     * Remove the ID of Event which will be cancelled from Users attending the Event
     * @param eventID the id of Event to be cancelled
     * @return true if and only if the EventID is successfully removed from list of all Users
     */
    public boolean removeEventFromUser(int eventID){
        ArrayList<Integer> userIDs = getEventManager().getUserIDs(eventID);
        boolean removeAttendee;
        boolean removeSpeaker;
        boolean removeOrganizer;
        for (Integer userID : userIDs) {
            getEventManager().removeUserID(userID, eventID);
            removeAttendee = getAttendeeManager().removeEventID(eventID, userID);
            removeSpeaker = getSpeakerManager().removeEventID(eventID, userID);
            removeOrganizer = getOrganizerManager().removeEventID(eventID, userID);
            if(!removeAttendee && !removeSpeaker && !removeOrganizer){ //userID not valid
                return false;
            }
        }
        return true;
    }

    /**
     * Remove all Users who signed up for the event from the list
     * @param eventID the id of Event to be cancelled
     * @return true if and only if all UserIDs are successfully removed from the list
     */
    public boolean removeUserFromEvent(int eventID){
        ArrayList<Integer> userIDs = getEventManager().getUserIDs(eventID);
        for(Integer userID: userIDs){
            if(!getEventManager().removeUserID(userID, eventID)){
                return false;
            }
        }
        return true;
    }

    /**
     * Remove Event from Room
     * @param eventID id of the Event held
     */
    public void removeEventFromRoom(int eventID){
        int roomID = getEventManager().getRoomID(eventID);
        getRoomManager().removeEventID(roomID, eventID);
    }


    /**
     * run the organizer main menu
     * @return null
     */
    @Override
    public User run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Presenter.printOrganizermenu();
        try {
            String input = br.readLine();
            while (!input.equals("7")) {
                if (input.equals("1")) {
                    Presenter.viewAllEvents(viewAllEvents(), getEventManager(), getRoomManager());
                    this.runViewAllEvents();
                } else if (input.equals("2")) {
                    Presenter.viewMyEvents(viewMyEvents(), getEventManager(), getRoomManager());
                    runViewMyEvents();
                } else if (input.equals("3")) {
                    Presenter.viewContacts(getOrganizerManager().getContactList(getUser()), getAttendeeManager(),
                            getOrganizerManager(), getSpeakerManager());
                    runViewContacts();
                } else if (input.equals("4")) {
                    runManageAccount();
                }
                else if (input.equals("5"))
                {
                    runCreateAccounts();
                }
                else if (input.equals("6"))
                {
                    runAddRoom();
                }
                Presenter.printOrganizermenu();
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
     * run View All Events submenu of the organizer main menu
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void runViewAllEvents() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Presenter.print("1. Sign up for event\n2. Schedule an event\n3. Go back to the main menu");
        try{
            String input = br.readLine();
            while (!input.equals("3")) {
                if (input.equals("1")) {
                    Presenter.print("Please enter an event number: ");
                    String input2 = br.readLine();
                    int index = Integer.parseInt(input2);
                    signUp(index);
                }
                else if (input.equals("2"))
                {
                    Presenter.print("Enter the start time of your event (format year-mm-ddThour:minute:second)");
                    LocalDateTime startTime = LocalDateTime.parse(br.readLine());
                    Presenter.print("Enter the end time of your event (format year-mm-ddThour:minute:second)");
                    LocalDateTime endTime = LocalDateTime.parse(br.readLine());

                    Presenter.printRooms(getRoomManager().getRooms(), getRoomManager());
                    Presenter.print("Enter the room ID where event will occur");
                    int roomID = Integer.parseInt(br.readLine());
                    Presenter.print("Enter the name of the event");
                    String name = br.readLine();
                    Presenter.print("Enter the event capacity");
                    int capacity = Integer.parseInt(br.readLine());
                    while (capacity <= 0)
                    {
                        Presenter.print("Enter a valid event capacity (postive number)");
                        capacity = Integer.parseInt(br.readLine());
                    }
                    //Maybe print our speaker ID's
                    Presenter.printSpeakers(getSpeakerManager().getUsers(), getSpeakerManager());
                    Presenter.print("Enter the speaker ID for this event");

                    int speakerID = Integer.parseInt(br.readLine());
                    if(startTime.isBefore(endTime)){
                    scheduleSpeakerToEvent(speakerID, startTime, endTime, roomID, name, capacity);}
                    else{
                        Presenter.print("The start time of your event is after or equal to the end time!!");
                    }
                }
                Presenter.print("1. Sign up for event\n2. Schedule an event\n3. Go back to the main menu");
                input = br.readLine();
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option: ");
        }
        catch (NumberFormatException n) {
            Presenter.print("Please enter an integer value for the ID!!");
        }
        catch (DateTimeParseException d)
        {
            Presenter.print("Please format your time properly!");
        }
    }
    /**
     * run Create new Account submenu of the organizer main menu
     */
    public void runCreateAccounts()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Presenter.print("1. Create Speaker Account\n2. Go back to the main menu");
        try{
            String input = br.readLine();
            while (!input.equals("2")) {
                if (input.equals("1")) {
                    Presenter.print("Enter the name of the speaker");
                    String name = br.readLine();
                    Presenter.print("Enter the username of the speaker");
                    String username = br.readLine();
                    while (getAttendeeManager().hasUserName(username) || getSpeakerManager().hasUserName(username) ||
                            getOrganizerManager().hasUserName(username))
                    {
                        Presenter.print("Username already in use, enter a new username");
                        username = br.readLine();
                    }
                    Presenter.print("Enter new password");
                    String password = br.readLine();
                    createSpeaker(name, username, password);
                }
                Presenter.print("1. Create Speaker Account\n2. Go back to the main menu");
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
     * run Add New Room submenu of the organizer main menu
     */
    public void runAddRoom()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            Presenter.print("Enter the name of the room");
            String name = br.readLine();
            Presenter.print("Enter capacity of the room");
            int capacity = Integer.parseInt(br.readLine());
            enterRoom(name, capacity);

        } catch (IOException e) {
            Presenter.print("Please enter a valid option: ");
        }
        catch (NumberFormatException n) {
            Presenter.print("Please enter an integer value for the ID!!");
        }

    }
    /**
     * run View Contact List submenu of the organizer main menu
     */
    @Override
    public void runViewContacts() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Presenter.print("1. View chat history\n2. Message All Attendees\n3. Message All Speakers\n" +
                "4. Go back to the main menu");
        try {
            String input = br.readLine();
            while (!input.equals("4")) {
                if (input.equals("1")) {
                    Presenter.print("Please enter a friend number: ");
                    int index = Integer.parseInt(br.readLine());
                    runViewChat(index);
                }
                else if (input.equals("2")) {
                    Presenter.print("What should your message be:");
                    String input2 = br.readLine();
                    messageAll(getAttendeeManager(), input2);
                }
                else if (input.equals("3")) {
                    Presenter.print("What should your message be:");
                    String input2 = br.readLine();
                    messageAll(getSpeakerManager(), input2);
                }
                else{
                    Presenter.print("Please enter a valid option!");
                }
                Presenter.print("1. View chat history\n2. Message All Attendees\n3. Message All Speakers\n" +
                        "4. Go back to the main menu");
                input = br.readLine();
            }
        } catch (IOException e) {
            Presenter.print("Please enter a valid option: ");
        } catch (NumberFormatException n) {
            Presenter.print("Please enter an integer value for the ID");
        }
    }

}
