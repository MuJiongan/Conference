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
import java.util.List;

import static java.lang.Integer.parseInt;

public class messageActivity extends Activity implements UserController.View, View.OnClickListener, Serializable {

    private UserController currentController;
    private int index;
    private String parent;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        currentController = (UserController) getIntent().getSerializableExtra("cc");
        currentController.setView(this);
        // get the receiver ID
        index = getIntent().getIntExtra("receiverID", -1);
        parent = getIntent().getStringExtra("parent");
        showHistory();
    }


    public void showHistory() {

        TextView history = findViewById(R.id.allMessages);
        String historyText = "";
        List<String> list = currentController.viewChatHistory(index);
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
                Intent myIntent4;
                if (parent.equals("contact"))
                {
                    myIntent4 = new Intent(this, viewContactListActivity.class);
                }
               else
                {
                    myIntent4 = new Intent(this, SocialNetworking.class);
                }
                myIntent4.putExtra("cc", currentController);
                setResult(3, myIntent4);
                finish();
                break;
                
            case R.id.markAsUnread:
                EditText messageIdText = findViewById(R.id.messageId);
                String messageIdString = messageIdText.getText().toString();
                try {
                        int messageId = Integer.parseInt(messageIdString);
                        currentController.markAsUnread(messageId, index);
                    }
                    catch(NumberFormatException n){
                        pushMessage("Please enter a valid MessageID");
                    }
                }
    }
}