package com.example.presenter;

import com.example.model.entities.Event;
import com.example.model.entities.Message;

import com.example.model.useCases.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpeakerController extends UserController implements Serializable{

    public SpeakerController(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm, EventManager em, MessageManager mm, VipManager vipm,
                             VipEventManager vipe, int userID, View view){
        super(am, om, sm, rm, em, mm, vipm, vipe, userID, view);

    }


    private boolean canSend(int receiverID)
    {
        List<Integer> talks = this.getSpeakerManager().getEventList(this.getUser());
        for (int x: talks)
        {
            List<Integer> people = this.getEventManager().getUserIDs(x);
            if (people.contains(receiverID))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the message to the messages hashmaps of both all the receivers in the given event and the sender
     * @param eventID ID of the event the user is sending all attendees message to
     * @param content content of the message
     */
    public void messageAll(int eventID, String content)
    {
        for (int userID: getEventManager().getUserIDs(eventID))
        {
            sendMessage(userID, content);
        }
        getView().pushMessage("Messages sent");
    }

    public String getType(){
        return "SpeakerController";
    }

}

