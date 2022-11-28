package com.example.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;


public class admin_home extends AppCompatActivity {
    ArrayList<String> courses = new ArrayList<String>();
    TextView createCourseButton;
    Button deleteButton;
    int i = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_admin_home);
        RecyclerView rvCourses = (RecyclerView) findViewById(R.id.coursesRecycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);


        createCourseButton = (TextView) findViewById(R.id.createCoursePage);
        createCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_home.this, admin_create_course.class));
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Courses");

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                courses.clear();
                for(DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String value = childSnapshot.getKey();
//                    Log.d("testing", value);
                    if(!courses.contains(value)) {
                        courses.add(value);
//                        Log.d("testing", courses.get(i));
//                        i = i +1;
                    }
                    Log.d("insideChildren", "child");

                }

                Log.d("onDataChangeTest", "testing");

                courseAdapter adapter = new courseAdapter(courses);
                rvCourses.setAdapter(adapter);
                rvCourses.setLayoutManager(llm);

//                deleteButton = (Button) findViewById(R.id.delete_button);
//                deleteButton.setOnClickListener(new View.OnClickListener() {
//                    //
////                    @Override
////                    public void onClick(View view) {
////        //                        Log.d("deleteTest", dataSnapshot.getKey());
////
////                    }
//
//                });

//                rvCourses.setLayoutManager(new LinearLayoutManager(this));

//                                    Log.d("testing", adapter;

                // This method is called once with the initial value and again
                // whenever data at this location is updated.
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("testing", "Failed to read value.", error.toException());
            }
        });





    }



//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_admin_home, container, false);
//    }
}