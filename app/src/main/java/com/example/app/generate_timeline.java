package com.example.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

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
    private List<String> inputCourses(String toTake) {
        return Arrays.asList(toTake.split(","));
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_generate_timeline);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        gen_time = (TextView) findViewById(R.id.generate_timeline);
        gen_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String coursesToTake = ((EditText) findViewById(R.id.to_take)).getText().toString();
                List toTake = inputCourses(coursesToTake);
                /*Intent intent = new Intent(generate_timeline.this, student_homepage.class);
                intent.putExtra("toTake", toTake);
                startActivity(intent);*/
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