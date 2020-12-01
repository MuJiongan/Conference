package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.model.interfaceAdapters.ReadWrite;
import com.example.presenter.LogInPresenter;
import com.example.presenter.OrganizerController;
import com.example.presenter.UserController;

import java.io.Serializable;

public class OrganizerMenu extends Activity implements View.OnClickListener, UserController.View, Serializable {
    private OrganizerController controller;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizermenu);
        LogInPresenter presenter = (LogInPresenter) getIntent().getSerializableExtra("presenter");
        controller = new OrganizerController(presenter.getAm(), presenter.getOm(), presenter.getSm(), presenter.getRm(), presenter.getEm(),
                presenter.getMm(),presenter.getVipManager(), presenter.getVipEvent(), presenter.getUserID(),this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.viewAllEvents:
                Toast.makeText(this, "These are all your events", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(v.getContext(), seeAllEventsActivity.class);
                myIntent.putExtra("controller", controller);
                startActivityForResult(myIntent, 1);
                break;
            case R.id.viewMyEvents:
                Toast.makeText(this, "These are all my events", Toast.LENGTH_SHORT).show();
                Intent myIntent2 = new Intent(v.getContext(), seeMyEventsActivity.class);
                startActivityForResult(myIntent2, 2);
                break;
            case R.id.createaccount:
                Intent accountIntent = new Intent(v.getContext(), SocialNetworking.class);
                startActivityForResult(accountIntent, 3);
                break;
            case R.id.manage:
                Toast.makeText(this, "Manage my account", Toast.LENGTH_SHORT).show();
                Intent myIntent3 = new Intent(v.getContext(), manageMyAccountActivity.class);
                startActivityForResult(myIntent3, 4);
                break;
            case R.id.viewContactList:
                Toast.makeText(this, "These are all my Contacts", Toast.LENGTH_SHORT).show();
                Intent myIntent4 = new Intent(v.getContext(), viewContactListActivity.class);
                startActivityForResult(myIntent4, 5);
                break;
            case R.id.social:
                Toast.makeText(this, "Networking", Toast.LENGTH_SHORT).show();
                Intent myIntent5 = new Intent(v.getContext(), SocialNetworking.class);
                startActivityForResult(myIntent5, 6);
                break;
            case R.id.exit:
                //Serialize objects
                ReadWrite.saveAttendees(getApplicationContext(),controller.getAttendeeManager());
                ReadWrite.saveOrganizers(getApplicationContext(), controller.getOrganizerManager());
                ReadWrite.saveSpeakers(getApplicationContext(), controller.getSpeakerManager());
                ReadWrite.saveEvent(getApplicationContext(), controller.getEventManager());
                ReadWrite.saveMessage(getApplicationContext(), controller.getMessageManager());
                ReadWrite.saveRoom(getApplicationContext(), controller.getRoomManager());
                ReadWrite.saveVips(getApplicationContext(), controller.getVipManager());
                ReadWrite.saveVipEventManager(getApplicationContext(), controller.getVipEventManager());

                //Send information back to main activity
                Intent myIntent6 = new Intent(OrganizerMenu.this, MainActivity.class);
                myIntent6.putExtra("controller", controller);
                setResult(2, myIntent6);
                finish();
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1){
            if (resultCode == 3){
                UserController passedData = (UserController) data.getSerializableExtra("cc");
                controller.setManagers(passedData.getAttendeeManager(), passedData.getOrganizerManager(), passedData.getSpeakerManager(), passedData.getRoomManager(),
                        passedData.getEventManager(), passedData.getMessageManager(), passedData.getVipManager(), passedData.getVipEventManager());
                controller.setView(this);
            }
        }
    }


    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }
}
