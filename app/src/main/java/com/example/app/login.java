package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//TODO: Replace UWUUWUWUWUWUWUW with the class that is transitioned into
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
                                               String Password = Password_in.getText().toString();
                                               String Username = UserName_in.getText().toString();
                                               DatabaseReference User_Data = FirebaseDatabase.getInstance().getReference("User Database");
                                               DatabaseReference User_Node = User_Data.child(Username);
                                               User_Node.addListenerForSingleValueEvent(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange(DataSnapshot dataSnapshot) {
                                                       if (dataSnapshot.exists())
                                                       {
                                                           DatabaseReference User_Password = User_Node.child("password");

                                                           User_Password.addListenerForSingleValueEvent(new ValueEventListener() {
                                                               @Override
                                                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                   System.out.println(snapshot.getValue().toString());
                                                                   System.out.println(Password);
                                                                   if (snapshot.getValue().toString().equals(Password))
                                                                   {
                                                                       if (Username.charAt(0) == 'a')
                                                                       {
                                                                           startActivity(new Intent(login.this, admin_home.class));
                                                                       }
                                                                       else
                                                                       {
                                                                           startActivity(new Intent(login.this, student_homepage.class));
                                                                       }
                                                                   }
                                                                   else {
                                                                       Snackbar yourSnackbar = Snackbar.make(v, "The password is incorrect", 2000);
                                                                       yourSnackbar.show();
                                                                   }
                                                               }
                                                               @Override
                                                               public void onCancelled(@NonNull DatabaseError error) {
                                                               }
                                                           });
                                                       } else {
                                                           Snackbar mySnackbar = Snackbar.make(v, "This account ID doesn't exist", 2000);
                                                           mySnackbar.show();
                                                       }
                                                   }

                                                   @Override
                                                   public void onCancelled(@NonNull DatabaseError error) {

                                                   }
                                               });
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