/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javamessengerapplication;

/**
 *
 * @author Neo Lebea - ST10507528
 */
//this class will act as an ID, it'll hold user information
public class Account {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String phoneNumber;
    
    // constructor to create the Account object
    
   public Account (String name, String surname, String username, String password, String phoneNumber){
      this.name = name;
      this.surname = surname;
      this.username = username;
      this.password = password;
      this.phoneNumber = phoneNumber; 
   }
   
   //getters: this will make it possible for the login class to access the data from this class
   public String getName(){return name;}
   public String getSurname(){return surname;}
   public String getUsername(){return username;}
   public String getPassword(){return password;}
   public String getPhoneNumber(){return phoneNumber;}
}
