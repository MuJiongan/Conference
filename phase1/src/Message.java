import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents the messages for the conference.
 */
public class Message implements Serializable {

    /**
     * Stores the User id of sender
     */
    private int senderID;

    /**
     * Stores the User id of receiver
     */
    private int receiverID;

    /**
     * Stores the message content
     */
    private String content;

    /**
     * Stores the total number of Messages
     */
    private static int numOfMessages = 0;

    /**
     * Stores the id of the message
     */
    private int messageID;


    /**
     * Constructor:
     *   Create a new Message object given
     *   @param _senderID of the message,
     *   @param _receiverID of the message,
     *   @param _content of the message.
     */
    public Message(int _senderID, int _receiverID, String _content){
        this.senderID = _senderID;
        this.receiverID = _receiverID;
        this.content = _content;
        this.messageID = numOfMessages;
        numOfMessages++;
    }

    /**
     * Getter:
     *    Returns the senderID of message
     */
    public int getSenderID(){
        return senderID;
    }

    /**
     * Getter:
     *    Returns the receiverID of message
     */
    public int getReceiverID(){
        return receiverID;
    }

    /**
     * Getter:
     *    Returns the content of message
     */
    public String getContent(){
        return content;
    }

    /**
     * Getter:
     *    Returns the messageID of message
     */
    public int getMessageID() {
        return messageID;
    }

}
