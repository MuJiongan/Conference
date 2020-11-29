package com.example.presenter;

import com.example.model.useCases.*;

import java.util.List;

public class VipController extends AttendeeController {
    /**
     * Store the instance of VipManager in the conference
     */
    private VipManager vipm;
    /**
     * Store the instance of VipEventManager in the conference
     */
    private VipEventManager vipEventM;

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
    public VipController(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm, EventManager em,
                         MessageManager mm, int userID, View view, VipManager vipm, VipEventManager vipEventM) {
        super(am, om, sm, rm, em, mm, userID, view);
        this.vipm = vipm;
        this.vipEventM = vipEventM;
    }

    /**
     * Return the instance of VipManager in the conference
     * @return the instance of VipManager in the conference
     */
    public VipManager getVipManager(){return vipm;}

    /**
     * Return the string representation of all the events in the conference
     * @return a list of string represetation of all non-Vip  and Vip events  in the conference in the format:
     * eventID + "\t" + name + "\t" + startTime + "\t" + endTime + "\t" + roomName
     */
    @Override
    public List<String> viewAllEvents()
    {
        List<String> mergedList = getEventManager().getAllEvents();
        mergedList.addAll(vipEventM.getAllEvents());
        return mergedList;
    }
}

