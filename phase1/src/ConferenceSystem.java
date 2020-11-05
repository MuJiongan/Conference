import java.util.ArrayList;

public class ConferenceSystem {
    private UserManager um;
    private RoomManager rm;
    private EventManager em;
    private MessageManager mm;
    private ReadWrite gateway;


    public ConferenceSystem() {
        gateway = new ReadWrite();
        um = gateway.readUser("phase1/src/usermanager.ser");
//        rm = gateway.readRoom("phase1/src/roommanager.ser");
//        em = gateway.readEvent("phase1/src/eventmanager.ser");
//        mm = gateway.readMessage("phase1/src/messagemanager.ser");
    }
    public void run()
    {
        UserController current = new LogInSystem(um);
        boolean iterate = true;
        while (iterate) {
            User new_user = current.run();
            if (new_user != null) {
                iterate = false;
                if (um.getAttendees().contains(new_user)) {
                   // current = new AttendeeMenu(um, rm, em, mm, new_user);
                   // current.run();
                } else if (um.getSpeakers().contains(new_user)) {
                    current = new SpeakerMenu(um, rm, em, mm, new_user, gateway);
                    current.run();
                    System.out.println("lol");
                }
                else if (um.getOrganizers().contains(new_user))
                {
                    current = new OrganizerMenu(um, rm, em, mm, new_user, gateway);
                    current.run();
                }
            } else {
                System.out.println("Error please try again");
            }
        }
    }

}
