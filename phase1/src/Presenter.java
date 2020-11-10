import java.util.ArrayList;
import java.util.HashMap;

public class Presenter {

    public static void print(String printStuff){
        System.out.println(printStuff);
    }

    public static void printOrganizermenu(){
        System.out.println("1. View All Events \n2. View your Events \n3.View events with vacancy " +
                "\n4. Message an Attendee \n5. Message Attendees in Event \n6. Enter New Room " +
                "\n7. Create Speaker Account \n8.Schedule Speaker \n9.Cancel enrollment(s) \n10.Sign up events\n10.Exit");
    }

    public static void printAttendeemenu(){
        System.out.println("1. View All Events \n2. View My Events \n3. View Contact List \n4. Manage Account" +
                "\n5. Log Out");
    }

    public static void printSpeakermenu(){
        System.out.println("1. View your Events \n2. View contact list \n3. Exit");
    }


    public static void viewAllevent(ArrayList<Event> actualEvent, EventManager em){
        String message = "Here is the conference's schedule:\n";
        for (Event event: actualEvent){
            message = message + " " + em.getStartTime(event) + " "+ em.getEndTime(event) + " " + em.getName(event);}
            System.out.println(message);
        }


    public static void viewAllmessage(HashMap<Integer, ArrayList<Integer>> messagereceived, MessageManager mm,
                                      UserManager um){
         String display  = "Here is all messages sent to you:";
         for (Integer senderId: messagereceived.keySet()){
             display += "\n------" + um.getUsernameById(senderId);
             ArrayList<Integer> mesIdList = messagereceived.get(senderId);
             for (int messageid: mesIdList){
                 display += "\n" + mm.getMescontentById(messageid);
             }
         display += "------End";
         }
    }
    }


