import java.io.IOException;

/**
 * Gateway class to read and write from files
 */
public class ReadWrite {
    private UserManager users;
    private RoomManager rooms;
    private EventManager events;
    private MessageManager messages;

    public ReadWrite()
    {
        users = new UserManager();
        rooms = new RoomManager();
        events = new EventManager();
        messages = new MessageManager();
    }
    public UserManager readUser(String path)
    {
        try
        {
            return users.readFromFile(path);
        }
        catch (ClassNotFoundException c) {
            System.out.println(c.getMessage());
            return users;
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
    //methods to write to file
    public void saveUser(String path, UserManager um)
    {
        try{
            users.saveToFile(path,um);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void saveEvent (String path, EventManager em)
    {
        try{
            events.saveToFile(path, em);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void saveRoom (String path, RoomManager rm) {
        try{
            rooms.saveToFile(path, rm);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void saveMessage (String path, MessageManager mm)
    {
        try{
            messages.saveToFile(path, mm);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void saveAll(UserManager um, EventManager em, RoomManager rm, MessageManager mm){
        saveUser("phase1/src/usermanager.ser", um);
        saveMessage("phase1/src/messagemanager.ser", mm);
        saveRoom("phase1/src/roommanager.ser", rm);
        saveEvent("phase1/src/eventmanager.ser", em);
    }
}
