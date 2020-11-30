package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;
import com.example.presenter.UserController;

import java.util.ArrayList;

public class seeAllEventsActivity extends Activity implements UserController.View, View.OnClickListener {
    private UserController currentController;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seeallevents);
        currentController = (UserController) getIntent().getSerializableExtra("cc");
        currentController.setView(this);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup:
                // TODO
                break;
            case R.id.back:

                if (currentController.getType().equals("SpeakerController")){
                    Intent myIntent1 = new Intent(this, SpeakerMenu.class);
                    myIntent1.putExtra("cc", currentController);
                    setResult(3, myIntent1);
                    finish();
                }
                else if (currentController.getType().equals("OrganizerController")){
                    Intent myIntent2 = new Intent(this, OrganizerMenu.class);
                    myIntent2.putExtra("cc", currentController);
                    setResult(3, myIntent2);
                    finish();
                }
                else if (currentController.getType().equals("AttendeeController")){
                    Intent myIntent3 = new Intent(this, AttendeeMenu.class);
                    myIntent3.putExtra("cc", currentController);
                    setResult(3, myIntent3);
                    finish();
                }
                //TODO Also do it for the VIPMenu

        }
    }

    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

}
