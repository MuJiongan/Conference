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
     *  Add event into the Speaker's eventsAsSpeaker list
     */
    public void addEventsAsSpeaker(Event event){
        this.eventsAsSpeaker.add(event.getEventID());
    }



}