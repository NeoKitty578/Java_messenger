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
    
    //My Classes
    public class Account {
        private String username;
        private String password;
    }
    //my class
    public class Login {
        private String name; //encaspulation = restricting access
        private String surname;
       // private String username;
       // private String password;
        private String phonenumber;
        
        //constructor 
        public Login (String name, String surname, String username, String password, String phonenumber){
            this.name = name; 
            this.surname = surname;
            this.username = username;
            this.password = password;
            this.phonenumber = phonenumber;
        }
        
        //getters
        public String getName(){
            return name;
        }
        public String getSurname(){
            return surname;
        }
        public String getPassword(){
            return password;
        }
        public String getUsername(){
            return username;
        }
        public String getPhonenumber(){
            return phonenumber;
        }
        
        //setters
        public void setName(String newName){
            this.name = newName; 
        }
        public void setSurname(String newSurname){
            this.surname = newSurname; 
        }
        public void setUsername(String newUsername){
            this.username = newUsername; 
        }
        public void setPassword(String newPassword){
            this.password = newPassword; 
        }
        public void setPhonenumber(String newPhonenumber){
            this.phonenumber = newPhonenumber; 
        }
        
        //my methods 
        //this method will validate the username
        static void checkUserName(String fusername){ 
            
            
            if ((fusername.length() <=5) && (fusername.contains("_"))){
             //output
                System.out.println("Username successfully captured.");
                
            }else{
                System.out.println("Username is not correctly formatted, "
                        + "please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        }
        
        //this method will check if the password meets the requirement.
        static void checkPasswordComplexity(String fpassword){
            boolean hasCapLetter = false;
            boolean hasNumber = false;
            boolean hasChar = false;
            
            for (int i = 0; i<fpassword.length(); i++){
              //checking if there's a Uppercase letter in the password
              char k = fpassword .charAt(i);
              
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
            //then check the overall password
            if (hasCapLetter && hasChar && hasNumber){
                //the password is valid
                System.out.println("Password successfully captured.");
            }else{
                //invalid password
                System.out.println("Password is not correctly formatted; "
                        + "please ensure that your password contains at least 8 characters, "
                        + "a capital letter and a special character.");
            }
        }
        
        //this method will check the format for the cellphone number
        static void checkCellPhoneNumber(String fPhonenumber){
            //checking if the password meets the standard format
            //prefixes = 06,07,08, with or without the plus sign
            String k = "^(?:\\+27|27|0)[678]\\d{8}$"; 
            
            //checking if the phonenumber matches with the k
            if (fPhonenumber.matches(k)){
                System.out.println("Cellphone number successfuly added.");
            }else{
                System.out.println("Cellphone number inccorrectly formatted or does not contain international code.");
                
            }
        }
    }
    

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        //this is where i will prompt the user 
       Scanner objlogin = new Scanner(System.in);
       
        System.out.println("Enter name");
        String name = objlogin.nextLine();
        
        System.out.println("Enter surname");
        String surname = objlogin.nextLine();
        
        System.out.println("Enter username");
        String username  = objlogin.nextLine();
        
        System.out.println("Enter password");
        String password  = objlogin.nextLine();
       
    }
    
}
