import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrganizerMenu extends AttendeeMenu implements UserController{
    public OrganizerMenu(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm, EventManager em, MessageManager mm, User user){
        super(am, om, sm, rm, em, mm, user);
    }

    public void messageAll(int eventID, String content)
    {
        Event event = getEventManager().getEventByID(eventID);
        for (int userID: event.getUserIDs())
        {
            sendMessage(userID, content);
        }
    }


    /**
     * Enter new Rooms into the System
     * @return true if room successfully enter
     */
    public boolean enterRoom(Room room){
        return getRoomManager().addRoom(room);
    }


    /**
     * Create new Speaker account
     * @return true if new Speaker account successfully created
     */
    public boolean addSpeaker(String name, String username, String password) {
        Speaker s = getSpeakerManager().createSpeaker(name, username, password);
        return getSpeakerManager().addUser(s);
    }


    /**
     * Schedule Speaker to an existing Event
     * @return true if speakerID successfully added to an existing Event
     */
    public boolean scheduleSpeakerToEvent(Speaker speaker, Event event){
        int speakerID = speaker.getUserId();
        return getEventManager().addSpeakerID(speakerID, event);
    }


    /**
     * Schedule Speaker to a new Event
     * @param speaker the Speaker who is to be scheduled
     * @param startTime the LocalDateTime of start time of Event
     * @param endTime the LocalDateTime of end time of Event
     * @param roomID ID of room that this Event is scheduled in
     * @param capacity maximum number of attendees allowed in this Event
     * @return true if speakerID successfully added to a new Event with given info
     */
    public boolean scheduleSpeakerToEvent(Speaker speaker, LocalDateTime startTime, LocalDateTime endTime,
                                          int roomID, String name, int capacity){
        int speakerID = speaker.getUserId();
        Event event = createEvent(startTime, endTime, roomID, name, capacity);
        return getEventManager().addSpeakerID(speakerID, event);
    }


    /**
     * Create a new Event and return it
     * @param startTime the LocalDateTime of start time of Event
     * @param endTime the LocalDateTime of end time of Event
     * @param roomID ID of room that this Event is scheduled in
     * @param capacity maximum number of attendees allowed in this Event
     * @return the Event object we created
     */
    public Event createEvent(LocalDateTime startTime, LocalDateTime endTime, int roomID, String name, int capacity){
        return new Event(startTime, endTime, roomID, name, capacity);
    }



    @Override
    public User run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserPropertiesIterator prompts = new UserPropertiesIterator();
        ArrayList<String> inputs = new ArrayList<>();
        System.out.println("1. View All Events \n2. View your Events \n3. Message User \n4. Message Users" +
                "in Event \n5. Enter New Room \n6. Create Speaker Account \n7.Schedule Speaker \n8.Exit");
        try{
            String input = br.readLine();
            while (!input.equals("8"))
            {
                if (input.equals("1"))
                {
                    this.viewAllEvents();
                }
                System.out.println("1. View All Events \n2. View your Events \n3. Message User \n4. Message Users" +
                        "in Event \n5. Enter New Room \n6. Create Speaker Account \n7.Schedule Speaker \n8.Exit");
                input = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Please enter a valid option");
            return null;
        }

        System.out.println("See you again soon");
        return null;
    }
}
