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
import android.view.Window;
import android.view.WindowManager;
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
    String Student_id;

    String [] input = new String [6];

    public void getIncomingIntent (){
        if(getIntent().hasExtra("Send")){
            Student_id = getIntent().getStringExtra("Send");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.fragment_generate_timeline);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        getIncomingIntent();
        gen_time = (TextView) findViewById(R.id.generate_timeline);
        gen_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getting input from user courses, placing in array input
                C1 = (TextView) findViewById(R.id.editTextTextPersonName2);
                input[0] = C1.getText().toString();
                C2 = (TextView) findViewById(R.id.editTextTextPersonName7);
                input[1] = C2.getText().toString();
                C3 = (TextView) findViewById(R.id.editTextTextPersonName8);
                input[2] = C3.getText().toString();
                C4 = (TextView) findViewById(R.id.editTextTextPersonName9);
                input[3] = C4.getText().toString();
                C5 = (TextView) findViewById(R.id.editTextTextPersonName10);
                input[4] = C5.getText().toString();
                C6 = (TextView) findViewById(R.id.editTextTextPersonName11);
                input[5] = C6.getText().toString();

                database.addListenerForSingleValueEvent(new ValueEventListener() { //everything from here in onDataChange
                    //function will not allow altering of global variables so won't be able to check on x[0]
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        final int[] x = {0}; //indicates whether all conditions are satisfied, 0 = yes, -1 = no
                        if (input[0].matches("")) { //check if first input it empty
                            Snackbar message = Snackbar.make(view, "Course #1 must be filled in", 3000);
                            message.show();
                            x[0] = -1; //first input is empty, notify student and end
                        } else {
                            for (int i = 0; i < 6; i++) { //loop through each input course
                                String course = input[i];
                                Log.d("test2", course);
                                if (!course.matches("")) { //these conditions only concern inputs that aren't empty
                                    //empty courses will crash app so make sure to filter them out
                                    if (!snapshot.child("Courses").child(course).exists()) {
                                        Log.d("test3", "not a valid course");
                                        Snackbar message = Snackbar.make(view, "At least one of the input courses is not offered :'(", 5000);
                                        message.show();
                                        x[0] = -1;//course does not exist in database, notify student and end
                                    } else if (snapshot.child("User Database").child(Student_id).child("Completed Courses").child(course).exists()) {

                                            Log.d("test3", "course complete");
                                            Snackbar message = Snackbar.make(view, "At least one of the input courses is already completed!", 5000);
                                            message.show();
                                            x[0] = -1; //course has already been completed by student, notify them and end

                                    }else if (snapshot.child("User Database").child(Student_id).child("Completed Courses").exists()) {
                                        for (DataSnapshot snap : snapshot.child("Courses").child(course).child("prerequisites").getChildren()) {
                                            if (!snapshot.child("User Database").child(Student_id).child("Completed Courses").child(snap.getValue().toString()).exists()) {
                                                Log.d("test3", "missing pre");
                                                Snackbar message = Snackbar.make(view, "Prerequisite of one or more of the input courses is not taken", 5000);
                                                message.show();
                                                x[0] = -1;
                                                break; //prerequisite was found that student has not completed, notify them and end
                                            }
                                        }
                                    }else if (!snapshot.child("User Database").child(Student_id).child("Completed Courses").exists()) {
                                        if(!(snapshot.child("Courses").child(course).child("prerequisites").child("0").getValue().toString().isEmpty())) {
                                            Snackbar message = Snackbar.make(view, "Prerequisite of one or more of the input courses is not taken", 5000);
                                            message.show();
                                            x[0] = -1;
                                        }
                                    }
                                }
                                Log.d("check if x changed", String.valueOf(x[0]));
                                if (x[0] == -1) break; //if one of the courses violates conditions, this line will run and
                                //loop on line 115 will terminate
                            }
                            if (x[0] == 0) { //all conditions have been met
                                String[] y = new String[6];
                                int index = 0; //some inputs may be empty, this will ensure all non-empty inputs are put at front of y
                                y[index] = input[0];
                                index++;
                                for (int j = 1; j < 6; j++) {
                                    if (!(input[j].matches(""))) {
                                        y[index] = input[j];
                                        index++; //index will not necessarily correspond with j, this ensures all inputs are put at front of y
                                    }
                                }
                                Intent send = new Intent(generate_timeline.this, timeline_page.class);
                                send.putExtra("Send", y); //brings y to timeline_page through an intent
                                startActivity(send);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
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