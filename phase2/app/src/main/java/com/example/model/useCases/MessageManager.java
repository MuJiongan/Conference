package com.example.model.useCases;

import com.example.model.entities.Message;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MessageManager implements Serializable{

    /**
     * The list of all Messages
     */
    private List<Message> messages = new ArrayList<>();

    /**
     * add a new entities.Message to messages
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
     * return the entities.Message object given the message ID
     * @param messageID the given entities.Message ID
     * @return the entities.Message object corresponding to the messageID
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
     * Return a new message with given features
     * @param content of new message
     * @param senderID of new message
     * @param receiverID of new message
     * @return a new message with given features
     */
    public int createMessage(String content, int senderID, int receiverID){
        Message message = new Message(senderID, receiverID, content, messages.size() + 1);
        this.addMessage(message);
        return message.getMessageID();
    }


    /**
     * return the sender ID by the given message id
     * @param messageId the message ID that want to know the sender id of
     * @return the sender ID by the given message id
     */
    public int getSenderIDByMessId(int messageId){
        return getMessageById(messageId).getSenderID();
    }

    /**
     * return the receiver ID by the given message id
     * @param messageId the message ID that want to know the sender id of
     * @return the receiver ID by the given message id
     */
    public int getReceiverIDByMessId(int messageId){
        return getMessageById(messageId).getReceiverID();
    }


    /**
     * Change the condition of message
     * @param messageID the message ID needed to change
     */
    public void changeMessageCondition(int messageID){
        this.getMessageById(messageID).changeMessageCondition();
    }

    /**
     * Change the read condition of the message to false
     * @param messageID the message ID needed to change
     */
    public void markAsUnread(int messageID){
        this.getMessageById(messageID).markAsUnread();
    }

    /**
     * Return the message condition by the given messageID
     * @param messageID the message ID
     * @return the message condition by the given messageID
     */
    public boolean getConditionByID(int messageID){
        return this.getMessageById(messageID).getMessageCondition();
    }

    public String getMescontentById(Integer messageID) {
        Message actualmessage = this.getMessageById(messageID);
        return actualmessage.getContent();
    }

    /**
     * return the number of messages in the conference
     * @return return the number of messages in the conference
     */
    public int getNumOfMess(){
        return messages.size();
    }
}
