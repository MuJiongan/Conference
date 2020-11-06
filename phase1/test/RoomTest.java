import org.junit.*;
import static org.junit.Assert.*;
import java.time.*;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class RoomTest {
    // the constructor
    @Test(timeout = 50)
    public void testConstructor(){
        Room room1 = new Room(30);
    }

    // test getCapacity
    @Test(timeout = 50)
    public void testGetCapacity(){
        Room room1 = new Room(30);
        assertSame("incorrect capacity\n", 30, room1.getCapacity());
    }

    // test getEventID
    @Test(timeout = 50)
    public void testGetEventID(){
        Room room1 = new Room(30);
        room1.addEventID(100);
        room1.addEventID(102);
        room1.addEventID(103);
        room1.removeEventID(102);
        assertSame("incorrect eventID\n", 100, room1.getEventsScheduled().get(0));
        assertSame("incorrect eventID\n", 103, room1.getEventsScheduled().get(1));
    }
}
