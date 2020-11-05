import java.util.ArrayList;


public class SpeakerMenu extends UserMenu implements UserController{


    public SpeakerMenu(UserManager um, RoomManager rm, EventManager em, MessageManager mm, User currentUser)
    {
        super(um, rm, em, mm, currentUser);
    }

    @Override
    public User run() {
        System.out.println("1. View All Events \n2. View your Events \n3. Message Attendee \n4. Message all Attendees " +
                "in Event");
        return null;
    }
}
