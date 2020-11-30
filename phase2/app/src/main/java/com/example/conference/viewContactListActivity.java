package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.presenter.UserController;

import java.util.ArrayList;
import java.util.HashMap;

public class viewContactListActivity extends Activity implements UserController.View, View.OnClickListener {
    private UserController currentController;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontactlist);
        currentController = (UserController) getIntent().getSerializableExtra("cc");
        currentController.setView(this);
        displayContactList();

    }

    public void displayContactList(){
        String message = "UNREAD" + "\n";
        HashMap<String, ArrayList<String>> messageMap = currentController.viewContactList();
        for (String s: messageMap.get("unread")){
            message = message + s + "\n";

        }
        message = message + "READ" + "\n";
        for (String s: messageMap.get("read")){
            message = message + s + "\n";

        }
        TextView allContacts = findViewById(R.id.allContacts);
        allContacts.setText(message);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.viewHistory:
                EditText userID = findViewById(R.id.userIDInput);
                String userIDString = userID.getText().toString();
                // TODO: PASS THE USERID TO THE NEW ACTIVITY AND ALSO START THE NEW ACTIVITY
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
