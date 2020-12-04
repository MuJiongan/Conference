package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import com.example.presenter.AttendeeController;
import com.example.presenter.OrganizerController;
import com.example.presenter.UserController;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static java.lang.Integer.parseInt;

public class OrganizerScheduleEvent extends Activity implements View.OnClickListener, UserController.View, Serializable {
    private OrganizerController controller;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizerscheduleevent);
        controller = (OrganizerController) getIntent().getSerializableExtra("cc");
        controller.setView(this);
    }

    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.schedule:
                try {
                    EditText event = findViewById(R.id.eventName);
                    String eventName = event.getText().toString();
                    EditText start = findViewById(R.id.startTime);
                    LocalDateTime startTime = LocalDateTime.parse(start.getText().toString());
                    EditText end = findViewById(R.id.endTime);
                    LocalDateTime endTime = LocalDateTime.parse(end.getText().toString());
                    EditText room = findViewById(R.id.roomID);
                    int roomID = parseInt(room.getText().toString());
                    EditText size = findViewById(R.id.capacity);
                    int capacity = parseInt(size.getText().toString());
                    Switch isVIP = (Switch) findViewById(R.id.vip);
                    if (isVIP.isChecked())
                    {
                        controller.scheduleEvent(startTime, endTime, roomID, eventName, capacity, controller.getVipEventManager());
                    }
                    else {
                        controller.scheduleEvent(startTime, endTime, roomID, eventName, capacity, controller.getEventManager());
                    }
                }
                catch(NumberFormatException n){
                    pushMessage("Please enter an integer for the ID");
                }
                catch(DateTimeParseException d)
                {
                    pushMessage("Please format your times as year-mm-ddThour:minute:second");
                }
                break;
            case R.id.back:
                    Intent myIntent = new Intent(this, seeAllEventsActivity.class);
                    myIntent.putExtra("cc", controller);
                    setResult(3, myIntent);
                    finish();
                    break;


        }
    }
}
