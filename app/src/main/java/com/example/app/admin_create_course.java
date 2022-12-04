package com.example.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

//import com.example.app.databinding.FragmentFirstBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarContentLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class admin_create_course extends AppCompatActivity {

//    private FragmentAdminCreateCourseBinding binding;
//    setContentView(R.layout.adm);

    private TextView submitCourse;
    private static int i;
    CheckBox checkFall, checkSummer, checkWinter;


    public List<String> setPrerequisites(String s){
        return Arrays.asList(s.split(","));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_admin_create_course);

//        Spinner spinner = (Spinner) findViewById(R.id.sessionsOfferedSpinner);
//        //creating array adapter
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.sessions_offered, android.R.layout.simple_spinner_item);
//        //layout
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        submitCourse = (TextView) findViewById(R.id.submit_course);

        submitCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference courses = database.getReference();

                String courseCodeInput = ((EditText) findViewById(R.id.courseCodeInput)).getText().toString();
                String courseNameInput = ((EditText) findViewById(R.id.edit_courseNameInput)).getText().toString();

                String coursePrerequisiteInput = ((EditText) findViewById(R.id.prerequisitesInput)).getText().toString();
                List<String> preList = setPrerequisites(coursePrerequisiteInput);

                checkFall = (CheckBox)findViewById(R.id.checkFall);
                checkWinter = (CheckBox)findViewById(R.id.checkWinter);
                checkSummer = (CheckBox)findViewById(R.id.checkSummer);

//                String sessionsPrerequisiteInput = ((EditText) findViewById(R.id.sessionsOffered)).getText().toString();
//                List<String> sessionsList = new ArrayList<String>();
                List<Boolean> sessionsList = new ArrayList<Boolean>();

//                if(!sessionsPrerequisiteInput.isEmpty()) {
//                    sessionsList = setPrerequisites(sessionsPrerequisiteInput);
//                }

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

//                sessionsList = setPrerequisites(sessionsPrerequisiteInput);
                //getting value from spinner
//                String sessionInput = spinner.getSelectedItem().toString();
//                String courseId = "course";
//                if(getIntent().hasExtra("x")) {
//                    courseId = "course" + getIntent().getStringExtra("x");
//
//                }

                String courseId = "course" + String.valueOf(i);

                Log.d("icourse", courseId);


                if(!courseCodeInput.isEmpty()) {
//                    courses.child("Courses").child(courseId).child("Course Code").setValue(courseCodeInput);
                    courses.child("Courses").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange( DataSnapshot snapshot) {
                            final boolean[] check = {true};
                            if(snapshot.child(courseCodeInput).exists()) {
                                Snackbar mySnackbar = Snackbar.make(view, "Course already exists", 3000);
                                mySnackbar.show();
                                check[0] = false;
                            }
                            if(preList != null) {
                                for (String prereq : preList) {
                                    if (!snapshot.child(prereq).exists()) {
                                        Snackbar mySnackbar = Snackbar.make(view, "Prerequisite course does not exist", 3000);
                                        mySnackbar.show();
                                        check[0] = false;
                                        break;
                                    }
                                }
                            }
                            if(!checkFall.isChecked() && !checkWinter.isChecked() && !checkSummer.isChecked()) {
                                Snackbar mySnackbar = Snackbar.make(view, "Choose at least one session to offer course", 4000);
                                mySnackbar.show();
                                check[0] = false;
                            }
                            if(check[0]) {
                                courses.child("Courses").child(courseCodeInput).child("Course Name").setValue(courseNameInput);
                                courses.child("Courses").child(courseCodeInput).child("prerequisites").setValue(preList);
                                courses.child("Courses").child(courseCodeInput).child("sessionOffered").child("Fall").setValue(sessionsList.get(0));
                                courses.child("Courses").child(courseCodeInput).child("sessionOffered").child("Winter").setValue(sessionsList.get(1));
                                courses.child("Courses").child(courseCodeInput).child("sessionOffered").child("Summer").setValue(sessionsList.get(2));


                                i += 1;
                                Log.d("ibob", String.valueOf(i));
                                startActivity(new Intent(admin_create_course.this, admin_home.class));
                            }
                        }
                        @Override
                        public void onCancelled( DatabaseError error) {

                        }
                    });
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
}