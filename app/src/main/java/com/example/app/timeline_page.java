package com.example.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link timeline_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class timeline_page extends AppCompatActivity {
    String[] a = new String[6] ;
    public void getIncomingIntent (){
        if(getIntent().hasExtra("Send")){
            a = getIntent().getStringArrayExtra("Send");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_timeline_page);
        getIncomingIntent();


    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline_page, container, false);
    }*/
}