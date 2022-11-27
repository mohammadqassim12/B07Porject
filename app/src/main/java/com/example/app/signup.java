package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    //User input
    EditText first_name_in;
    EditText password_in;
    EditText ID_num_in;
    Button submit_button;

    private TextView back_click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);
        first_name_in = (EditText) findViewById(R.id.editTextTextPersonName);
        password_in = (EditText) findViewById(R.id.editTextTextPersonName4);
        ID_num_in = (EditText) findViewById(R.id.editTextTextPersonName5);
        submit_button = (Button) findViewById(R.id.button);
        submit_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //TODO: Insert a verification to make sure two IDs can't exist in the same database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference User_Data = database.getInstance().getReference("User Database");
                User_Data.child("name").setValue(first_name_in.getText().toString());
                User_Data.child("password").setValue(password_in.getText().toString());
                User_Data.child("ID").setValue(ID_num_in.getText().toString());
            }
        });


        back_click = (TextView) findViewById(R.id.button2);
        back_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signup.this, login.class));
            }
        });
    }
}