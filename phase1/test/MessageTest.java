import org.junit.Test;

import static org.junit.Assert.assertSame;

public class MessageTest {

    // the constructor
    @Test(timeout = 50)
    public void testConstructor(){
        Message m1 = new Message(1, 2, "Hi");
        Message m2 = new Message(2, 3, "Hello");
    }

    // test getSenderID
    @Test(timeout = 50)
    public void testSenderID(){
        Message m1 = new Message(1, 2, "Hi");
        Message m2 = new Message(2, 3, "Hello");
        assertSame("gets incorrect sender ID\n", 1, m1.getSenderID());
        assertSame("gets incorrect sender ID\n", 2, m2.getSenderID());
    }

    // test getReceiverID
    @Test(timeout = 50)
    public void testReceiverID(){
        Message m1 = new Message(1, 2, "Hi");
        Message m2 = new Message(2, 3, "Hello");
        assertSame("gets incorrect receiver ID\n", 2, m1.getReceiverID());
        assertSame("gets incorrect receiver ID\n", 3, m2.getReceiverID());
    }

    // test getContent
    @Test(timeout = 50)
    public void testContent(){
        Message m1 = new Message(1, 2, "Hi");
        Message m2 = new Message(2, 3, "Hello");
        assertSame("gets incorrect content\n", "Hi", m1.getContent());
        assertSame("gets incorrect content\n", "Hello", m2.getContent());
    }

    // test getMessageCondition
    @Test(timeout = 50)
    public void testGetMessageCondition(){
        Message m1 = new Message(1, 2, "Hi");
        Message m2 = new Message(2, 3, "Hello");
        assertSame("gets incorrect condition\n", false, m1.getMessageCondition());
        assertSame("gets incorrect condition\n", false, m2.getMessageCondition());
    }

    // test changeMessageCondition
    @Test(timeout = 50)
    public void testChangeMessageCondition(){
        Message m1 = new Message(1, 2, "Hi");
        Message m2 = new Message(2, 3, "Hello");
        assertSame("gets incorrect condition\n", false, m1.getMessageCondition());
        assertSame("gets incorrect condition\n", false, m2.getMessageCondition());
        m1.changeMessageCondition();
        assertSame("failed to change condition\n", true, m1.getMessageCondition());
        assertSame("failed to change condition\n", false, m2.getMessageCondition());
    }
}
