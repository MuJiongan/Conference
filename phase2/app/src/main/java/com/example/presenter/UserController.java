package com.example.presenter;

import com.example.model.entities.Message;
import com.example.model.entities.Organizer;
import com.example.model.useCases.*;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserController implements Serializable {

    private AttendeeManager am;
    private OrganizerManager om;
    private SpeakerManager sm;
    private RoomManager rm;
    private EventManager em;
    private MessageManager mm;
    private VipManager vipm;
    private VipEventManager vipe;
    private int userID;
    /**
     * Store the current Manager of the current user
     */
    private UserManager currentManager;
    private View view;

    /**
     * Construct an instance of UserMenu with the given Managers
     *
     * @param am     the instance of <code>AttendeeManager</code> in the conference
     * @param om     the instance of <code>OrganizerManager</code> in the conference
     * @param sm     the instance of <code>SpeakerManager</code> in the conference
     * @param rm     the instance of <code>RoomManager</code> in the conference
     * @param em     the instance of <code>EventManager</code> in the conference
     * @param mm     the instance of <code>MessageManager</code> in the conference
     * @param userID a instance of <code>User</code> that simulate the user on the keyboard
     */
    public UserController(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm, EventManager em, MessageManager mm, VipManager vipm,
                          VipEventManager vipe, int userID, View view) {
        this.am = am;
        this.om = om;
        this.sm = sm;
        this.rm = rm;
        this.em = em;
        this.mm = mm;
        this.vipm = vipm;
        this.vipe = vipe;
        this.userID = userID;
        this.view = view;

        if (this.am.idInList(this.userID)) {
            currentManager = this.am;
        } else if (this.om.idInList((this.userID))) {
            currentManager = this.om;
        } else {
            currentManager = this.sm;
        }
    }

    /**
     * Adds the message to the messages hashmaps of both the receiver and the sender, returns true iff successful
     *
     * @param receiverID     ID of the other user the current user is sending message to
     * @param messageContent content of the message
     * @return true iff the message is sent successfully
     */
    public boolean sendMessage(int receiverID, String messageContent) {
        if (getAttendeeManager().idInList(receiverID)) {
            int messageID = getMessageManager().createMessage(messageContent, getUser(), receiverID);
            //add message to receiver's hashmap
            getAttendeeManager().addMessageID(messageID, receiverID, userID);
            //add message to current user's hashmap
            currentManager.addMessageID(messageID, userID, receiverID);
            getView().pushMessage("Message Sent");
            return true;
        } else if (getOrganizerManager().idInList(receiverID)) {
            int messageID = getMessageManager().createMessage(messageContent, getUser(), receiverID);
            //add message to receiver's hashmap
            getOrganizerManager().addMessageID(messageID, receiverID, userID);
            //add message to current user's hashmap
            currentManager.addMessageID(messageID, userID, receiverID);
            getView().pushMessage("Message Sent");
            return true;
        } else if (getSpeakerManager().idInList(receiverID)) {
            int messageID = getMessageManager().createMessage(messageContent, getUser(), receiverID);
            //add message to receiver's hashmap
            getSpeakerManager().addMessageID(messageID, receiverID, userID);
            //add message to current user's hashmap
            currentManager.addMessageID(messageID, userID, receiverID);
            getView().pushMessage("Message Sent");
            return true;
        } else if (getVipManager().idInList(receiverID)) {
            int messageID = getMessageManager().createMessage(messageContent, getUser(), receiverID);
            //add message to receiver's hashmap
            getVipManager().addMessageID(messageID, receiverID, getUser());
            //add message to current user's hashmap
            currentManager.addMessageID(messageID, userID, receiverID);
            getView().pushMessage("Message Sent");
            return true;
        }
        return false;
    }

    /**
     * Return the current userID
     *
     * @return the current userID
     */
    public int getUser() {
        return userID;
    }

    /**
     * Return EventManager
     *
     * @return the <code>EventManager</code> in the conference
     */
    public EventManager getEventManager() {
        return em;
    }

    /**
     * Return MessageManager
     *
     * @return the <code>MessageManager</code> in the conference
     */
    public MessageManager getMessageManager() {
        return mm;
    }

    /**
     * Return RoomManager
     *
     * @return the <code>RoomManager</code> in the conference
     */
    public RoomManager getRoomManager() {
        return rm;
    }

    /**
     * Return AttendeeManager
     *
     * @return the <code>AttendeeManager</code> in the conference
     */
    public AttendeeManager getAttendeeManager() {
        return am;
    }

    /**
     * Return OrganizerManager
     *
     * @return the <code>OrganizerManager</code> in the conference
     */
    public OrganizerManager getOrganizerManager() {
        return om;
    }

    /**
     * Return SpeakerManager
     *
     * @return the <code>SpeakerManager</code> in the conference
     */
    public SpeakerManager getSpeakerManager() {
        return sm;
    }

    public VipManager getVipManager() {
        return vipm;
    }

    public VipEventManager getVipEventManager() {
        return vipe;
    }

    /**
     * Return currentManager
     *
     * @return return the <code>AttendeeManager</code> in the conference if the user on the keyboard is an attendee,
     * return the <code>OrganizerManager</code> in the conference if the user on the keyboard is an organizer,
     * return the <code>AttendeeManager</code> in the conference if the user on the keyboard is an attendee,
     */
    public UserManager getCurrentManager() {
        return currentManager;
    }

    public int getUserID(){
        return userID;
    }

    /**
     * Return list of all EventIDs the user is going to attend
     *
     * @return list of all EventIDs the user is going to attend
     */
    public String viewMyEvents() {
        List<Integer> eventIDs = getEventManager().getEvents();
        if (eventIDs.size() == 0) {
            return "You haven't signed up for any event yet!";
        }
        String output = "";
        for (int ID : eventIDs) {
            output = output + ID + ".\t" + getEventManager().getName(ID) + "\t" + getEventManager().getStartTime(ID)
                    + "\t" + getEventManager().getEventByID(ID) + "\t" + getRoomManager().getRoomName(getEventManager().getRoomID(ID))
                    + "\n";
        }
        return output;

    }

    public String getUserName(int userID) {
        if (getAttendeeManager().idInList(userID)) {
            return getAttendeeManager().getnameById(userID);
        }
        if (getOrganizerManager().idInList(userID)) {
            return getOrganizerManager().getnameById(userID);
        }
        if (getSpeakerManager().idInList(userID)) {
            return getSpeakerManager().getnameById(userID);
        }
        return "This is not an valid ID.";
    }

    /**
     * Return HashMap of contactList that key is condition of contacts and value is the list of
     * string representation of contacts that satisfied the condition
     *
     * @return HashMap of contactList that key is condition of contacts and value is the list of
     * string representation of contacts that satisfied the condition in the format:
     * friendID + "\t" + username
     */
    public HashMap<String, ArrayList<String>> viewContactList() {
        //create a new HashMap that key is the condition of contacts and values are the empty list.
        HashMap<String, ArrayList<String>> contactList = new HashMap<>();
        ArrayList<String> readList = new ArrayList<>();
        ArrayList<String> unreadList = new ArrayList<>();
        contactList.put("read", readList);
        contactList.put("unread", unreadList);
        //get the contactList of user
        ArrayList<Integer> contact = new ArrayList<>();
        contact.addAll(getAttendeeManager().getUserIDs());
        contact.addAll(getOrganizerManager().getUserIDs());
        contact.addAll(getSpeakerManager().getUserIDs());
        contact.addAll(getVipManager().getUserIDs());
        // remove the user himself
        int index = contact.indexOf(userID);
        contact.remove(index);
        //get the message HashMap of user
        HashMap<Integer, ArrayList<Integer>> allMessage = getCurrentManager().getMessages(userID);
        // TODO: If no messages are found in the message hashmap, we won't get messages
        //add the string representation of contacts to the final HashMap
        for (int friendID : contact) {
            ArrayList<Integer> messageList = allMessage.get(friendID);
            //check whether there are unread message and add the contact to the corresponding list
            if(messageList != null) {
                boolean hasUnread = false;
                for (int messageID : messageList) {
                    if (!getMessageManager().getConditionByID(messageID)) {
                        hasUnread = true;
                    }
                }
                if (hasUnread){
                    contactList.get("unread").add(friendID + "\t" + getUserName(friendID));
                } else {
                    contactList.get("read").add(friendID + "\t" + getUserName(friendID));
                }
            } else {
                contactList.get("read").add(friendID + "\t" + getUserName(friendID));
            }
        }
        return contactList;
    }


    /**
     * Return list of strings representation of all messages in the chat history with the friendID,
     * content and the condition of the message
     *
     * @return list of strings representation of all messages in the chat history in the format:
     * friendID + ":\t" + content + "\t" + condition(when the condition is unread)
     */
    public ArrayList<String> viewChatHistory(int friendID) {
        ArrayList<String> chatHistory = new ArrayList<String>();
        // get the chat history between user and given friend
        ArrayList<Integer> messageIDList = getCurrentManager().getMessages(userID).get(friendID);
        for (Integer messageID : messageIDList) {
            Integer sendID = getMessageManager().getSenderIDByMessId(messageID);
            //if the sender of message is friend and the condition of this message is unread, add the unread mark at the end of this message
            if (!getMessageManager().getConditionByID(messageID) && sendID == friendID) {
                chatHistory.add(getUserName(sendID) + ":\t" + getMessageManager().getMescontentById(messageID) + "\t(unread)");
            }
            chatHistory.add(getUserName(sendID) + ":\t" + getMessageManager().getMescontentById(messageID));
        }
        return chatHistory;
    }

    /**
     * Return whether the given event's ID is in the list of all events
     *
     * @param eventID ID of the event that is going to be checked
     * @return true iff the given event's ID is in the list of all events
     */
    public boolean hasEvent(int eventID) {
        return this.getEventManager().getEvents().contains(getEventManager().getEventByID(eventID));
    }

    /**
     * Return whether the given event's ID is in the event list of the current user
     *
     * @param eventID ID of the event that is going to be checked
     * @return true iff the given event's ID is in the event list of the current user
     */
    public boolean hasMyEvent(int eventID) {
        return currentManager.getEventList(userID).contains(eventID);
    }

    /**
     * Set all messages receiving from the friend given by ID as already read
     *
     * @param friendID ID of the friend that the current user is chatting to
     */
    public void readAllMessage(int friendID) {
        // TODO: How to handle this null-pointer exception
        for (Integer messageID : getCurrentManager().getMessages(userID).get(friendID)) {
            Message message = getMessageManager().getMessageById(messageID);
            if (message.getSenderID() == friendID) {
                getMessageManager().changeMessageCondition(messageID);
            }
        }

    }

    /**
     * Return the next ID that is going to be assigned to the new User created
     *
     * @return the next ID that is going to be assigned to the new User created
     */
    public int getNewID() {
        int size = getAttendeeManager().getUsers().size() + getOrganizerManager().getUsers().size() + getSpeakerManager().getUsers().size()
                + getVipManager().getUsers().size();
        return size + 1;
    }

    public void setView(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void setName(String name) {
        getCurrentManager().setName(getUser(), name);
    }

    public String getType() {
        return "UserController";
    }


    public interface View {
        void pushMessage(String info);
    }

    /**
     * Delete the given message from the message hashmap of the user. Return true iff the message
     * is successfully deleted
     *
     * @param messageID the message id
     * @return true iff the message is successfully deleted
     */
    public boolean deleteMessage(int messageID) {
        int friendID = getMessageManager().getSenderIDByMessId(messageID);
        if (getMessageManager().getSenderIDByMessId(messageID) == userID) {
            friendID = getMessageManager().getReceiverIDByMessId(messageID);
        }
        return currentManager.deleteMessage(userID, friendID, messageID);
    }

    /**
     * Archive the given message to the archived message list of the user. Return true iff the
     * message is successfully archived
     *
     * @param messageID the message id
     * @return true iff the message is successfully archived
     */
    public boolean archiveMessage(int messageID) {
        int friendID = getMessageManager().getSenderIDByMessId(messageID);
        if (getMessageManager().getSenderIDByMessId(messageID) == userID) {
            friendID = getMessageManager().getReceiverIDByMessId(messageID);
        }
        return currentManager.addArchivedMessage(userID, friendID, messageID);
    }

    /**
     * Mark the given message as unread to the receiver, who is the current user. Return true iff
     * the message is successfully marked
     *
     * @param messageID the message id
     * @return true iff the message is successfully marked
     */
    public boolean markAsUnread(int messageID) {
        if (getMessageManager().getSenderIDByMessId(messageID) == userID) {
            return false;
        }
        getMessageManager().markAsUnread(messageID);
        return true;
    }

    public void setManagers(AttendeeManager am, OrganizerManager om, SpeakerManager sm, RoomManager rm,
                            EventManager em, MessageManager mm, VipManager vipm, VipEventManager vipe) {
        this.am = am;
        this.om = om;
        this.sm = sm;
        this.rm = rm;
        this.em = em;
        this.mm = mm;
        this.vipm = vipm;
        this.vipe = vipe;
    }

    public boolean hasUserID(int userID) {
        if (am.idInList(userID)) {
            return true;
        }
        if (om.idInList(userID)) {
            return true;
        }
        if (sm.idInList(userID)) {
            return true;
        }
        if (vipm.idInList(userID)) {
            return true;
        }
        return false;
    }

    public boolean hasUserName(String username) {
        if (am.hasUserName(username)) {
            return true;
        }
        if (om.hasUserName(username)) {
            return true;
        }
        if (sm.hasUserName(username)) {
            return true;
        }
        if (vipm.hasUserName(username)) {
            return true;
        }
        return false;
    }

    /**
     * Return a hashmap of friend ids to number of common events.
     *
     * @return a hashmap of friend ids to number of common events, with key of the id of the friend
     * and value of the number of common events the friend has as the current user
     */
    public HashMap<Integer, Integer> friendToNumOfCommonEvent() {
        // create a new hash map
        HashMap<Integer, Integer> friendToNumOfCommonEvent = new HashMap<>();
        // get a list of all attendees, vip attendees, and organizers
        ArrayList<Integer> contact = new ArrayList<>();
        contact.addAll(getAttendeeManager().getUserIDs());
        contact.addAll(getOrganizerManager().getUserIDs());
        contact.addAll(getVipManager().getUserIDs());
        // remove the user himself
        contact.remove(userID);
        // loop through all the events the current user has signed up for
        for (Integer eventID : getCurrentManager().getEventList(userID)) {
            // loop through all contacts in the list
            for (Integer friendID : contact) {
                // add 1 to the corresponding friend key in the hash map if the friend also has
                // the event
                ArrayList<Integer> friendEvents = getFriendEvents(friendID);
                if (friendEvents.contains(eventID)) {
                    if (!friendToNumOfCommonEvent.containsKey(eventID)) {
                        friendToNumOfCommonEvent.put(friendID, 1);
                    } else {
                        int curr = friendToNumOfCommonEvent.get(friendID);
                        friendToNumOfCommonEvent.put(friendID, curr + 1);
                    }
                }
            }
        }
        return friendToNumOfCommonEvent;
    }

    /**
     * Return a list of events the friend is attending given the friend's id.
     *
     * @param friendID the friend id
     * @return a list of events the friend is attending given the friend's id.
     */
    public ArrayList<Integer> getFriendEvents(int friendID) {
        if (getOrganizerManager().getUserIDs().contains(friendID)) {
            return getOrganizerManager().getEventList(friendID);
        } else if (getAttendeeManager().getUserIDs().contains(friendID)) {
            return getAttendeeManager().getEventList(friendID);
        } else if (getVipManager().getUserIDs().contains(friendID)) {
            return getVipManager().getEventList(friendID);
        } else {
            return getSpeakerManager().getEventList(friendID);
        }
    }

    public HashMap<String, ArrayList<String>> viewRecommendedFriend() {
        HashMap<Integer, Integer> friendToNumOfCommonEvent = friendToNumOfCommonEvent();
        HashMap<String, ArrayList<String>> viewRecommendedFriend = new HashMap<String, ArrayList<String>>();
        for (Integer friendID : friendToNumOfCommonEvent.keySet()) {
            String numberOfSameEvent = String.valueOf(friendToNumOfCommonEvent.get(friendID));
            if (viewRecommendedFriend.containsKey(numberOfSameEvent)) {
                viewRecommendedFriend.get(numberOfSameEvent).add(friendID + "\t" + getUserName(friendID));
            } else {
                ArrayList<String> friendList = new ArrayList<String>();
                friendList.add(friendID + "\t" + getUserName(friendID));
                viewRecommendedFriend.put(numberOfSameEvent, friendList);
            }
        }
        return viewRecommendedFriend;
    }



}



