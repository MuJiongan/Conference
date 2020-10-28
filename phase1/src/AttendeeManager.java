import java.util.ArrayList;

public class AttendeeManager {
    /**
     * The list of all attendees
     */
    private ArrayList<Attendee> attendees;
    /**
     * Returns the shallow copy of all attendees in a list
     * @return the shallow copy of all attendees in a list
     */
    public ArrayList<Attendee> getAttendees(){
        return (ArrayList<Attendee>) attendees.clone();
    }
    /**
     * add an attendee to attendees
     * @param attendee attendee to be added
     * @return true if and only if the attendee is successfully added to the attendee list
     */
    public boolean addAttendee(Attendee attendee){
        for (Attendee person: attendees){
            if(person == attendee){
                return false;
            }
        }
        attendees.add(attendee);
        return true;
    }
    /**
     * return the attendee object given the attendee ID
     * @param attendeeID the given attendeeID
     * @return the attendee object corresponding to the ID
     */
    public Attendee getAttendee(int attendeeID){
        for (Attendee person: attendees){
            if(person.getUserId() == attendeeID){
                return person;
            }
        }
        return null;
    }
    /**
     * return the attendeeID given the attendee object
     * @param attendee the given Attendee object
     * @return the attendeeID corresponding to the attendee object
     */
    private int getIDByAttendee(Attendee attendee){
        return attendee.getUserId();
    }

    // should we consider the capacity of the event?
    public boolean addEventID(int eventID, Attendee attendee){
        for (int i = 0; i<attendee.getEventsAttend().size(); i++){
            if (eventID == attendee.getEventsAttend().get(i)){
                return false;
            }
        }
        attendee.addEvent(eventID);
        return true;
    }


    public boolean cancelEventID(int eventID, Attendee attendee) {
        boolean exists = false;
        for (int i = 0; i < attendee.getEventsAttend().size(); i++) {
            if (eventID == attendee.getEventsAttend().get(i)) {
                exists = true;
            }
        }
        if (!exists) {
            return false;
        } else {
            attendee.removeEvent(eventID);
            return true;
        }
    }

    public boolean addMessageID(int messageID, Attendee attendee){
        for (int i = 0; i<attendee.getMessagesList().size(); i++){
            if(messageID == attendee.getMessagesList().get(i)){
                return false;
            }
        }
        attendee.addMessage(messageID);
        return true;
    }

    public ArrayList<Integer> getEventList(Attendee attendee){
        return attendee.getEventsAttend();
    }
}
