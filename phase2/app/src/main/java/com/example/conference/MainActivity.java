package com.example.conference;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.presenter.LogInPresenter;
import com.example.presenter.UserController;
import java.io.*;


public class MainActivity extends AppCompatActivity implements LogInPresenter.View,View.OnClickListener, Serializable {

    private LogInPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);


        presenter = new LogInPresenter(this, getApplicationContext());
        pushMessage("Files read");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                EditText username1 = findViewById(R.id.Usernameinput);
                EditText password1 = findViewById(R.id.passwordinput);
                String username = username1.getText().toString();
                String password = password1.getText().toString();
                Object obj = presenter.validate(username, password);
                if (obj!= null)
                    {
                        Toast.makeText(this, "Login successfully!!", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(v.getContext(), (Class<?>) obj);
                        myIntent.putExtra("presenter", presenter);
                        startActivityForResult(myIntent, 2);
                    }
                else
                    {
                        Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                break;
            case R.id.signup:
                Intent myIntent2 = new Intent(v.getContext(), SignUp.class);
                myIntent2.putExtra("presenter", presenter);
                startActivityForResult(myIntent2, 1);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1){
            if (resultCode == 3){
                presenter = (LogInPresenter) data.getSerializableExtra("presenter");
                presenter.setView(this);
            }
        }
        else if (requestCode == 2)
        {
            if (resultCode == 2)
            {
                UserController controller = (UserController) data.getSerializableExtra("controller");
                presenter.setManagers(controller.getAttendeeManager(), controller.getOrganizerManager(), controller.getSpeakerManager(),
                        controller.getRoomManager(), controller.getEventManager(), controller.getMessageManager(), controller.getVipManager(),
                        controller.getVipEventManager());
                presenter.setView(this);
            }
        }
    }

    @Override
    public void pushMessage(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }
}