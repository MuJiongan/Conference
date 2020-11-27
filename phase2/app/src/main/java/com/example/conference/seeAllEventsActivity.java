package com.example.conference;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TableLayout;

import java.util.ArrayList;

public class  seeAllEventsActivity extends Activity {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seeallevents);
    }




    public void displayEvents(ArrayList<Integer> eventIDs, ArrayList<String> eventNames, ArrayList<String> roomNames, ArrayList<String> time){

        String heading1 = "Events";
        String heading2 = "Time";
        String heading3 = "Vacancy";
        String heading4 = "Room";
        String text = "Here are all the scheduled events:";
        // TODO: Display the events using the same format we did in text user interface

    }
}
