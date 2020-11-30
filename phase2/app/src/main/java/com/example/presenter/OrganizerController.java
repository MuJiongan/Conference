package com.example.presenter;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.conference.AttendeeMenu;
import com.example.model.entities.Event;
import com.example.model.entities.Room;
import com.example.model.entities.Speaker;
import com.example.model.entities.User;

import com.example.model.useCases.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrganizerController extends AttendeeController implements Serializable {


    public OrganizerController(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm,
                               EventManager em, MessageManager mm, int userID, View view, VipManager vm, VipEventManager vipEventM) {
        super(am, om, sm, rm, em, mm, userID, view, vm, vipEventM);

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
        for (User user : current.getUsers()) {
            sendMessage(current.getIDByUser(user), content);
        }

    }
    /**
     * return true if message with the given content is sent successfully, and update the information.
     * @param receiverID     the id of user that attendee on the keyboard wanted to send message to
     * @param messageContent the content of message that attendee on the keyboard wanted to send
     * @return return true if message with the given content is sent successfully
     */
    // Need to override because when an organizer sends a message, we might add that organizer to the receiver's
    // contact list
    @Override
    public boolean sendMessage(int receiverID, String messageContent) {
        if (super.sendMessage(receiverID, messageContent)) {
            int organizerID = getUser();
            // If this receiver is a speaker, add the organizer to the speaker's contact list
            if (getSpeakerManager().idInList(receiverID)) {


                // Check if the organizer already exists in the speaker's contact list
                if (!getSpeakerManager().getContactList(receiverID).contains(organizerID)) {
                    getSpeakerManager().addToContactsList(receiverID, organizerID);}
            }
            // If this receiver is an attendee, add the organizer to the attendee's contact list
            else if (getAttendeeManager().idInList(receiverID)) {


                // Check if the organizer already exists in the speaker's contact list
                if (!getAttendeeManager().getContactList(receiverID).contains(organizerID)) {
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
     * @param name     name of the room wanted to create
     * @param capacity capacity of the room wanted to create
     */
    public void enterRoom(String name, int capacity) {
        int newRoomID = getRoomManager().createRoom(name, capacity);
        getView().pushMessage("Room Succesfully added");
        //Maybe we need to check duplicate names
    }
    /**
     * Create new Speaker account
     * @param name     name of the speaker wanted to create
     * @param username username of the speaker wanted to create
     * @param password password of the speaker wanted to create
     * @return true if new Speaker account successfully created
     */
    public boolean createSpeaker(String name, String username, String password) {
        int speakerID = getNewID();
        boolean successful = getSpeakerManager().createUser(name, username, password, getNewID());
        // can't initialize contact list because the speaker has no talks to give for now
        // add this speaker to organizers and attendees contact list
        if (!successful) {
            return false;
        }
        // Add the speaker to the attendee's contact list
        for (int attendeeID : getAttendeeManager().getUserIDs()) {
            getAttendeeManager().addToContactsList(attendeeID, speakerID);
        }
        // Add the speaker to the organizer's contact list
        for (int organizerID : getOrganizerManager().getUserIDs()) {
            getOrganizerManager().addToContactsList(organizerID, speakerID);
        }
        getView().pushMessage("Speaker successfully created");
        return true;
    }

    /**
     * Schedule Speaker to a new Event
     * @param startTime the LocalDateTime of start time of Event
     * @param endTime   the LocalDateTime of end time of Event
     * @param roomID    ID of room that this Event is scheduled in
     * @param capacity  maximum number of attendees allowed in this Event
     * @param name      event's name
     * @return true if speakerID successfully added to the new Event
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean scheduleEvent(LocalDateTime startTime, LocalDateTime endTime,
                                 int roomID, String name, int capacity) {


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



        int eventID = getEventManager().createEvent(startTime, endTime, roomID, name, capacity);
        getRoomManager().scheduleEvent(roomID, eventID);
        getView().pushMessage("New Event Scheduled");
        return true;

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

        if (getEventManager().idInList(eventID)) {
            getView().pushMessage("Event ID doesn't exist!");
            return false;
        }
        // check if the speaker ID exists

        if (getSpeakerManager().idInList(speakerID)) {
            getView().pushMessage("Speaker doesn't exist!");
            return false;
        }
        LocalDateTime startTime = getEventManager().getStartTime(eventID);
        LocalDateTime endTime = getEventManager().getEndTime(eventID);

        // check if the speaker is available at the time
        if (!availableAtTime(speakerID, startTime, endTime)) {
            getView().pushMessage("The speaker is not available at the time");
            return false;
        }
        // check if the speaker is already in the event
        if (!getEventManager().addSpeakerID(speakerID, eventID)) {
            getView().pushMessage("You already added this speaker!");
            return false;
        }
        // update the speaker's and the attendees' contact list
        for (Integer userID : getEventManager().getUserIDs(eventID)) {
            // check if the speaker is already in their contact list
            updateSpeakerContactList(userID, speakerID);
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

            LocalDateTime existingStartTime = getEventManager().getStartTime(eventID);
            LocalDateTime existingEndTime = getEventManager().getEndTime(eventID);
            if (!checkTime(startTime, endTime, existingStartTime, existingEndTime))
                return false;
        }
        return true;
    }
    private boolean updateSpeakerContactList(int userID, int speakerID) {
        if (getAttendeeManager().idInList(userID)) {

            ArrayList<Integer> contacts = getAttendeeManager().getContactList(userID);
            // speaker already in their contact list
            if (!contacts.contains(speakerID)) {
                // speaker not in contact list, add it now
                getAttendeeManager().addToContactsList(userID, speakerID);
            }


            // add the userID to speaker
            User speaker = getSpeakerManager().getUserByID(speakerID);
            ArrayList<Integer> speakerContacts = getSpeakerManager().getContactList(speakerID);
            // userID not in speaker Contacts, good! now we add the userID
            if (!speakerContacts.contains(userID)) {
                getSpeakerManager().addToContactsList(speakerID, userID);

            }
            return true;
        }

        if (getOrganizerManager().idInList(userID)) {

            ArrayList<Integer> contacts = getOrganizerManager().getContactList(userID);
            // speaker already in their contact list
            if (!contacts.contains(speakerID)) {
                // speaker not in contact list, add it now
                getOrganizerManager().addToContactsList(userID, speakerID);
            }


            // add the userID to speaker

            ArrayList<Integer> speakerContacts = getSpeakerManager().getContactList(speakerID);
            // userID not in speaker Contacts, good! now we add the userID
            if (!speakerContacts.contains(userID)) {
                getSpeakerManager().addToContactsList(speakerID, userID);

            }
            return true;
        }
        // TODO: loop through the VIP usermanager
        return false;
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
        // return true if the endtime is in between
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
        ArrayList<Integer> events = getRoomManager().getRoomByID(roomID).getEventsScheduled();
        for (Integer eventID : events) {

            LocalDateTime existingStartTime = getEventManager().getStartTime(eventID);
            LocalDateTime existingEndTime = getEventManager().getEndTime(eventID);
            if (!checkTime(startTime, endTime, existingStartTime, existingEndTime)) return false;
        }
        return true;
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
                return getVIPManager().createUser(name, username, password, getNewID());
        }
        return false;
    }
}




