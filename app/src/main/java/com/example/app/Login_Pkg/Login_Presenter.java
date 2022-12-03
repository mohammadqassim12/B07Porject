package com.example.app.Login_Pkg;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class Login_Presenter {
    public String Username;
    private String Password;
    public int ErrorCode;
    //Constructor
    public Login_Presenter(String U_in, String P_in)
    {
        Username = U_in;
        Password = P_in;
    }
    //returns the database reference to the password of the corresponding instantiated Username
    public DatabaseReference getDatabasePassword()
    {
        Login_Model model = new Login_Model();
        return model.getDBPasswordRef(Username);
    }
    //get() method for the private Password field
    public String getPassword()
    {
        return Password;
    }
    //returns True if an error exists in the user input, false o/w
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
    //Checks if the user input of the initialized Username is an admin assuming admin userIDs start with a
    public boolean checkAdmin()
    {
        return Username.startsWith("a");
    }
    //Returns the string to be displayed to the user if an error has occurred with the initialized ErrorCode
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
