package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.presenter.AttendeeController;
import android.widget.Toast;
import com.example.presenter.OrganizerController;
import com.example.presenter.UserController;
import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;


public class  seeAllEventsActivity extends Activity implements View.OnClickListener, UserController.View, Serializable {
    private AttendeeController controller;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        controller = (AttendeeController) getIntent().getSerializableExtra("controller");
        controller.setView(this);
        if (controller.getType().equals("AttendeeController") || controller.getType().equals("VIPController"))
        {
            setContentView(R.layout.seeallevents);
        }
        else
        {
            setContentView(R.layout.organizerseeallevents);
        }
        setAllEventsText();

    }
    public void setAllEventsText()
    {
        TextView allevents = findViewById(R.id.allEvents);
        allevents.setText(controller.viewAllEvents());
    }
    @Override
    public void onClick(View v) {
        EditText event = findViewById(R.id.eventIDinput);
        switch (v.getId()){
            case R.id.signup:
                try {
                    int eventID = parseInt(event.getText().toString());
                    controller.signUp(eventID);
                }
                catch(NumberFormatException n){
                    pushMessage("Please enter a valid eventID");
                }
                break;
            case R.id.cancel:
                try {
                    int eventID = parseInt(event.getText().toString());
                    ((OrganizerController)controller).cancelEvent(eventID);
                }
                catch(NumberFormatException n){
                    pushMessage("Please enter a valid eventID");
                }
                break;
            case R.id.schedule:
                Intent schedule = new Intent (this, OrganizerScheduleEvent.class);
                schedule.putExtra("cc", controller);
                startActivityForResult(schedule, 10);
                break;
            case R.id.reschedule:
                Intent reschedule = new Intent(this, OrganizerReschedule.class);
                try{
                    int eventID = parseInt(event.getText().toString());
                    reschedule.putExtra("eventID", eventID);
                    reschedule.putExtra("cc", controller);
                    startActivityForResult(reschedule, 11);
                }
                catch(NumberFormatException n){
                    pushMessage("Please enter a valid eventID");
                }

            case R.id.back:
                if (controller.getType().equals("VIPController")){
                    Intent myIntent = new Intent(this, SpeakerMenu.class);
                    myIntent.putExtra("cc", controller);
                    setResult(3, myIntent);
                    finish();
                }
                 if (controller.getType().equals("OrganizerController")){
                    Intent myIntent = new Intent(this, OrganizerMenu.class);
                    myIntent.putExtra("cc", controller);
                    setResult(3, myIntent);
                    finish();
                }
                else if (controller.getType().equals("AttendeeController")){
                    Intent myIntent = new Intent(this, AttendeeMenu.class);
                    myIntent.putExtra("cc", controller);
                    setResult(3, myIntent);
                    finish();
                }
                break;
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 10){
            if (resultCode == 3){
                UserController passedData = (UserController) data.getSerializableExtra("cc");
                controller.setManagers(passedData.getAttendeeManager(), passedData.getOrganizerManager(), passedData.getSpeakerManager(), passedData.getRoomManager(),
                        passedData.getEventManager(), passedData.getMessageManager(), passedData.getVipManager(), passedData.getVipEventManager());
                controller.setView(this);
            }
        }
        if (requestCode == 11){
            if (resultCode == 3){
                UserController passedData = (UserController) data.getSerializableExtra("cc");
                controller.setManagers(passedData.getAttendeeManager(), passedData.getOrganizerManager(), passedData.getSpeakerManager(), passedData.getRoomManager(),
                        passedData.getEventManager(), passedData.getMessageManager(), passedData.getVipManager(), passedData.getVipEventManager());
                controller.setView(this);
            }
        }
    }
    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

}
