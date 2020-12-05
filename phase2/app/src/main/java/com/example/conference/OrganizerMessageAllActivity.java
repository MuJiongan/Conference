package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.presenter.OrganizerController;
import com.example.presenter.UserController;

import java.io.Serializable;


public class OrganizerMessageAllActivity extends Activity implements UserController.View, View.OnClickListener, Serializable {

    private OrganizerController currentController;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizermessageall);
        currentController = (OrganizerController) getIntent().getSerializableExtra("controller");
        currentController.setView(this);
        // get the receiver ID

    }





    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.allAttendee:
                EditText message = findViewById(R.id.messageContent);
                String messageString = message.getText().toString();
                if (!messageString.equals("")) {
                    currentController.messageAll(currentController.getAttendeeManager(), messageString);

                } else {
                    pushMessage("Your message can't be empty!");
                }
                break;
            case R.id.allSpeaker:
                EditText message2 = findViewById(R.id.messageContent);
                String messageString2 = message2.getText().toString();
                if (!messageString2.equals("")) {
                    currentController.messageAll(currentController.getSpeakerManager(), messageString2);

                } else {
                    pushMessage("Your message can't be empty!");
                }
                break;
            case R.id.allVIP:
                EditText message3 = findViewById(R.id.messageContent);
                String messageString3 = message3.getText().toString();
                if (!messageString3.equals("")) {
                    currentController.messageAll(currentController.getVipManager(), messageString3);

                } else {
                    pushMessage("Your message can't be empty!");
                }

                break;
            case R.id.back:
                Intent myIntent2 = new Intent(this, OrganizerMenu.class);
                myIntent2.putExtra("cc", currentController);
                setResult(3, myIntent2);
                finish();





    }
}}