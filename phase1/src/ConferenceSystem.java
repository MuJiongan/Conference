public class ConferenceSystem {
    private UserManager um;
    private RoomManager rm;
    private EventManager em;
    private MessageManager mm;
    private ReadWrite gateway;


    public ConferenceSystem() {
        gateway = new ReadWrite();
        um = gateway.readUser("phase1/src/usermanager.ser");
//        rm = gateway.readRoom("phase1/src/roommanager.ser");
//        em = gateway.readEvent("phase1/src/eventmanager.ser");
//        mm = gateway.readMessage("phase1/src/messagemanager.ser");
    }
    public void run()
    {
        UserController current = new LogInSystem(um);
        User new_user = current.run();
        if (new_user != null)
        {
            System.out.println(um.getAttendees().size());
            System.out.println(new_user.getUserName());
        }
    }

}
