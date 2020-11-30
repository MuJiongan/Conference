package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.presenter.AttendeeController;
import android.widget.Toast;
import com.example.presenter.UserController;
import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;


public class  seeAllEventsActivity extends Activity implements View.OnClickListener, UserController.View, Serializable {
    private AttendeeController controller;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seeallevents);
        controller = (AttendeeController) getIntent().getSerializableExtra("controller");
        controller.setView(this);
        setAllEventsText();

    }
    public void setAllEventsText()
    {
        TextView allevents = findViewById(R.id.allEvents);
        allevents.setText(controller.viewAllEvents());
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup:
                EditText event = findViewById(R.id.eventIDinput);
                try {
                    int eventID = parseInt(event.getText().toString());
                    controller.signUp(eventID);
                }
                catch(NumberFormatException n){
                    pushMessage("Please enter a valid eventID");
                }
            case R.id.back:
                Intent myIntent = new Intent(this, AttendeeMenu.class);
                myIntent.putExtra("cc", controller);
                setResult(3, myIntent);
                finish();
////                if (controller.getType().equals("VIPController")){
////                    Intent myIntent = new Intent(this, SpeakerMenu.class);
////                    myIntent.putExtra("cc", currentController);
////                    setResult(3, myIntent);
////                    finish();
////                }
//                 if (controller.getType().equals("OrganizerController")){
//                    Intent myIntent = new Intent(this, OrganizerMenu.class);
//                    myIntent.putExtra("cc", controller);
//                    setResult(3, myIntent);
//                    finish();
//                }
////                else if (controller.getType().equals("AttendeeController")){
//                    Intent myIntent = new Intent(this, AttendeeMenu.class);
//                    myIntent.putExtra("cc", controller);
//                    setResult(3, myIntent);
//                    finish();
//                }

        }

    }



    public void displayEvents(ArrayList<Integer> eventIDs, ArrayList<String> eventNames, ArrayList<String> roomNames, ArrayList<String> time){

        String heading1 = "Events";
        String heading2 = "Time";
        String heading3 = "Vacancy";
        String heading4 = "Room";
        String text = "Here are all the scheduled events:";
        // TODO: Display the events using the same format we did in text user interface

    }


//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.signup:
//                // TODO
//                break;
//            case R.id.back:
//
//                if (currentController.getType().equals("SpeakerController")){
//                    Intent myIntent1 = new Intent(this, SpeakerMenu.class);
//                    myIntent1.putExtra("cc", currentController);
//                    setResult(3, myIntent1);
//                    finish();
//                }
//                else if (currentController.getType().equals("OrganizerController")){
//                    Intent myIntent2 = new Intent(this, OrganizerMenu.class);
//                    myIntent2.putExtra("cc", currentController);
//                    setResult(3, myIntent2);
//                    finish();
//                }
//                else if (currentController.getType().equals("AttendeeController")){
//                    Intent myIntent3 = new Intent(this, AttendeeMenu.class);
//                    myIntent3.putExtra("cc", currentController);
//                    setResult(3, myIntent3);
//                    finish();
//                }
//                else if (currentController.getType().equals("VIPController")){
//                    Intent myIntent3 = new Intent(this, AttendeeMenu.class);
//                    myIntent3.putExtra("cc", currentController);
//                    setResult(3, myIntent3);
//                    finish();
//                }
//
//        }
//    }

    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

}
