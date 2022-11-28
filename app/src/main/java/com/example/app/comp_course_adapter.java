package com.example.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class comp_course_adapter extends RecyclerView.Adapter<comp_course_adapter.courseViewHolder> {

    List<String> list;

    public comp_course_adapter( ArrayList<String> list) {
        this.list = list;
    }

    public static class courseViewHolder extends RecyclerView.ViewHolder {
        TextView comp_course;

        public courseViewHolder(View itemView) {
            super(itemView);
            comp_course = (TextView) itemView.findViewById(R.id.comp_course_name);
        }

        public TextView getTextView() {
            return comp_course;
        }
    }

    @Override
    public courseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comp_course_item, parent, false);
        return new courseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(courseViewHolder holder, int position) {
        holder.getTextView().setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
