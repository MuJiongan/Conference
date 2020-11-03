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
}
