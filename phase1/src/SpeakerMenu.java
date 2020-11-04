import java.util.ArrayList;


public class SpeakerMenu extends UserMenu implements UserController{
    private UserManager um;
    private RoomManager rm;
    private EventManager em;
    private MessageManager mm;
    private User currentUser;

    public SpeakerMenu(UserManager um, RoomManager rm, EventManager em, MessageManager mm, User currentUser)
    {
        super(um, rm, em, mm, currentUser);
    }

    @Override
    public User run() {
        return null;
    }
}
