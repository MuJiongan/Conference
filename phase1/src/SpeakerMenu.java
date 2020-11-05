import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class SpeakerMenu extends UserMenu implements UserController{


    public SpeakerMenu(UserManager um, RoomManager rm, EventManager em, MessageManager mm, User currentUser, ReadWrite gateway)
    {
        super(um, rm, em, mm, currentUser, gateway);
    }
    private boolean canSend(int receiverID)
    {
        ArrayList<Integer> talks = this.getUsers().getEventList(this.getUser());
        for (int x: talks)
        {
            Event talk = this.getEvents().getEventByID(x);
            ArrayList<Integer> people = this.getEvents().getUserIDs(talk);
            if (people.contains(receiverID))
            {
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean sendMessage(int receiverID, String messageContent)
    {
        if (canSend(receiverID))
        {
            super.sendMessage(receiverID, messageContent);
            System.out.println("Messages sent");
            return true;
        }
        else
        {
            System.out.println("Please enter a valid userID");
            return false;
        }
    }
    @Override
    public User run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserPropertiesIterator prompts = new UserPropertiesIterator();
        ArrayList<String> inputs = new ArrayList<>();
        System.out.println("1. View All Events \n2. View your Events \n3. Message Attendee \n4. Message all Attendees " +
                "in Event \n5. View Messages \n6. Exit");
        try{
            String input = br.readLine();
            while (!input.equals("6"))
            {
                if (input.equals("1"))
                {
                    this.viewAllEvents();
                }
                else if (input.equals("5"))
                {
                    this.viewMessage();
                }
                System.out.println("1. View All Events \n2. View your Events \n3. Message Attendee \n4. Message all Attendees " +
                        "in Event \n5. View Messages \n6. Exit");
                input = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Please enter a valid option");
            return null;
        }
        this.saveInfo();
        System.out.println("See you again soon");
        return null;
    }
}
