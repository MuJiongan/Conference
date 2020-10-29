import java.util.ArrayList;

public class MessageManager {

    /**
     * The list of all Messages
     */
    private ArrayList<Message> messages;

    /**
     * Returns the shallow copy of all messages in a list
     * @return the shallow copy of all messages in a list
     */
    public ArrayList<Message> getMessages(){
        return (ArrayList<Message>) messages.clone();
    }

    /**
     * add a new Message to messages
     * @param message message to be added
     * @return true if and only if the message is successfully added to the message list
     */
    public boolean addMessage(Message message){
        for (Message content: messages){
            if(content == message){
                return false;
            }
        }
        messages.add(message);
        return true;
    }

    /**
     * return the Message object given the message ID
     * @param messageID the given Message ID
     * @return the Message object corresponding to the messageID
     */
    public Message getMessageById(int messageID){
        for (Message message: messages){
            if (message.getMessageID() == messageID){
                return message;
            }
        }
        return null;
    }

    /**
     * return the messageID given the Message object
     * @param message the given Message object
     * @return the messageID corresponding to the Message object
     */
    public int getIdByMessage(Message message){
        return message.getMessageID();
    }


}
