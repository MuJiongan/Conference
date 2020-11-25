package com.example.model.interfaceAdapters;

import com.example.model.entities.Event;
import com.example.model.entities.Room;
import com.example.model.entities.User;
import com.example.model.useCases.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Presenter {
    /**  print the specific String
     * @param printStuff a string wanted to print
     * */
    public static void print(String printStuff){
        System.out.println(printStuff);
    }
    /**  print the options displayed to Organizers
     * */
    public static void printOrganizermenu(){
        System.out.println("1. View All Events \n2. View My Events \n3. View Contact List \n4. Manage Account" +
                "\n5. Create new Accounts\n6. Add New Room\n7. Log Out");
    }
    /**  print the options displayed to Attendees
     * */
    public static void printAttendeeMenu(){
        System.out.println("1. View All Events \n2. View My Events \n3. View Contact List \n4. Manage Account" +
                "\n5. Log Out");
    }
    /**  print the options displayed to Speakers
     * */
    public static void printSpeakerMenu(){
        System.out.println("1. View your Events \n2. View contact list \n3. Manage account \n4. Log Out");
    }

    /**
     * print corresponding username of each userid in the specific contactList
     * @param contactList an ArrayList of userid that user on the keyboard can message to
     * @param am the instance of <code>AttendeeManager</code> in the conference system
     * @param om the instance of <code>OrganizerManager</code> in the conference system
     * @param sm the instance of <code>SpeakerManager</code> in the conference system
     */
    public static void viewContacts(ArrayList<Integer> contactList, AttendeeManager am, OrganizerManager om, SpeakerManager sm){
        System.out.println("Here are people you can send messages to: ");
        String divider = "------------------------";
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
        System.out.println(divider);
    }

    /**
     * print the chat history between receiver specified by receiverID and user that choose to view the chat history
     * on the menu
     * @param receiverID a userid who communicate through message with the user on the keyboard
     * @param messageIDList a HashMap where key is a userid who communicate through message with the user on the keyboard
     *                      and value is an Arraylist of messageId
     * @param mm the instance of <code>MassageManager</code> in the conference system
     * @param am the instance of <code>AttendeeManager</code> in the conference system
     * @param om the instance of <code>OrganizerManager</code> in the conference system
     * @param sm the instance of <code>SpeakerManager</code> in the conference system
     */
        public static void viewChat (int receiverID, HashMap<Integer, ArrayList<Integer>> messageIDList,MessageManager mm,
                                     AttendeeManager am, OrganizerManager om, SpeakerManager sm){
            String divider = "------------------------";
            System.out.println("Here are your chat history:");
            if (messageIDList.get(receiverID) == null)
            {
                System.out.println("No chat history");
            }
            else{
                for (Integer messageId : messageIDList.get(receiverID)) {
                    int sendID = mm.getSenderIDByMessId(messageId);
                    String senderName = "";
                    if (am.idInList(sendID))
                    {
                        senderName = am.getnameById(sendID);
                    }
                    else if (om.idInList(sendID))
                    {
                        senderName = om.getnameById(sendID);
                    }
                    else
                    {
                        senderName = sm.getnameById(sendID);
                    }
                    System.out.println(senderName + ":" + mm.getMescontentById(messageId));
                }
            }

            System.out.println(divider);
        }

    /**
     * print event names of all events that user on the keyboard signed up
     * @param eventsTheyAttended  an Arraylist of event that user on the keyboard signed up
     * @param em the instance of <code>EventManager</code> in the conference system
     * @param rm the instance of <code>RoomManager</code> in the conference system
     */
    public static void viewMyEvents(ArrayList<Event> eventsTheyAttended, EventManager em, RoomManager rm) {
        String divider = "------------------------";
        String heading1 = "Events";
        String heading2 = "Time";
        String heading3 = "Number of Attendees";
        String heading4 = "Room";

        System.out.println("Here are your scheduled events:");
        System.out.printf("%-15s %-15s %43s %11s %n", heading1, heading2, heading3, heading4);
        for (Event event : eventsTheyAttended) {
            int roomID = em.getRoomID(event);
            Room room = rm.getRoomByID(roomID);
            System.out.printf("%-15s %-10s %10s %20s %n", em.getIDByEvent(event) + "." + em.getName(event), em.getStartTime(event) + " "
                    + em.getEndTime(event) + " ", em.getNumOfAttendee(event), rm.getRoomName(room));
        }
        System.out.println(divider);
    }

    /**
     * print event names of all events in the conference
     * @param allEventsInSystem an ArrayList of all event in the conference
     * @param em an instance of <code>EventManager</code> in the conference system
     * @param rm an instance of <code>RoomManager</code> in the conference system
     */

    public static void viewAllEvents(ArrayList<Event> allEventsInSystem, EventManager em, RoomManager rm) {
        String divider = "------------------------";
        String heading1 = "Events";
        String heading2 = "Time";
        String heading3 = "Vacancy";
        String heading4 = "Room";

        System.out.println("Here are all the scheduled events:");
        System.out.printf("%-15s %-35s %-15s %-15s %n", heading1, heading2, heading3, heading4);
        for (Event event : allEventsInSystem) {
            int roomID = em.getRoomID(event);
            Room room = rm.getRoomByID(roomID);
            System.out.printf("%-15s %-15s %-15s %-15s %n", em.getIDByEvent(event) + "." + em.getName(event), em.getStartTime(event) + " "
                    + em.getEndTime(event) + " ", em.getVacancy(event) + " ", rm.getRoomName(room));
        }
        System.out.println(divider);
    }

    /**
     * print a ArrayList of speakers' names corresponds to the specific ArrayList of speakers
     * @param speakers an ArrayList of Speaker wanted to print out
     * @param current  a specific <code>UserManager</code> corresponding to the user on the keyboard
     */
    public static void printSpeakers(ArrayList<User> speakers, UserManager current) {
        String heading = "Speaker's name";
        int i = 1;
        System.out.println("Here are all speakers registered");
        System.out.println(" ");
        System.out.printf("%-4s %n", heading);
        for (User speaker : speakers) {
            int speakerID = current.getIDByUser(speaker);
            System.out.println(current.getIDByUser(speaker) + "." + current.getnameById(speakerID));
            i += 1;
        }
    }

    /**
     * print a list of rooms' names
     * @param rooms an ArrayList of Room wanted to print out
     * @param rm the instance of <code>RoomManager</code> in the conference system
     */
    public static void printRooms(ArrayList<Room> rooms, RoomManager rm) {
        String heading = "Room's name";

        System.out.println("Here are all rooms available");
        System.out.println(" ");
        System.out.printf("%-4s %n", heading);
        for (Room room : rooms) {
            System.out.println(rm.getIDbyRoom(room) + "." + rm.getRoomName(room));

        }
    }
}






