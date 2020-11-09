import org.junit.Test;

import static org.junit.Assert.assertSame;

public class MessageManagerTest {

    // test MessageManager class
    @Test(timeout = 50)
    public void testMessageManager(){

        // test getMessages
        MessageManager mm = new MessageManager();
        assertSame("incorrect getMessage\n", 0, mm.getMessages().size());
        Message m1 = new Message(1, 2, "Hi");
        Message m2 = new Message(2, 3, "Hello");
        Message m3 = new Message(1, 3, "Message not added");
        mm.addMessage(m1);
        mm.addMessage(m2);
        assertSame("incorrect getMessage\n", 2, mm.getMessages().size());
        assertSame("incorrect getMessage\n", 0, mm.getMessages().get(0).getMessageID());
        assertSame("incorrect getMessage\n", 1, mm.getMessages().get(1).getMessageID());

        // test getMessageById
        assertSame("gets incorrect message\n", m1, mm.getMessageById(0));
        assertSame("gets incorrect message\n", m2, mm.getMessageById(1));
        assertSame("gets incorrect message\n", null, mm.getMessageById(2));

        // test getMessageContentById
        assertSame("gets incorrect message content\n", "Hi", mm.getMescontentById(0));
        assertSame("gets incorrect message content\n", "Hello", mm.getMescontentById(1));

        // test createMessage
        Message m4 = mm.createMessage("When is the meeting?", 3, 2);
        Message m5 = mm.createMessage("See you", 3, 1);
        mm.addMessage(m4);
        mm.addMessage(m5);
        assertSame("create message failed\n", 4, mm.getMessages().size());
        assertSame("create message failed\n", 3, mm.getMessages().get(2).getMessageID());
        assertSame("create message failed\n", 4, mm.getMessages().get(3).getMessageID());

        // test getIdByMessage
        assertSame("gets incorrect message id\n", 0, mm.getIdByMessage(m1));
        assertSame("gets incorrect message id\n", 1, mm.getIdByMessage(m2));

        // test changeMessageCondition
        mm.changeMessageCondition(1);
        mm.changeMessageCondition(3);
        assertSame("incorrect message condition\n", false, m1.getMessageCondition());
        assertSame("incorrect message condition\n", true, m2.getMessageCondition());
        assertSame("incorrect message condition\n", true, m4.getMessageCondition());
        assertSame("incorrect message condition\n", false, m5.getMessageCondition());
    }

}
