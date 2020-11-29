package com.example.presenter;

import com.example.model.entities.Event;
import com.example.model.entities.Message;

import com.example.model.useCases.*;

import java.io.Serializable;
import java.util.ArrayList;

public class SpeakerController extends UserController implements Serializable{

    public SpeakerController(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm, EventManager em, MessageManager mm, int userID, View view){
        super(am, om, sm, rm, em, mm, userID, view);

    }


    private boolean canSend(int receiverID)
    {
        ArrayList<Integer> talks = this.getSpeakerManager().getEventList(this.getUser());
        for (int x: talks)
        {

            ArrayList<Integer> people = this.getEventManager().getUserIDs(x);
            if (people.contains(receiverID))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the message to the messages hashmaps of both the receiver and the sender, returns true iff successful
     * @param receiverID ID of the other user the current user is sending message to
     * @param messageContent content of the message
     * @return true iff the message is sent successfully
     */
    public boolean sendMessage(int receiverID, String messageContent)
    {
        if (canSend(receiverID))
        {
            int messageID = getMessageManager().createMessage(messageContent, getUser(), receiverID);

            //Add message to receiver's hashmap
            if (getAttendeeManager().idInList(receiverID)) {
                getAttendeeManager().addMessageID(messageID, receiverID, getUser());
            }
            else if (getOrganizerManager().idInList(receiverID))
            {
                getOrganizerManager().addMessageID(messageID, receiverID, getUser());
            }
            //add message to this user's hashmap
            super.sendMessage(receiverID, messageID);

            getView().pushMessage("Messages sent");
            return true;
        }
        else
        {
            getView().pushMessage("Receiver ID doesn't exist or you cannot message them");
            return false;
        }
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
            getView().pushMessage("Messages sent");
        }
    }

    public String getType(){
        return "SpeakerController";
    }




}

