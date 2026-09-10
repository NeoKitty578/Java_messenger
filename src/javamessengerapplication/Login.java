/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javamessengerapplication;

/**
 *
 * @author Neo Lebea 
 */

// This class will have methods that will validate user input
public class Login {
   
    //
    private Account account;
    //
    //a constructor that accepts what we want to process
    public Login(Account account){
        this.account = account;
    }
    
    public boolean checkUserName(){
        String username = account.getUsername();//getting the username from the "account"
        if (username == null) return false;
        return username.contains("_") && username.length()>=5;
    }
    //the following method checks if the password meets the requirements.
    //password requirements
    /*At least 8 characters long
    *Contains a capital letter
    *Contains a number
    *Contains a special character
    */
    public boolean checkPasswordComplexity(){
        String password = account.getPassword();
        //if the password is empty or doesnt meet at least 8 characters then return false
        if (password == null || password.length()<8) return false;
        
        //variables to check a capital letter, a number, and a speacial character
        boolean hasCapLetter = false;
        boolean hasNumber = false;
        boolean hasChar = false;
        
        for (int i = 0; i<password.length(); i++){
              //checking if there's a Uppercase letter in the password
              char k = password .charAt(i);
              
              //now checking if the password conditions are met
              if (Character.isUpperCase(k)){
                  hasCapLetter = true;
              }
              //checking digits
              if (Character.isDigit(k)){
                  hasNumber = true;
              }
              //checking if there's special characters excluding space
              if (!Character.isLetterOrDigit(k) && !Character.isWhitespace(k)){
                hasChar = true;
            }
            }
        return hasCapLetter && hasChar && hasNumber;
    }
    
    //
    public boolean checkCellPhoneNumber(){
        String phonenumber = account.getPhoneNumber();
       //checking if the password meets the standard format
       if (phonenumber == null)return false;
       //prefixes = 06,07,08, with or without the plus sign
            String standardRegex = "^(?:\\+27|27|0)[678]\\d{8}$"; 
            
            return phonenumber.matches(standardRegex);
    }
    //the register method
    public String registerUser(){
        if (!checkUserName()){
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity()){
           return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter,a number, and a special character."; 
        }
        if (!checkCellPhoneNumber()){
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        return "User has been registered successfully.";
    }
    //login method
    public boolean loginUser(String fUsername, String fPassword){
        return account.getUsername().equals(fUsername) && account.getPassword().equals(fPassword);
    }
    
    //login status :
    public String returnLoginStatus(boolean isLoggedIn){
       if (isLoggedIn){
         return "Welcome" +account.getName()+ ","+account.getSurname()+" it is great to see you again";
       } else{
      return "Incorrect username or password";  
    }
       
    }
    
}
