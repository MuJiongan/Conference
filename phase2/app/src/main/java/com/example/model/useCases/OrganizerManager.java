package com.example.model.useCases;
import com.example.model.entities.User;

import java.io.*;

public class OrganizerManager extends UserManager implements Serializable{

    public OrganizerManager()
    {
        super();
    }

    public boolean createOrganizer(String name, String userName, String password, int ID)
    {
        User user = new User(name, userName, password, ID);
        if (addUser(user))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
