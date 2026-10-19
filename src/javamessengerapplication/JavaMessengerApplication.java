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
    
// Main entry for Java Messages Application 
    public static void main(String[] args) {
        
        //this is where i will prompt the user 
       Scanner userInput = new Scanner(System.in);
       
        System.out.println("=== REGISTRATION ===");
        Account registeredAccount = null;
        
       //account registration
       //loop repeats until a successful registration occurs or user cancels
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
        
        //pass the account into Login helper
        //move this!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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
       }
    
    //(UDATED SINCE THE LAST SUBMISSION: PART 1 - now registration is outside the loop)
    //Login phase
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
           //only continue if login worked
           if (!isAuthenticated){
               System.out.println("You cannot send messages without login in"); 
               userInput.close();
               return;
           } 
           System.out.println("You are now loggen in. Continue with the application.");
           
           //-----QUICK CHAT STARTS HERE----
           //Quick chat: ask the user how many messages the user will enter in this session
           System.out.println("Welcome to QuickChat.");
           int maxMessages = readInt(userInput, "How many messages do you wish to enter?");
           int entered = 0;
           boolean running = true;
         
           // the user will be able to choose from a numeric menu, this while loop  will run until user selects quite(priming input)
           while (running){
               System.out.println("1 Send messages");
               System.out.println("2 Show recently sent messages - Coming Soon!!");
               System.out.println("3 Quit");
               int option = readInt(userInput, "Choose an option: ");
               
           switch (option) {
               case 1 -> {
                   //send messages flow, allow exactly the number the user specified.
                   if (entered >= maxMessages){
                       System.out.println("You have already entered all "+ maxMessages +" messages.");
                   }else{
                       while (entered < maxMessages){
                           //handle one message at a time
                           SendOne (userInput, entered);
                           entered++;
                       }
                       //after all entries print the messages that were sent
                       System.out.println("\n" + Messages.printMessages());
                       System.out.println("Total messages sent: " + Messages.returnTotalMessage());
                   }
               }
               case 2 -> System.out.println("Coming soon!");
               case 3 -> {
                   System.out.println("Goodbye");
                   running = false;
               }
               default -> System.out.println("Invalid option");        
           }
           }
           userInput.close();
        }
    //so this method handles one message at a time: it collects, validate and send/store
    //collects data and performs user action
    private static void SendOne (Scanner scannerInput, int number){
        Messages m = null;
        
        //keep asking until recipient and message pass validation
        while (true){
            System.out.println("Recipient cell number (e.g +27718693002): ");
            String recipient = scannerInput.nextLine().trim();
            System.out.println("Messages (max 250 characters): ");
            String text = scannerInput.nextLine().trim();
            
            if (text.isEmpty()){
                System.out.println("Message cannot be empty");
                //to skip the remaining code inside the current loop iteration and immedialtely jump to the next pass of the loop (according to google)
                continue; 
            }
            
            //create a message and run validations
            m = new Messages(number, recipient, text);
            String cellResult = m.checkRecipientCell();
            String lenResult = m.checkMessageLength();
            System.out.println(cellResult);
            System.out.println(lenResult);
            
            if (cellResult.startsWith("Cell phone number successfully")
                && lenResult.equals("Message ready to send.")){
                break; //if both valid leave the loop
            } 
            //if loop fails let the user re-enter
        }
        //display the genereated ID AND hash to user
        System.out.println("Messages ID genereated: "+ m.getMessageID());
        System.out.println("Message Hash: "+ m.getMessageHash());
        
        //ask the user what to do with the message
        int choice = 0;
        while (choice < 1 || choice > 3 ){
          System.out.println("1) Send Messages 2)Disregard Message 3)Store message to send later");
          choice = readInt(scannerInput, "Choice:");
        }
        System.out.println(m.sentMessage(choice));
    }
    
    //this method will keep asking the user until the user types a valid whole number
    private static int readInt(Scanner userInput, String prompt){
        while(true){
            System.out.println(prompt);
            try{
                return Integer.parseInt(userInput.nextLine().trim());
            }catch (NumberFormatException e){
                System.out.println("Please enter a number");
            }
        }
    }
}

    
  

