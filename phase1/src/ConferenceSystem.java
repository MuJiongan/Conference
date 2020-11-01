public class ConferenceSystem {
    private UserManager um;
    private RoomManager rm;
    private EventManager em;
    private MessageManager mm;


    public ConferenceSystem()
    {
        rm = rm.readFromFile("roommanager.ser");
        em = em.readFromFile();
        am = am.readFromFile();
        sm = sm.readFromFile();
        om = om.readFromFile();
    }
    public void run()
    {
        UserController current = new LogInSystem(am, om, sm);
        current = current.run();
        current.run()
    }

}
