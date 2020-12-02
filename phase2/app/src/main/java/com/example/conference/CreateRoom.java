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

public class CreateRoom extends Activity implements View.OnClickListener, UserController.View, Serializable {

    private OrganizerController controller;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createroom);
        controller = (OrganizerController) getIntent().getSerializableExtra("controller");
        controller.setView(this);
        displayRooms();
    }
    public void displayRooms(){
        //TODO: set a string
    }
    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create:
                EditText roomName = findViewById(R.id.roomName);
                EditText capacity = findViewById(R.id.capacity);
                String roomNameString = roomName.getText().toString();
                String capacityString = capacity.getText().toString();
                if (roomName.equals("")){
                    pushMessage("Your room name can't be empty");
                }
                try{
                    int index = Integer.parseInt(capacityString);
                    if (index > 0){controller.enterRoom(roomNameString, index);
                        Toast.makeText(this, "room created", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        pushMessage("Please enter a capacity > 0");
                    }


                }catch(NumberFormatException n){
                    pushMessage("Please enter a valid capacity");
                }
                displayRooms();
            case R.id.back:
                Intent myIntent = new Intent(this, seeAllEventsActivity.class);
                myIntent.putExtra("cc", controller);
                setResult(3, myIntent);
                finish();


        }
    }
}
