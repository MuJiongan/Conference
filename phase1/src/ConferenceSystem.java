import java.util.ArrayList;

public class ConferenceSystem {
    private AttendeeManager am;
    private OrganizerManager om;
    private SpeakerManager sm;
    private RoomManager rm;
    private EventManager em;
    private MessageManager mm;
    private ReadWrite gateway;


    public ConferenceSystem() {
        gateway = new ReadWrite();
        am = gateway.readAttendee("phase1/src/attendeemanager.ser");
        om = gateway.readOrganizer("phase1/src/organizermanager.ser");
        sm = gateway.readSpeaker("phase1/src/speakermanager.ser");
        rm = gateway.readRoom("phase1/src/roommanager.ser");
        em = gateway.readEvent("phase1/src/eventmanager.ser");
        mm = gateway.readMessage("phase1/src/messagemanager.ser");
        gateway.setManagers(am, om, sm, em, rm, mm);
        System.out.println(am.getUsers().size());
        System.out.println(om.getUsers().size());
        System.out.println(sm.getUsers().size());
    }
    public void run()
    {
        UserController current = new LogInSystem(am, om, sm);
        boolean iterate = true;
        while (iterate) {
            User new_user = current.run();
            if (new_user != null) {
                iterate = false;
                if (am.getUsers().contains(new_user)) {
                   current = new AttendeeMenu(am, om, sm, rm, em, mm, new_user);
                   current.run();
                   gateway.saveAll();

                }
                else if (sm.getUsers().contains(new_user)) {
                    current = new SpeakerMenu(am, om, sm, rm, em, mm, new_user);
                    current.run();
                    gateway.saveAll();
                }
                else if (om.getUsers().contains(new_user))
                {
                    current = new OrganizerMenu(am, om, sm, rm, em, mm, new_user);
                    current.run();
                    gateway.saveAll();
                }
            } else {
                System.out.println("Error please try again");
            }
        }
    }

}
