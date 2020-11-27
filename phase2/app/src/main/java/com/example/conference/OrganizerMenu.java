package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OrganizerMenu extends Activity implements View.OnClickListener{


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizermenu);
        // EditText view = findViewById(R.id.viewAllEvents);
        // String string = view.getText().toString();




    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.viewAllEvents:
                Toast.makeText(this, "These are all your events", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(v.getContext(), seeAllEventsActivity.class);
                startActivityForResult(myIntent, 0);
                break;
            case R.id.viewMyEvents:
                Toast.makeText(this, "These are all my events", Toast.LENGTH_SHORT).show();
                Intent myIntent2 = new Intent(v.getContext(), seeMyEventsActivity.class);
                startActivityForResult(myIntent2, 0);
                break;
            case R.id.createaccount:
                Intent accountIntent = new Intent(v.getContext(), SocialNetworking.class);
                startActivityForResult(accountIntent, 0);
                break;
            case R.id.manage:
                Toast.makeText(this, "Manage my account", Toast.LENGTH_SHORT).show();
                Intent myIntent3 = new Intent(v.getContext(), manageMyAccountActivity.class);
                startActivityForResult(myIntent3, 0);
                break;
            case R.id.viewContactList:
                Toast.makeText(this, "These are all my Contacts", Toast.LENGTH_SHORT).show();
                Intent myIntent4 = new Intent(v.getContext(), viewContactListActivity.class);
                startActivityForResult(myIntent4, 0);
                break;
            case R.id.social:
                Toast.makeText(this, "All your social networking", Toast.LENGTH_SHORT).show();
                Intent myIntent5 = new Intent(v.getContext(), SocialNetworking.class);
                startActivityForResult(myIntent5, 0);
                break;
            case R.id.exit:
                Toast.makeText(this, "See you!", Toast.LENGTH_SHORT).show();
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                break;


        }
    }
}
