import interfaceAdapters.ConferenceSystem;

public class Main
{
    public static void main(String[] args){
//        entities.Organizer o = new entities.Organizer("Jonathan", "chenjo14", "12345678");
//        entities.Speaker s = new entities.Speaker("Steve", "ste123", "steven");
//        entities.Attendee a = new entities.Attendee("Michael", "GOAT", "Jordan11");
//        useCases.OrganizerManager om = new useCases.OrganizerManager();
//        useCases.SpeakerManager sm = new useCases.SpeakerManager();
//        useCases.AttendeeManager am = new useCases.AttendeeManager();
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
