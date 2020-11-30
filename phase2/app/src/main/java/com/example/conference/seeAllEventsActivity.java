package com.example.conference;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.presenter.AttendeeController;
import com.example.presenter.UserController;

import java.io.Serializable;
import java.util.ArrayList;

public class  seeAllEventsActivity extends Activity implements View.OnClickListener, UserController.View, Serializable {
    private AttendeeController controller;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seeallevents);
        controller = (AttendeeController) getIntent().getSerializableExtra("controller");
        controller.setView(this);
        TextView allevents = findViewById(R.id.allEvents);
        allevents.setText(controller.viewAllEvents());
    }

    @Override
    public void onClick(View v) {

    }



    public void displayEvents(ArrayList<Integer> eventIDs, ArrayList<String> eventNames, ArrayList<String> roomNames, ArrayList<String> time){

        String heading1 = "Events";
        String heading2 = "Time";
        String heading3 = "Vacancy";
        String heading4 = "Room";
        String text = "Here are all the scheduled events:";
        // TODO: Display the events using the same format we did in text user interface

    }

    @Override
    public void pushMessage(String info) {

    }
}
