/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package javamessengerapplication;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Neo
 */
public class NewEmptyJUnitTest {
    private Account validAccount;
    private Login validLogin;
    
    @Before
    public void setup(){
        //name, surname, username, password, phone
        validAccount = new Account ("Kyle","Smith","kyl_1","Ch&&sec@ke99!","0821234567");
        validLogin = new Login(validAccount);
    }
    //REGISTER MESSAGE
    @Test 
    public void username_incorrect_format_and_register_message(){
        Account a = new Account("Kyle","Smith","kyle!!!!!!","Ch&&sec@ke99!","0821234567");
        Login login = new Login(a);
        
        assertFalse("Username should be invalid (no underscore / too long)", login.checkUserName());
        
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.",login.registerUser());
    }
    //PASSWORD
    @Test 
    public void password_meets_complexity(){
        Account a = new Account ("Kyle","Smith","kyl_1","Ch&&sec@ke99!","+27838968976");
        Login login = new Login(a);
        
        assertTrue("Password should meet complexity rules",login.checkPasswordComplexity());
    }
    
    @Test 
    public void password_does_not_meet_complexity_and_register_message(){
     Account a = new Account ("Kyle","Smith","kyl_1","password","+27838968976");
     Login login = new Login(a);
          
     assertFalse("Simple 'password' should fail complexity",login.checkPasswordComplexity());
     assertTrue("Register should return a password-format error",login.registerUser().toLowerCase().contains("password is not correctly formatted"));
    }
    
    //PHONE NUMBER
    @Test
    public void phone_correctly_formatted(){
     Account a = new Account ("Kyle","Smith","kyl_1","Ch&&sec@ke99!","+27838968976");
     Login login = new Login(a);
     
     assertTrue("Phone with +27 and valid digits should pass",login.checkCellPhoneNumber());
    }
    
    @Test
    public void phone_incorrectly_formatted(){
      Account a = new Account ("Kyle","Smith","kyl_1","Ch&&sec@ke99!","08966553");
      Login login = new Login(a);  
      
      assertFalse("To short phone should fail",login.checkCellPhoneNumber());
      assertTrue("Register should return a cell-phone error when phone invalid",login.registerUser().toLowerCase().contains("cell"));
    }

    //LOGIN
    @Test
    public void login_success_and_failure(){
     Account a = new Account ("Kyle","Smith","kyl_1","Ch&&sec@ke99!","0821234567");
     Login login = new Login(a);   
     
     assertTrue("Login should succeed with correct credentials",login.loginUser("kyl_1", "Ch&&sec@ke99!"));
     assertFalse("Login should fail with incorrect password",login.loginUser("kyl_1", "password"));
     assertFalse("Login should fail with incorrect username",login.loginUser("kyle!!!!!!", "Ch&&sec@ke99!"));
    }
    @Test
    public void return_login_status_message(){
     Account a = new Account ("Kyle","Smith","kyl_1","Ch&&sec@ke99!","0821234567");
     Login login = new Login(a);
     
     String ok = login.returnLoginStatus(true);
     assertTrue("Welcome message should include name and surname",ok.contains("Kyle")&& ok.contains("Smith"));
     
     String fail = login.returnLoginStatus(false);
     assertEquals("Incorrect username or password", fail);
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
