import org.junit.Test;

import static org.junit.Assert.*;

public class RoomManagerTest {
    // test RoomManager class
    @Test(timeout = 50)
    public void testRoomManager(){

        // test addRoom
        RoomManager rm = new RoomManager();
        Room r1 = new Room(2, "Meeting room 1");
        Room r2 = new Room(200, "MP102");
        Room r3 = new Room(300, "MP202");
        assertSame("add room failed\n", true, rm.addRoom(r1));
        assertSame("add room failed\n", true, rm.addRoom(r2));
        assertSame("add room failed\n", false, rm.addRoom(r1));

        // test getRoomID
        assertSame("gets incorrect roomID\n", r1, rm.getRoomByID(0));
        assertSame("gets incorrect roomID\n", r2, rm.getRoomByID(1));
        assertSame("gets incorrect roomID\n", null, rm.getRoomByID(3));

        // test getName
        assertSame("gets incorrect room name\n", "Meeting room 1", rm.getName(r1));
        assertSame("gets incorrect room name\n", "MP102", rm.getName(r2));

        // test getIDbyRoom
        assertSame("gets incorrect room\n", 0, rm.getIDbyRoom(r1));
        assertSame("gets incorrect room\n", 1, rm.getIDbyRoom(r2));
        assertSame("gets incorrect room\n", -1, rm.getIDbyRoom(r3));

        // test getCapacity
        assertEquals("gets incorrect capacity\n", 2, rm.getCapacity(r1));
        assertEquals("gets incorrect capacity\n", 200, rm.getCapacity(r2));

        // test scheduleEvent and getSchedule
        assertSame("gets incorrect schedule\n", 0, rm.getSchedule(r1).size());
        rm.scheduleEvent(r1, 1);
        assertSame("gets incorrect schedule\n", 1, rm.getSchedule(r1).size());
        assertSame("gets incorrect schedule\n", 1, rm.getSchedule(r1).get(0));
        rm.scheduleEvent(r1, 1);
        assertSame("gets incorrect schedule\n", 1, rm.getSchedule(r1).size());
        assertSame("gets incorrect schedule\n", 1, rm.getSchedule(r1).get(0));
        rm.scheduleEvent(r1, 2);
        assertSame("gets incorrect schedule\n", 2, rm.getSchedule(r1).size());
        assertSame("gets incorrect schedule\n", 1, rm.getSchedule(r1).get(0));
        assertSame("gets incorrect schedule\n", 2, rm.getSchedule(r1).get(1));
    }
}
