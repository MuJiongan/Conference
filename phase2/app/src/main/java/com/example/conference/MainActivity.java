package com.example.conference;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);


    }
    

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(v.getContext(), AttendeeMenu.class);
                startActivityForResult(myIntent, 0);
                break;
            case R.id.signup:
                Intent myIntent2 = new Intent(v.getContext(), SignUp.class);
                startActivityForResult(myIntent2, 0);
                break;


        }
    }
}