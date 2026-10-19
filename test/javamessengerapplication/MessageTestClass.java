/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package javamessengerapplication;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Neo
 */
public class MessageTestClass {
    //the two messages
    private Messages m1;
    private Messages m2;
    
    //the expected message for an invalid cell number
    private static final String ErrorMessage = "Cell phone number is incorrectly formatted or does not contain an international code."
            + "Please correct the number and try again.";
    
    
    //
    @Before 
    public void setUp(){
        Messages.resetForTest();
        
        //test 1, uses a fixed ID so the hash is predictable 
        m1 = new Messages ("0012345678",0,"+27718693002","Hi Mike, can you join us for dinner tonight?");
        //test 2
        m2 =  new Messages ("0198765432",1,"+27834557896","Hi Keegan, did you receive the payment?");
    }
 //test units 
    //
    @Test
    //success test, a short message should be accepted
    public void messages_Lenght_Success(){
        assertEquals("Message ready to send.", m1.checkMessageLength());
    }
     
    @Test 
    //a failure test, 260 characters is 10 over the limit and the error must indicated "by 10"
    public void message_Length_Failure(){
        String longText = "a".repeat(260);
        Messages m =  new Messages ("0012345678",0,"+27718693002",longText);
        assertEquals("Message exceeds 250 characters by 10, please reduce the size.", m.checkMessageLength());
    }
        
    @Test
    //that is, it is a valid south african phone number in international format
    public void recipient_Success(){
        assertEquals("Cell phone number successfully captured.", m1.checkRecipientCell());
    }
    @Test
    //failure if its starts with 0 and there's no international code
    public void recipient_Failure(){
        Messages m = new Messages ("0012345678", 0, "08575975889", "Hi there");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. "
                + "Please correct the number and try again.", m.checkRecipientCell());
    }
     
     @Test 
    public void allHashInLoop(){
        Messages[]msgs = {m1, m2};
        String[] expected = {"00:0:HITONIGHT", "01:1:HIPAYMENT"};
        for (int i = 0; i < msgs.length; i++){
            assertEquals(expected[i], msgs[i].createMessageHash());
        }
    }
    
    @Test
    //randomly generated ID for each message must be exactly 10 characters long
    public void message_ID_Created(){
        Messages m = new Messages(0, "+27718693002", "Hello there");
        assertTrue(m.checkMessageID());
        assertEquals(10, m.getMessageID().length());
    }
    
    @Test
    //hash  should be first 2 digits of the ID and the message number being the first word and last word in CAPS
    public void message_Hash_Correct(){
        assertEquals("00:0:HITONIGHT", m1.createMessageHash());
    }
   
    
    @Test
    //choice 1: send, 2:disregard and 3:each returns its own message
    public void with_send_Disregard_Store(){
        assertEquals("Message successfully sent.", m1.sentMessage(1));
        assertEquals("Press 0 to delete the message.", m1.sentMessage(2));
        assertEquals("Message successfully stored.", m1.sentMessage(3));
}
    @Test
    //sending one message from each two of test messages gives a total of 2
    public void total_Messages_Returned(){
        m1.sentMessage(1);
        m1.sentMessage(1);
        assertEquals(2, Messages.returnTotalMessage());
    }
    
    @Test
    //failure if a longer ID character that is more than 10 
    public void message_ID_too_long(){
        Messages m = new Messages("12345678901", 0, "+27718693002", "Hello there");
        assertFalse(m.checkMessageID());
    }
    
    @Test
    //exactly only 250 characters are allowed per message
    public void message_mst_be_250_characters(){
        Messages m = new Messages("0012345678", 0, "+27718693002", "a".repeat(250));
        assertEquals("Message ready to send.", m.checkMessageLength());
    }
    
    @Test
    //if nothing has been sent output be no messages sent to in this sessions
    public void print_Messages_when_no_message_is_sent(){
        assertEquals("No messages sent to in this session", Messages.printMessages());
    }
    
    @Test
    //after sending the output must have a hash and the recipient
    public void print_Messages_After_Sending(){
        m1.sentMessage(1);
        String Output = Messages.printMessages();
        assertTrue(Output.contains("Message Hash: 00:0:HITONIGHT"));
        assertTrue(Output.contains("Recipient: +27718693002"));
    }
    
    @Test
    //failure if it's the correct format but a different country code
    public void recipient_wrong_code(){
        Messages m = new Messages("0012345678", 0,"+44718693002","Hi there");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. "
                + "Please correct the number and try again.", m.checkRecipientCell());
    }
    
    @Test
    //failure if there's very few digits after +27
    public void recipient_short(){
        Messages m = new Messages("0012345678", 0, "+2771869", "Hi there");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. "
            + "Please correct the number and try again.", m.checkRecipientCell());
    }
    
    @Test
    //With a one word message the first and the last word are the same word
    public void hash_with_one_word(){
      Messages m = new Messages("0512345678", 3, "+27718693002", "Hello");
        assertEquals("05:3:HELLOHELLO", m.createMessageHash());  
    }
    
    @Test
    //this is any other choice than 1,2 or 3 is by default invalid
    public void invalid_choice_return_valid(){
        assertEquals("Invalid option", m1.sentMessage(9));
    }
}   

 
