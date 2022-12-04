package com.example.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class comp_timetable_adapter extends RecyclerView.Adapter<comp_timetable_adapter.courseViewHolder>{
    ArrayList<String> list;
    String studentID;

    public static class courseViewHolder extends RecyclerView.ViewHolder {
        private final TextView comp_timetable;

        public courseViewHolder(View itemView) {
            super(itemView);
            comp_timetable = (TextView) itemView.findViewById(R.id.comp_timetable_name);
        }

        public TextView getTextView() {
            return comp_timetable;
        }

    }
    public comp_timetable_adapter( ArrayList<String> list, String studentID) {
        this.list = list;
        this.studentID = studentID;
    }

    @NonNull
    @Override
    public comp_timetable_adapter.courseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comp_timetable_item, parent, false);
        return new comp_timetable_adapter.courseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(comp_timetable_adapter.courseViewHolder holder, int position) {
        holder.getTextView().setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
