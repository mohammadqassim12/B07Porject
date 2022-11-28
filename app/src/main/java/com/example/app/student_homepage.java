package com.example.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link student_homepage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class student_homepage extends AppCompatActivity {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment student_homepage.
     */
    // TODO: Rename and change types and number of parameters
    public static student_homepage newInstance(String param1, String param2) {
        student_homepage fragment = new student_homepage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }



    private TextView add_comp_course;
    private TextView confirm_course_click;
    private TextView make_timeline_click;
    private RecyclerView recycler_view;
    comp_course_adapter myAdapter;
    ArrayList<String> list = new ArrayList<String>();
    Button deleteCourse;

    public student_homepage() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_student_homepage);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference user_database = database.getReference();

        recycler_view = (RecyclerView) findViewById(R.id.comp_courses_rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);

        DatabaseReference myRef = user_database.child("User Database").child("Bob (test)").child("Completed Courses(test)");
        myRef.addValueEventListener(new ValueEventListener() {

            int i = 0;
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot childSnapshot: snapshot.getChildren()) {
                    String course = childSnapshot.getKey();
                    list.add(course);
                    Log.d("testing", list.get(i));
                    i++;
                }
                myAdapter = new comp_course_adapter(list);
                recycler_view.setAdapter(myAdapter);
                recycler_view.setLayoutManager(llm);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("testing", "Failed to read value.", error.toException());
            }
        });

        add_comp_course = (TextView)findViewById(R.id.add_comp_course);
        confirm_course_click = (TextView)findViewById(R.id.confirm_add);
        confirm_course_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comp_course_input = ((EditText) findViewById(R.id.add_comp_course)).getText().toString();
                user_database.child("User Database").child("Bob (test)").child("Completed Courses(test)").child(comp_course_input).setValue(true);
                add_comp_course.setText("");
            }
        });

        make_timeline_click = (TextView) findViewById(R.id.make_timeline);
        make_timeline_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                startActivity(new Intent(student_homepage.this, generate_timeline.class));
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
