package com.example.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

//import com.example.app.databinding.FragmentFirstBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class admin_create_course extends AppCompatActivity {

    private TextView submitCourse;
    private static int i;
    CheckBox checkFall, checkSummer, checkWinter;
    ArrayList<String> existingCourses = new ArrayList<>();
    int wrongCount;
    Boolean checked = false;

    public List<String> setPrerequisites(String s){
        return Arrays.asList(s.split(","));
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("courses")){
            existingCourses = getIntent().getStringArrayListExtra("courses");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_admin_create_course);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        getIncomingIntent();

        submitCourse = (TextView) findViewById(R.id.submit_course);


        submitCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference courses = database.getReference();

                String courseCodeInput = ((EditText) findViewById(R.id.courseCodeInput)).getText().toString();
                String courseNameInput = ((EditText) findViewById(R.id.edit_courseNameInput)).getText().toString();

                String coursePrerequisiteInput = ((EditText) findViewById(R.id.prerequisitesInput)).getText().toString();
                List<String> preList = new LinkedList<String>(setPrerequisites(coursePrerequisiteInput));

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

                if(!checkFall.isChecked() && !checkSummer.isChecked() && !checkWinter.isChecked()) {
                    Snackbar mySnackbar = Snackbar.make(view, "Please select at least one session", 5000);
                    mySnackbar.show();
                } else {
                    checked = true;
                }

                wrongCount = 0;

                if(preList.get(0) != "") {
                    for (String pre : preList) {
                        if (!existingCourses.contains(pre)) {
                            Snackbar mySnackbar = Snackbar.make(view, "Please enter a prerequisite from a course that exists", 5000);
                            mySnackbar.show();
                            preList.remove(pre);
//                            Log.d("helloooooooooo", "hello");
                            wrongCount += 1;
                        }
                    }
                }

                if(courseCodeInput.isEmpty()) {
                    Snackbar mySnackbar = Snackbar.make(view, "Please enter a course code", 5000);
                    mySnackbar.show();
                }

                if(!courseCodeInput.isEmpty() && wrongCount == 0 && checked) {
//                    courses.child("Courses").child(courseId).child("Course Code").setValue(courseCodeInput);
                    courses.child("Courses").child(courseCodeInput).child("Course Name").setValue(courseNameInput);
                    courses.child("Courses").child(courseCodeInput).child("prerequisites").setValue(preList);
                    courses.child("Courses").child(courseCodeInput).child("sessionOffered").child("Fall").setValue(sessionsList.get(0));
                    courses.child("Courses").child(courseCodeInput).child("sessionOffered").child("Winter").setValue(sessionsList.get(1));
                    courses.child("Courses").child(courseCodeInput).child("sessionOffered").child("Summer").setValue(sessionsList.get(2));

                    startActivity(new Intent(admin_create_course.this, admin_home.class));
                }

            }
        });
    }

////    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
////        return inflater.inflate(R.layout.fragment_admin_create_course, container, false);
//
//
//        binding = FragmentAdminCreateCourseBinding.inflate(inflater, container, false);
//        return binding.getRoot();
//    }

//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
////        super.onViewCreated(view, savedInstanceState);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//
////        binding.submitCourse.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                DatabaseReference courses = database.getReference("Course Database");
////                String courseNameInput = binding.courseNameInput.getText().toString();
////
////                DatabaseReference prerequisites = database.getReference("");
////                String coursePrerequisiteInput = binding.prerequisitesInput.getText().toString();
////
////
////                courses.setValue(courseNameInput);
////
////                courses.child("prerequisites").setValue(coursePrerequisiteInput);
////
//////                NavHostFragment.findNavController(admin_create_course.this)
//////                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
////            }
////        });
//    }


    //                sessionsList = setPrerequisites(sessionsPrerequisiteInput);
    //getting value from spinner
//                String sessionInput = spinner.getSelectedItem().toString();
//                String courseId = "course";
//                if(getIntent().hasExtra("x")) {
//                    courseId = "course" + getIntent().getStringExtra("x");
//
//                }
}