package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        ID_num_in = (EditText) findViewById(R.id.editTextTextPersonName6);
        submit_button = (Button) findViewById(R.id.button);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText confirmation = findViewById(R.id.editTextTextPersonName5);
                String ID = ID_num_in.getText().toString();
                boolean acceptable_ID = ID.charAt(0) == 'a' || ID.charAt(0) == 's';
                DatabaseReference User_Data = FirebaseDatabase.getInstance().getReference("User Database" + ID);
                DatabaseReference User_Node = User_Data.child(ID);
                User_Node.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!(dataSnapshot.exists())) {
                            if (acceptable_ID && password_in.getText().toString().equals(confirmation.getText().toString())) {
                                User_Node.child("name").setValue(first_name_in.getText().toString());
                                User_Node.child("password").setValue(password_in.getText().toString());
                                User_Node.child("ID").setValue(ID);
                                startActivity(new Intent(signup.this, login.class));
                            } else if (!acceptable_ID) {
                                Snackbar mySnackbar = Snackbar.make(v, "Invalid ID. ID must begin with 'a' (For admins) or 's' (For students)", 5000);
                                mySnackbar.show();
                            } else {
                                Snackbar mySnackbar = Snackbar.make(v, "Passwords Do Not Match", 2000);
                                mySnackbar.show();
                            }
                        } else {
                            Snackbar mySnackbar = Snackbar.make(v, "Invalid ID. ID already exists", 5000);
                            mySnackbar.show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
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

