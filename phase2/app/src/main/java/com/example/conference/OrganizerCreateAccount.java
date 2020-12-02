package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.model.useCases.OrganizerManager;
import com.example.presenter.LogInPresenter;
import com.example.presenter.OrganizerController;
import com.example.presenter.UserController;

import java.io.Serializable;

public class OrganizerCreateAccount extends Activity implements View.OnClickListener, LogInPresenter.View, Serializable{

    private OrganizerController controller;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizercreateaccount);
        controller = (OrganizerController) getIntent().getSerializableExtra("cc");

    }
    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

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
                if (controller.hasUserName(usernameString)){
                    pushMessage("Username already exists!");
                }else{
                    controller.createUser(nameString,usernameString,passwordString,"Attendee");
                }


                break;
            case R.id.createOrganizeraccount:
                if (controller.hasUserName(usernameString)){
                    pushMessage("Username already exists!");
                }else{
                controller.createUser(nameString,usernameString,passwordString, "Organizer");}
                break;
            case R.id.createspeaker:
                if (controller.hasUserName(usernameString)){
                    pushMessage("Username already exists!");
                }else{
                controller.createUser(nameString, usernameString,passwordString, "Speaker");}
            case R.id.createvip:
                if (controller.hasUserName(usernameString)){
                    pushMessage("Username already exists!");
                }else{
                controller.createUser(nameString,usernameString,passwordString, "Vip");}
                // TODO: push message is not created successfully, and check the implementation of createUser

            case R.id.back:

                Intent myIntent2 = new Intent(this, OrganizerMenu.class);
                myIntent2.putExtra("cc", controller);
                setResult(3, myIntent2);
                finish();
        }
    }
}
