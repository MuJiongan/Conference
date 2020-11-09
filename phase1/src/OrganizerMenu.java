import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public boolean enterRoom(int capacity){
        Room r = new Room(capacity);
        return getRoomManager().addRoom(r);
    }


    /**
     * Create new Speaker account
     * @return true if new Speaker account successfully created
     */
    public boolean addSpeaker(String name, String username, String password) {
        Speaker s = new Speaker(name, username, password);
        return getSpeakerManager().addUser(s);
    }

    /**
     * Schedule Speaker to an Event
     * @return
     */


    /**
     * Schedule new Event
     * @return
     */




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
