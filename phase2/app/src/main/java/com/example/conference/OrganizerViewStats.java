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

    /**
     * Create this new activity
     * @param  savedInstanceState the saved instanceState
     */
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
        List<Integer> sortedKey = currentController.sortedKeys(new ArrayList<>(allEvents.keySet()));
        statistics += "\n\nNumber of Attendees  Name of event \n";
        for (int key: sortedKey){
            List<Integer> events = allEvents.get(key);
            for (int eventId: events){
                statistics += "\t\t\t\t";
                statistics += key;
                statistics += "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t";
                statistics += currentController.getEventName(eventId) + "\n";
            }
        }

        TextView allStats = findViewById(R.id.organizerStats);
        allStats.setText(statistics); //TODO: check
    }


    /**
     * Perform certain actions when the user clicks a button
     * @param v view of the current activity
     */
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

    /**
     * Display a toast message given a string
     * @param info message content of the toast message
     */
    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }
}

