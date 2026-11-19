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
//part 3
import java.util.List;
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
        Login registrationLogin = new Login (newAccount);
        
        //registering
        String regUser = registrationLogin.registerUser();
        System.out.println(regUser);
        
    //if registering is successful    
    if (regUser.toLowerCase().contains("registered successfully")){
      registeredAccount = newAccount;
       }else{
     //allowing the user to retry or to quit
     System.out.println("Registration failed. Try again? (y/n)");
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
               System.out.println("You cannot send messages without logging in"); 
               userInput.close();
               return;
           } 
           System.out.println("You are now logged in. Continue with the application.");
           
           //-----QUICK CHAT STARTS HERE----
           //Quick chat: ask the user how many messages the user will enter in this session
           System.out.println("------Welcome to QuickChat.-------");
           System.out.println("                                  ");
           // fills the stored array from message.json
           Messages.loadStoredFromJson(); 
           
           //changed readInt to readPositiveInt and removed int entered = 0 (for part3 )
           int maxMessages = readPositiveInt(userInput, "How many messages do you wish to enter?");
           boolean running = true;
           boolean messageEntered = false;
         
           // the user will be able to choose from a numeric menu, this while loop  will run until user selects quite(priming input)
           while (running){
               System.out.println("1 Send messages");
               System.out.println("2 Show recently sent messages");
               System.out.println("3 Quit");
               System.out.println("4 Stored messages");
               int option = readInt(userInput, "Choose an option: ");
               
           switch (option) {
               case 1 -> {
                   //send messages flow, allow exactly the number the user specified.
                   if (messageEntered){
                       System.out.println("You have already entered all "+ maxMessages +" messages.");
                   }else{
                      for (int i = 0; i  < maxMessages; i++){
                          SendOne(userInput, i);
                       }
                       //after all entries print the messages that were sent
                       messageEntered = true;
                       System.out.println("Total messages sent: " + Messages.returnTotalMessage());
                   }
               }
               //changed the coming soon part and added case 4
               case 2 -> System.out.println(Messages.printMessages());
               case 3 -> {
                   System.out.println("Goodbye");
                   running = false;
               }
               case 4 -> storedMessagesMenu(userInput, registeredAccount.getName());
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
        System.out.println("Message ID generated: "+ m.getMessageID());
        System.out.println("Message Hash: "+ m.getMessageHash());
        
        //ask the user what to do with the message
        int choice = 0;
        while (choice < 1 || choice > 3 ){
          System.out.println("1) Send Messages 2)Disregard Message 3)Store message to send later");
          choice = readInt(scannerInput, "Choice:");
        }
        System.out.println(m.sentMessage(choice));
        //added line 197 and 198 for part 3
        if (choice == 1){ // show the full message details only when the message was actually sent
    System.out.println(m.printMessage()); // details straight after sending
}
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
    
    // asks until the user types a whole number of 1 or more
private static int readPositiveInt(Scanner userInput, String prompt){
    while (true){
        int n = readInt(userInput, prompt);
        if (n >= 1){
            return n;
        }
        System.out.println("Please enter a number of 1 or more.");
    }
}

// the Stored Messages sub-menu (option 4)
private static void storedMessagesMenu(Scanner userInput, String sender){
    boolean back = false;
    
    while (!back){
        //show the menu for each time round the loop
        System.out.println("a) Sender and recipient of stored messages");
        System.out.println("b) Longest message");
        System.out.println("c) Search by message ID");
        System.out.println("d) Search by recipient");
        System.out.println("e) Delete by message hash");
        System.out.println("f) Report");
        System.out.println("g) Back");
        //read the choice, trim to remove any spaces and toLowercase lets "A" and "a" both work
        String c = userInput.nextLine().trim().toLowerCase();

        switch (c){
            //list the sender
            case "a"-> System.out.println(Messages.displaySenderAndRecipient(sender));
            
            //display the longest message
            case "b"-> System.out.println(Messages.findLongest());
            
            //search for messages by its ID ans also show the recipient and message text
            case "c"->{
                System.out.println("Enter message ID:");
                  Messages m = Messages.findByID(userInput.nextLine().trim());
                System.out.println(m == null ? "Message ID not found." : "Recipient: " + m.getRecipient() + "\nMessage: " + m.getText());
            }
            //search for every messages sent
            case "d" ->{
                System.out.println("Enter recipient number:");
                List<String> found = Messages.searchByRecipient(userInput.nextLine().trim());
                //tell the user when nothing matched
                if (found.isEmpty()){
                  System.out.println("No messages for that recipient.");
                }
                //print every matching message on its own line
                for (int i = 0; i < found.size(); i++){
                   System.out.println(found.get(i));
              }
            }
            // e) delete a message using its hash; deleteByHash returns the confirmation
            case "e" -> {
                System.out.println("Enter message hash:");
                System.out.println(Messages.deleteByHash(userInput.nextLine().trim()));
               }
            //print a report wiht hash
            case "f"-> System.out.println(Messages.report());
            //leave the sub menu to go back to the main menu
            case "g"-> back = true;
            default-> System.out.println("Invalid option");
        }
    }
}
}

    
  

