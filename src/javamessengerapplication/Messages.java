/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javamessengerapplication;

//packages

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//for the json file
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    //sent message
    private static final List<Messages> sentMessages = new ArrayList<>(); //the user picks one(Send)
    //part 3 arrays
     //disregarded messages
    private static final List<Messages> disregardedMessages = new ArrayList<>(); //the user picks 2 (Disregard)
    //stored messages (in the JSON file) 
    private static final List<Messages> storedMessages = new ArrayList<>(); //the user picks 3(Store) or when loadStoredFromJson() reads message.json
    //Message Hash
    private static final List<String> messageHashes = new ArrayList<>();//everytime a message is sent or stored, through register()
    //MessagesID
    private static final List<String> messageIDs = new ArrayList<>();//everytime a message is sent or stored, through register()
    
    private static int totalSent = 0;
    private static Path jsonFile = Path.of("message.json"); //tests can point this at a temporary files
    
    public static void setJsonFile(Path p) {jsonFile = p;}
    
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
    //for part 3 this method was modified.
    public String sentMessage(int choice){
        switch (choice){
            case 1: 
              sentMessages.add(this);
              //added register();
              register();
              totalSent++;
                  return "Message successfully sent.";
            case 2: 
                //added disregardmessages
              disregardedMessages.add(this);
              return "Press 0 to delete the message.";
            case 3: 
               storedMessages.add(this);
               //added register
               register();
               storedMessage();
               return "Message successfully stored.";
            default : return "Invalid option";
        }
    }
   //keeps the ID and hash arrays in step with the sent/stored arrays  
    private void register(){
        messageIDs.add(messageID);
        messageHashes.add(messageHash);
    }
    


    //print message
    public String printMessage(){
        return "MessageID: "+ messageID + "\n"+  "Message Hash: " 
                + messageHash + "\n" + "Recipient: " + recipient + "\n" +"Message: " + text;
                
    }
    
    //Human readable representation of this message
    public static String printMessages(){
        if (sentMessages.isEmpty()){
            return "No messages sent in this session.";
        }
        StringBuilder sb = new StringBuilder();
        for (Messages m: sentMessages){
            sb.append(m.printMessage()).append("\n\n");
        }
        return sb.toString();
    }
    
    //count of message will be sent to this runtime
    public static int returnTotalMessage(){
        return totalSent;
    }
    
    //test helper, this clears runtime collections
    //this method has to clear all five, otheriwse test datas will leak into each other
    public static void resetForTest(){
        sentMessages.clear();
        storedMessages.clear();
        disregardedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
        totalSent = 0;
    }
    
    
    
    //this method write stored messages to message json file as a simple JSON array.
    public static void storedMessage(){
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
        try{Files.writeString(jsonFile, json.toString()); //try and catch handles errors if writing to the file fails and the tostring converts the completed builder into String.
        } catch (IOException e){
            System.out.println("Could not write JSON file: "+ e.getMessage());
        }
    }
    
    //
    private static String esc(String s){
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()){
            switch (c){
                case '"' -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default -> {
                    if (c < 0x20)sb.append(String.format("\\u%04x", (int) c));
                else sb.append(c);
                }
            }
        }
        return sb.toString();
    }
    public String getMessageID(){
        return messageID;
    }
    
    public String getMessageHash(){
        return messageHash;
    }
    
    //GETTERS - Part3
    public String getRecipient(){ return recipient;}
    public String getText() {return text;}
    
    public static List<String> getSentText() {return textOf(sentMessages);}
    public static List<String> getStoredText() {return textOf(storedMessages);}
    public static List<String> getDisregardedText() {return textOf(disregardedMessages);}
    
    //collects the text of every message in a list
    private static List<String> textOf(List<Messages> list) {
        List<String> out  = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) out.add(list.get(i).text);
        return out;
    }
    
     // sent first, then stored; used by the search features
    private static List<Messages> allMessages() {
        List<Messages> all = new ArrayList<>(sentMessages);
        all.addAll(storedMessages);
        return all;
    }
    
    //read the JSON file back into the stored array 
    //Reference:Oracle Java Tutorials
    public static void loadStoredFromJson(){
        String content;
        try {
            content = Files.readString(jsonFile);
        } catch (IOException e) {
            return;
        }
        //a JSON string value
        String str = "\"((?:[^\"\\\\]|\\\\.)*)\"";
       
        Pattern p = Pattern.compile("\\{\\s*\"messageID\"\\s*:\\s*" + str
                + "\\s*,\\s*\"messageHash\"\\s*:\\s*" + str
                + "\\s*,\\s*\"recipient\"\\s*:\\s*" + str
                + "\\s*,\\s*\"message\"\\s*:\\s*" + str + "\\s*\\}");
        Matcher m =p.matcher(content);
        while (m.find()){
            String id = unesc(m.group(1));
            if (messageIDs.contains(id)) continue; //already in memory
            String hash = unesc(m.group(2));
            int number = 0;
            try{
             number = Integer.parseInt(hash.split(":")[1]);
            }catch (RuntimeException e){
                
            }
            Messages msg = new Messages(id, number, unesc(m.group(3)), unesc(m.group(4)));
            storedMessages.add(msg);
            msg.register();
        }
    }
    
     // reverses esc(): turns \n, \t, \" etc. back into real characters
    private static String unesc(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 1 < s.length()) {
                char n = s.charAt(++i);
                switch (n) {
                    case 'n' -> sb.append('\n');
                     case 'r' -> sb.append('\r');
                    case 't' -> sb.append('\t');
                    case 'u' -> { sb.append((char)  Integer.parseInt(s.substring(i + 1, i + 5), 16)); i += 4; }
                    default -> sb.append(n);
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    //sender and recipient of every stored message
    public static String displaySenderAndRecipient(String sender){
        if (storedMessages.isEmpty()) return "No stored message.";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < storedMessages.size();i++){
            sb.append("Sender: ").append(sender).append("| Recipient: ").append(storedMessages.get(i).recipient).append("\n");
        } 
        return sb.toString();
    }
    
 
    
    //search by message ID
    public static Messages findByID(String id){
        List<Messages> all = allMessages();
        for (int i = 0; i < all.size(); i++){
            if (all.get(i).messageID.equals(id)) return all.get(i); 
        }
        return null;
    }
    
    public static String searchByID(String id){
       Messages m = findByID(id);
       return (m == null)? "Message ID not found." :m.text;
    }
    
    //all messages for one recipient
    public static List<String> searchByRecipient(String recipient){
        List<String> found = new ArrayList<>();
        List<Messages> all = allMessages();
        for (int i = 0; i < all.size(); i++){
            if (all.get(i).recipient.equals(recipient)) found.add(all.get(i).text);
        }
        return found;
    }
    
    private static Messages removeByHash(List<Messages> list, String hash) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).messageHash.equalsIgnoreCase(hash)) return list.remove(i);
        }
        return null;
    }
    
    
   public static String deleteByHash(String hash) {
        Messages removed = removeByHash(sentMessages, hash);
        boolean wasStored = false;
        if (removed == null) {
            removed = removeByHash(storedMessages, hash);
            wasStored = (removed != null);
        }
       if (removed == null) return "Message hash not found.";
        messageIDs.remove(removed.messageID);
        messageHashes.remove(removed.messageHash);
        if (wasStored) storedMessage(); // keep the JSON file in step
        // the brief's expected output has no full stop before the closing quote
        String shown = removed.text.endsWith(".")
                ? removed.text.substring(0, removed.text.length() - 1) : removed.text;
        return "Message: \"" + shown + "\" successfully deleted.";
   }
   public static String report() {
        StringBuilder sb = new StringBuilder("=== SENT MESSAGES ===\n");
        appendReport(sb, sentMessages);
        sb.append("=== STORED MESSAGES ===\n");
        appendReport(sb, storedMessages);
        return sb.toString();
    }
   
    private static void appendReport(StringBuilder sb, List<Messages> list) {
        if (list.isEmpty()) sb.append("(none)\n");
        for (int i = 0; i < list.size(); i++) {
            Messages m = list.get(i);
            sb.append("Message Hash: ").append(m.messageHash)
              .append(" | Recipient: ").append(m.recipient)
              .append(" | Message: ").append(m.text).append("\n");
        }
    }
    
// finds the message with the longest text, searching sent first, then stored
  public static Messages findLongest() {
    List<Messages> all = allMessages();
    if (all.isEmpty()) return null;
    int longest = 0;
    for (int i = 1; i < all.size(); i++) {
        if (all.get(i).text.length() > all.get(longest).text.length()) longest = i;
    }
    return all.get(longest);
}
    
    public static String longestMessage(){
    Messages m = findLongest();
    return (m == null) ? "No messages available." : m.text;
}
    
    
    }
