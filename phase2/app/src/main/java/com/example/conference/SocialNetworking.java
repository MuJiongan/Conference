package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.presenter.AttendeeController;
import com.example.presenter.UserController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Integer.parseInt;

public class SocialNetworking extends Activity implements UserController.View, View.OnClickListener, Serializable{
    private AttendeeController currentController;
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.socialnetworking);
        currentController = (AttendeeController) getIntent().getSerializableExtra("controller");
        currentController.setView(this);
        display();
    }

    public void display(){
        TextView social = findViewById(R.id.socialmessage);

        String socialMessage = "";
        HashMap<String, ArrayList<String>> hashMap =  currentController.viewRecommendedFriend();
        for (String score: hashMap.keySet()){
            socialMessage = socialMessage + score + " COMMON EVENTS:\n";
            for (String user: hashMap.get(score)){
                socialMessage = socialMessage + user + "\n";
            }
        }
        social.setText(socialMessage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.message:
                EditText userIDInput = findViewById(R.id.userIDInput);
                String userID = userIDInput.getText().toString();
                try{
                    int userIDInt = parseInt(userID);
                    if (currentController.hasUserID(userIDInt)){
                        Intent myIntent = new Intent(v.getContext(), messageActivity.class);
                        myIntent.putExtra("cc", currentController);
                        myIntent.putExtra("receiverID", userIDInt);
                        startActivityForResult(myIntent, 3);

                }}catch(NumberFormatException e){
                    pushMessage("Please enter an integer");
                }

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
                else if (currentController.getType().equals("VIPController")){
                    Intent myIntent3 = new Intent(this, AttendeeMenu.class);
                    myIntent3.putExtra("cc", currentController);
                    setResult(3, myIntent3);
                    finish();
                }
        }
    }


    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }
    }
