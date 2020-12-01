package com.example.presenter;

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
     * Return the string representation of all the events in the conference or message that inform user on the keyboard
     * if there is no event in the conference yet
     * @return a list of string representation of all non-Vip  and Vip events  in the conference in the format:
     * eventID + "\t" + name + "\t" + startTime + "\t" + endTime + "\t" + roomName
     * or message that inform user on the keyboard if there is no event in the conference yet
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
}

