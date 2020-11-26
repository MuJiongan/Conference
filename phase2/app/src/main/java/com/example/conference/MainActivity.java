package com.example.conference;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.presenter.LogInPresenter;
import com.example.presenter.LogInPresenter;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements LogInPresenter.View,View.OnClickListener, Serializable {

    private LogInPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);



        presenter = new LogInPresenter(this);

        pushMessage("Files read");

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
//                if ( getIntent().getSerializableExtra("presenter") != null) {
//                    presenter = (LogInPresenter) getIntent().getSerializableExtra("presenter");
//                }
                EditText username1 = findViewById(R.id.Usernameinput);
                EditText password1 = findViewById(R.id.passwordinput);
                String username = username1.getText().toString();
                String password = password1.getText().toString();
                boolean successful = presenter.validate(username, password);
                if (successful)
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
                myIntent2.putExtra("presenter", presenter);
                startActivityForResult(myIntent2, 0);
                break;


        }
    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        if (requestCode == 0){
//            if (resultCode == 3){
//                Toast.makeText(this, "RUNSDFSLKDFJLKS", Toast.LENGTH_SHORT).show();
//                presenter = (LogInPresenter) data.getSerializableExtra("presenter");
//            }
//        }
//    }

    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }
}