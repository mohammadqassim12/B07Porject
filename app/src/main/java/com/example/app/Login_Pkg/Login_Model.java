package com.example.app.Login_Pkg;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login_Model {
    public DatabaseReference DB_ref;
    public Login_Model(){
        DB_ref = FirebaseDatabase.getInstance().getReference("User Database");
    }
    public DatabaseReference getDBPasswordRef(String Username)
    {
        return DB_ref.child(Username).child("password");
    }
}
