import java.util.ArrayList;

public class Presenter {

    public static void print(String printStuff) {
        System.out.println(printStuff);
    }

    public static void printOrganizermenu() {
        System.out.println("1. View All Events \n2. View My Events \n3. View Contact List \n4. Manage Account" +
                "\n5. Create new Accounts\n6. Add New Room\n7. Log Out");
    }

    public static void printAttendeeMenu() {
        System.out.println("1. View All Events \n2. View My Events \n3. View Contact List \n4. Manage Account" +
                "\n5. Log Out");
    }

    public static void printSpeakerMenu() {
        System.out.println("1. View your Events \n2. View contact list \n3. Manage account \n4. Log Out");
    }

    public static void viewContacts(ArrayList<Integer> contactList, UserManager currentManager) {
        int i = 1;

        System.out.println("Here is people you can sent messages to: ");
        System.out.println("");
        for (Integer userId : contactList) {
            System.out.println(i + "." + currentManager.getnameById(userId));
        }
        // want to add unread feature.
        String divider = "------------------------";
    }

    public static void viewChat(ArrayList<Integer> messageIDList, MessageManager mm,
                                UserManager um, int receiverID) {
        String divider = "------------------------";

        System.out.println("Here is all messages with" + um.getnameById(receiverID));
        System.out.println("");
        for (Integer messageId : messageIDList) {
            String senderName = um.getnameById(mm.getSenderIDByMessId(messageId));
            System.out.println(senderName + ":" + mm.getMescontentById(messageId));
        }
        System.out.println(divider);
    }


    public static void viewMyEvents(ArrayList<Event> eventsTheyAttended, EventManager em) {
        String divider = "------------------------";
        String heading1 = "Events";
        String heading2 = "Time";
        String heading3 = "Room";
        int i = 1;

        System.out.println("Here is your events:");
        System.out.println("");
        System.out.printf("%-15s %-15s %34s %n", heading1, heading2, heading3);
        for (Event event : eventsTheyAttended) {
            System.out.printf("%-15s %-10s %10s %n", i + "." + em.getName(event), em.getStartTime(event) + " "
                    + em.getEndTime(event), em.getRoomNameByEvent(event));
            i += 1;
        }
        System.out.println(divider);
    }

    public static void viewAllEvents(ArrayList<Event> allEventsInSystem, EventManager em, RoomManager rm) {
        String divider = "------------------------";
        String heading1 = "Events";
        String heading2 = "Time";
        String heading3 = "Vacancy";
        String heading4 = "Room";
        int i = 1;

        System.out.println("Here is all the scheduled events:");
        System.out.println("");
        System.out.printf("%-15s %-15s %32s %5s %n", heading1, heading2, heading3, heading4);
        for (Event event : allEventsInSystem) {
            int roomID = em.getRoomID(event);
            Room room = rm.getRoomByID(roomID);
            System.out.printf("%-15s %-10s %d %10s %n", em.getIDByEvent(event) + "." + em.getName(event), em.getStartTime(event) + " "
                    + em.getEndTime(event) + " ", em.getVacancy(event), rm.getRoomName(room));
            i += 1;
        }
        System.out.println(divider);
    }

    public static void printRoom(ArrayList<Room> rooms, RoomManager rm) {
        String heading = "Room's name";
        int i = 1;
        System.out.println("Here is all rooms available");
        System.out.println(" ");
        System.out.printf("%-4s %n", heading);
        for (Room room : rooms) {
            System.out.println(i + "." + rm.getRoomName(room));
            i += 1;
        }
    }

    public static void printSpeakers(ArrayList<Integer> speakers, UserManager current) {
        String heading = "Speaker's name";
        int i = 1;
        System.out.println("Here is all speakers available");
        System.out.println(" ");
        System.out.printf("%-4s %n", heading);
        for (Integer speaker : speakers) {
            System.out.println(i + "." + current.getnameById(speaker));
            i += 1;
        }
    }
}

//        String display = "Here is your schedule:";
//        int i = 1;
//        for (Event event: actualEvent){
//            display += "/n" + i + "." + em.getName(event) + " " + em.getStartTime(event) + " "
//                    + em.getEndTime(event) + " " + em.getVacancy(event);
//            i += 1;
//        }
//        System.out.println(display);



