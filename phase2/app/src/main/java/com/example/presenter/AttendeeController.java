package com.example.presenter;

import android.os.Build;


import androidx.annotation.RequiresApi;
import com.example.model.entities.Event;

import com.example.model.useCases.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AttendeeController extends UserController {

    /**
     * Create an instance of AttendeeController with the given Managers
     * @param am the instance of <code>AttendeeManager</code> in the conference
     * @param om the instance of <code>OrganizerManager</code> in the conference
     * @param sm the instance of <code>SpeakerManager</code> in the conference
     * @param rm the instance of <code>RoomManager</code> in the conference
     * @param em the instance of <code>EventManager</code> in the conference
     * @param mm the instance of <code>MessageManager</code> in the conference
     * @param userID is the ID value of user currently in program
     */
    public AttendeeController(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm, EventManager em, MessageManager mm, VipManager vipm,
                              VipEventManager vipe, int userID, View view)
    {
        super(am, om, sm, rm, em, mm, vipm, vipe, userID, view);

    }

    /**
     * return true if signed up successfully, and false if not and update the attendee list in the
     * given event and update the contact list of each speaker host the given event
     * @param eventID the id of event that attendee on the keyboard want to sign up for
     * 1. the given event is not in the conference
     * 2. attendee on the keyboard has already signed up for the given event
     * 3. there is no vacancy in the given event
     * 4. the given event is conflicted with an event that the attendee on the keyboard signed up
     */
     @RequiresApi(api = Build.VERSION_CODES.O)
     public boolean signUp(int eventID) {
         if (!getEventManager().idInList(eventID)){
             getView().pushMessage("That Event does not exist!");
             return false;
         }

         if (getCurrentManager().getEventList(getUser()).contains(eventID)){
             getView().pushMessage("You already signed up for this event.");
             return false;
         }
         if (getEventManager().getCapacity(eventID) - getEventManager().getUserIDs(eventID).size() <= 0){
             getView().pushMessage("The event is already full.");
             return false;
         }
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             if (isUserAvailable(getEventManager().getStartTime(eventID), getEventManager().getEndTime(eventID)))
             {
                 //sign Attendee up for the event

                 getCurrentManager().addEventID(eventID, getUser());
                 getEventManager().addUserID(getUser(), eventID);
                 getView().pushMessage("Successfully signed up!");
                 return true;
             }
         }
         return false;
     }
     @RequiresApi(api = Build.VERSION_CODES.O)
     public boolean isUserAvailable(LocalDateTime startTime, LocalDateTime endTime)
     {
         for (int currentEvents: getCurrentManager().getEventList(getUser()))
         {
             LocalDateTime start = getEventManager().getStartTime(currentEvents);
             LocalDateTime end = getEventManager().getEndTime(currentEvents);
             if (checkTime(start, end, startTime, endTime))
             {
                 return false;
             }
         }
         return true;
     }
     @RequiresApi(api = Build.VERSION_CODES.O)
     private boolean checkTime(LocalDateTime startTime, LocalDateTime endTime, LocalDateTime newStartTime, LocalDateTime newEndTime)
     {
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

     public boolean cancelEnrollment(int eventID)
     {
         if (getEventManager().getEventByID(eventID) != null)
         {
             if (getCurrentManager().getEventList(getUser()).contains(eventID))
             {
                 //#TODO: consider VIP events case
                 int position = getCurrentManager().getEventList(getUser()).indexOf(eventID);
                 getCurrentManager().getEventList(getUser()).remove(position);
                 int index =  getEventManager().getUserIDs(eventID).indexOf(getUser());
                 getEventManager().getUserIDs(eventID).remove(index);
                 getView().pushMessage("Cancellation Successful");
                 return true;
             }
             getView().pushMessage("Enter an event you have signed up for!");
             return false;
         }
         getView().pushMessage("Enter a valid event ID");
         return false;
     }/**
     * Return the string representation of all the events in the conference
     * @return a list of string representation of all events  in the given list in the format:
     * eventID + "\t" + name + "\t" + startTime + "\t" + endTime + "\t" + roomName
     */
     public String formatEvents(List<Integer> eventIDs){
         String output ="";
         for (int ID: eventIDs) {
             if (getEventManager().idInList(ID)) {
                 output = output + ID + ".\t" + getEventManager().getName(ID) + "\t" + getEventManager().getStartTime(ID) + "\t" + getEventManager().getEndTime(ID)
                     + "\t" + getRoomManager().getRoomName(getEventManager().getRoomID(ID)) + "\t" + getEventManager().getCapacity(ID)
                     + "\n";
             }
             else if (getVipEventManager().idInList(ID))
             {
                 output = output + ID + ".\t" + getVipEventManager().getName(ID) + "\t" + getVipEventManager().getStartTime(ID) + "\t" + getVipEventManager().getEndTime(ID)
                         + "\t" + getRoomManager().getRoomName(getVipEventManager().getRoomID(ID)) + "\t" + getVipEventManager().getCapacity(ID)
                         + "\n";
             }
         }
         return output;
     }
    /**
     * Return the string representation of all the events in the conference or a String message which informs user on the keyboard
     * if there is no event in the conference yet
     * @return return a list of string representation of all non-Vip events  in the conference in the format:
     * eventID + "\t" + name + "\t" + startTime + "\t" + endTime + "\t" + roomName
     * or a String message which inform user on the keyboard if there is no event in the conference yet
     */
    public String viewAllEvents() {
        List<Integer> eventIDs = getEventManager().getEvents();
        if (eventIDs.size() == 0) {
            return "There are no current events at the moment! Check again soon";
        }
        return formatEvents(eventIDs);
    }

//    /**
//     * Return the string representation of all the events in the conference
//     * @return a list of string represetation of all non-Vip events  in the conference in the format:
//     * eventID + "\t" + name + "\t" + startTime + "\t" + endTime + "\t" + roomName
//     */
//    public List<String> viewAllEvents(){
//        List<String> allStringRep = getEventManager().getAllEvents();
//        return allStringRep;}

    public String getType(){
        return "AttendeeController";
    }

    /**
     * Return a hashmap of friend ids to number of common events.
     *
     * @return a hashmap of friend ids to number of common events, with key of the id of the friend
     * and value of the number of common events the friend has as the current user
     */
    public HashMap<Integer, Integer> friendToNumOfCommonEvent() {
        // create a new hash map
        HashMap<Integer, Integer> friendToNumOfCommonEvent = new HashMap<>();
        // get a list of all attendees, vip attendees, and organizers
        List<Integer> contact = new ArrayList<>();
        contact.addAll(super.getAttendeeManager().getUserIDs());
        contact.addAll(super.getOrganizerManager().getUserIDs());
        contact.addAll(super.getVipManager().getUserIDs());
        // remove the user himself
        int index = contact.indexOf(getUserID());
        contact.remove(index);
        // loop through all the events the current user has signed up for
        int numEvents = getCurrentManager().getEventList(getUserID()).size();
        if (numEvents != 0) {
            for (Integer eventID : super.getCurrentManager().getEventList(super.getUserID())) {
                // loop through all contacts in the list
                for (Integer friendID : contact) {
                    // add 1 to the corresponding friend key in the hash map if the friend also has
                    // the event
                    List<Integer> friendEvents = getFriendEvents(friendID);
                    if (friendEvents.contains(eventID)) {
                        if (!friendToNumOfCommonEvent.containsKey(eventID)) {
                            friendToNumOfCommonEvent.put(friendID, 1);
                        } else {
                            int curr = friendToNumOfCommonEvent.get(friendID);
                            friendToNumOfCommonEvent.put(friendID, curr + 1);
                        }
                    }
                }
            }
        }
        else
        {
            for (int id: contact)
            {
                friendToNumOfCommonEvent.put(id, 0);
            }
        }
        return friendToNumOfCommonEvent;
    }

    /**
     * Return a list of events the friend is attending given the friend's id.
     *
     * @param friendID the friend id
     * @return a list of events the friend is attending given the friend's id.
     */
    public List<Integer> getFriendEvents(int friendID) {
        if (super.getOrganizerManager().getUserIDs().contains(friendID)) {
            return super.getOrganizerManager().getEventList(friendID);
        } else if (super.getAttendeeManager().getUserIDs().contains(friendID)) {
            return super.getAttendeeManager().getEventList(friendID);
        } else if (super.getVipManager().getUserIDs().contains(friendID)) {
            return super.getVipManager().getEventList(friendID);
        } else {
            return super.getSpeakerManager().getEventList(friendID);
        }
    }

    public HashMap<String, List<String>> viewRecommendedFriend() {
        HashMap<Integer, Integer> friendToNumOfCommonEvent = friendToNumOfCommonEvent();
        HashMap<String, List<String>> viewRecommendedFriend = new HashMap<>();
        for (int friendID : friendToNumOfCommonEvent.keySet()) {
            String numberOfSameEvent = String.valueOf(friendToNumOfCommonEvent.get(friendID));
            if (viewRecommendedFriend.containsKey(numberOfSameEvent)) {
                viewRecommendedFriend.get(numberOfSameEvent).add(friendID + "\t" + super.getUserName(friendID));
            } else {
                List<String> friendList = new ArrayList<>();
                friendList.add(friendID + "\t" + super.getUserName(friendID));
                viewRecommendedFriend.put(numberOfSameEvent, friendList);
            }
        }
        return viewRecommendedFriend;
    }



}