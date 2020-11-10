import java.util.ArrayList;
import java.util.HashMap;

public class Presenter {

    public static void print(String printStuff){
        System.out.println(printStuff);
    }

    public static void printOrganizerMenu(){
        System.out.println("1. View All Events \n2. View your Events \n3.View events with vacancy " +
                "\n4. Message an Attendee \n5. Message Attendees in Event \n6. Enter New Room " +
                "\n7. Create Speaker Account \n8.Schedule Speaker \n9.Cancel enrollment(s) \n10.Sign up events\n10.Log out");
    }

    public static void printAttendeeMenu(){
        System.out.println("1. View All Events \n2. View your Events \n3.View events with vacancy \n4. Message " +
                "an Attendee \n5. Message a Speaker in Event \n6. View Messages \n7.Cancel enrollment(s) " +
                "\n7.Sign up events\n8. Log out");
    public static void printAttendeemenu(){
        System.out.println("1. View All Events \n2. View My Events \n3. View Contact List \n4. Manage Account" +
                "\n5. Log Out");
    }

    public static void printSpeakerMenu(){
        System.out.println("1. View your Events \n2. View contact list \n3. Manage account \n4. Log in");
    }

    public static void viewContacts(ArrayList<Integer> contactList, UserManager currentManager){
        int i = 1;

        System.out.println("Here is people you can sent messages to: ");
        System.out.println("");
        for (Integer userId: contactList){
            System.out.println(i + "." + currentManager.getnameById(userId));
        }
        // want to add unread feature.
        String divider = "------------------------";
    }

    public static void viewChat(ArrayList<Integer> messageIDList, MessageManager mm,
                                      UserManager um, int receiverID){
        String divider = "------------------------";
        // Should the method recieve a usermenu as parameter or a hashmap?

        System.out.println("Here is all messages with" + um.getnameById(receiverID));
        System.out.println("");
         for (Integer messageId: messageIDList) {
             String senderName = um.getnameById(mm.getSenderIDByMessId(messageId));
             System.out.println(senderName + ":" + mm.getMescontentById(messageId));
         }
         System.out.println(divider);
         }


    public static void viewMyEvents(ArrayList<Event> eventsTheyAttended, EventManager em){
        String divider = "------------------------";
        String heading1 = "Events";
        String heading2 = "Time";
        int i = 1;

        System.out.println("Here is your events:");
        System.out.println("");
        System.out.printf("%-15s %-15s %n", heading1, heading2);
        for (Event event: eventsTheyAttended){
            System.out.printf("%-15s %10s %n", i + "." + em.getName(event),em.getStartTime(event) + " "
                    + em.getEndTime(event));
            i += 1;
        }
        System.out.println(divider);
    }

    public static void viewAllEvents(ArrayList<Event> allEventsInSystem, EventManager em){
        String divider = "------------------------";
        String heading1 = "Events";
        String heading2 = "Time";
        String heading3 = "Vacancy";
        int i = 1;

        System.out.println("Here is your events:");
        System.out.println("");
        System.out.printf("%-15s %-15s %-15s %n", heading1, heading2, heading3);
        for (Event event: allEventsInSystem){
            System.out.printf("%-15s %-10s %d %n", i + "." + em.getName(event),em.getStartTime(event) + " "
                    + em.getEndTime(event) + " ", em.getVacancy(event));
            i += 1;
        }
        System.out.println(divider);
    }

//        String display = "Here is your schedule:";
//        int i = 1;
//        for (Event event: actualEvent){
//            display += "/n" + i + "." + em.getName(event) + " " + em.getStartTime(event) + " "
//                    + em.getEndTime(event) + " " + em.getVacancy(event);
//            i += 1;
//        }
//        System.out.println(display);
    }



