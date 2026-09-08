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
   
    public boolean checkUserName(String fUsername){
        if ((fUsername.length() <=5) && (fUsername.contains("_"))){
            System.out.println("Username successfully captured.");
            }else{
                
        }
    }
}
