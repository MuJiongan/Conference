import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LogInSystem implements UserController {
    private AttendeeManager am;
    private OrganizerManager om;
    private SpeakerManager sm;

    public LogInSystem(AttendeeManager am, OrganizerManager om, SpeakerManager sm)
    {
        this.am = am;
        this.om = om;
        this.sm = sm;
    }
    public void initializeAttendeeContactsList(Attendee newAttendee){
        for (User attendee: am.getUsers()){
            // Add every attendee to this new attendee's contact list
            am.addToContactsList(newAttendee, am.getIDByUser(attendee));
            // Add this new attendee's to every attendee's contact list
            am.addToContactsList(attendee, am.getIDByUser(newAttendee));

        }
        for (User speaker: sm.getUsers()){

            // Add every speaker to this new attendee's contact list
            am.addToContactsList(newAttendee, sm.getIDByUser(speaker));
        }
        for (User organizer: om.getUsers()){
            // Add this new attendee to each organizer's contact list
            om.addToContactsList(organizer, am.getIDByUser(newAttendee));
        }
        // There are still things we need to do.
        // Whenever an organizer messages an attendee, we add that organizer to the attendee's contact list (done)
        // Whenever this attendee signs up for an event, add this attendee to speaker's contact list (done)
        // (check if the attendee is already is the speaker's contact list)
        // Whenever an attendee messages a speaker, add this attendee to speaker's contact list (done)
        // (check if the attendee is already is the speaker's contact list)
    }

    @Override
    public User run()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserPropertiesIterator prompts = new UserPropertiesIterator();
        ArrayList<String> inputs = new ArrayList<>();
        System.out.println("1. Login\n2. Create new Account");
        try {
            String input = br.readLine();
            if (input.equals("1")) {
                prompts.next();
                System.out.println("Please enter your credentials");
                while (!input.equals("exit") && prompts.hasNext()) {
                    System.out.println(prompts.next());
                    input = br.readLine();
                    if (!input.equals("exit")) {
                        inputs.add(input);
                    }
                }
                User user =  am.validate(inputs.get(0), inputs.get(1));
                if (!(user == null))
                {
                    return user;
                }
                user = om.validate(inputs.get(0), inputs.get(1));
                if (!(user == null))
                {
                    return user;
                }
                return sm.validate(inputs.get(0), inputs.get(1));
            }
            else if (input.equals("2"))
            {
                System.out.println("Please follow the steps to create an account:");
                while (!input.equals("exit") && prompts.hasNext()) {
                    System.out.println(prompts.next());
                    input = br.readLine();
                    if (!input.equals("exit")) {
                        inputs.add(input);
                    }
                }
                if (am.hasUserName(inputs.get(1)) || om.hasUserName(inputs.get(1)) || sm.hasUserName(inputs.get(1)))
                {
                    System.out.println("That username is already in use");
                    return null;
                }
                else
                {
                    Attendee newAccount = am.createAttendee(inputs.get(0), inputs.get(1), inputs.get(2), getNewID());
                    am.addUser(newAccount);
                    initializeAttendeeContactsList(newAccount);
                    return newAccount;
                }
            }
            else {
                return null;
            }

        } catch (IOException e) {
            System.out.println("Something went wrong");
            return null;
        }
    }
    public int getNewID(){
        int size = am.getUsers().size() + om.getUsers().size() + sm.getUsers().size();
        return size + 1;
    }

}
