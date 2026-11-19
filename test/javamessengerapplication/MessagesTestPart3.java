/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package javamessengerapplication;

import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Neo
 */
public class MessagesTestPart3 {
    
    static final String T1 = "Did you get the cake?";
    static final String T2 = "Where are you? You are late! I have asked you to be on time.";
    static final String T3 = "Yohoooo, I am at your gate.";
    static final String T4 = "It is dinner time!";
    static final String T5 = "Ok, I am leaving without you.";
  
   private Messages m1, m2, m3, m4, m5;
   
   //this is a temporary folder so the test never touch the real message.json(jsom file)
   @Rule
   public TemporaryFolder tmp = new TemporaryFolder();
   
   @Before
   public void setUp(){
       Messages.resetForTest();
       Messages.setJsonFile(tmp.getRoot().toPath().resolve("message.json"));
       m1 = new Messages("0000000001", 0, "+27834557896", T1); m1.sentMessage(1);
       m2 = new Messages("0000000002", 1, "+27838884567", T2); m2.sentMessage(3);
       m3 = new Messages("0000000003", 2, "+27834484567", T3); m3.sentMessage(2);
       m4 = new Messages("0000000004", 3, "0838884567", T4); m4.sentMessage(1);
       m5 = new Messages("0000000005", 4, "+27838884567", T5); m5.sentMessage(3);
   }
   
   @Test
   public void sentArrayPopulated(){
     assertEquals(List.of(T1, T4), Messages.getSentText());  
   }
   @Test
   public void disregardArrayPopulated(){
    assertEquals(List.of(T3), Messages.getDisregardedText());    
   }
   @Test
   public void longestMessage(){
    assertEquals(T2, Messages.longestMessage());    
   }
   @Test
   public void searchByMessageID(){
      assertEquals(T4, Messages.searchByID("0000000004"));  
   }
   @Test
   public void searchByUnknownID(){
      assertEquals("Message ID not found.", Messages.searchByID("9999999999"));   
   }
   @Test
   public void searchByRecipient(){
    assertEquals(List.of(T2, T5), Messages.searchByRecipient("+27838884567"));   
   }
   @Test
   public void deleteByHash(){
    assertEquals("Message: \"Where are you? You are late! I have asked you to be on time\" successfully deleted.",
                Messages.deleteByHash(m2.getMessageHash()));
        assertEquals(List.of(T5), Messages.getStoredText());   
   }
   @Test
   public void reportContainsDetails(){
     String report = Messages.report();
     assertTrue(report.contains(m1.getMessageHash()));
      assertTrue(report.contains("+27834557896"));
        assertTrue(report.contains(T1));
        assertTrue(report.contains(m2.getMessageHash()));
   }
   @Test
   public void hashFormat(){
     assertEquals("00:1:WHERETIME", m2.getMessageHash());   
   }
   @Test
   public void jsonSpecialCharacters(){
     Messages.resetForTest();
        String tricky = "He said \"hi\"\nsee you\tsoon";
        new Messages("0000000009", 0, "+27834557896", tricky).sentMessage(3);
        Messages.resetForTest();            // wipe memory, keep the file
        Messages.loadStoredFromJson();
        assertEquals(List.of(tricky), Messages.getStoredText());  
   }
   @Test
   public void searchByUnknownRecipient(){
        assertTrue(Messages.searchByRecipient("+27000000000").isEmpty());
   }
}
