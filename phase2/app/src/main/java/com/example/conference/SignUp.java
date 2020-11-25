package com.example.conference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SignUp extends Activity implements View.OnClickListener{
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createaccount:
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
                break;



        }
    }
}
