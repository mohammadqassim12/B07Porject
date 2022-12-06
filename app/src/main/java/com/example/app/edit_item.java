package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class edit_item extends AppCompatActivity {

    private TextView submit;
    private ArrayList<String> array = new ArrayList<String>();
    CheckBox checkFall, checkSummer, checkWinter;
    Boolean checked = false;

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

                checkFall = (CheckBox)findViewById(R.id.checkFall);
                checkWinter = (CheckBox)findViewById(R.id.checkWinter);
                checkSummer = (CheckBox)findViewById(R.id.checkSummer);

                List<Boolean> sessionsList = new ArrayList<Boolean>();

                if(checkFall.isChecked()) {
                    sessionsList.add(true);
                } else {
                    sessionsList.add(false);
                }
                if(checkWinter.isChecked()) {
                    sessionsList.add(true);
                } else {
                    sessionsList.add(false);
                }
                if(checkSummer.isChecked()) {
                    sessionsList.add(true);
                } else {
                    sessionsList.add(false);
                }
                //&& !courseNameInput.isEmpty() && !preList.isEmpty() && !sessionsList.isEmpty()

                if(!checkFall.isChecked() && !checkSummer.isChecked() && !checkWinter.isChecked()) {
                    Snackbar mySnackbar = Snackbar.make(v, "Please select at least one session", 5000);
                    mySnackbar.show();
                } else {
                    checked = true;
                }

                Log.d("CHECKED1", String.valueOf(checked));
                if(!courseCodeInput.isEmpty()) {
                    courses.child("Courses").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                for(DataSnapshot pre: childSnapshot.child("prerequisites").getChildren()) {

                                    if(pre.getValue().toString().equals(courseCode)) {
                                        Map<String, Object> map = new HashMap<>();
                                        map.put(pre.getKey().toString(), courseCodeInput);
                                        courses.child("Courses").child(childSnapshot.getKey()).child("prerequisites").updateChildren(map);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Log.w("testing", "Failed to read value.", error.toException());
                        }
                    });

                    courses.child("Courses").child(courseCodeInput).setValue(courseCodeInput);
                    if(!courseNameInput.isEmpty()) {
                        courses.child("Courses").child(courseCodeInput).child("Course Name").setValue(courseNameInput);
                    } else {
                        getData(courses,"Course Name", courseCode, courseCodeInput);
                    }
                    if(!preList.isEmpty()) {
                        courses.child("Courses").child(courseCodeInput).child("prerequisites").setValue(preList);
                    } else {
                        getData(courses,"prerequisites", courseCode, courseCodeInput);
                    }

                    DatabaseReference sessionOffer = courses.child("Courses").child(courseCodeInput).child("sessionOffered");

                    boolean value;
                    if(checkFall.isChecked()) {
                        value = true;
                    } else {
                        value = false;
                    }
                    sessionOffer.child("Fall").setValue(value);

                    if(checkWinter.isChecked()) {
                        value = true;
                    } else {
                        value = false;
                    }
                    sessionOffer.child("Winter").setValue(value);

                    if(checkSummer.isChecked()) {
                        value = true;
                    } else {
                        value = false;
                    }
                    sessionOffer.child("Summer").setValue(value);

//                    Log.d("here", courses.child("Courses").child(courseCode).getKey());
                    courses.child("Courses").child(courseCode).removeValue();

                    courses.child("User Database").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange( DataSnapshot dataSnapshot) {
                            for(DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
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

                    if(!courseNameInput.isEmpty()) {
                        courses.child("Courses").child(courseCode).child("Course Name").setValue(courseNameInput);
                    }
                    if(!preList.isEmpty()) {
                        courses.child("Courses").child(courseCode).child("prerequisites").setValue(preList);
                    }

                    DatabaseReference sessionOffer = courses.child("Courses").child(courseCode).child("sessionOffered");

                    boolean value;
                    if(checkFall.isChecked()) {
                        value = true;
                    } else {
                        value = false;
                    }
                    sessionOffer.child("Fall").setValue(value);

                    if(checkWinter.isChecked()) {
                        value = true;
                    } else {
                        value = false;
                    }
                    sessionOffer.child("Winter").setValue(value);

                    if(checkSummer.isChecked()) {
                        value = true;
                    } else {
                        value = false;
                    }
                    sessionOffer.child("Summer").setValue(value);
                }
                if(checked) {
                    startActivity(new Intent(edit_item.this, admin_home.class));
                }
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