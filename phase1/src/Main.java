import java.io.IOException;

import java.util.ArrayList;

public class Main
{
    public static void main(String[] args){
        Organizer o = new Organizer("Jonathan", "chenjo14", "12345678");
        Speaker s = new Speaker("Steve", "ste123", "steven");
        OrganizerManager om = new OrganizerManager();
        SpeakerManager sm = new SpeakerManager();
        om.addUser(o);
        sm.addUser(s);
        try{
            om.saveToFile("phase1/src/organizermanager.ser");
            sm.saveToFile("phase1/src/speakermanager.ser");
        }catch(IOException i )
        {
            System.out.println(i.getMessage());
        }

        ConferenceSystem c = new ConferenceSystem();
        c.run();
    }
}
