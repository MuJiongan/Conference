import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventManager {
    /**
     * The list of all events
     */
    private ArrayList<Event> events = new ArrayList<>();
    /**
     * Returns the shallow copy of all events in a list
     * @return the shallow copy of all events in a list
     */
    public LocalDateTime getStartTime(Event event)
    {
        return event.getStartTime();
    }
    public void setStartTime(Event event, LocalDateTime time)
    {
        event.setStartTime(time);
    }
    public LocalDateTime getEndTime(Event event)
    {
       return event.getEndTime();
    }
    public void setEndTime(Event event, LocalDateTime time)
    {
        event.setEndTime(time);
    }
    public ArrayList<Event> getEvents() {
        return (ArrayList<Event>) events.clone();
    }
    /**
     * add an event to events
     * @param event event to be added
     * @return true if and only if the event is successfully added to the event list
     */
    public boolean addEvent(Event event){
        for (Event value : events) {
            if (event == value) {
                return false;
            }
        }
        events.add(event);
        return true;
    }
    /**
     * return the event object given the event ID
     * @param eventID the given eventID
     * @return the event object corresponding to the ID
     */
    public Event getEventByID(int eventID){
        for (Event event: events){
            if (event.getEventID() == eventID){
                return event;
            }

        }
        return null;
    }
    /**
     * return the event ID given the event object
     * @param event the given Event object
     * @return the event ID corresponding to the Event object
     */
    public int getIDByEvent(Event event){
        return event.getEventID();
    }
    /**
     * add a userID to the list of all attendees in the event
     * @param userID Attendee to be removed
     * @param event in which event the attendee is being removed
     * @return true if and only if the attendee is successfully added to the list
     */
    public boolean addUserID(int userID, Event event){

        for (int i = 0; i<event.getUserIDs().size(); i++){
            if (userID == event.getUserIDs().get(i)){
                return false;
            }
        }
        event.addUserID(userID);
        return true;
    }
    /**
     * add a speakerID to the list of all speakers in the event
     * @param speakerID speaker to be removed
     * @param event in which event the speaker is being removed
     * @return true if and only if the speaker is successfully added to the list
     */
    public boolean addSpeakerID(int speakerID, Event event){

        for (int i = 0; i<event.getSpeakerIDs().size(); i++){
            if (speakerID == event.getSpeakerIDs().get(i)){
                return false;
            }
        }
        event.addSpeakerID(speakerID);
        return true;
    }
    /**
     * Remove an attendee to the list of all attendees in the event
     * @param userID Attendee to be removed
     * @param event in which event the attendee is being removed
     * @return true if and only if the attendee is successfully removed
     */
    public boolean removeUserID(int userID, Event event) {
        boolean exists = false;
        for (int i = 0; i < event.getUserIDs().size(); i++) {
            if (userID == event.getUserIDs().get(i)) {
                exists = true;
            }
        }
        if (!exists){
            return false;
        }
        else{
            event.removeUserID(userID);
            return true;
        }

    }
    /**
     * Remove an speaker to the list of all attendees in the event
     * @param speakerID speaker to be removed
     * @param event in which event the attendee is being removed
     * @return true if and only if the speaker is successfully removed
     */
    public boolean removeSpeakerID(int speakerID, Event event) {
        boolean exists = false;
        for (int i = 0; i < event.getSpeakerIDs().size(); i++) {
            if (speakerID == event.getSpeakerIDs().get(i)) {
                exists = true;
            }
        }
        if (!exists){
            return false;
        }
        else{
            event.removeSpeakerID(speakerID);
            return true;
        }

    }
    /**
     * change the room ID to a new ID of a given event
     * @param roomID new room ID
     * @param event in which event the roomID is being changed
     */
    public void changeRoomID(int roomID, Event event){
        event.changeRoomID(roomID);
    }
    /**
     * Read the EventManager object that was stored in a .ser file
     * @param path String representing the file path
     * @return EventManager object read from .ser file
     * @throws ClassNotFoundException is thrown if EventManager object is not found
     */
    public EventManager readFromFile (String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path); // String path should be "fileName.ser"
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the StudentManager
            EventManager em = (EventManager) input.readObject();
            input.close();
            return em;
        } catch (IOException ex) {
            return new EventManager();
        }
    }
    /**
     * return the event name given the event object
     * @param event the given Event object
     * @return the event name corresponding to the Event object
     */
    public String getName (Event event){
        return event.getName();
    }
    /**
     * Write the EventManager object to a .ser file to store once program exists
     * @param filePath file to write to
     * @throws IOException is thrown if file we want to write to does not exist
     */
    public void saveToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the EventManager
        output.writeObject(this);
        output.close();
    }

}
