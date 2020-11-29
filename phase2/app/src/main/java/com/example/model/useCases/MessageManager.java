package com.example.model.useCases;

import com.example.model.entities.Message;

import java.io.*;
import java.util.ArrayList;

public class MessageManager implements Serializable{

    /**
     * The list of all Messages
     */
    private ArrayList<Message> messages = new ArrayList<>();

    /**
     * Returns the shallow copy of all messages in a list
     * @return the shallow copy of all messages in a list
     */
    public ArrayList<Message> getMessages(){
        return (ArrayList<Message>) messages.clone();
    }

    /**
     * add a new entities.Message to messages
     * @param messageID message to be added
     * @return true if and only if the message is successfully added to the message list
     */
    public boolean addMessage(int messageID){
        Message message = getMessageById(messageID);
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
     * return the entities.Message content given the message ID
     * @param messageID the given entities.Message ID
     * @return the content variable in entities.Message corresponding to the messageID
     */
    public String getMescontentById(int messageID){
        Message actualmessage = this.getMessageById(messageID);
        return actualmessage.getContent();
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
        int messageID = message.getMessageID();
        this.addMessage(messageID);
        return messageID;
    }

    /**
     * return the messageID given the entities.Message object
     * @param message the given entities.Message object
     * @return the messageID corresponding to the entities.Message object
     */
    public int getIdByMessage(Message message){
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
     * Read the useCases.MessageManager object that was stored in a .ser file
     * @param path String representing the file path
     * @return useCases.MessageManager object read from .ser file
     * @throws ClassNotFoundException is thrown if useCases.MessageManager object is not found
     */
    public MessageManager readFromFile (String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path); // String path should be "fileName.ser"
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the StudentManager
            MessageManager mm = (MessageManager) input.readObject();
            input.close();
            return mm;
        } catch (IOException ex) {
            return new MessageManager();
        }
    }

    /**
     * Write the useCases.MessageManager object to a .ser file to store once program exists
     * @param filePath file to write to
     * @throws IOException is thrown if file we want to write to does not exist
     */
    public void saveToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the useCases.MessageManager
        output.writeObject(this);
        output.close();
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
}
