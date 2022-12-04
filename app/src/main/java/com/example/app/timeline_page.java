package com.example.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

public class timeline_page extends AppCompatActivity {
    private TextView add_comp_timetable;
    private TextView confirm_course_click;
    private TextView make_timeline_click;
    private TextView logoutClick;
    private RecyclerView recycler_view1;
    private RecyclerView recycler_view2;
    comp_timetable_adapter myAdapter;
    comp_timetable_adapter myAdapter2;

    String studentID;
    ArrayList<String> courseCodeList = new ArrayList<>();

    ArrayList<String> winter = new ArrayList<>();

    String[] a = new String[6] ;
    public void getIncomingIntent (){
        if(getIntent().hasExtra("Send")){
            a = getIntent().getStringArrayExtra("Send");
        }
    }


    public timeline_page() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.fragment_timeline_page);
        getIncomingIntent();


        // put a into courseCodeList
        for (int i=0; i<6; i++){
            courseCodeList.add(a[i]);
        }
        courseCodeList.add("test test");
        recycler_view1 = (RecyclerView) findViewById(R.id.comp_timetable_aa);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        //add courses to the display
        //courseCodeList.add("test1" + " " + "testtest1");
        //courseCodeList.add("test2");
        //courseCodeList.add("test3");

        myAdapter = new comp_timetable_adapter(courseCodeList, studentID);

        recycler_view1.setAdapter(myAdapter);
        recycler_view1.setLayoutManager(llm);

        //recycler_view2 = (RecyclerView) findViewById(R.id.comp_timetable2_aa);
        //LinearLayoutManager llm2 = new LinearLayoutManager(this);
        //add courses to the display
        //winter.add("test1" + " " + "testtest1");


       // myAdapter2 = new comp_timetable_adapter(winter, studentID);

        //recycler_view2.setAdapter(myAdapter);

        //recycler_view2.setLayoutManager(llm2);



    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline_page, container, false);
    }*/
}