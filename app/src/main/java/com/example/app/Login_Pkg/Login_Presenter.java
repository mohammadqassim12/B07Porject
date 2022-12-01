package com.example.app.Login_Pkg;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class Login_Presenter {
    public String Username;
    private String Password;
    private int ErrorCode;
    public Login_Presenter(String U_in, String P_in)
    {
        Username = U_in;
        Password = P_in;
    }
    public DatabaseReference getDatabasePassword()
    {
        Login_Model model = new Login_Model();
        return model.getDBPasswordRef(Username);
    }
    public String getPassword()
    {
        return Password;
    }

    public boolean errorExists(DataSnapshot snapshot)
    {
        if (!(snapshot.exists()))
        {
            ErrorCode = 1;
            return true;
        }
        if(snapshot.getValue().toString().equals(Password))
        {
            ErrorCode = 0;
            return false;
        }
        ErrorCode = 2;
        return true;
    }
    public boolean checkAdmin()
    {
        return Username.startsWith("a");
    }
    public String createErrorMessage()
    {
        if (ErrorCode == 1)
        {
            return ("This User ID does not exist");
        }
            return ("The password is incorrect");
        //Only is returned if ErrorCode == 2
    }

}
