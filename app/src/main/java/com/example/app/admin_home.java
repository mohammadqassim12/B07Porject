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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.app.Login_Pkg.login;
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
    private TextView logoutClick;
    int i = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.fragment_admin_home);
        RecyclerView rvCourses = (RecyclerView) findViewById(R.id.coursesRecycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Courses");

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                courses.clear();
                for(DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String value = childSnapshot.getKey();
                    if(!courses.contains(value)) {
                        courses.add(value);
//                        Log.d("testing", courses.get(i));
//                        i = i +1;
                    }
//                    Log.d("insideChildren", "child");

                }

                courseAdapter adapter = new courseAdapter(courses, admin_home.this);
                rvCourses.setAdapter(adapter);
                rvCourses.setLayoutManager(llm);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("testing", "Failed to read value.", error.toException());
            }
        });

        createCourseButton = (TextView) findViewById(R.id.createCoursePage);
        createCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_home.this, admin_create_course.class);
                intent.putExtra("courses", courses);
                admin_home.this.startActivity(intent);
            }
        });

        logoutClick = (TextView) findViewById(R.id.logout);
        logoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(admin_home.this, login.class));
            }
        });

    }
}