package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;
import com.example.presenter.OrganizerController;
import com.example.presenter.UserController;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;




public class OrganizerViewStats extends Activity implements View.OnClickListener, UserController.View, Serializable {

    private OrganizerController currentController;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizerstatictics);
        currentController = (OrganizerController) getIntent().getSerializableExtra("controller");
        currentController.setView(this);
        displayStats();
    }

    /**
     * display all events with the number of attendee registered
     */
    public void displayStats(){
        String statistics = currentController.systemStats();

        HashMap<Integer, ArrayList<Integer>> allEvents = currentController.allEventsWithAttendee();
        List<Integer> sortedKey = currentController.sortedKeys(new ArrayList<Integer>(allEvents.keySet()));
        statistics += "\n\nNumber of Attendees  Name of event \n";
        for (int key: sortedKey){
            List<Integer> events = allEvents.get(key);
            for (int eventId: events){
                statistics += key;
                statistics += "  ";
                statistics += currentController.getEventName(eventId) + "\n";
            }
        }

        TextView allStats = findViewById(R.id.organizerStats);
        allStats.setText(statistics); //TODO: check
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                Intent myIntent = new Intent(this, OrganizerMenu.class);
                myIntent.putExtra("cc", currentController);
                setResult(3, myIntent);
                finish();
                break;


        }
    }

    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }
}

