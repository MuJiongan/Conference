import java.io.IOException;

public class Main
{
    public static void main(String[] args){
        Organizer o = new Organizer("Jonathan", "chenjo14", "12345678");
        UserManager um = new UserManager();
        um.addOrganizer(o);
        try{
            um.saveToFile("phase1/src/usermanager.ser");
        }catch(IOException i )
        {
            System.out.println(i.getMessage());
        }

        ConferenceSystem c = new ConferenceSystem();
        c.run();



    }
}
