/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javamessengerapplication;

//packages
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//for the json file
import java.nio.file.Files;
import java.nio.file.Path;


/**
 *
 * @author Neo Lebea ST10507528
 * message class represents a single message and contains helpers to validate, store and print messages for the session
 * core features
 * a generated 10 digit message ID
 *  validating recipient format
 * validate message length 
 * compute message hash
 */

public class Messages {
 // variables that belong to this class for storage for sent and stored message
    private static final List<Messages> sentMessages = new ArrayList<>();
    private static final List<Messages> storedMessages = new ArrayList<>();
    private static int totalSent = 0;
    
    //instance fields for each message
    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String text;
    private final String messageHash;
    
    //my constructor: ID will be randomely generated.
    public Messages(int messageNumber, String recipient, String text){
        this(generateID(), messageNumber, recipient, text);
        
    }
    //secondary cosntructor, this constructor will be used by the unit test so that the ID AND the hash are predictable.
    public Messages(String messageID,int messageNumber, String recipient, String text){
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.text = text;
        this.messageHash = createMessageHash();
    }
    // Generate a random 10-digit string (each char 0-9)
    private static String generateID(){
        Random k = new Random ();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<10; i++){
            sb.append(k.nextInt(10));
        }
        return sb.toString();
    }
    
    // ID REQUIREMENT: must not be longer than 10 characters
    public boolean checkMessageID(){
        return messageID != null && messageID.matches("\\d{10}");
    }
    
    //MESSAGE REQUIREMENT: message must be 250 characters or less
    public String checkMessageLength(){
        if (text.length() <= 250){
            return "Message ready to send.";
        }
        int over = text.length() - 250;
        return "Message exceeds 250 characters by "+ over + ", please reduce the size.";
    }
    
    
     // Validate recipient format
     //Current pattern for South African mobile numbers: 
     // must start with +27
     //then a digit 1-9 and 8 more digits (total length after the + is 9)
     //
    public String checkRecipientCell(){
        if (recipient != null && recipient.matches("^\\+27[1-9]\\d{8}$")){ //reference way of using "matches": geeksforgeeks
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. "
                + "Please correct the number and try again.";
    }
            
    //the message hash has
    //first two characters of messageID
    //colon
    //messageNumber
    //another colon
    //then a concatination of the first and the last words of the message
    //result is then return in uppercase
    public String createMessageHash(){
        String[] words = text.trim().split("\\s+");
        String first = clean(words[0]);
        String last = clean(words[words.length - 1]);
        return (messageID.substring(0, 2)+":"+messageNumber + ":" +first + last).toUpperCase();  
    }
    
     // strips punctuation such as "tonight?" -> "tonight", it basically removes non-alpahbetic characters from a word
    private String clean(String word) {
        return word.replaceAll("[^A-Za-z0-9]", "");
    }
    
    //following method has 1(send), 2(disregard), 3(store for later)
    public String sentMessage(int choice){
        switch (choice){
            case 1: 
              sentMessages.add(this);
              totalSent++;
                  return "Message successfully sent.";
            case 2: 
              return "Press 0 to delete the message.";
            case 3: 
               storedMessages.add(this);
               storedMessage();
               return "Message successfully stored.";
            default : return "Invalid option";
        }
    }
     
    //print message
    public String printMessage(){
        return "MessageID: "+ messageID + "\n"+  "Message Hash: " 
                + messageHash + "\n" + "Recipient: " + recipient + "\n" +"Message: " + text;
                
    }
    
    //Human readable representation of this message
    public static String printMessages(){
        if (sentMessages.isEmpty()){
            return "No messages sent to in this session";
        }
        StringBuilder sb = new StringBuilder();
        for (Messages m: sentMessages){
            sb.append(m.printMessage()).append("\n\\n");
        }
        return sb.toString();
    }
    
    //count of message will be sent to this runtime
    public static int returnTotalMessage(){
        return totalSent;
    }
    
    //test helper, this clears runtime collections
    public static void resetForTest(){
        sentMessages.clear();
        storedMessages.clear();
        totalSent = 0;
    }
    
    
    
    //this method write stored messages to message json file as a simple JSON array.
    public void storedMessage(){
        //creating a json builder 
        StringBuilder json = new StringBuilder("[\n");
        //looping through the messages
        for (int i = 0; i < storedMessages.size();i++){ //returns the number of messages in the list
            //building each message object
            Messages m = storedMessages.get(i); //gets the message at index [i]
            
            json.append(" {\n")//adding text to the json file
                .append("    \"messageID\": \"").append(esc(m.messageID)).append("\",\n") //the esc means that it should escape special characters so that they shouldnt break the json file
                .append("    \"messageHash\": \"").append(esc(m.messageHash)).append("\",\n")
                .append("    \"recipient\": \"").append(esc(m.recipient)).append("\",\n")
                .append("    \"message\": \"").append(esc(m.text)).append("\"\n")
                    //short form of an if-statement
                .append("  }").append(i < storedMessages.size() - 1 ? "," : "").append("\n");    
        }
        //finishing the json file
        json.append("]");
        //writing to it
        try{Files.writeString(Path.of("message.json"), json.toString()); //try and catch handles errors if writing to the file fails and the tostring converts the completed builder into String.
        } catch (IOException e){
            System.out.println("Could not write JSON file: "+ e.getMessage());
        }
    }
    
    //
    private static String esc(String s){
        return s.replace("\\","\\\\").replace("\"", "\\\"").replace("\n","\\n");
    }
    public String getMessageID(){
        return messageID;
    }
    
    public String getMessageHash(){
        return messageHash;
    }
}
