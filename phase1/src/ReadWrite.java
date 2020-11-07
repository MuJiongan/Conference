import java.io.IOException;

/**
 * Gateway class to read and write from files
 */
public class ReadWrite {
    private AttendeeManager attendees;
    private OrganizerManager organizers;
    private SpeakerManager speakers;
    private RoomManager rooms;
    private EventManager events;
    private MessageManager messages;

    public ReadWrite()
    {

        attendees = new AttendeeManager();
        organizers = new OrganizerManager();
        speakers = new SpeakerManager();
        rooms = new RoomManager();
        events = new EventManager();
        messages = new MessageManager();
    }
    public AttendeeManager readAttendee(String path)
    {
        try
        {
            return attendees.readFromFile(path);
        }
        catch (ClassNotFoundException c) {
            System.out.println("Couldn't find file " + path);
            return attendees;
         }
    }
    public OrganizerManager readOrganizer(String path)
    {
        try
        {
            return organizers.readFromFile(path);
        }
        catch (ClassNotFoundException c) {
            System.out.println("Couldn't find file " + path);
            return organizers;
        }
    }
    public SpeakerManager readSpeaker(String path)
    {
        try
        {
            return speakers.readFromFile(path);
        }
        catch (ClassNotFoundException c) {
            System.out.println("Couldn't find file " + path);
            return speakers;
        }
    }
    public RoomManager readRoom(String path)
    {
        try
        {
            return rooms.readFromFile(path);
        }
        catch (ClassNotFoundException c) {
            System.out.println(c.getMessage());
            return rooms;
        }
    }
    public EventManager readEvent(String path)
    {
        try
        {
            return events.readFromFile(path);
        }
        catch (ClassNotFoundException c) {
            System.out.println(c.getMessage());
            return events;
        }
    }
    public MessageManager readMessage(String path)
    {
        try
        {
            return messages.readFromFile(path);
        }
        catch (ClassNotFoundException c) {
            System.out.println(c.getMessage());
            return messages;
        }
    }
    public void setManagers(AttendeeManager am, OrganizerManager om, SpeakerManager sm, EventManager em, RoomManager rm, MessageManager mm)
    {
        attendees = am;
        organizers = om;
        speakers = sm;
        events = em;
        rooms = rm;
        messages = mm;
    }
    //methods to write to file
    public void saveAttendees(String path)
    {
        try{
            attendees.saveToFile(path);
        }
        catch (IOException e)
        {
            System.out.println("Couldn't save AttendeeManager");
        }
    }
    public void saveOrganizers(String path)
    {
        try{
            organizers.saveToFile(path);
        }
        catch (IOException e)
        {
            System.out.println("Couldn't save OrganizerManager");
        }
    }
    public void saveSpeakers(String path)
    {
        try{
            speakers.saveToFile(path);
        }
        catch (IOException e)
        {
            System.out.println("Couldn't save SpeakerManager");
        }
    }
    public void saveEvent (String path)
    {
        try{
            events.saveToFile(path);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void saveRoom (String path) {
        try{
            rooms.saveToFile(path);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void saveMessage (String path)
    {
        try{
            messages.saveToFile(path);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void saveAll(){
        saveAttendees("phase1/src/attendeemanager.ser");
        saveOrganizers("phase1/src/organizermanager.ser");
        saveSpeakers("phase1/src/speakersmanager.ser");
        saveMessage("phase1/src/messagemanager.ser");
        saveRoom("phase1/src/roommanager.ser");
        saveEvent("phase1/src/eventmanager.ser");
    }
}
