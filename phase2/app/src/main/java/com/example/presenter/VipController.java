package com.example.presenter;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.model.useCases.*;
import java.io.Serializable;
import java.util.List;

public class VipController extends AttendeeController implements Serializable {

    /**
     * Create an instance of AttendeeController with the given Managers
     *
     * @param am     the instance of <code>AttendeeManager</code> in the conference
     * @param om     the instance of <code>OrganizerManager</code> in the conference
     * @param sm     the instance of <code>SpeakerManager</code> in the conference
     * @param rm     the instance of <code>RoomManager</code> in the conference
     * @param em     the instance of <code>EventManager</code> in the conference
     * @param mm     the instance of <code>MessageManager</code> in the conference
     * @param userID is the ID value of user currently in program
     * @param view
     */
    public VipController(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm, EventManager em, MessageManager mm, VipManager vipm,
                         VipEventManager vipe, int userID, View view){
        super(am, om, sm, rm, em, mm, vipm, vipe, userID, view);
    }
    public String getType() {
        return "VIPController";
    }

    /**
     * Return the string representation of all the Events in the conference or message that
     * inform User on the keyboard if there is no Event in the conference yet
     *
     * @return a list of string representation of all non-Vip  and Vip events  in the conference in the format:
     *         eventID + "\t" + name + "\t" + startTime + "\t" + endTime + "\t" + roomName
     *         or message that inform User on the keyboard if there is no event in the conference yet
     */
    @Override
    // # TODO: Change from String representation
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

    /**
     * Return the list of all EventIDs the user is going to attend
     *
     * @return the list of all EventIDs the user is going to attend
     */
    @Override
    public String viewMyEvents() {
        List<Integer> eventIDs = getCurrentManager().getEventList(getUserID());
        if (eventIDs.size() == 0) {
            return "You haven't signed up for any event yet!";
        }
        return formatEvents(eventIDs);
    }

    /**
     * Return true if signed up successfully and false if not
     * Update the Attendee list in the given Event
     * Update the contact list of each Speaker host the given Event
     *
     * @param eventID the id of Event that attendee on the keyboard want to sign up for
     * 1. the given Event is not in the conference
     * 2. the Attendee on the keyboard has already signed up for the given event
     * 3. there is no vacancy in the given Event
     * 4. the given Event is conflicted with an Event that the Attendee on the keyboard signed up
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean signUp(int eventID) {
        boolean successful1 = VIPSignUp(eventID);
        if (successful1)
        {
            return true;
        }
        else
        {
            return super.signUp(eventID);
        }

    }

    /**
     * Sign up for a Vip Event
     *
     * @param eventID the ID of Event to be signed up for
     *
     * @return true iff successfully signed up
     */
    private boolean VIPSignUp(int eventID) {

        if (!getVipEventManager().idInList(eventID)){
            return false;
        }

        if (getCurrentManager().getEventList(getUser()).contains(eventID)){
            getView().pushMessage("You already signed up for this event.");
            return false;
        }

        if (getVipEventManager().getCapacity(eventID) - getVipEventManager().getUserIDs(eventID).size() <= 0){
            getView().pushMessage("The event is already full.");
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (isUserAvailable(getVipEventManager().getStartTime(eventID), getVipEventManager().getEndTime(eventID)))
            {
                //sign Attendee up for the event
                getCurrentManager().addEventID(eventID, getUser());
                getVipEventManager().addUserID(getUser(), eventID);
                getView().pushMessage("Successfully signed up!");
                return true;
            }
        }
        return false;
    }

}

