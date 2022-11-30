package com.example.app;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class comp_course_adapter extends RecyclerView.Adapter<comp_course_adapter.courseViewHolder> {

    ArrayList<String> list;
    Button deleteCourse;
    String studentID;


    public static class courseViewHolder extends RecyclerView.ViewHolder {
        private final TextView comp_course;

        public courseViewHolder(View itemView) {
            super(itemView);
            comp_course = (TextView) itemView.findViewById(R.id.comp_course_name);
        }

        public TextView getTextView() {
            return comp_course;
        }
    }

    public comp_course_adapter( ArrayList<String> list, String studentID) {
        this.list = list;
        this.studentID = studentID;
    }

    @Override
    public courseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comp_course_item, parent, false);
        return new courseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(courseViewHolder holder, int position) {
        holder.getTextView().setText(list.get(position));

        View view = holder.itemView;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("User Database").child(studentID).child("Completed Courses");

        deleteCourse = (Button) view.findViewById(R.id.deleteCourse);
        deleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child(holder.getTextView().getText().toString()).removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
