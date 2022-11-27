package com.example.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

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

    private Button confirm_add;
    private TextView add_course;
    private Button make_timeline;
    private DatabaseReference FireBaseReference;

    public student_homepage() {
        // Required empty public constructor
    }

   /* public List<String> setPrerequisites(String s){
        return Arrays.asList(s.split(","));
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_student_homepage);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        add_course = (TextView) findViewById(R.id.add_comp_course);

        add_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference comp_courses = database.getReference();
//                String courseNameInput = binding.courseNameInput.getText().toString();

                String comp_course_input = ((EditText) findViewById(R.id.add_comp_course)).getText().toString();

                comp_courses.child("User Database").child("Bob (test)").child("Completed Courses (test)").setValue(comp_course_input);
                startActivity(new Intent(student_homepage.this, generate_timeline.class));
            }
        });
    }


    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_homepage, container, false);
    }
}

