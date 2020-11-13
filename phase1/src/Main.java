import java.io.IOException;

import java.util.ArrayList;

public class Main
{
    public static void main(String[] args){
//        Organizer o = new Organizer("Jonathan", "chenjo14", "12345678");
//        Speaker s = new Speaker("Steve", "ste123", "steven");
//        Attendee a = new Attendee("Michael", "GOAT", "Jordan11");
//        OrganizerManager om = new OrganizerManager();
//        SpeakerManager sm = new SpeakerManager();
//        AttendeeManager am = new AttendeeManager();
//        om.addUser(o);
//        sm.addUser(s);
//        am.addUser(a);
//        try{
//            om.saveToFile("phase1/src/organizermanager.ser");
//            sm.saveToFile("phase1/src/speakermanager.ser");
//            am.saveToFile("phase1/src/attendeemanager.ser");
//        }catch(IOException i )
//        {
//            System.out.println(i.getMessage());
//        }

        ConferenceSystem c = new ConferenceSystem();
        //c.createOrganizerAccount("Max", "Lonzo", "12345678");
        c.run();
    }
}
