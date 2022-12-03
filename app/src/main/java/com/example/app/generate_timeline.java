package com.example.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link generate_timeline#newInstance} factory method to
 * create an instance of this fragment.
 */
public class generate_timeline extends AppCompatActivity {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public generate_timeline() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment generate_timeline.
     */
    // TODO: Rename and change types and number of parameters
    public static generate_timeline newInstance(String param1, String param2) {
        generate_timeline fragment = new generate_timeline();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
       // fragment.setArguments(args);
        return fragment;
    }

    private TextView gen_time;

    //text view of courses
    TextView C1,C2,C3,C4,C5,C6;
    String Student_id ="s1";
    int [] a = {0,0,0,0,0,0};
    public void getIncomingIntent (){
        if(getIntent().hasExtra("Send")){
            Student_id = getIntent().getStringExtra("Send");
        }
    }
    public void check_valid (String c1, int b){
        // empty string
        Log.d("test2", c1);
        if (c1.length()<1){
            a[b] = -1;
        }
        else{
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.child("Courses").child(c1).exists()){
                        Log.d("test3", "not a valid course");
                        a[b] = -2;
                    }
                    else if (snapshot.child("User Database").child(Student_id).child("Completed Courses").child(c1).exists()){
                        a[b] = -3;
                        Log.d("test3", "course complete");
                    }
                    else{
                    for (DataSnapshot snap: snapshot.child("Courses").child(c1).child("prerequisites").getChildren()) {
                        snap.getValue();
                        if (!snapshot.child("User Database").child(Student_id).child("Completed Courses").child(snap.getValue().toString()).exists()) {
                            Log.d("test3", "missing pre");
                            a[b] = -4;
                            break;
                        }
                    }
                   }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        return;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIncomingIntent();
        setContentView(R.layout.fragment_generate_timeline);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        gen_time = (TextView) findViewById(R.id.generate_timeline);
        gen_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getting input from user courses
                C1 = (TextView)findViewById(R.id.editTextTextPersonName2);
                String S1 = C1.getText().toString();
                C2 = (TextView)findViewById(R.id.editTextTextPersonName7);
                String S2 = C2.getText().toString();
                C3 = (TextView)findViewById(R.id.editTextTextPersonName8);
                String S3 = C3.getText().toString();
                C4 = (TextView)findViewById(R.id.editTextTextPersonName9);
                String S4 = C4.getText().toString();
                C5 = (TextView)findViewById(R.id.editTextTextPersonName10);
                String S5 = C5.getText().toString();
                C6 = (TextView)findViewById(R.id.editTextTextPersonName11);
                String S6 = C6.getText().toString();
            int x = 0;
            check_valid(S1,0);
            check_valid(S2,1);
            check_valid(S3,2);
            check_valid(S4,3);
            check_valid(S5,4);
            check_valid(S6,5);
            Log.d("List valus", (Integer.toString(a[0])));
            Log.d("List valus", (Integer.toString(a[1])));
            Log.d("List valus", (Integer.toString(a[2])));
            Log.d("List valus", (Integer.toString(a[3])));
            Log.d("List valus", (Integer.toString(a[4])));
            Log.d("List valus", (Integer.toString(a[5])));
                if (a[0] == -1){
                    Snackbar message = Snackbar.make(view,"Field should not be empty", 3000);
                    message.show();
                }
                else {
                    for(int i =0; i<= 5; i++){
                        if (a[i] == -2) {
                            Snackbar message = Snackbar.make(view,"At least one of the input courses is not offered :'(", 3000);
                            message.show();
                            x=-1;
                            break;
                        }
                        else if (a[i] == -3){
                            Snackbar message = Snackbar.make(view,"At least one of the input courses is already taken!", 3000);
                            message.show();
                            x=-1;
                            break;
                        }
                        else if (a[i] == -4){
                            Snackbar message = Snackbar.make(view,"Prerequisite of one or more of the input courses is not taken", 3000);
                            message.show();
                            x=-1;
                            break;
                        }
                    }
                    if (x == 0) {
                        String[] y = new String[6];
                        y[0] = C1.toString();
                        if (a[1] == 1) y[1] = C2.toString();
                        if (a[2] == 1) y[1] = C3.toString();
                        if (a[3] == 1) y[1] = C4.toString();
                        if (a[4] == 1) y[1] = C5.toString();
                        if (a[5] == 1) y[1] = C6.toString();
                        Intent send = new Intent(generate_timeline.this, timeline_page.class);
                        send.putExtra("Send", y);
                        startActivity(send);
                    }
                }
            }
        });
    }

   // @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_generate_timeline, container, false);
    }
}