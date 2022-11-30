package com.example.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

//import com.example.app.databinding.FragmentFirstBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
//                String courseNameInput = binding.courseCodeInput.getText().toString();

                String courseCodeInput = ((EditText) findViewById(R.id.courseCodeInput)).getText().toString();
                String courseNameInput = ((EditText) findViewById(R.id.edit_courseNameInput)).getText().toString();



//                DatabaseReference prerequisites = database.getReference("");
//                String coursePrerequisiteInput = binding.prerequisitesInput.getText().toString();
                String coursePrerequisiteInput = ((EditText) findViewById(R.id.prerequisitesInput)).getText().toString();
                List<String> preList = setPrerequisites(coursePrerequisiteInput);
                String sessionsPrerequisiteInput = ((EditText) findViewById(R.id.sessionsOffered)).getText().toString();
                List<String> sessionsList = new ArrayList<String>();
                if(!sessionsPrerequisiteInput.isEmpty()) {
                    sessionsList = setPrerequisites(sessionsPrerequisiteInput);
                }
                //getting value from spinner
//                String sessionInput = spinner.getSelectedItem().toString();

                if(!courseCodeInput.isEmpty()) {
                    courses.child("Courses").child(courseCodeInput).child("Course Name").setValue(courseNameInput);
                    courses.child("Courses").child(courseCodeInput).child("prerequisites").setValue(preList);
                    courses.child("Courses").child(courseCodeInput).child("sessionOffered").setValue(sessionsList);
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
}