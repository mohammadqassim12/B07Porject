package com.example.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.app.Login_Pkg.Login_Presenter;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterUnitTests {
    @Mock
    


    @Test
    public void constructTest() {
        //Construct a logic tester, then ensure that the username and password are set and equal the input
        Login_Presenter logic_tester = new Login_Presenter("UwU", "RawrXDNuzzle");
        assertEquals(logic_tester.Username, "UwU");
        assertEquals("RawrXDNuzzle", logic_tester.getPassword());
    }
 //   @Test
    public void DataBaseRefTest() {
        //MOCKQUITO!!! Create a new user in the database with UserID "OwO" for purposes of testing
        // and make sure the getDatabasePassword method can get that reference
        Login_Presenter logic = new Login_Presenter("OwO", "Please Work");
        assertTrue(logic.errorExists());
        assertEquals(logic.ErrorCode, 1);
    }

    @Test
    public void checkAdminTest() {
        Login_Presenter logic = new Login_Presenter("aaaaaaaaaaaa", "pppppppppp");
        assertTrue(logic.checkAdmin());
    }
    @Test
    public void checkAdminTestFalse()
    {
        Login_Presenter logic = new Login_Presenter("waaaaaaaaaaa", "pppppppppp");
        assertFalse(logic.checkAdmin());
    }
    @Test
    public void createMessageForUserNotExist() {
        Login_Presenter logic = new Login_Presenter(":P", "1776");
        logic.ErrorCode = 1;
        assertEquals("This User ID does not exist", logic.createMessage());
    }
    @Test
    public void createMessageForPasswordWrong()
    {
        Login_Presenter logic = new Login_Presenter("XD", "I forgor");
        logic.ErrorCode = 2;
        assertEquals("The password is incorrect", logic.createMessage());
    }
    //In order to test the errorExists function with Mockquito style, we need to override the function
    //so that we pretend we have already read a value from the database instead of actually reading
    //it
    @Test
    public void testErrorExistsNoError()
    {
        Login_Presenter logic = new Login_Presenter("Coolio", "Eyyyyyyyyyyy") {
            @Override
            public void setErrorCode() {
                String Password = this.getPassword();
                checkForErrors calling_method = new checkForErrors()
                {
                    @Override
                    public void errorCheck(String s) {
                        if (s == null) {
                            ErrorCode = 1;
                        }
                        if (s.equals(logic.Password)) {
                            ErrorCode = 0;
                        } else {
                            ErrorCode = 2;
                        }
                    }
                };
                //This is our pretended read value
                calling_method.errorCheck("Eyyyyyyyyyyy");
            }
        };
        assertFalse(logic.ErrorExists());
    }
    @Test
    public void testErrorExistsUserNotExist()
    {
        Login_Presenter logic = new Login_Presenter("Coolio", "Eyyyyyyyyyyy") {
            @Override
            public boolean errorExists() {
                //This is our pretended read value
                String Password;
                checkForErrors calling_method = new checkForErrors()
                {
                    @Override
                    public void errorCheck(String s) {
                        if (s == null) {
                            ErrorCode = 1;
                        }
                        if (s.equals(Password)) {
                            ErrorCode = 0;
                        } else {
                            ErrorCode = 2;
                        }
                    }
                };
                return ErrorCode != 0;
            }
        };
        assertFalse(logic.errorExists());
    }
}


