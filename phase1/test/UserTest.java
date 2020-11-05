import org.junit.*;
import static org.junit.Assert.*;
import java.time.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserTest {

    // the constructor
    @Test(timeout = 50)
    public void testAttendee(){
        Attendee attendee = new Attendee("a", "a", "123");
        Organizer organizer = new Organizer("o", "o", "123");
        Speaker speaker = new Speaker("s", "s", "123");
    }

    // test getName
    @Test(timeout = 50)
    public void testGetName(){
        Attendee attendee = new Attendee("a", "a", "123");
        Organizer organizer = new Organizer("o", "o", "123");
        Speaker speaker = new Speaker("s", "s", "789");
        assertSame("incorrect name\n", "a", attendee.getName());
        assertSame("incorrect name\n", "o", organizer.getName());
        assertSame("incorrect name\n", "s", speaker.getName());
    }

    // test test getUserName
    @Test(timeout = 50)
    public void testGetUserName(){
        Attendee attendee = new Attendee("a", "a", "123");
        Organizer organizer = new Organizer("o", "o", "123");
        Speaker speaker = new Speaker("s", "s", "789");
        assertSame("incorrect username\n", "a", attendee.getUserName());
        assertSame("incorrect username\n", "o", organizer.getUserName());
        assertSame("incorrect username\n", "s", speaker.getUserName());
    }

    // test test getPassword
    @Test(timeout = 50)
    public void testGetPassword(){
        Attendee attendee = new Attendee("a", "a", "123");
        Organizer organizer = new Organizer("o", "o", "456");
        Speaker speaker = new Speaker("s", "s", "789");
        assertSame("incorrect password\n", "123", attendee.getPassWord());
        assertSame("incorrect password\n", "456", organizer.getPassWord());
        assertSame("incorrect password\n", "789", speaker.getPassWord());
    }

    // test addSentMessage and getMessageSent
    @Test(timeout = 50)
    public void testGetMessageSent(){
        Attendee attendee = new Attendee("a", "a", "123");
        Organizer organizer = new Organizer("o", "o", "123");
        Speaker speaker = new Speaker("s", "s", "789");
        assertEquals("incorrect message sent\n", 0, attendee.getMessagesSent().size());
        assertEquals("incorrect message sent\n", 0, organizer.getMessagesSent().size());
        assertEquals("incorrect message sent\n", 0, speaker.getMessagesSent().size());
        Message message1 = new Message(1, 2, "Hi");
        attendee.addSentMessage(2, 1);
        assertEquals("incorrect message sent\n", 1, attendee.getMessagesSent().size());
        assertEquals("incorrect message sent\n", 1, attendee.getMessagesSent().get(2).size());
        assertEquals("incorrect message sent\n", 1, (int) attendee.getMessagesSent().get(2).get(0));
        attendee.addSentMessage(2, 1);
        assertEquals("incorrect message sent\n", 1, attendee.getMessagesSent().size());
        assertEquals("incorrect message sent\n", 2, attendee.getMessagesSent().get(2).size());
        assertEquals("incorrect message sent\n", 1, (int) attendee.getMessagesSent().get(2).get(0));
        assertEquals("incorrect message sent\n", 1, (int) attendee.getMessagesSent().get(2).get(1));
        Message message2 = new Message(1, 3, "Hello");
        attendee.addSentMessage(3, 2);
        assertEquals("incorrect message sent\n", 2, attendee.getMessagesSent().size());
        assertEquals("incorrect message sent\n", 2, attendee.getMessagesSent().get(2).size());
        assertEquals("incorrect message sent\n", 1, (int) attendee.getMessagesSent().get(2).get(0));
        assertEquals("incorrect message sent\n", 1, (int) attendee.getMessagesSent().get(2).get(1));
        assertEquals("incorrect message sent\n", 1, attendee.getMessagesSent().get(3).size());
        assertEquals("incorrect message sent\n", 2, (int) attendee.getMessagesSent().get(3).get(0));
    }

    // test addReceivedMessage and getMessageReceived
    @Test(timeout = 50)
    public void testGetMessageReceived(){
        Attendee attendee = new Attendee("a", "a", "123");
        Organizer organizer = new Organizer("o", "o", "123");
        Speaker speaker = new Speaker("s", "s", "789");
        assertEquals("incorrect message received\n", 0, attendee.getMessagesReceived().size());
        assertEquals("incorrect message received\n", 0, organizer.getMessagesReceived().size());
        assertEquals("incorrect message received\n", 0, speaker.getMessagesReceived().size());
        Message message1 = new Message(2, 2, "Hi");
        attendee.addReceivedMessage(2, 1);
        assertEquals("incorrect message received\n", 1, attendee.getMessagesReceived().size());
        assertEquals("incorrect message received\n", 1, attendee.getMessagesReceived().get(2).size());
        assertEquals("incorrect message received\n", 1, (int) attendee.getMessagesReceived().get(2).get(0));
        attendee.addReceivedMessage(2, 1);
        assertEquals("incorrect message received\n", 1, attendee.getMessagesReceived().size());
        assertEquals("incorrect message received\n", 2, attendee.getMessagesReceived().get(2).size());
        assertEquals("incorrect message received\n", 1, (int) attendee.getMessagesReceived().get(2).get(0));
        assertEquals("incorrect message received\n", 1, (int) attendee.getMessagesReceived().get(2).get(1));
        Message message2 = new Message(3, 3, "Hello");
        attendee.addReceivedMessage(3, 2);
        assertEquals("incorrect message received\n", 2, attendee.getMessagesReceived().size());
        assertEquals("incorrect message received\n", 2, attendee.getMessagesReceived().get(2).size());
        assertEquals("incorrect message received\n", 1, (int) attendee.getMessagesReceived().get(2).get(0));
        assertEquals("incorrect message received\n", 1, (int) attendee.getMessagesReceived().get(2).get(1));
        assertEquals("incorrect message received\n", 1, attendee.getMessagesReceived().get(3).size());
        assertEquals("incorrect message received\n", 2, (int) attendee.getMessagesReceived().get(3).get(0));
    }

    // test addContact and getContactList
    @Test(timeout = 50)
    public void testGetContactList(){
        Attendee attendee = new Attendee("a", "a", "123");
        Organizer organizer = new Organizer("o", "o", "123");
        Speaker speaker = new Speaker("s", "s", "789");
        assertEquals("incorrect contact list\n", 0, attendee.getContactList().size());
        assertEquals("incorrect contact list\n", 0, organizer.getContactList().size());
        assertEquals("incorrect contact list\n", 0, speaker.getContactList().size());
        attendee.addContact(2);
        assertEquals("incorrect contact list\n", 1, attendee.getContactList().size());
        assertEquals("incorrect contact list\n", 1, attendee.getContactList().size());
        assertEquals("incorrect contact list\n", 2, (int) attendee.getContactList().get(0));
        attendee.addContact(3);
        assertEquals("incorrect contact list\n", 2, attendee.getContactList().size());
        assertEquals("incorrect contact list\n", 2, (int) attendee.getContactList().get(0));
        assertEquals("incorrect contact list\n", 3, (int) attendee.getContactList().get(1));
    }

    // test addEvent, removeEvent, and getEventsAttend
    @Test(timeout = 50)
    public void testEventsAttend(){
        Attendee attendee = new Attendee("a", "a", "123");
        Organizer organizer = new Organizer("o", "o", "123");
        Speaker speaker = new Speaker("s", "s", "789");
        assertEquals("incorrect message sent\n", 0, attendee.getEventsAttend().size());
        assertEquals("incorrect message sent\n", 0, organizer.getEventsAttend().size());
        assertEquals("incorrect message sent\n", 0, speaker.getEventsAttend().size());
        LocalDateTime start1 = LocalDateTime.of(2020, 11, 1, 15, 00, 00);
        LocalDateTime end1 = LocalDateTime.of(2020, 11, 1, 16, 00, 00);
        Room room1 = new Room(10);
        Event event1 = new Event(start1, end1, 1, "Talk 1", 10);
        attendee.addEvent(0);
        assertEquals("incorrect message sent\n", 1, attendee.getEventsAttend().size());
        assertEquals("incorrect message sent\n", 0, (int) attendee.getEventsAttend().get(0));
        LocalDateTime start2 = LocalDateTime.of(2020, 11, 1, 10, 00, 00);
        LocalDateTime end2 = LocalDateTime.of(2020, 11, 1, 11, 00, 00);
        Room room2 = new Room(10);
        Event event2 = new Event(start2, end2, 2, "Talk 2", 10);
        attendee.addEvent(1);
        assertEquals("incorrect message sent\n", 2, attendee.getEventsAttend().size());
        assertEquals("incorrect message sent\n", 0, (int) attendee.getEventsAttend().get(0));
        assertEquals("incorrect message sent\n", 1, (int) attendee.getEventsAttend().get(1));
        attendee.removeEvent(0);
        assertEquals("incorrect message sent\n", 1, attendee.getEventsAttend().size());
        assertEquals("incorrect message sent\n", 1, (int) attendee.getEventsAttend().get(0));
        attendee.removeEvent(1);
        assertEquals("incorrect message sent\n", 0, attendee.getEventsAttend().size());
    }

}
