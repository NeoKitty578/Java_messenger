/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javamessengerapplication;

/**
 *
 * @author Neo Lebea ST10507528
 */
import java.util.Scanner;
public class JavaMessengerApplication { 
    
   

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        
        //this is where i will prompt the user 
       Scanner userInput = new Scanner(System.in);
       
        System.out.println("=== REGISTRATION ===");
        Account registeredAccount = null;
        
       //account registration
       while (registeredAccount == null){
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
        Login registrationLogin = new Login (newAccount);
        
        //registering
        String regUser = registrationLogin.registerUser();
        System.out.println(regUser);
        
    //if registering is successful    
    if (regUser.toLowerCase().contains("registered successfully")){
      registeredAccount = newAccount;
       }else{
     //allowing the user to retry or to quit
     System.out.println("Registration failed. Try again? /n");
     String choice = userInput.nextLine().trim();
     if (choice.equalsIgnoreCase("n")){
         System.out.println("Registration cancelled. ");
         userInput.close();
         return;
     }
     System.out.println("Let's try registration again."); 
 }
    //
           System.out.println("=== ACCOUNT LOGIN ==="); 
           Login systemLogin = new Login(registeredAccount);
           
           final int maxAttempts = 3;
           int attempt = 0;
           boolean isAuthenticated = false;
           
           while (!isAuthenticated && attempt < maxAttempts){
               System.out.println("Enter username: ");
               String loginUser = userInput.nextLine().trim();
               
               System.out.println("Enter password: ");
               String loginPass = userInput.nextLine();
               
               isAuthenticated = systemLogin.loginUser(loginUser, loginPass);
               System.out.println(systemLogin.returnLoginStatus(isAuthenticated));
               
               if (!isAuthenticated){
                   attempt++;
                   if (attempt < maxAttempts){
                       System.out.println("Attempts remaining: "+(maxAttempts - attempt));
                   }else{
                       System.out.println("Maximum login attempts reached. ");
                   }
               }
           }
           if (!isAuthenticated){
               System.out.println("You are now loggen in. Continue with the application.");
           }  
        }
    }
}
