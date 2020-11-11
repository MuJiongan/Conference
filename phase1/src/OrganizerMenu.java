import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrganizerMenu extends AttendeeMenu implements UserController{
    public OrganizerMenu(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm, EventManager em, MessageManager mm, User user){
        super(am, om, sm, rm, em, mm, user);
    }

    public void messageAll(int eventID, String content)
    {
        Event event = getEventManager().getEventByID(eventID);
        for (int userID: event.getUserIDs())
        {
            sendMessage(userID, content);
        }
    }


    // Need to override because when an organizer sends a message, we might add that organizer to the receiver's contact list
    @Override
    public boolean sendMessage(int receiverID, String messageContent) {
        if (super.sendMessage(receiverID, messageContent)){
            Integer organizerID = getOrganizerManager().getIDByUser(getUser());
            // If this receiver is a speaker, add the organizer to the speaker's contact list
            if (getSpeakerManager().idInList(receiverID)){
                User speaker = getSpeakerManager().getUserByID(receiverID);

                // Check if the organizer already exists in the speaker's contact list
                if (!getSpeakerManager().getContactList(speaker).contains(organizerID)){
                getSpeakerManager().addToContactsList(speaker, organizerID);}
            }
            // If this receiver is a attendee, add the organizer to the attendee's contact list
            else if (getAttendeeManager().idInList(receiverID)){
                User attendee = getAttendeeManager().getUserByID(receiverID);

                // Check if the organizer already exists in the speaker's contact list
                if (!getAttendeeManager().getContactList(attendee).contains(organizerID)){
                    getAttendeeManager().addToContactsList(attendee, organizerID);}
            }
            // Don't know about the organizer. I assumed organizers aren't allowed to message each other
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Enter new Rooms into the System
     * @return true if room successfully enter
     */
    public boolean enterRoom(Room room){
        return getRoomManager().addRoom(room);
    }


    /**
     * Create new Speaker account
     * @return true if new Speaker account successfully created
     */
    public boolean createSpeaker(String name, String username, String password) {

        Speaker s = getSpeakerManager().createSpeaker(name, username, password);
        // can't initialize contact list because the speaker has no talks to give for now
        // add this speaker to organizers and attendees contact list
        boolean added = getSpeakerManager().addUser(s);
        int speakerID = getSpeakerManager().getIDByUser(s);
        if (added){
            // Add the speaker to the attendee's contact list
            for (User attendee: getAttendeeManager().getUsers()){
                getAttendeeManager().addToContactsList(attendee, speakerID);
            }
            // Add the speaker to the organizer's contact list
            for (User organizer: getOrganizerManager().getUsers()){
                getOrganizerManager().addToContactsList(organizer, speakerID);
            }
            Presenter.print("Speaker successfully created");
            return true;
        }
        else{
            Presenter.print("Something went wrong!");
            return false;
        }


    }


    /**
     * Schedule Speaker to an existing Event
     * @param speaker the Speaker to be scheduled
     * @param event the Event that the Speaker scheduled for
     * @return true if and only if time and room are both available and
     *                             speakerID could be successfully added to the existing Event
     */
    public boolean scheduleSpeakerToEvent(Speaker speaker, Event event){
        if(availableAtTime(speaker, event.getStartTime(), event.getEndTime())
                && availableInRoom(speaker, event.getRoomID(), event.getStartTime(), event.getEndTime())
                && getEventManager().addSpeakerID(speaker.getUserId(), event)){
            getSpeakerManager().addEventID(event.getEventID(), speaker);
            // initialize the speaker's contact list
            for(Integer userIDInEvent: getEventManager().getUserIDs(event)){
                if (!getSpeakerManager().getContactList(speaker).contains(userIDInEvent)){
                getSpeakerManager().addToContactsList(speaker, userIDInEvent);}
            }
            return true;
        }
        return false;
    }


    /**
     * Schedule Speaker to a new Event
     * @param speakerID the Speaker who is to be scheduled
     * @param startTime the LocalDateTime of start time of Event
     * @param endTime the LocalDateTime of end time of Event
     * @param roomID ID of room that this Event is scheduled in
     * @param capacity maximum number of attendees allowed in this Event
     * @return true if speakerID successfully added to the new Event
     */
    public boolean scheduleSpeakerToEvent(int speakerID, LocalDateTime startTime, LocalDateTime endTime,
                                          int roomID, String name, int capacity){
        User speaker = getSpeakerManager().getUserByID(speakerID);
        if (speaker == null){
            Presenter.print("Speaker ID doesn't exist.");
            return false;
        }
        Event event = createEvent(startTime, endTime, roomID, name, capacity);
        if(event != null
                && availableAtTime(speaker, event.getStartTime(), event.getEndTime())
                && availableInRoom(speaker, event.getRoomID(), event.getStartTime(), event.getEndTime())
                && getEventManager().addSpeakerID(speaker.getUserId(), event)){
            getSpeakerManager().addEventID(event.getEventID(), speaker);
            return true;
        }
        return false;
    }


    /**
     * Check whether a Speaker is available to be scheduled at specific time (avoiding double-booking a speaker)
     * @param speaker the Speaker who is to be scheduled
     * @param startTime the LocalDateTime of start time of Event
     * @param endTime the LocalDateTime of end time of Event
     * @return true if Speaker is available to speak at specific time
     */
    public boolean availableAtTime(User speaker, LocalDateTime startTime, LocalDateTime endTime){
        for(Integer eventID : getSpeakerManager().getEventList(speaker)){
            if(!checkTime(startTime, endTime, eventID)){
                return false;
            }
        }
        return true;
    }


    /**
     * Check whether event conflict with the given period of time
     * @param startTime start time of 1st pair of time
     * @param endTime end time of 1st pair of time
     * @param eventID event ID of 2nd event
     * @return true if and only if no conflict occur
     */
    private boolean checkTime(LocalDateTime startTime, LocalDateTime endTime, int eventID){
        LocalDateTime startTimeForNewEvent = getEventManager().getEventByID(eventID).getStartTime();
        LocalDateTime endTimeForNewEvent = getEventManager().getEventByID(eventID).getEndTime();
        boolean condition1 = (endTime.isAfter(startTimeForNewEvent))&&(endTime.isBefore(endTimeForNewEvent));
        boolean condition2 = (startTime.isAfter(startTimeForNewEvent))&&(startTime.isBefore(endTimeForNewEvent));
        return !condition1 && !condition2;
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
            if(checkTime(startTime, endTime, eventID) && hasSpeaker(speaker, eventID)) return false;
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
     * @return the Event object we created
     */
    public Event createEvent(LocalDateTime startTime, LocalDateTime endTime, int roomID, String name, int capacity){
        Event event = new Event(startTime, endTime, roomID, name, capacity);
        if(getEventManager().addEvent(event)){
            return event;
        }
        return null;
    }


/*
    * remove Speakers, Attendees, Organizers from Event
    * remove Event from list

    public boolean cancelEvents(Event event){
        ArrayList<Integer> Speakers = event.getSpeakerIDs();
        for(Integer speakerID: Speakers) {
            Speaker speaker = (Speaker) getSpeakerManager().getUserByID(speakerID);
            getSpeakerManager().removeEventID(event.getEventID(), speaker);
        }
        return true;
    }


    public boolean rescheduleEvents(){
    }
*/


    @Override
    public User run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("1. View All Events \n2. View your Events \n3.View events with vacancy " +
                "\n4. Message an Attendee \n5. Message Attendees in Event \n6. Enter New Room " +
                "\n7. Create Speaker Account \n8.Schedule Speaker \n9.Cancel enrollment(s)" +
                "\n10.Sign up events\n10.Exit");
        try{
            String input = br.readLine();
            while (!input.equals("10"))
            {
                if (input.equals("1"))
                {
                    this.viewAllEvents();
                }
                else if (input.equals("4"))
                {
                    runMessage();
                }
                System.out.println("1. View All Events \n2. View your Events \n3.View events with vacancy " +
                        "\n4. Message an Attendee \n5. Message Attendees in Event \n6. Enter New Room " +
                        "\n7. Create Speaker Account \n8.Schedule Speaker \n9.Cancel enrollment(s)" +
                        "\n10.Sign up events\n10.Exit");
                input = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Please enter a valid option");
            return null;
        }

        System.out.println("See you again soon");
        return null;
    }

    public void runMessage()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Presenter.print("Please print the ID of who you would like to message");
        try {
            String userID = br.readLine();
            int index = Integer.parseInt(userID) - 1;

        } catch (IOException e) {
            System.out.println("Please enter a valid option");
        }

    }
}
