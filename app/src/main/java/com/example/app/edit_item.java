package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class edit_item extends AppCompatActivity {

    private TextView submit;


    public List<String> setPrerequisites(String s){
        return Arrays.asList(s.split(","));

    }

    public void getIncomingIntent(){
        if(getIntent().hasExtra("course_code")) {
            String courseCode = getIntent().getStringExtra("course_code");
            setData(courseCode);

        }


    }

    public void setData(String courseCode) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        submit = (TextView) findViewById(R.id.edit_submit_course);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference courses = database.getReference();

                String courseCodeInput = ((EditText) findViewById(R.id.edit_courseCodeInput)).getText().toString();
                String courseNameInput = ((EditText) findViewById(R.id.edit_courseNameInput)).getText().toString();


                String coursePrerequisiteInput = ((EditText) findViewById(R.id.edit_prerequisitesInput)).getText().toString();
                List<String> preList = new ArrayList<String>();
                if(!coursePrerequisiteInput.isEmpty()) {
                    preList = setPrerequisites(coursePrerequisiteInput);
                }

                String sessionsPrerequisiteInput = ((EditText) findViewById(R.id.edit_sessionsOffered)).getText().toString();
                List<String> sessionsList = new ArrayList<String>();
                if(!sessionsPrerequisiteInput.isEmpty()) {
                    sessionsList = setPrerequisites(sessionsPrerequisiteInput);
                }
                //&& !courseNameInput.isEmpty() && !preList.isEmpty() && !sessionsList.isEmpty()

                if(!courseCodeInput.isEmpty() ) {
                    Log.d("hellotest3", "hello");


                    courses.child("Courses").child(courseCodeInput);
                    if(!courseNameInput.isEmpty()) {
                        courses.child("Courses").child(courseCodeInput).child("Course Name").setValue(courseNameInput);
                    } else {
                    }
                    if(!preList.isEmpty()) {
                        courses.child("Courses").child(courseCodeInput).child("prerequisites").setValue(preList);
                    }
                    if(!sessionsList.isEmpty()) {
                        courses.child("Courses").child(courseCodeInput).child("sessionOffered").setValue(sessionsList);
                    }

                    courses.child("Courses").child(courseCode).removeValue();


                }

                if(courseCodeInput.isEmpty()) {
                    Log.d("hellotest2", "hello");

                    if(!courseNameInput.isEmpty()) {
                        courses.child("Courses").child(courseCode).child("Course Name").setValue(courseNameInput);
                    }
                    if(!preList.isEmpty()) {
                        courses.child("Courses").child(courseCode).child("prerequisites").setValue(preList);
                    }
                    if(!sessionsList.isEmpty()) {
                        courses.child("Courses").child(courseCode).child("sessionOffered").setValue(sessionsList);
                    }
                }
                startActivity(new Intent(edit_item.this, admin_home.class));
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item);

        getIncomingIntent();

    }

}