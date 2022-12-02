package com.example.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.annotation.NonNull;

import com.example.app.Login_Pkg.Login_Presenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;

public class Login_Presenter_Unit_Tests {
    @Test
    public void getPassTest()
    {
        //Set password, then ensure that the same password is returned using the getPassword() method
        Login_Presenter logic_tester = new Login_Presenter("UwU", "OwOWhatsThis?");
        String held_password = logic_tester.getPassword();
        assertEquals(held_password, "OwOWhatsThis?");
    }
    @Test
    public void constructTest()
    {
        //Construct a logic tester, then ensure that the username and password are set and equal the input
        Login_Presenter logic_tester = new Login_Presenter("UwU", "RawrXDNuzzle");
        assertEquals(logic_tester.Username, "UwU");
        assertEquals("RawrXDNuzzle", logic_tester.getPassword());
    }
    @Test
    public void DataBaseRefTest()
    {
        //MOCKQUITO!!! Create a new user in the database with UserID "OwO" for purposes of testing
        // and make sure the getDatabasePassword method can get that reference
        DatabaseReference DBref = FirebaseDatabase.getInstance().getReference("User Database");
        DBref.child("OwO").child("password").setValue("Slightly Cursed");
        Login_Presenter logic_tester = new Login_Presenter("OwO", "Slightly Cursed");
        DatabaseReference pass_ref = logic_tester.getDatabasePassword();
        assertEquals(pass_ref, DBref.child("OwO").child("password"));
        //Make sure to prevent memory leak
        DBref.child("OwO").setValue(null);
    }
    @Test
    public void ErrorExistsTestNonExistantUser()
    {
        //Use another Mockquito style test, except now we mock the input from view
        DatabaseReference DBref = FirebaseDatabase.getInstance().getReference("User Database");
        //Get the snapshot of a key which obviously does not exist
        DBref.child("wejhaefhfkhuqriridhjdwiofewhsdfjdasjbhasdkh").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Login_Presenter logic = new Login_Presenter("Stuff", "PStuff");
                //The method should return the error code for a nonexistant User in the DB
                assertTrue(logic.errorExists(snapshot));
                assertEquals(logic.ErrorCode, 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Test
    public void ErrorExistsTestDiffPasswords()
    {
        DatabaseReference DBref = FirebaseDatabase.getInstance().getReference("User Database");
        DBref.child("Weeb").child("password").setValue("Rawr");
        DBref.child("Weeb").child("password").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Same password, except the first character in the Login_Presenter field password is lowercase
                Login_Presenter logic = new Login_Presenter("Weeb", "rawr");
                assertTrue(logic.errorExists(snapshot));
                assertEquals(2, logic.ErrorCode);
                //Delete the data to prevent a memory leak
                DBref.child("Weeb").child(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Test
    public void ErrorExistsTestNoError() {
        DatabaseReference DBref = FirebaseDatabase.getInstance().getReference("User Database");
        DBref.child("OwO").child("password").setValue("I'm running out of ideas");
        DBref.child("OwO").child("password").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Login_Presenter logic = new Login_Presenter("OwO", "I'm running out of ideas");
                assertFalse(logic.errorExists(snapshot));
                assertEquals(0, logic.ErrorCode);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
