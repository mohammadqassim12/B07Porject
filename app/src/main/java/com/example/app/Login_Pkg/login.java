package com.example.app.Login_Pkg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;
import com.example.app.admin_home;
import com.example.app.signup;
import com.example.app.student_homepage;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class login extends AppCompatActivity {
    EditText UserName_in;
    EditText Password_in;

    private TextView login_click;
    private TextView signup_click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.login_screen);
        login_click = (TextView) findViewById(R.id.log_in);
        login_click.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UserName_in = (EditText) findViewById(R.id.username);
                Password_in = (EditText) findViewById(R.id.password);
                Login_Presenter logic = new Login_Presenter(UserName_in.getText().toString(), Password_in.getText().toString());
                logic.getDatabasePassword().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!(logic.errorExists(snapshot))) {
                            if (logic.checkAdmin()) {
                                startActivity(new Intent(login.this, admin_home.class));
                            } else {
                                Intent intent = new Intent(login.this, student_homepage.class);
                                intent.putExtra("studentID", logic.Username);
                                startActivity(intent);
                            }
                        } else {
                            Snackbar mySnackbar = Snackbar.make (v, logic.createErrorMessage(), 2000);
                            mySnackbar.show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                signup_click = (TextView) findViewById(R.id.sign_up);
                signup_click.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(login.this, signup.class));
                    }
                });
            }
        });
    }
}