package com.example.app.Login_Pkg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
        Context login_con = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.login_screen);
        login_click = (TextView) findViewById(R.id.log_in);
        login_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserName_in = (EditText) findViewById(R.id.username);
                Password_in = (EditText) findViewById(R.id.password);
                String Username = UserName_in.getText().toString();
                String Password = Password_in.getText().toString();
                Login_Presenter database_logic = new Login_Presenter(login_con, Username, Password);
                String DBpassword = database_logic.getDatabaseValue();
                if (!(database_logic.ErrorExists(DBpassword))) {
                    if (database_logic.checkAdmin()) {
                        startActivity(new Intent(login.this, admin_home.class));
                    } else {
                        Intent intent = new Intent(login.this, student_homepage.class);
                        intent.putExtra("studentID", database_logic.Username);
                        startActivity(intent);
                    }
                } else {
                    Snackbar mySnackbar = Snackbar.make(v, database_logic.createMessage(), 2000);
                    mySnackbar.show();
                }
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
}
