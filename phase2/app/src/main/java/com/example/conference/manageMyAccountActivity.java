package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class manageMyAccountActivity extends Activity implements View.OnClickListener{
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managemyaccount);

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(v.getContext(), AttendeeMenu.class);
                startActivityForResult(myIntent, 0);
                break;

        }
    }
}

