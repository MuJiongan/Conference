package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.presenter.LogInPresenter;
import com.example.presenter.SpeakerController;

public class SpeakerMenu extends Activity implements View.OnClickListener, SpeakerController.View{

    private SpeakerController sc;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speakermenu);
        LogInPresenter presenter = (LogInPresenter) getIntent().getSerializableExtra("presenter");

        sc = new SpeakerController(presenter.getAm(), presenter.getOm(), presenter.getSm(), presenter.getRm(), presenter.getEm(), presenter.getMm(), presenter.getUserID(), this, presenter.getVipManager(), presenter.getVipEvent());


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.viewMyEvents:
                Toast.makeText(this, "These are all my events", Toast.LENGTH_SHORT).show();
                Intent myIntent2 = new Intent(v.getContext(), seeMyEventsActivity.class);
                myIntent2.putExtra("cc", sc);
                startActivityForResult(myIntent2, 0);
                break;
            case R.id.manage:
                Toast.makeText(this, "Manage my account", Toast.LENGTH_SHORT).show();
                Intent myIntent3 = new Intent(v.getContext(), manageMyAccountActivity.class);
                myIntent3.putExtra("sc", sc);
                startActivityForResult(myIntent3, 0);
                break;
            case R.id.viewContactList:
                Toast.makeText(this, "These are all my Contacts", Toast.LENGTH_SHORT).show();
                Intent myIntent4 = new Intent(v.getContext(), viewContactListActivity.class);
                myIntent4.putExtra("sc", sc);
                startActivityForResult(myIntent4, 0);
                break;
            case R.id.social:
                Toast.makeText(this, "All your social networking", Toast.LENGTH_SHORT).show();
                Intent myIntent5 = new Intent(v.getContext(), SocialNetworking.class);
                myIntent5.putExtra("sc", sc);
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

    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

}


