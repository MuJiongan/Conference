package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.model.interfaceAdapters.ReadWrite;
import com.example.presenter.LogInPresenter;

import java.io.Serializable;

public class SignUp extends Activity implements View.OnClickListener, LogInPresenter.View, Serializable{
    private LogInPresenter presenter;

    /**
     * Create this new activity
     * @param  savedInstanceState the saved instanceState
     */
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        presenter = (LogInPresenter) getIntent().getSerializableExtra("presenter");
        presenter.setView(this);
    }

    /**
     * Display a toast message given a string
     * @param info message content of the toast message
     */
    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }


    /**
     * Perform certain actions when the user clicks a button
     * @param v view of the current activity
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createaccount:
                //Get user inputted string
                EditText name = findViewById(R.id.nameinput);
                String nameString = name.getText().toString();
                EditText username = findViewById(R.id.Usernameinput);
                String usernameString = username.getText().toString();
                EditText password = findViewById(R.id.passwordinput);
                String passwordString = password.getText().toString();

                if (presenter.createAttendeeAccount(nameString, usernameString, passwordString)){
                    //Serialize objects
                    ReadWrite.saveAttendees(getApplicationContext(), presenter.getAm());
                    ReadWrite.saveOrganizers(getApplicationContext(), presenter.getOm());
                    ReadWrite.saveSpeakers(getApplicationContext(), presenter.getSm());
                    ReadWrite.saveEvent(getApplicationContext(), presenter.getEm());
                    ReadWrite.saveMessage(getApplicationContext(), presenter.getMm());
                    ReadWrite.saveRoom(getApplicationContext(), presenter.getRm());
                    ReadWrite.saveVips(getApplicationContext(), presenter.getVipManager());
                    ReadWrite.saveVipEventManager(getApplicationContext(), presenter.getVipEvent());
                    Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(SignUp.this, MainActivity.class);
                    myIntent.putExtra("presenter", presenter);
                    setResult(3, myIntent);
                    finish();
                }
            case R.id.back:
                Intent myIntent = new Intent(SignUp.this, MainActivity.class);
                setResult(4, myIntent);
                finish();
        }
    }
}
