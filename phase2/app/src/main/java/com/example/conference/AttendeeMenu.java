

package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;
import com.example.model.interfaceAdapters.ReadWrite;
import com.example.presenter.*;

import java.io.Serializable;

public class AttendeeMenu extends Activity implements View.OnClickListener, UserController.View, Serializable {
    private AttendeeController controller;

    /**
     * Create this new activity
     * @param  savedInstanceState the saved instanceState
     */
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendeemenu);
        LogInPresenter presenter = (LogInPresenter) getIntent().getSerializableExtra("presenter");
        if (presenter.getAm().idInList(presenter.getUserID())) {
            controller = new AttendeeController(presenter.getAm(), presenter.getOm(), presenter.getSm(), presenter.getRm(), presenter.getEm(),
                    presenter.getMm(), presenter.getVipManager(), presenter.getVipEvent(), presenter.getUserID(), this);
        }
        else {
            controller = new VipController(presenter.getAm(), presenter.getOm(), presenter.getSm(), presenter.getRm(), presenter.getEm(),
                    presenter.getMm(), presenter.getVipManager(), presenter.getVipEvent(), presenter.getUserID(), this);
        }
    }


    /**
     * Perform certain actions when the user clicks a button
     * @param v view of the current activity
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.viewAllEvents:
                Toast.makeText(this, "These are all your events", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(v.getContext(), seeAllEventsActivity.class);
                myIntent.putExtra("controller", controller);
                startActivityForResult(myIntent, 3);
                break;
            case R.id.viewMyEvents:
                Toast.makeText(this, "These are all my events", Toast.LENGTH_SHORT).show();
                Intent myIntent2 = new Intent(v.getContext(), seeMyEventsActivity.class);
                myIntent2.putExtra("controller", controller);
                startActivityForResult(myIntent2, 4);
                break;
            case R.id.manage:
                Toast.makeText(this, "Manage my account", Toast.LENGTH_SHORT).show();
                Intent myIntent3 = new Intent(v.getContext(), manageMyAccountActivity.class);
                myIntent3.putExtra("controller", controller);
                startActivityForResult(myIntent3, 5);
                break;
            case R.id.viewContactList:
                Toast.makeText(this, "These are all my Contacts", Toast.LENGTH_SHORT).show();
                Intent myIntent4 = new Intent(v.getContext(), viewContactListActivity.class);
                myIntent4.putExtra("controller", controller);
                startActivityForResult(myIntent4, 6);
                break;
            case R.id.social:
                Toast.makeText(this, "Network", Toast.LENGTH_SHORT).show();
                Intent myIntent5 = new Intent(v.getContext(), SocialNetworking.class);
                myIntent5.putExtra("controller", controller);
                startActivityForResult(myIntent5, 7);
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
                Intent myIntent6 = new Intent(AttendeeMenu.this, MainActivity.class);
                myIntent6.putExtra("controller", controller);
                setResult(2, myIntent6);
                finish();
        }
    }

    // Get the data from previous activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

            if (resultCode == 3){
                UserController passedData = (UserController) data.getSerializableExtra("cc");
                controller.setManagers(passedData.getAttendeeManager(), passedData.getOrganizerManager(), passedData.getSpeakerManager(), passedData.getRoomManager(),
                        passedData.getEventManager(), passedData.getMessageManager(), passedData.getVipManager(), passedData.getVipEventManager());
                controller.setView(this);
            }
        }


    /**
     * Display a toast message given a string
     * @param info message content of the toast message
     */
    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }
}
