package com.example.model.useCases;

import com.example.model.useCases.AttendeeManager;

import java.io.Serializable;

public class VipManager extends AttendeeManager implements Serializable {
    /**
     * Store a list of Vip users, who can view and sign up for vip events
     */
    public VipManager(){
        super();
    }

}

