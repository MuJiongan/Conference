import java.util.ArrayList;
import java.util.HashMap;

public class Presenter {

    public static void print(String printStuff){
        System.out.println(printStuff);
    }

    public static void printOrganizermenu(){
        System.out.println("1. View All Events \n2. View My Events \n3. View Contact List \n4. Manage Account" +
                "\n5. Create new Accounts\n6. Add New Room\n7. Log Out");
    }

    public static void printAttendeeMenu(){
        System.out.println("1. View All Events \n2. View My Events \n3. View Contact List \n4. Manage Account" +
                "\n5. Log Out");
    }

    public static void printSpeakerMenu(){
        System.out.println("1. View your Events \n2. View contact list \n3. Manage account \n4. Log Out");
    }

    public static void viewContacts(ArrayList<Integer> contactList, AttendeeManager am, OrganizerManager om, SpeakerManager sm){
        System.out.println("Here is people you can sent messages to: ");
        for (Integer userId : contactList) {
            if (am.idInList(userId))
            {
                System.out.println(userId + "." + am.getnameById(userId));
            }
            else if (om.idInList(userId))
            {
                System.out.println(userId + "." + om.getnameById(userId));
            }
            else
            {
                System.out.println(userId + "." + sm.getnameById(userId));
            }


        }
        // want to add unread feature.
        String divider = "------------------------";
        }

        public static void viewChat (ArrayList <Integer> messageIDList, MessageManager mm,
                UserManager um,int receiverID){
            String divider = "------------------------";


            System.out.println("Here is all messages with" + um.getnameById(receiverID));
            System.out.println("");
            for (Integer messageId : messageIDList) {
                String senderName = um.getnameById(mm.getSenderIDByMessId(messageId));
                System.out.println(senderName + ":" + mm.getMescontentById(messageId));
            }
            System.out.println(divider);
        }



        public static void viewMyEvents (ArrayList < Event > eventsTheyAttended, EventManager em, RoomManager rm){
            String divider = "------------------------";
            String heading1 = "Events";
            String heading2 = "Time";
            String heading3 = "Vacancy";
            String heading4 = "Room";

            System.out.println("Here is your scheduled events:");
            System.out.println("");
            System.out.printf("%-15s %-35s %-15s %-15s %n", heading1, heading2, heading3, heading4);
            for (Event event : eventsTheyAttended) {
                int roomID = em.getRoomID(event);
                Room room = rm.getRoomByID(roomID);
                System.out.printf("%-15s %-15s %-15s %-15s %n", em.getIDByEvent(event)+ "." + em.getName(event), em.getStartTime(event) + " "
                        + em.getEndTime(event) + " ", em.getVacancy(event) + " ", rm.getRoomName(room));
            }
            System.out.println(divider);
        }


            public static void viewAllEvents (ArrayList < Event > allEventsInSystem, EventManager em, RoomManager rm){
                String divider = "------------------------";
                String heading1 = "Events";
                String heading2 = "Time";
                String heading3 = "Vacancy";
                String heading4 = "Room";

                System.out.println("Here is all the scheduled events:");
                System.out.println("");
                System.out.printf("%-15s %-35s %-15s %-15s %n", heading1, heading2, heading3, heading4);
                for (Event event : allEventsInSystem) {
                    int roomID = em.getRoomID(event);
                    Room room = rm.getRoomByID(roomID);
                    System.out.printf("%-15s %-15s %-15s %-15s %n", em.getIDByEvent(event)+ "." + em.getName(event), em.getStartTime(event) + " "
                            + em.getEndTime(event) + " ", em.getVacancy(event) + " ", rm.getRoomName(room));
                }
                System.out.println(divider);
            }
        }




