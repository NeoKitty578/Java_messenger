/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javamessengerapplication;

/**
 *
 * @author Neo
 */
import java.util.Scanner;
public class JavaMessengerApplication { //testing gut changes
    
   

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        //this is where i will prompt the user 
       Scanner userInput = new Scanner(System.in);
       
       //account registration
        System.out.println("Enter First Name:");
        String fname = userInput.nextLine();
        
        System.out.println("Enter surname");
        String fsurname = userInput.nextLine();
        
        System.out.println("Enter username");
        String fusername  = userInput.nextLine();
        
        System.out.println("Enter password");
        String fpass  = userInput.nextLine();
        
        System.out.println("Enter cell phone number:");
        String fphone = userInput.nextLine();
        
        //creating an account using the inputs
        Account newAccount = new Account(fname,fsurname,fusername,fpass,fphone);
        
        //pass the account to the login class
        Login systemLogin = new Login (newAccount);
        
        //registering
        String regUser = systemLogin.registerUser();
        System.out.println(""+ regUser);
        
        //if registering process is successful
        if (regUser.contains("registered successfully")){
            System.out.println("===ACCOUNT LOGIN===");
            //
            System.out.println("Enter username");
            String loginUser = userInput.nextLine();
            //
            System.out.println("Enter paasword");
            String loginPass = userInput.nextLine();
            
            boolean isAuthenticated = systemLogin.loginUser(loginUser, loginPass);
            System.out.println(""+systemLogin.returnLoginStatus(isAuthenticated));
            
        }
       
    }
    
}
