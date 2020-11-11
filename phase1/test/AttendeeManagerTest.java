import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;


public class AttendeeManagerTest {
    // initialize AttendeeManager
    AttendeeManager am = new AttendeeManager();

    @Test
    public void tests(){
        // Test getUser
        Assert.assertEquals("The list of attendee is initially non-empty",0, am.getUsers().size());

        Attendee a1 = new Attendee("Steve", "Steve@gmail.com", "123",1);
        Attendee a2 = new Attendee("Mike", "Mike@gmail.com", "12334",2);
        Attendee a3 = new Attendee("Crystal", "crystal@gmail.com", "23423",3);

        // Test addUser
        boolean result1 = am.addUser(a1);
        boolean result2 = am.addUser(a2);
        boolean result3 = am.addUser(a3);

        Assert.assertTrue("a1 is not added successfully", result1);
        Assert.assertTrue("a2 is not added successfully", result2);
        Assert.assertTrue("a3 is not added successfully", result3);

        ArrayList<User> users = am.getUsers();
        Assert.assertNotNull("user1,2,3 are not added in the list", users);
        Assert.assertEquals("wrong size", 3, users.size());
        Assert.assertEquals("Wrong 1st element", a1, users.get(0));
        Assert.assertEquals("Wrong 2nd element", a2, users.get(1));
        Assert.assertEquals("Wrong 3rd element", a3, users.get(2));


        // Test getnameById  // Id must be valid?
        Assert.assertSame("gets incorrect name", "Steve", am.getnameById(1));
        Assert.assertSame("gets incorrect name", "Mike", am.getnameById(2));
        Assert.assertSame("gets incorrect name", "Crystal", am.getnameById(3));

        // Test hasUserName
        boolean result10 = am.hasUserName("Steve@gmail.com");
        boolean result11 = am.hasUserName("Mike@gmail.com");
        boolean result12 = am.hasUserName("crystal@gmail.com");
        boolean result13 = am.hasUserName("Sophia@gmail.com");

        Assert.assertTrue("the username is in the list but not detected", result10);
        Assert.assertTrue("the username is in the list but not detected", result11);
        Assert.assertTrue("the username is in the list but not detected", result12);
        Assert.assertFalse("the username is not in the list but assert true", result13);

        // Test setName
        String a1NewName = "Steve1234";
        String a2NewName = "Mike1233";

        am.setName(a1, a1NewName);
        am.setName(a2, a2NewName);

        Assert.assertEquals("a1's name is not changed properly", "Steve1234", a1.getName());
        Assert.assertEquals("a2's name is not changed properly", "Mike1233", a2.getName());

        am.setName(a1, "Steve");
        am.setName(a2, "Mike");

        // Test getUserById   // Id must be valid?
        Assert.assertSame("gets incorrect attendee\n", a1, am.getUserByID(1));
        Assert.assertSame("gets incorrect attendee\n", a2, am.getUserByID(2));
        Assert.assertSame("gets incorrect attendee\n", a3, am.getUserByID(3));
        Assert.assertNull("gets incorrect message\n", am.getUserByID(9));

        // Test getIDByUser  //All User must be added to the list
        Assert.assertSame("gets incorrect ID\n", 1, am.getIDByUser(a1));
        Assert.assertSame("gets incorrect ID\n", 2, am.getIDByUser(a2));
        Assert.assertSame("gets incorrect ID\n", 3, am.getIDByUser(a3));

        // Test idInList

        Assert.assertFalse("the userid is not properly added to the list", am.idInList(0));
        Assert.assertTrue("the userid is not properly added to the list", am.idInList(1));
        Assert.assertTrue("the userid is not properly added to the list", am.idInList(2));
        Assert.assertTrue("the userid is not properly added to the list", am.idInList(3));

        Assert.assertFalse("the userid should not be added", am.idInList(7));


        // Test addEventID  // add to the precondition: all event id should be valid?
        boolean result4 = am.addEventID(1, a1);
        boolean result5 = am.addEventID(3, a2);
        boolean result6 = am.addEventID(5, a3);
        boolean result7 = am.addEventID(1, a1);
        boolean result8 = am.addEventID(3, a1);
        boolean result9 = am.addEventID(5, a1);



        Assert.assertTrue("eventID: 1 is not added", result4);
        Assert.assertTrue("eventID: 3 is not added", result5);
        Assert.assertTrue("eventID: 5 is not added", result6);
        Assert.assertFalse("Shouldn't add the repeated event id", result7);
        Assert.assertTrue("eventID: 3 is not added to a different attendee", result8);
        Assert.assertTrue("eventID: 5 is not added to a different attendee", result9);

        Assert.assertSame("the size of the list of events is incorrect", 3, a1.getEventsAttend().size());// Should I interact with entity class?
        Assert.assertSame("the size of the list of events is incorrect", 1, a2.getEventsAttend().size());// Should I interact with entity class?
        Assert.assertSame("the size of the list of events is incorrect", 1, a3.getEventsAttend().size());// Should I interact with entity class?

        Assert.assertTrue("eventID 1 is not successfully added", a1.getEventsAttend().contains(1));// Should I interact with entity class?
        Assert.assertTrue("eventID 3 is not successfully added", a1.getEventsAttend().contains(3));// Should I interact with entity class?
        Assert.assertTrue("eventID 5 is not successfully added", a1.getEventsAttend().contains(5));// Should I interact with entity class?

        Assert.assertTrue("eventID 3 is not successfully added", a2.getEventsAttend().contains(3));// Should I interact with entity class?
        Assert.assertTrue("eventID 5 is not successfully added", a3.getEventsAttend().contains(5));// Should I interact with entity class?


        // Test getEventList
        ArrayList<Integer> eventList1 = am.getEventList(a1);
        ArrayList<Integer> eventList2 = am.getEventList(a2);
        ArrayList<Integer> eventList3 = am.getEventList(a3);

        Assert.assertSame("the size of the list of events is incorrect", 3, eventList1.size());
        Assert.assertSame("the size of the list of events is incorrect", 1, eventList2.size());
        Assert.assertSame("the size of the list of events is incorrect", 1, eventList3.size());

        Assert.assertTrue("eventID 1 is not successfully added",  eventList1.contains(1));
        Assert.assertTrue("eventID 3 is not successfully added",  eventList1.contains(3));
        Assert.assertTrue("eventID 5 is not successfully added",  eventList1.contains(5));

        Assert.assertTrue("eventID 3 is not successfully added",  eventList2.contains(3));
        Assert.assertTrue("eventID 5 is not successfully added",  eventList3.contains(5));

        // Test removeEventID
        am.removeEventID(5, a3);
        am.removeEventID(3, a2);
        Assert.assertEquals("eventID: 5 is not properly removed",0, am.getEventList(a3).size());
        Assert.assertEquals("eventID: 3 is not properly removed",0, am.getEventList(a2).size());

        Assert.assertSame("the size of eventlist of a1 is wrong", 3, am.getEventList(a1).size());
        am.removeEventID(1, a1);
        Assert.assertFalse("eventID: 1 is not removed properly", am.getEventList(a1).contains(1));
        am.removeEventID(3, a1);
        Assert.assertFalse("eventID: 3 is not removed properly", am.getEventList(a1).contains(3));
        am.removeEventID(5, a1);
        Assert.assertFalse("eventID:  is not removed properly", am.getEventList(a1).contains(5));
        Assert.assertEquals(0, am.getEventList(a1).size());


        // Test addMessagesID   // add the same message twice?
        am.addMessageID(1,a1, 2); // a3 communicates with a1
        am.addMessageID(3,a1, 1); // a2 communicates with a1
        am.addMessageID(7,a1, 1); // a2 communicates with a1
        am.addMessageID(4,a3,1); // a2 communicates with a3

        Assert.assertTrue("a3 is not added properly", a1.getMessages().containsKey(2));
        Assert.assertTrue("a2 is not added properly", a1.getMessages().containsKey(1));

        Assert.assertSame("a1's message with a3 is not added properly",1, a1.getMessages().get(2).size());
        Assert.assertSame("a1's message with a3 is not added properly",1, a1.getMessages().get(2).get(0));

        Assert.assertSame("a1's message with a2 is not added properly",2, a1.getMessages().get(1).size());
        Assert.assertSame("a1's message with a2 is not added properly",3, a1.getMessages().get(1).get(0));
        Assert.assertSame("a1's message with a2 is not added properly",7, a1.getMessages().get(1).get(1));

        Assert.assertSame("a3's message with a2 is not added properly",1, a3.getMessages().get(1).size());
        Assert.assertSame("a3's message with a2 is not added properly",4, a3.getMessages().get(1).get(0));

        // Test getMessages
        HashMap<Integer, ArrayList<Integer>> messageList = am.getMessages(a1);
        Assert.assertTrue("a3 is not obtained correctly", messageList.containsKey(2));
        Assert.assertTrue("a2 is not obtained correctly", messageList.containsKey(1));

        Assert.assertSame("a1's message with a3 is not obtained properly",1, messageList.get(2).size());
        Assert.assertSame("a1's message with a3 is not obtained correctly",1, messageList.get(2).get(0));

        Assert.assertSame("a1's message with a2 is not obtained correctly",2, messageList.get(1).size());
        Assert.assertSame("a1's message with a2 is not obtained correctly",3, messageList.get(1).get(0));
        Assert.assertSame("a1's message with a2 is not obtained correctly",7, messageList.get(1).get(1));

        // Test getContactList
        ArrayList<Integer> list1 = am.getContactList(a1);
        Assert.assertEquals("the contact list is not initially empty", 0,  list1.size());

        // Test addToContactList //what if the user id doesn't exist?
        am.addToContactsList(a1, 7);
        am.addToContactsList(a1, 9);
        am.addToContactsList(a1, 2);

        Assert.assertSame("the size of contact list is wrong", 3, a1.getContactList().size());
        Assert.assertSame("userid 7 is not added properly", 7, a1.getContactList().get(0));
        Assert.assertSame("userid 9 is not added properly", 9, a1.getContactList().get(1));
        Assert.assertSame("userid 2 is not added properly", 2, a1.getContactList().get(2));

        Assert.assertSame("the size of contact list is wrong", 3, am.getContactList(a1).size());
        Assert.assertSame("userid 7 is not got properly", 7, am.getContactList(a1).get(0));
        Assert.assertSame("userid 9 is not got properly", 9, am.getContactList(a1).get(1));
        Assert.assertSame("userid 2 is not got properly", 2, am.getContactList(a1).get(2));

        // Test validate
        Assert.assertEquals("doesn't return correct user object", a1, am.validate("Steve@gmail.com", "123"));
        Assert.assertEquals("doesn't return correct user object", a2, am.validate("Mike@gmail.com", "12334"));
        Assert.assertNull("no such username and password pair and should return null", am.validate("Mikdfse@gmail.com", "12334"));

    }

}

