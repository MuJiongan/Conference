import org.junit.Test;

import static org.junit.Assert.assertSame;

public class MessageTest {

    // the constructor
    @Test(timeout = 50)
    public void testConstructor(){
        Message m1 = new Message(1, 2, "Hi");
        Message m2 = new Message(2, 3, "Hallo");
    }

    // test getSenderID
    @Test(timeout = 50)
    public void testSenderID(){
        Message m1 = new Message(1, 2, "Hi");
        Message m2 = new Message(2, 3, "Hallo");
        assertSame("incorrect name\n", 1, m1.getSenderID());
        assertSame("incorrect name\n", 2, m2.getSenderID());
    }

    // test getReceiverID
    @Test(timeout = 50)
    public void testReceiverID(){
        Message m1 = new Message(1, 2, "Hi");
        Message m2 = new Message(2, 3, "Hallo");
        assertSame("incorrect name\n", 2, m1.getReceiverID());
        assertSame("incorrect name\n", 3, m2.getReceiverID());
    }

    // test getContent
    @Test(timeout = 50)
    public void testContent(){
        Message m1 = new Message(1, 2, "Hi");
        Message m2 = new Message(2, 3, "Hallo");
        assertSame("incorrect name\n", "Hi", m1.getContent());
        assertSame("incorrect name\n", "Hallo", m2.getContent());
    }
}
