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
    public boolean checkUserName(String username){
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
    public boolean checkPasswordComplexity(String password){
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
    public boolean checkCellPhoneNumber(String phonenumber){
       //checking if the password meets the standard format
       if (phonenumber == null)return false;
       //prefixes = 06,07,08, with or without the plus sign
            String standardRegex = "^(?:\\+27|27|0)[678]\\d{8}$"; 
            
            return phonenumber.matches(standardRegex);
    }
    //the register method
    public String registerUser(String username, String password){
        if (!Login.checkUserName(username)) return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        if (!Login.checkPasswordComplexity(password)) return "Password is not correctly formatted, please ensure that your password contains at least 8 characters a capital letter and a special character.";
        return "Username and Password successfully captured.";
    }
    //login method
    public boolean loginUser(String fUsername, String fPassword){
        
    }
    
}
