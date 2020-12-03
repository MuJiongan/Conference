package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.presenter.UserController;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class messageActivity extends Activity implements UserController.View, View.OnClickListener, Serializable {


    private UserController currentController;
    int index;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        currentController = (UserController) getIntent().getSerializableExtra("cc");
        currentController.setView(this);
        // get the receiver ID
        index = getIntent().getIntExtra("eventID", -1);
        showHistory();
    }


    public void showHistory() {

        TextView history = findViewById(R.id.history);
        String historyText = "";
        ArrayList<String> list = currentController.viewChatHistory(index);
        if (list != null){
            for (String s: list){
                historyText = historyText + s + "\n";
            }
            history.setText(historyText);
        }else{
            history.setText("There is no message right now");
        }


    }


    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                // get the receiver ID
                String receiverID = (String) getIntent().getSerializableExtra("receiverID");

                int index = Integer.parseInt(receiverID);
                EditText message = findViewById(R.id.messageContent);
                String messageString = message.getText().toString();
                if (!messageString.equals("")) {
                    currentController.sendMessage(index, messageString);
                    showHistory();
                } else {
                    pushMessage("Your message can't be empty!");
                }

                break;
            case R.id.back:
                if (currentController.getType().equals("SpeakerController")) {
                    Intent myIntent1 = new Intent(this, SpeakerMenu.class);
                    myIntent1.putExtra("cc", currentController);
                    setResult(3, myIntent1);
                    finish();
                } else if (currentController.getType().equals("OrganizerController")) {
                    Intent myIntent2 = new Intent(this, OrganizerMenu.class);
                    myIntent2.putExtra("cc", currentController);
                    setResult(3, myIntent2);
                    finish();
                } else if (currentController.getType().equals("AttendeeController")) {
                    Intent myIntent3 = new Intent(this, AttendeeMenu.class);
                    myIntent3.putExtra("cc", currentController);
                    setResult(3, myIntent3);
                    finish();
                } else if (currentController.getType().equals("VIPController")) {
                    Intent myIntent3 = new Intent(this, AttendeeMenu.class);
                    myIntent3.putExtra("cc", currentController);
                    setResult(3, myIntent3);
                    finish();
                }
            case R.id.markAsUnread:
                EditText messageIdText = findViewById(R.id.messageId);
                String messageIdString = messageIdText.getText().toString();
                int messageId = Integer.parseInt(messageIdString);
//                if (messageId){
//
//                }
//                else{
                    try {
                        currentController.markAsUnread(messageId);
                    }
                    catch(NumberFormatException n){
                        pushMessage("Please enter a valid MessageID");
                    }
                }

//        }
    }
}