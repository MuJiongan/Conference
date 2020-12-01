package com.example.conference;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.presenter.AttendeeController;
import com.example.presenter.OrganizerController;
import com.example.presenter.UserController;

import java.io.Serializable;

public class OrganizerScheduleEvent extends Activity implements View.OnClickListener, UserController.View, Serializable {
    private OrganizerController controller;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizerscheduleevent);
        controller = (OrganizerController) getIntent().getSerializableExtra("cc");
        controller.setView(this);
    }

    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

    }
}
