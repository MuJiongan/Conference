package com.example.presenter;
import com.example.model.useCases.*;
import java.io.Serializable;


public class SpeakerController extends UserController implements Serializable{

    /**
     * Create an instance of SpeakerController with the given Managers' instance
     * @param am the instance of <code>AttendeeManager</code> in the conference
     * @param om the instance of <code>OrganizerManager</code> in the conference
     * @param sm the instance of <code>SpeakerManager</code> in the conference
     * @param rm the instance of <code>RoomManager</code> in the conference
     * @param em the instance of <code>EventManager</code> in the conference
     * @param mm the instance of <code>MessageManager</code> in the conference
     * @param vipm the instance of <code>VipManager</code> in the conference
     * @param vipe the instance of <code>VipEventManager</code> in the conference
     * @param userID the ID of User currently in program
     * @param view the View of the application
     */
    public SpeakerController(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm,
                             EventManager em, MessageManager mm, VipManager vipm, VipEventManager vipe,
                             int userID, View view){
        super(am, om, sm, rm, em, mm, vipm, vipe, userID, view);
    }

    /**
     * Adding the Message to the messages hashmaps of both all the receivers in the given Event and the sender
     * @param eventID the ID of the Event the User is sending all Attendees message to
     * @param content the content of the Message
     */
    public void messageAll(int eventID, String content) {
        if (getEventManager().idInList(eventID)){
            for (int userID: getEventManager().getUserIDs(eventID)) {
                sendMessage(userID, content);
            }
            getView().pushMessage("Messages sent");
        } else if (getVipEventManager().idInList(eventID)){
            for (int userID : getVipEventManager().getUserIDs(eventID)) {
                sendMessage(userID, content);
            }
            getView().pushMessage("Messages sent");
        }
        else{
            getView().pushMessage("Event ID not valid");
        }
    }

    /**
     * Return a String represents the type of the current class
     * @return the type of the current class: "SpeakerController"
     */
    public String getType(){
        return "SpeakerController";
    }

}

