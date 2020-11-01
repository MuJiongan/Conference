public class ConferenceSystem {
    private UserManager um;
    private RoomManager rm;
    private EventManager em;
    private MessageManager mm;


    public ConferenceSystem() {
        um = new UserManager();
        rm = new RoomManager();
        em = new EventManager();
        mm = new MessageManager();
        try{
            um = um.readFromFile("phase1/src/usermanager.ser");
//            rm = rm.readFromFile("roommanager.ser");
//            em = em.readFromFile("eventmanager.ser");
//            mm = mm.readFromFile("messagemanager.ser");
        }catch (ClassNotFoundException ex)
        {
            System.out.println("Something went wrong when trying to read from file");
        }



    }
    public void run()
    {
        UserController current = new LogInSystem(um);
        User new_user = current.run();
        if (new_user != null)
        {
            System.out.println("Good");
        }
    }

}
