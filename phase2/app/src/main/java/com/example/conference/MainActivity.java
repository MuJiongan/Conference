package com.example.conference;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.model.interfaceAdapters.ReadWrite;
import com.example.model.useCases.AttendeeManager;
import com.example.presenter.LogInPresenter;
import com.example.presenter.LogInPresenter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MainActivity extends AppCompatActivity implements LogInPresenter.View,View.OnClickListener{

    private LogInPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        Context context = this;
        presenter = new LogInPresenter(this);
    }
    

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                EditText username1 = findViewById(R.id.Usernameinput);
                EditText password1 = findViewById(R.id.passwordinput);
                String username = username1.getText().toString();
                String password = password1.getText().toString();
                boolean succesful = presenter.validate(username, password);
                if (succesful)
                {
                    Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(v.getContext(), AttendeeMenu.class);
                    startActivityForResult(myIntent, 0);
                }
                else
                {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.signup:
                Intent myIntent2 = new Intent(v.getContext(), SignUp.class);
                startActivityForResult(myIntent2, 0);
                break;


        }
    }

    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }
}