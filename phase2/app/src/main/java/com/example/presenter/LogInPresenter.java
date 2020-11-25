package com.example.presenter;

import com.example.model.entities.User;
import com.example.model.interfaceAdapters.ReadWrite;
import com.example.model.useCases.*;

public class LogInPresenter {
    /**
     * Store the AttendeeManager
     */
    private AttendeeManager am;
    /**
     * Store the Organizer Manager
     */
    private OrganizerManager om;
    /**
     * Store the SpeakerManager
     */
    private SpeakerManager sm;
    //VIP Manager private variable

    private ReadWrite gateway;

    public LogInPresenter(View view) {
        gateway = new ReadWrite();
        am = gateway.readAttendee("attendeemanager.ser");
        om = gateway.readOrganizer("rganizermanager.ser");
        sm = gateway.readSpeaker("speakermanager.ser");
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
