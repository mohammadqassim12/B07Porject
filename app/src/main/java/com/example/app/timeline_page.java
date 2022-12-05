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
import java.util.Arrays;
import java.util.LinkedHashSet;

public class timeline_page extends AppCompatActivity {
    private RecyclerView recycler_view1;
    comp_timetable_adapter myAdapter;

    private RecyclerView recycler_view2;
    comp_timetable_adapter myAdapter2;

    private RecyclerView recycler_view3;
    comp_timetable_adapter myAdapter3;

    ArrayList<String> fall = new ArrayList<>();

    ArrayList<String> winter = new ArrayList<>();

    ArrayList<String> summer = new ArrayList<>();

    String[] a = new String[6] ;
    public void getIncomingIntent (){
        if(getIntent().hasExtra("Send")){
            a = getIntent().getStringArrayExtra("Send");
        }
    }
    public void cleaner(){
        ArrayList<String> aListColors = new ArrayList<String>();
        for(int i=0; i < a.length; i++){

            if( !aListColors.contains(a[i]) ){
                aListColors.add(a[i]);
            }
        }
        a = aListColors.toArray( new String[aListColors.size()] );

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
        cleaner();

        //Database handling
        LinearLayoutManager llm = new LinearLayoutManager(this);
        LinearLayoutManager llm2 = new LinearLayoutManager(this);
        LinearLayoutManager llm3 = new LinearLayoutManager(this);



        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("Courses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot childSnapshot: snapshot.getChildren()) {
                    for (int i = 0; i < a.length; i++ ){
                        String course = a[i];
                        Log.d("snapshoit", childSnapshot.getKey().toString());
                        if(childSnapshot.getKey().toString().equals(course)){
                            if((Boolean)childSnapshot.child("sessionOffered").child("Fall").getValue()){
                                Log.d("Fall course", course);
                                fall.add(course);

                            }
                            else if ((Boolean)childSnapshot.child("sessionOffered").child("Winter").getValue()){
                                Log.d("Winter course", course);
                                winter.add(course);
                            }
                            else{
                                Log.d("Summer course", course);
                                summer.add(course);
                            }
                        }





                    }
                }
                
                recycler_view1 = (RecyclerView) findViewById(R.id.comp_timetable_aa);

                myAdapter = new comp_timetable_adapter(fall);
                recycler_view1.setAdapter(myAdapter);
                recycler_view1.setLayoutManager(llm);

                recycler_view2 = (RecyclerView) findViewById(R.id.comp_timetable2_aa);

                myAdapter2 = new comp_timetable_adapter(winter);
                recycler_view2.setAdapter(myAdapter2);
                recycler_view2.setLayoutManager(llm2);

                recycler_view3 = (RecyclerView) findViewById(R.id.comp_timetable3_aa);
                myAdapter3 = new comp_timetable_adapter(summer);

                recycler_view3.setAdapter(myAdapter3);
                recycler_view3.setLayoutManager(llm3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline_page, container, false);
    }*/
}