package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.model.entities.Organizer;
import com.example.presenter.OrganizerController;
import com.example.presenter.UserController;

import java.io.Serializable;

import static java.lang.Integer.parseInt;

public class AssignSpeaker extends Activity implements UserController.View, View.OnClickListener, Serializable {


    private OrganizerController currentController;
    int index;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignspeaker);
        currentController = (OrganizerController) getIntent().getSerializableExtra("cc");
        currentController.setView(this);
        // get the receiver ID
        index = getIntent().getIntExtra("eventID", -1);
        showSpeaker();
    }


    public void showSpeaker() {

    }


    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.assign:
                // get the receiver ID
                EditText speakerIDEditText = findViewById(R.id.speakerID);
                String speakerID = speakerIDEditText.getText().toString();
                try {
                    int speaker = parseInt(speakerID);
                    currentController.assignSpeaker(speaker, index);
                }
                catch(NumberFormatException n){
                    pushMessage("Please enter a valid speakerID");
                }

                break;
            case R.id.back:

                Intent myIntent2 = new Intent(this, OrganizerMenu.class);
                myIntent2.putExtra("cc", currentController);
                setResult(3, myIntent2);
                finish();


        }


    }
}