package com.example.app.Login_Pkg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;
import com.example.app.admin_home;
import com.example.app.student_homepage;
import android.app.Activity;
import android.provider.ContactsContract;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_Model {
    public DatabaseReference DB_ref;
    SharedPreferences preference;
    SharedPreferences.Editor editor;
    public Login_Model(SharedPreferences s, SharedPreferences.Editor e)
    {
        preference = s;
        editor = e;
        DB_ref = FirebaseDatabase.getInstance().getReference("User Database");
    }

    public void Password_Error_Searching(String Username) {
        DatabaseReference user_key = DB_ref.child(Username);

        user_key.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    DatabaseReference pass_key = user_key.child("password");
                    pass_key.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            editor.putString("Password", snapshot.getValue().toString());
                            editor.apply();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                else
                {
                    editor.putString("Password", null);
                    editor.apply();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
