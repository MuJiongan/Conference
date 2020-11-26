package com.example.presenter;

import com.example.model.entities.User;
import com.example.model.interfaceAdapters.ReadWrite;
import com.example.model.useCases.*;

public class LogInPresenter {
    private AttendeeManager am;
    private OrganizerManager om;
    private SpeakerManager sm;
    private RoomManager rm;
    private EventManager em;
    private MessageManager mm;
    private ReadWrite gateway;
    //VIP Manager private variable


    public LogInPresenter(View view) {
        gateway = new ReadWrite();
        //https://stackoverflow.com/questions/14768191/how-do-i-read-the-file-content-from-the-internal-storage-android-app
        am = gateway.readAttendee("src/main/java/com/example/model/useCases/attendeemanager.ser");
        om = gateway.readOrganizer("src/main/java/com/example/model/useCases/organizermanager.ser");
        sm = gateway.readSpeaker("src/main/java/com/example/model/useCases/speakermanager.ser");
        rm = gateway.readRoom("src/main/java/com/example/model/useCases/roommanager.ser");
        em = gateway.readEvent("src/main/java/com/example/model/useCases/eventanager.ser");
        mm = gateway.readMessage("src/main/java/com/example/model/useCases/messagemanager.ser");

        om.addUser(om.createOrganizer("Jonathan", "chenjo14", "12345678", 1));
        gateway.setManagers(am, om, sm);

    }

    public boolean validate(String username, String password)
    {
        User user =  am.validate(username, password);
        if (!(user == null))
        {
            return true;
        }
        user = om.validate(username, password);
        if (!(user == null))
        {
            return true;
        }
        user = sm.validate(username, password);
        if (!(user == null))
        {
            return true;
        }
        return false;
    }

    public interface View {
        void pushMessage(String info);
    }


}
