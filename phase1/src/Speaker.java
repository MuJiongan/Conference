import java.util.ArrayList;
/**
 * Represents a organizer in the conference.
 */

public class Speaker extends User{

    /**
     * Stores a list of event IDs the user is going to give speak (as a Speaker)
     */
    private ArrayList<Integer> eventsAsSpeaker;


    /**
     * Create an instance of Organizer
     */
    public Speaker(String name, String username, String password){
        super(name, username, password);
        this.eventsAsSpeaker = new ArrayList<>();
    }


    /**
     * Returns the shallow copy of eventsAsSpeaker list of the Speaker
     * @return shallow copy of eventsAsSpeaker list of the Speaker
     */
    public ArrayList<Integer> getEventsAsSpeaker(){
        return (ArrayList<Integer>) eventsAsSpeaker.clone();
    }


    /**
     *  Add eventID into the Speaker's eventsAsSpeaker list
     */
    public void addEventsAsSpeaker(int eventID){
        this.eventsAsSpeaker.add(eventID);
    }


    /**
     * Remove eventID from the Speaker's eventsAsSpeaker list
     */
    public void removeEventsAsSpeaker(int eventID){
        this.eventsAsSpeaker.remove(eventID);
    }

}