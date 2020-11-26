package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.model.useCases.OrganizerManager;
import com.example.presenter.LogInPresenter;

import java.io.Serializable;

public class SignUp extends Activity implements View.OnClickListener, LogInPresenter.View{
    private LogInPresenter presenter;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        presenter = (LogInPresenter) getIntent().getSerializableExtra("presenter");
        presenter.setView(this);

    }
    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createaccount:

                EditText name = findViewById(R.id.nameinput);
                String nameString = name.getText().toString();
                EditText username = findViewById(R.id.Usernameinput);
                String usernameString = username.getText().toString();
                EditText password = findViewById(R.id.passwordinput);
                String passwordString = password.getText().toString();

                if (presenter.createAttendeeAccount(nameString, usernameString, passwordString)){
                    Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                    myIntent.putExtra("presenter", presenter);
                    startActivityForResult(myIntent, 0);
//                    setResult(3, myIntent);
//                    finish();





                }
                break;



        }
    }

}
