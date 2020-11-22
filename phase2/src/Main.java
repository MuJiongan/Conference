import interfaceAdapters.ConferenceSystem;

public class Main
{
    /**
     * Main method where program runs
     */
    public static void main(String[] args){
        ConferenceSystem c = new ConferenceSystem();
        // c.createOrganizerAccount("Zo", "Zo", "12345678");
        // c.createOrganizerAccount("Cos", "Cos", "12345678");
        c.run();
    }
}
