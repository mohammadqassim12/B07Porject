package com.example.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.app.databinding.FragmentAdminCreateCourseBinding;
import com.example.app.databinding.FragmentAdminHomeBinding;
//import com.example.app.databinding.FragmentFirstBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class admin_create_course extends AppCompatActivity {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//    private FragmentAdminCreateCourseBinding binding;
//    setContentView(R.layout.adm);

    private TextView submitCourse;

//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public admin_create_course() {
        // Required empty public constructor
    }

    public List<String> setPrerequisites(String s){
        return Arrays.asList(s.split(","));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_admin_create_course);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }


        Spinner spinner = (Spinner) findViewById(R.id.sessionsOfferedSpinner);
        //creating array adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sessions_offered, android.R.layout.simple_spinner_item);
        //layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        submitCourse = (TextView) findViewById(R.id.submit_course);

        submitCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference courses = database.getReference();
//                String courseNameInput = binding.courseNameInput.getText().toString();

                String courseNameInput = ((EditText) findViewById(R.id.courseNameInput)).getText().toString();


//                DatabaseReference prerequisites = database.getReference("");
//                String coursePrerequisiteInput = binding.prerequisitesInput.getText().toString();
                String coursePrerequisiteInput = ((EditText) findViewById(R.id.prerequisitesInput)).getText().toString();
                List<String> preList = setPrerequisites(coursePrerequisiteInput);
                //getting value from spinner
                String sessionInput = spinner.getSelectedItem().toString();

                if(courseNameInput != "") {
                    courses.child("Courses").child(courseNameInput).child("prerequisites").setValue(preList);
                    courses.child("Courses").child(courseNameInput).child("sessionOffered").setValue(sessionInput);
                }

                startActivity(new Intent(admin_create_course.this, admin_home.class));
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