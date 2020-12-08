package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.presenter.LogInPresenter;
import com.example.presenter.OrganizerController;


import java.io.Serializable;

public class OrganizerCreateAccount extends Activity implements View.OnClickListener, LogInPresenter.View, Serializable{

    private OrganizerController controller;

    /**
     * Create this new activity
     * @param  savedInstanceState the saved instanceState
     */
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizercreateaccount);
        controller = (OrganizerController) getIntent().getSerializableExtra("cc");
    }

    /**
     * Display a toast message given a string
     * @param info message content of the toast message
     */
    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }


    /**
     * Perform certain actions when the user clicks a button
     * @param v view of the current activity
     */
    @Override
    public void onClick(View v) {
        //Get user inputted string
        EditText name = findViewById(R.id.nameinput);
        String nameString = name.getText().toString();
        EditText username = findViewById(R.id.Usernameinput);
        String usernameString = username.getText().toString();
        EditText password = findViewById(R.id.passwordinput);
        String passwordString = password.getText().toString();
        switch (v.getId()){
            case R.id.createattendee:
                if (nameString.equals("") || usernameString.equals("") || passwordString.equals("")){
                    pushMessage("You can't leave any of the input field empty");
                }
                else if (controller.hasUserName(usernameString)){
                    pushMessage("Username already exists!");
                }else{
                    controller.createUser(nameString,usernameString,passwordString,"Attendee");
                    pushMessage("Account created successfully");
                }


                break;
            case R.id.createOrganizeraccount:
                if (nameString.equals("") || usernameString.equals("") || passwordString.equals("")){
                    pushMessage("You can't leave any of the input field empty");
                }
                else if (controller.hasUserName(usernameString)){
                    pushMessage("Username already exists!");
                }else{
                controller.createUser(nameString,usernameString,passwordString, "Organizer");
                    pushMessage("Account created successfully");}
                break;
            case R.id.createspeaker:
                if (nameString.equals("") || usernameString.equals("") || passwordString.equals("")){
                    pushMessage("You can't leave any of the input field empty");
                }
                else if (controller.hasUserName(usernameString)){
                    pushMessage("Username already exists!");
                }else{
                controller.createUser(nameString, usernameString,passwordString, "Speaker");
                    pushMessage("Account created successfully");}
                break;
            case R.id.createvip:
                if (nameString.equals("") || usernameString.equals("") || passwordString.equals("")){
                    pushMessage("You can't leave any of the input field empty");
                }
                else if (controller.hasUserName(usernameString)){
                    pushMessage("Username already exists!");
                }else{
                controller.createUser(nameString,usernameString,passwordString, "Vip");
                    pushMessage("Account created successfully");}
                // TODO: push message is not created successfully, and check the implementation of createUser
                break;
            case R.id.back:

                Intent myIntent2 = new Intent(this, OrganizerMenu.class);
                myIntent2.putExtra("cc", controller);
                setResult(3, myIntent2);
                finish();
        }
    }
}
