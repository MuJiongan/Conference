package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.presenter.OrganizerController;
import com.example.presenter.UserController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import static java.lang.Integer.parseInt;


public class OrganizerViewStats extends Activity implements UserController.View, View.OnClickListener, Serializable {

    private OrganizerController currentController;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizerstatictics);
        currentController = (OrganizerController) getIntent().getSerializableExtra("cc"); // TODO: check
        currentController.setView(this);
        displayStats();
    }

    /**
     * totalNumOfUsers","totalNumOfMessages","totalNumOfRooms","totalNumOfEvents"
     */
    public void displayStats(){
        String statistics = currentController.systemStats();

        HashMap<Integer, ArrayList<Integer>> allEvents = currentController.topThreeEvents();
        List<Integer> sortedKey = currentController.sortedKeys(new ArrayList<Integer>(allEvents.keySet()));
        statistics += "\n\nNumber of Attendees  Name of event";
        for (int key: sortedKey){
            List<Integer> events = allEvents.get(key);
            for (int eventId: events){
                statistics += key;
                statistics += "  ";
                statistics += currentController.getEventName(eventId);
            }
        }

        TextView allStats = findViewById(R.id.orgainzerstats);
        allStats.setText(statistics); //TODO: check
    }



    @Override
    public void onClick(View view) { // TODO: do we need this method?

    }


    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }
}

