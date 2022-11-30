package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class edit_item extends AppCompatActivity {

    private TextView submit;
    private ArrayList<String> array = new ArrayList<String>();


    public List<String> setPrerequisites(String s){
        return Arrays.asList(s.split(","));

    }

    public void getIncomingIntent(){
        if(getIntent().hasExtra("course_code")) {
            String courseCode = getIntent().getStringExtra("course_code");
            setData(courseCode);
        }
    }

    public void getData(DatabaseReference courses, String path, String courseCode, String newCourse) {
        courses.child("Courses").child(courseCode).child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {

                if(path == "Course Name") {
                    courses.child("Courses").child(newCourse).child(path).setValue(dataSnapshot.getValue());
                }
                else {
                    array.clear();
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        if(!array.contains(childSnapshot.getValue())) {
                            array.add(childSnapshot.getValue().toString());
                        }
                    }
                    courses.child("Courses").child(newCourse).child(path).setValue(array);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
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
//                    Log.d("hellotest3", "hello");

                    courses.child("Courses").child(courseCodeInput).setValue(courseCodeInput);
                    if(!courseNameInput.isEmpty()) {
                        courses.child("Courses").child(courseCodeInput).child("Course Name").setValue(courseNameInput);
                    } else {
//                        Log.d("asdf", courses.child("Courses").child(courseCode).child("Course Name").toString());
                        getData(courses,"Course Name", courseCode, courseCodeInput);
//                        courses.child("Courses").child(courseCodeInput).child("Course Name").setValue();
                    }
                    if(!preList.isEmpty()) {
                        courses.child("Courses").child(courseCodeInput).child("prerequisites").setValue(preList);
                    } else {
                        getData(courses,"prerequisites", courseCode, courseCodeInput);
                    }
                    if(!sessionsList.isEmpty()) {
                        courses.child("Courses").child(courseCodeInput).child("sessionsOffered").setValue(sessionsList);
                    } else {
                        getData(courses,"sessionsOffered", courseCode, courseCodeInput);
                    }
//                    Log.d("here", courses.child("Courses").child(courseCode).getKey());
                    courses.child("Courses").child(courseCode).removeValue();

                    courses.child("User Database").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange( DataSnapshot dataSnapshot) {
                            for(DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
//                                Log.d("childcourseCode", courseCode);
//                                Log.d("childcourseCode", childSnapshot.getKey());
//                                Log.d("childBoolean", String.valueOf(childSnapshot.child("Completed Courses").hasChild(courseCode)));
                                if( childSnapshot.child("Completed Courses").hasChild(courseCode)) {
                                    Log.d("childhello", "hello" );
                                    courses.child("User Database").child(childSnapshot.getKey()).child("Completed Courses").child(courseCode).removeValue();
                                    courses.child("User Database").child(childSnapshot.getKey()).child("Completed Courses").child(courseCodeInput).setValue(true);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });

                }

                if(courseCodeInput.isEmpty()) {
//                    Log.d("hellotest2", "hello");

                    if(!courseNameInput.isEmpty()) {
                        courses.child("Courses").child(courseCode).child("Course Name").setValue(courseNameInput);
                    }
                    if(!preList.isEmpty()) {
                        courses.child("Courses").child(courseCode).child("prerequisites").setValue(preList);
                    }
                    if(!sessionsList.isEmpty()) {
                        courses.child("Courses").child(courseCode).child("sessionsOffered").setValue(sessionsList);
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