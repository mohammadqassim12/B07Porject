package com.example.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class comp_course_adapter extends RecyclerView.Adapter<comp_course_adapter.courseViewHolder> {

    ArrayList<String> list;

    public comp_course_adapter( ArrayList<String> list) {
        this.list = list;
    }

    public static class courseViewHolder extends RecyclerView.ViewHolder {
        TextView comp_course;

        public courseViewHolder(@NonNull View itemView) {
            super(itemView);
            comp_course = (TextView) itemView.findViewById(R.id.comp_course_item);
        }

        public TextView getTextView() {
            return comp_course;
        }
    }
    @NonNull
    @Override
    public courseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_student_homepage, parent, false);
        return new courseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull courseViewHolder holder, int position) {
        holder.getTextView().setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
