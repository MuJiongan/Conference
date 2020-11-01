import java.io.*;
import java.util.ArrayList;

public class UserManager implements Serializable{
    /**
     * The list of all attendees
     */
    private ArrayList<Attendee> attendees = new ArrayList<Attendee>();
    /**
     * The list of all organizers
     */
    private ArrayList<Organizer> organizers = new ArrayList<Organizer>();
    /**
     * The list of all speakers
     */
    private ArrayList<Speaker> speakers = new ArrayList<Speaker>();
    /**
     * Returns the shallow copy of all attendees in a list
     * @return the shallow copy of all attendees in a list
     */
    public ArrayList<Attendee> getAttendees(){
        return (ArrayList<Attendee>) attendees.clone();
    }
    /**
     * Returns the shallow copy of all organizers in a list
     * @return the shallow copy of all organizers in a list
     */
    public ArrayList<Organizer> getOrganizers(){
        return (ArrayList<Organizer>) organizers.clone();
    }
    /**
     * Returns the shallow copy of all speakers in a list
     * @return the shallow copy of all speakers in a list
     */
    public ArrayList<Speaker> getSpeakers(){
        return (ArrayList<Speaker>) speakers.clone();
    }
    /**
     * add a attendee to attendees
     * @param attendee to be added
     * @return true if and only if the attendee is successfully added to the attendee list
     */
    public boolean addAttendee(Attendee attendee){
        for (User person: attendees){
            if(person == attendee){
                return false;
            }
        }
        attendees.add(attendee);
        return true;
    }
    /**
     * add an organizer to organizers
     * @param organizer to be added
     * @return true if and only if the organizer is successfully added to the organizer list
     */
    public boolean addOrganizer(Organizer organizer){
        for (User person: organizers){
            if(person == organizer){
                return false;
            }
        }
        organizers.add(organizer);
        return true;
    }
    /**
     * add a speaker to speakers
     * @param speaker to be added
     * @return true if and only if the speaker is successfully added to the speaker list
     */
    public boolean addSpeaker(Speaker speaker){
        for (User person: speakers){
            if(person == speaker){
                return false;
            }
        }
        speakers.add(speaker);
        return true;
    }
    /**
     * return the attendee object given the user ID, null if not found
     * @param attendeeID the given attendeeID
     * @return the attendee object corresponding to the ID, null if not found
     */
    public User getAttendee(int attendeeID){
        for (User person: attendees){
            if(person.getUserId() == attendeeID){
                return person;
            }
        }
        return null;
    }
    /**
     * return the organizer object given the organizer ID, null if not found
     * @param organizerID the given organizerID
     * @return the organizer object corresponding to the ID, null if not found
     */
    public User getOrganizer(int organizerID){
        for (User person: organizers){
            if(person.getUserId() == organizerID){
                return person;
            }
        }
        return null;
    }
    /**
     * return the speaker object given the speaker ID, null if not found
     * @param speakerID the given speakerID
     * @return the speaker object corresponding to the ID, null if not found
     */
    public User getSpeaker(int speakerID){
        for (User person: speakers){
            if(person.getUserId() == speakerID){
                return person;
            }
        }
        return null;
    }
    /**
     * return the userID given the user object
     * @param user the given User object
     * @return the userID corresponding to the user object
     */
    private int getIDByUser(User user){
        return user.getUserId();
    }
    /**
     * add an eventID to the list of all events the user is going to attend
     * @param user the given User object
     * @param eventID ID of the event the user is going to add
     * @custom.precondition
     *       MAKE_SURE_EVENT_VACANCY_IS_GREATER_THAN_ZERO
     * @return true if and only if the user is successfully added to the list
     */
    public boolean addEventID(int eventID, User user){
        for (int i = 0; i<user.getEventsAttend().size(); i++){
            if (eventID == user.getEventsAttend().get(i)){
                return false;
            }
        }
        user.addEvent(eventID);
        return true;
    }
    /**
     * remove an eventID from the list of all events the user is going to cancel
     * @param user the given User object
     * @param eventID ID of the event the user is going to cancel
     * @return true if and only if the user is successfully removed from the list
     */
    public boolean removeEventID(int eventID, User user) {
        boolean exists = false;
        for (int i = 0; i < user.getEventsAttend().size(); i++) {
            if (eventID == user.getEventsAttend().get(i)) {
                exists = true;
            }
        }
        if (!exists) {
            return false;
        } else {
            user.removeEvent(eventID);
            return true;
        }
    }
    /**
     * add a messageID to the list of all messages of the user
     * @param user the given User object
     * @param messageID ID of the message the user is receiving
     * @return true if and only if the message is successfully added to the list
     */
    public boolean addMessageID(int messageID, User user){
        for (int i = 0; i<user.getMessagesList().size(); i++){
            if(messageID == user.getMessagesList().get(i)){
                return false;
            }
        }
        user.addMessage(messageID);
        return true;
    }
    /**
     * return a list of all events the user is going to attend
     * @param user the given User object
     * @return a list of all events the user is going to attend
     */
    public ArrayList<Integer> getEventList(User user){
        return user.getEventsAttend();
    }
    /**
     * return a User object if the User has the correct password, username, and type and return null otherwise
     * @param username the username given by the user
     * @param password the password given by the user
     * @param userType the given User type
     * @custom.precondition
     * userType.equals("attendee") || userType.equals("speaker") || userType.equals("organizer")
     * @return the user if it has the correct password, username and type and null otherwise
     */
    public User validate(String username, String password, String userType){
        if(userType.equals("attendee")){
            for (User user: attendees){
                if (user.getUserName().equals(username) && user.getPassWord().equals(password)){
                    return user;
                }
            }
        }
        else if(userType.equals("speaker")){
            for (User user: speakers){
                if (user.getUserName().equals(username) && user.getPassWord().equals(password)){
                    return user;
                }
            }
        }
        else if(userType.equals("organizer")){
            for (User user: organizers){
                if (user.getUserName().equals(username) && user.getPassWord().equals(password)){
                    return user;
                }
            }
        }
        return null;
    }




    /**
     * Read the UserManager object that was stored in a .ser file
     * @param path String representing the file path
     * @return UserManager object read from .ser file
     * @throws ClassNotFoundException is thrown if UserManager object is not found
     */
    public UserManager readFromFile (String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path); // String path should be "fileName.ser"
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the StudentManager
            UserManager um = (UserManager) input.readObject();
            input.close();
            return um;
        } catch (IOException ex) {
            return new UserManager();
        }
    }

    /**
     * Write the UserManager object to a .ser file to store once program exists
     * @param filePath file to write to
     * @throws IOException is thrown if file we want to write to does not exist
     */
    public void saveToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the UserManager
        output.writeObject(this);
        output.close();
    }

}
