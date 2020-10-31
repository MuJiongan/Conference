import java.util.ArrayList;

public class UserManager {
    /**
     * The list of all attendees
     */
    private ArrayList<Attendee> attendees;
    /**
     * The list of all organizers
     */
    private ArrayList<Organizer> organizers;
    /**
     * The list of all speakers
     */
    private ArrayList<Speaker> speakers;
    /**
     * Returns the shallow copy of all attendees in a list
     * @return the shallow copy of all attendees in a list
     */
    public ArrayList<Attendee> getAttendees(){
        return (ArrayList<Attendee>) attendees.clone();
    }
    /**
     * Returns the shallow copy of all organizers in a list
     * @return the shallow copy of all organizers in a list
     */
    public ArrayList<Organizer> getOrganizers(){
        return (ArrayList<Organizer>) organizers.clone();
    }
    /**
     * Returns the shallow copy of all speakers in a list
     * @return the shallow copy of all speakers in a list
     */
    public ArrayList<Speaker> getSpeakers(){
        return (ArrayList<Speaker>) speakers.clone();
    }
    /**
     * add a attendee to attendees
     * @param attendee to be added
     * @return true if and only if the attendee is successfully added to the attendee list
     */
    public boolean addAttendee(Attendee attendee){
        for (User person: attendees){
            if(person == attendee){
                return false;
            }
        }
        attendees.add(attendee);
        return true;
    }
    /**
     * add an organizer to organizers
     * @param organizer to be added
     * @return true if and only if the organizer is successfully added to the organizer list
     */
    public boolean addOrganizer(Organizer organizer){
        for (User person: organizers){
            if(person == organizer){
                return false;
            }
        }
        organizers.add(organizer);
        return true;
    }
    /**
     * add a speaker to speakers
     * @param speaker to be added
     * @return true if and only if the speaker is successfully added to the speaker list
     */
    public boolean addSpeaker(Speaker speaker){
        for (User person: speakers){
            if(person == speaker){
                return false;
            }
        }
        speakers.add(speaker);
        return true;
    }
    /**
     * return the attendee object given the user ID, null if not found
     * @param attendeeID the given attendeeID
     * @return the attendee object corresponding to the ID, null if not found
     */
    public User getAttendee(int attendeeID){
        for (User person: attendees){
            if(person.getUserId() == attendeeID){
                return person;
            }
        }
        return null;
    }
    /**
     * return the organizer object given the organizer ID, null if not found
     * @param organizerID the given organizerID
     * @return the organizer object corresponding to the ID, null if not found
     */
    public User getOrganizer(int organizerID){
        for (User person: organizers){
            if(person.getUserId() == organizerID){
                return person;
            }
        }
        return null;
    }
    /**
     * return the speaker object given the speaker ID, null if not found
     * @param speakerID the given speakerID
     * @return the speaker object corresponding to the ID, null if not found
     */
    public User getSpeaker(int speakerID){
        for (User person: speakers){
            if(person.getUserId() == speakerID){
                return person;
            }
        }
        return null;
    }
    /**
     * return the userID given the user object
     * @param user the given User object
     * @return the userID corresponding to the user object
     */
    private int getIDByUser(User user){
        return user.getUserId();
    }
    /**
     * add an eventID to the list of all events the user is going to attend
     * @param user the given User object
     * @param eventID ID of the event the user is going to add
     * @custom.precondition
     *       MAKE_SURE_EVENT_VACANCY_IS_GREATER_THAN_ZERO
     * @return true if and only if the user is successfully added to the list
     */
    public boolean addEventID(int eventID, User user){
        for (int i = 0; i<user.getEventsAttend().size(); i++){
            if (eventID == user.getEventsAttend().get(i)){
                return false;
            }
        }
        user.addEvent(eventID);
        return true;
    }
    /**
     * remove an eventID from the list of all events the user is going to cancel
     * @param user the given User object
     * @param eventID ID of the event the user is going to cancel
     * @return true if and only if the user is successfully removed from the list
     */
    public boolean removeEventID(int eventID, User user) {
        boolean exists = false;
        for (int i = 0; i < user.getEventsAttend().size(); i++) {
            if (eventID == user.getEventsAttend().get(i)) {
                exists = true;
            }
        }
        if (!exists) {
            return false;
        } else {
            user.removeEvent(eventID);
            return true;
        }
    }
    /**
     * add a messageID to the list of all messages of the user
     * @param user the given User object
     * @param messageID ID of the message the user is receiving
     * @return true if and only if the message is successfully added to the list
     */
    public boolean addMessageID(int messageID, User user){
        for (int i = 0; i<user.getMessagesList().size(); i++){
            if(messageID == user.getMessagesList().get(i)){
                return false;
            }
        }
        user.addMessage(messageID);
        return true;
    }
    /**
     * return a list of all events the user is going to attend
     * @param user the given User object
     * @return a list of all events the user is going to attend
     */
    public ArrayList<Integer> getEventList(User user){
        return user.getEventsAttend();
    }
}
