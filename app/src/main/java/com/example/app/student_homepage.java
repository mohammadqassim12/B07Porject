package com.example.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app.Login_Pkg.login;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class student_homepage extends AppCompatActivity {

    /*
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    // TODO: Rename and change types and number of parameters
    public static student_homepage newInstance(String param1, String param2) {
        student_homepage fragment = new student_homepage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }
    */

    private TextView add_comp_course;
    private TextView confirm_course_click;
    private TextView make_timeline_click;
    private TextView logoutClick;
    private RecyclerView recycler_view;
    comp_course_adapter myAdapter;
    String studentID;
    ArrayList<String> courseCodeList = new ArrayList<>();

    public void getIncomingIntent() {
        if (getIntent().hasExtra("studentID")) {
            Log.d("ur gay",getIntent().getStringExtra("studentID") );
            studentID = getIntent().getStringExtra("studentID");
        }
    }


    public student_homepage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.fragment_student_homepage);
        getIncomingIntent();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference key = database.child("User Database").child(studentID).child("name");
        key.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                TextView a = findViewById(R.id.textView5);
                a.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recycler_view = (RecyclerView) findViewById(R.id.comp_courses_rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        DatabaseReference myRef = database.child("User Database").child(studentID).child("Completed Courses");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                courseCodeList.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String courseCode = childSnapshot.getKey();
                    if (!courseCodeList.contains(courseCode)) {
                        courseCodeList.add(courseCode);
                    }
                }
                myAdapter = new comp_course_adapter(courseCodeList, studentID);
                recycler_view.setAdapter(myAdapter);
                recycler_view.setLayoutManager(llm);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("testing", "Failed to read value.", error.toException());
            }
        });

        add_comp_course = (TextView) findViewById(R.id.add_comp_course);
        confirm_course_click = (TextView) findViewById(R.id.confirm_add);
        confirm_course_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String comp_course_input = ((EditText) findViewById(R.id.add_comp_course)).getText().toString();
                        if (!dataSnapshot.child("Courses").child(comp_course_input).exists()) {
                            Snackbar mySnackbar = Snackbar.make(view, "Course does not exist", 3000);
                            mySnackbar.show();
                        }
                        else if(dataSnapshot.child("User Database").child(studentID).child("Completed Courses").child(comp_course_input).exists()) {
                            Snackbar newSnackbar = Snackbar.make(view, "Course already completed and in list", 4000);
                            newSnackbar.show();
                        }
                        else {
                            final boolean[] hasAllPrereq = {true};
                            for(DataSnapshot prereqSnapshot: dataSnapshot.child("Courses").child(comp_course_input).child("prerequisites").getChildren()) {
                                DataSnapshot preInComp = dataSnapshot.child("User Database").child(studentID).child("Completed Courses").child(prereqSnapshot.getValue().toString());
                                if(!prereqSnapshot.getValue().toString().equals("") && !preInComp.exists()) {
                                    hasAllPrereq[0] = false;
                                    Snackbar yourSnackbar = Snackbar.make(view, "Prerequisites for course have not been completed", 5000);
                                    yourSnackbar.show();
                                    break;
                                }
                            }
                            if(hasAllPrereq[0]) {
                                database.child("User Database").child(studentID).child("Completed Courses").child(comp_course_input).setValue(true);
                                add_comp_course.setText("");
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

        });

        make_timeline_click = (TextView) findViewById(R.id.make_timeline);
        make_timeline_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Intent send = new Intent(student_homepage.this, generate_timeline.class);
                send.putExtra("Send", studentID);
                startActivity(send);
            }
        });

        logoutClick = (TextView) findViewById(R.id.logout);
        logoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(student_homepage.this, login.class));
            }
        });
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_homepage, container, false);
    }*/
}

