package com.example.model.entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents the messages for the conference.
 */
public class Message implements Serializable {
    private int senderID;
    private int receiverID;
    private String content;
    private int messageID;
    private boolean unread;

    /**
     * Constructor:
     *   Create a new Message object given
     *   @param _senderID of this message,
     *   @param _receiverID of this message,
     *   @param _content of this message,
     *   @param messageID of this message.
     */
    public Message(int _senderID, int _receiverID, String _content, int messageID){
        this.senderID = _senderID;
        this.receiverID = _receiverID;
        this.content = _content;
        this.messageID = messageID;
        this.unread = false;
    }

    /**
     * Return the sender ID of the message
     * @return the sender ID of the message
     */
    public int getSenderID(){
        return senderID;
    }

    /**
     * Return the receiver ID of the message
     * @return the receiver ID of the message
     */
    public int getReceiverID(){
        return receiverID;
    }

    /**
     * Return the content of the message
     * @return the content of the message
     */
    public String getContent(){
        return content;
    }

    /**
     * Return the ID of the message
     * @return the ID of the message
     */
    public int getMessageID() {
        return messageID;
    }

    /**
     * Return the condition of the message
     * @return the condition of the message
     */
    public boolean getMessageCondition(){
        return unread;
    }

    /**
     * Change the read condition of the message to true
     */
    public void changeMessageCondition(){
        unread = true;
    }

    /**
     * Change the read condition of the message to false
     */
    public void markAsUnread(){
        unread = false;
    }
}
