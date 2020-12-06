package com.example.presenter;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.model.useCases.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.*;

public class OrganizerController extends AttendeeController implements Serializable {

    public OrganizerController(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm,
                               EventManager em, MessageManager mm, VipManager vipm,
                               VipEventManager vipe, int userID, View view) {
        super(am, om, sm, rm, em, mm,  vipm, vipe, userID, view);
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
        for (int userid : current.getUserIDs()) {
            sendMessage(userid, content);
        }
        getView().pushMessage("Messages sent");
    }

    /**
     * Enter new Rooms into the System
     * @param name     name of the room wanted to create
     * @param capacity capacity of the room wanted to create
     */
    public void enterRoom(String name, int capacity) {
        getRoomManager().createRoom(name, capacity);
        getView().pushMessage("Room Succesfully added");
        //Maybe we need to check duplicate names

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean signUp(int eventID) {
        if (getVipEventManager().idInList(eventID)){
            getView().pushMessage("You can't sign up for a VIP event.");

        }else{
        return super.signUp(eventID);}
        return false;
    }

    /**
     * Schedule Speaker to a new Event
     * @param startTime the LocalDateTime of start time of Event
     * @param endTime   the LocalDateTime of end time of Event
     * @param roomID    ID of room that this Event is scheduled in
     * @param capacity  maximum number of attendees allowed in this Event
     * @param name      event's name
     * @param em type of manager of event we want to create
     * @return true if speakerID successfully added to the new Event
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean scheduleEvent(LocalDateTime startTime, LocalDateTime endTime,
                                 int roomID, String name, int capacity, EventManager em) {

        if (!getRoomManager().idInList(roomID)) {
            getView().pushMessage("RoomID doesn't exist.");
            return false;
        }
        if (!haveEnoughCapacity(roomID, capacity)) {
            getView().pushMessage("Room doesn't have enough capacity.");
            return false;
        }
        if (!availableInRoom(roomID, startTime, endTime)) {
            getView().pushMessage("The room you entered is occupied at the time");
            return false;
        }
        int eventID = em.createEvent(startTime, endTime, roomID, name, capacity, getEventID());
        getRoomManager().scheduleEvent(roomID, eventID);
        getView().pushMessage("New Event Scheduled");
        return true;
    }
    /**
     * Return the next ID that is going to be assigned to the new Event created
     *
     * @return the next ID that is going to be assigned to the new Event created
     */
    public int getEventID() {
        int size = getEventManager().getEvents().size() + getEventManager().getNumOfCancelledEvents() +
                getVipEventManager().getEvents().size() + getVipEventManager().getNumOfCancelledEvents();
        return size + 1;
    }

    /**
     * Assign Speaker to an existing Event
     * @param speakerID the Speaker who is to be scheduled
     * @param eventID   the eventID of which event
     * @return true if speakerID successfully added to the existing Event
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean assignSpeaker(int speakerID, int eventID) {
        // check if the event ID exists
        if (!getEventManager().idInList(eventID) && !getVipEventManager().idInList(eventID)) {
            getView().pushMessage("Event ID doesn't exist!");
            return false;
        }
        // check if the speaker ID exists
        if (!getSpeakerManager().idInList(speakerID)) {
            getView().pushMessage("Speaker doesn't exist!");
            return false;
        }

        LocalDateTime startTime;
        LocalDateTime endTime;
        if (getEventManager().idInList(eventID)){
            startTime = getEventManager().getStartTime(eventID);
            endTime = getEventManager().getEndTime(eventID);
        }else if(getVipEventManager().idInList(eventID)){
            startTime = getVipEventManager().getStartTime(eventID);
            endTime = getVipEventManager().getEndTime(eventID);
        }else{
            getView().pushMessage("An error occurred while reading the time.");
            return false;
        }
        // check if the speaker is available at the time
        if (!availableAtTime(speakerID, startTime, endTime)) {
            getView().pushMessage("The speaker is not available at the time");
            return false;
        }
        // check if the speaker is already in the event
        if (getEventManager().idInList(eventID) && !getEventManager().addSpeakerID(speakerID, eventID)) {
            getView().pushMessage("You already added this speaker!");
            return false;
        }
        if (getVipEventManager().idInList(eventID) && !getVipEventManager().addSpeakerID(speakerID, eventID)) {
            getView().pushMessage("You already added this speaker!");
            return false;
        }

        getSpeakerManager().addEventID(eventID, speakerID);
        getView().pushMessage("Speaker assigned");
        return true;
    }

    /**
     * Check whether a Speaker is available to be scheduled at specific time (avoiding double-booking a speaker)
     * @param speakerID the Speaker who is to be scheduled
     * @param startTime the LocalDateTime of start time of Event
     * @param endTime   the LocalDateTime of end time of Event
     * @return true if Speaker is available to speak at specific time
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean availableAtTime(int speakerID, LocalDateTime startTime, LocalDateTime endTime) {
        for (Integer eventID : getSpeakerManager().getEventList(speakerID)) {
            LocalDateTime existingStartTime;
            LocalDateTime existingEndTime;
            if (getEventManager().idInList(eventID)){
                existingStartTime = getEventManager().getStartTime(eventID);
                existingEndTime = getEventManager().getEndTime(eventID);
            }else{
                existingStartTime = getVipEventManager().getStartTime(eventID);
                existingEndTime = getVipEventManager().getEndTime(eventID);
            }

            if (!checkTime(startTime, endTime, existingStartTime, existingEndTime))
                return false;
        }
        return true;
    }

    /**
     * Check whether event conflict with the given period of time
     * @param startTime    start time of 1st pair of time
     * @param endTime      end time of 1st pair of time
     * @param newStartTime start time of 2nd pair of time
     * @param newEndTime   end time of 2nd pair of time
     * @return true if and only if no conflict occur
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean checkTime(LocalDateTime startTime, LocalDateTime endTime, LocalDateTime newStartTime,
                              LocalDateTime newEndTime) {
        // endTime is between the newStartTime and newEndTime
        // return true if the end time is in between
        boolean condition1 = (!endTime.isAfter(newEndTime)) && (!endTime.isBefore(newStartTime));
        boolean condition2 = (!startTime.isAfter(newEndTime)) && (!startTime.isBefore(newStartTime));
        boolean condition3 = (!newEndTime.isAfter(endTime)) && (!newEndTime.isBefore(startTime));
        boolean condition4 = (!newStartTime.isAfter(endTime)) && (!newStartTime.isBefore(startTime));

        // if one of the conditions fails, return false
        if (condition1 || condition2 || condition3 || condition4) {
            return false;
        }
        return true;
    }

    /**
     * Check whether a Speaker is available to be scheduled in specific room (avoiding double-booking a room)
     * @param roomID    ID of room that this Event is scheduled in
     * @param startTime start time of event
     * @param endTime   end time of event
     * @return true if Speaker is available to speak in specific room
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean availableInRoom(int roomID, LocalDateTime startTime, LocalDateTime endTime) {
        List<Integer> events = getRoomManager().getRoomByID(roomID).getEventsScheduled();
        for (Integer eventID : events) {
            if (getEventManager().idInList(eventID)){
            LocalDateTime existingStartTime = getEventManager().getStartTime(eventID);
            LocalDateTime existingEndTime = getEventManager().getEndTime(eventID);
            if (!checkTime(startTime, endTime, existingStartTime, existingEndTime)) return false;}
            else{
                LocalDateTime existingStartTime = getVipEventManager().getStartTime(eventID);
                LocalDateTime existingEndTime = getVipEventManager().getEndTime(eventID);
                if (!checkTime(startTime, endTime, existingStartTime, existingEndTime)) return false;
            }
        }
        return true;
    }
    /**
     * Return the string representation of all the events in the conference or message that inform user on the keyboard
     * if there is no event in the conference yet
     * @return a list of string representation of all non-Vip  and Vip events  in the conference in the format:
     * eventID + "\t" + name + "\t" + startTime + "\t" + endTime + "\t" + roomName
     * or message that inform user on the keyboard if there is no event in the conference yet
     */
    @Override
    public String viewAllEvents()
    {
        List<Integer> nonVipEventIDs = getEventManager().getEvents();
        List<Integer> allEventIDs = getVipEventManager().getEvents();
        allEventIDs.addAll(nonVipEventIDs);
        if (allEventIDs.size() == 0) {
            return "There are no current events at the moment! Check again soon";
        }
        return formatEvents(allEventIDs);
    }

    private boolean haveEnoughCapacity(int roomID, int capacity) {
        int roomCapacity = getRoomManager().getCapacity(roomID);
        return roomCapacity >= capacity;
    }

    public String getType() {
        return "OrganizerController";
    }

    /**
     * Return true if and only if an give type of User is successfully created
     * @param name     name of the user
     * @param username username of the user
     * @param password password associated with the user
     * @return Return true if and only if an Attendee is successfully created.
     */
    public boolean createUser(String name, String username, String password, String type) {
        // invariant: type is one of "Organizer", "Speaker", "Attendee", "Vip"
        switch (type) {
            case "Organizer":
                return getOrganizerManager().createUser(name, username, password, getNewID());
            case "Speaker":
                return getSpeakerManager().createUser(name, username, password, getNewID());
            case "Attendee":
                return getAttendeeManager().createUser(name, username, password, getNewID());
            case "Vip":
                return getVipManager().createUser(name, username, password, getNewID());
        }
        return false;
    }

//    /**
//     * Reschedule an Event with given startTime, endTime and roomID
//     * @param eventID the id of Event to be rescheduled
//     * @param startTime new start time that the event will be rescheduled at
//     * @param endTime new end time that the event will be rescheduled at
//     * @param roomID id of new room that the event will be rescheduled in
//     */
//    public void rescheduleEvent(int eventID, LocalDateTime startTime, LocalDateTime endTime, int roomID){
//        getEventManager().setStartTime(eventID, startTime);
//        getEventManager().setEndTime(eventID, endTime);
//        getEventManager().changeRoomID(roomID, eventID);
//        //TODO: CHECK THE CONDITION
//    }

//    /**
//     * Change the Capacity of Event
//     * @param eventID the id of Event to be rescheduled
//     * @param capacity the new capacity of the Event
//     */
//    public void changeCapacity(int eventID, int capacity){
//        getEventManager().setCapacity(eventID, capacity);
//    }

    /**
     * Cancel Event
     *  - Remove the EventID from all User signed up for the Event
     *  - Remove all the UserID from the Event
     *  - Remove the EventID from Room that the Event held
     *  - Remove EventID from the list events
     * @param eventID id of Event to be cancelled
     */
    public void cancelEvent(int eventID){
        if (getEventManager().getEvents().contains(eventID)) {
            removeEventFromUser(eventID, getEventManager());
            removeEventFromRoom(eventID, getEventManager());
            getEventManager().removeEvent(eventID);
            getEventManager().setNumOfCancelledEvents();
        }
        else if (getVipEventManager().getEvents().contains(eventID)){
            removeEventFromUser(eventID, getVipEventManager());
            removeEventFromRoom(eventID, getVipEventManager());
            getVipEventManager().removeEvent(eventID);
            getVipEventManager().setNumOfCancelledEvents();

        }
        else {
            getView().pushMessage("That event ID does not exist!");
        }
       // else if (getVipEventManager().ge)


    }
    public String showSpeaker(){
        String finalString = "";
        for (int speakerID: getSpeakerManager().getUserIDs()){
            finalString = finalString + speakerID + ": " + getSpeakerManager().getnameById(speakerID) + "\n";


        }
        return finalString;
    }
    

    /**
     * Remove the ID of Event which will be cancelled from Users attending the Event
     * @param eventID the id of Event to be cancelled
     * @return true if and only if the EventID is successfully removed from list of all Users
     */
    private boolean removeEventFromUser(int eventID, EventManager em){
        //remove speakers
        List<Integer> speakerIDs = em.getSpeakerIDs(eventID);
        for (int speaker: speakerIDs)
        {
            getSpeakerManager().removeEventID(eventID, speaker);
        }
        //remove attendees
        List<Integer> userIDs = em.getUserIDs(eventID);
        boolean removeAttendee;
        boolean removeOrganizer;
        boolean removeVIP;
        for (Integer userID : userIDs) {
            em.removeUserID(userID, eventID);
            removeAttendee = getAttendeeManager().removeEventID(eventID, userID);
            removeVIP =getVipManager().removeEventID(eventID, userID);
            removeOrganizer = getOrganizerManager().removeEventID(eventID, userID);
            if(!removeAttendee && !removeVIP && !removeOrganizer){ //userID not valid
                return false;
            }
        }
        return true;
    }

//    /**
//     * Remove all Users who signed up for the event from the list
//     * @param eventID the id of Event to be cancelled
//     * @return true if and only if all UserIDs are successfully removed from the list
//     */
//    private boolean removeUserFromEvent(int eventID){
//        List<Integer> userIDs = getEventManager().getUserIDs(eventID);
//        for(Integer userID: userIDs){
//            if(!getEventManager().removeUserID(userID, eventID)){
//                return false;
//            }
//        }
//        return true;
//    }

    /**
     * Remove Event from Room
     * @param eventID id of the Event held
     */
    private void removeEventFromRoom(int eventID, EventManager em){
        int roomID = em.getRoomID(eventID);
        getRoomManager().removeEventID(roomID, eventID);
    }

    /**
     * Return a hashmap contains statistics in the conference
     * @return a hash map where the keys are the following strings and values are data indicated by the corresponding
     * key.
     * keys are totalNumOfUsers","totalNumOfMessages","totalNumOfRooms","totalNumOfEvents"
     */
    public String systemStats(){

        int totalNumOfUsers = 0;
        totalNumOfUsers += getAttendeeManager().numberOfUser();
        totalNumOfUsers += getOrganizerManager().numberOfUser();
        totalNumOfUsers += getSpeakerManager().numberOfUser();
        totalNumOfUsers += getVipManager().numberOfUser();

        int totalNumOfMess = getMessageManager().getNumOfMess();

        int totalNumOfRooms = getRoomManager().getNumOfRooms();

        int totalNumOfEvents  = getEventManager().getNumOfEvents();
        totalNumOfEvents += getVipEventManager().getNumOfEvents();

        String allStats = "Total Number of Users:\t" + totalNumOfUsers + "\n" + "Total Number of Messages:\t" + totalNumOfMess
                + "\n" + "Total Number of Events:\t" + totalNumOfEvents + "\n" + "Total Number of Rooms:\t" + totalNumOfRooms;
        return allStats;
    }

    /**
     * Return a list of three or less id of events that are most of people in the conference attend
     * @return return a list of id of events that are most popular in the conference.
     */
    // TODO: look for exception of addAll method
    public HashMap<Integer, ArrayList<Integer>> topThreeEvents(){
        HashMap<Integer, ArrayList<Integer>> topThreeEvents = new HashMap<>();
        List<Integer> allEvents = new ArrayList<>();
        List<Integer> normalEvents = getEventManager().getEvents();
        allEvents.addAll(normalEvents);
        allEvents.addAll(getVipEventManager().getEvents());

        int numOfAllEvents = allEvents.size();

        if (numOfAllEvents == 0){
            return topThreeEvents;
        }
        else{
            for (int eventId: allEvents){
                if (normalEvents.contains(eventId)){
                    int numOfAttendee = getEventManager().getNumOfAttendee(eventId);
                    if (topThreeEvents.keySet().contains(numOfAllEvents)){
                            topThreeEvents.get(numOfAttendee).add(eventId);
                        }
                    else{
                            topThreeEvents.put(numOfAttendee, new ArrayList<>());
                            topThreeEvents.get(numOfAttendee).add(eventId); // TODO: any problems?
                        }
                }
                else{
                    int numOfVipAttendee = getVipEventManager().getNumOfAttendee(eventId);
                    if (topThreeEvents.keySet().contains(numOfAllEvents)){
                            topThreeEvents.get(numOfVipAttendee).add(eventId);
                    }
                    else{
                        topThreeEvents.put(numOfVipAttendee, new ArrayList<>());
                        topThreeEvents.get(numOfVipAttendee).add(eventId); /// TODO: any problems?
                    }
                }
            }
        }
        return topThreeEvents;

    }

    /**
     * return a sorted set of key
     * @param keySet a set of key in a Hashmap
     * @return a sorted set of key, the set is sorted in an increasing order
     */
    public ArrayList<Integer> sortedKeys(ArrayList<Integer> keySet){
            Collections.sort(keySet);
            return keySet;
    }

    /**
     * return the name of event given the id of an event in the conference
     * @param eventId the id of an event in the conference
     * @return the name of event given the id of an event in the conference
     */
    public String getEventName(int eventId){
        if (getVipEventManager().getEvents().contains(eventId)){
            return getVipEventManager().getName(eventId);
        }
        else{
            return getEventManager().getName(eventId);
        }

    }
}






