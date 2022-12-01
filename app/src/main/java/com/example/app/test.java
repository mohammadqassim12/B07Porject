package com.example.app;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class test {
    DatabaseReference gay = FirebaseDatabase.getInstance().getReference("Courses");
    DatabaseReference User_Node = gay.child("TEST22").child("Course Name");
    User_Node.setValue("Im Gay");


}
