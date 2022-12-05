package com.example.app.Login_Pkg;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.SharedPreferencesUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class Login_Presenter extends AppCompatActivity {
    public String Username;
    private String Password;
    private int ErrorCode;
    public SharedPreferences pref;
    public SharedPreferences.Editor edit;
    //Constructor
    public Login_Presenter(Context login_con, String U_in, String P_in)
    {
        Username = U_in;
        Password = P_in;
        pref = login_con.getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        edit = pref.edit();
    }
    //Since this calls the Login_Model, this would be tested on an Integration Test, not a JUnit test
    public String getDatabaseValue()
    {
        Login_Model model = new Login_Model(pref, edit);
        model.Password_Error_Searching(Username);
        System.out.println(pref.getString("Password", null));
        return pref.getString("Password", null);
    }
    //returns True if an error exists in the user input, false o/w
    public boolean ErrorExists(String s) {
        if (s == null) {
            ErrorCode = 1;
        }
        else if (s.equals(Password)) {
            ErrorCode = 0;
        }
        else {
            ErrorCode = 2;
        }
        return ErrorCode != 0;
    }

    //Checks if the user input of the initialized Username is an admin assuming admin userIDs start with a
    public boolean checkAdmin()
    {
        return Username.startsWith("a");
    }
    //Returns the string to be displayed to the user if an error has occurred with the initialized ErrorCode
    public String createMessage()
    {
        if (ErrorCode == 1)
        {
            return ("This User ID does not exist");
        }
            return ("The password is incorrect");
        //Only is returned if ErrorCode == 2
    }

}
