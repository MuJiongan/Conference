package com.example.model.interfaceAdapters;

import android.content.Context;
import com.example.model.entities.Message;
import com.example.model.entities.Speaker;
import com.example.model.useCases.*;

import java.io.*;

/**
 * Gateway class to read and write from files
 */
public class ReadWrite implements Serializable {



    /**
     * Read stored information and load the stored AttendeeManager
     * @param path filepath of the ser. file that stores nothing if the main method is never ran and store
     *             <code>AttendeeManager</code> by the end of the last running if the main method is ran
     * @return return the instance of <code>AttendeeManager</code> updated based on the given file
     */
    public static AttendeeManager readAttendee(Context context)
    {
        try {
            InputStream fis = context.openFileInput("attendeemanager.ser");
            InputStream buffer = new BufferedInputStream(fis);
            ObjectInput input = new ObjectInputStream(buffer);
            AttendeeManager am = (AttendeeManager) input.readObject();
            input.close();
            return am;
        } catch (FileNotFoundException e) {
            return new AttendeeManager();
        } catch (IOException e) {
            return new AttendeeManager();
        } catch (ClassNotFoundException e) {
            return new AttendeeManager();
        }
    }

    /**
     * Read stored information and load the stored OrganizerManager
     * @param path filepath of the ser. file that stores nothing if the main method is never ran and store
     *             <code>OrganizerManager</code> by the end of the last running if the main method is ran
     * @return return the instance of <code>OrganizerManager</code> updated based on the given file
     */
    public static OrganizerManager readOrganizer(Context context)
    {
        try {
            InputStream fis = context.openFileInput("organizermanager.ser");
            InputStream buffer = new BufferedInputStream(fis);
            ObjectInput input = new ObjectInputStream(buffer);
            OrganizerManager om = (OrganizerManager) input.readObject();
            input.close();
            return om;
        } catch (FileNotFoundException e) {
            return new OrganizerManager();
        } catch (IOException e) {
            return new OrganizerManager();
        } catch (ClassNotFoundException e) {
            return new OrganizerManager();
        }
    }

    /**
     * Read stored information and load the stored SpeakerManager
     * @param path filepath of the ser. file that stores nothing if the main method is never ran and store
     *             <code>SpeakerManager</code> by the end of the last running if the main method is ran
     * @return return the instance of <code>SpeakerManager</code> updated based on the given file
     */
    public static SpeakerManager readSpeaker(Context context)
    {
        try {
            InputStream fis = context.openFileInput("speakermanager.ser");
            InputStream buffer = new BufferedInputStream(fis);
            ObjectInput input = new ObjectInputStream(buffer);
            SpeakerManager sm = (SpeakerManager) input.readObject();
            input.close();
            return sm;
        } catch (FileNotFoundException e) {
            return new SpeakerManager();
        } catch (IOException e) {
            return new SpeakerManager();
        } catch (ClassNotFoundException e) {
            return new SpeakerManager();
        }
    }
    /**
     * Read stored information and load the stored RoomManager
     * @param path filepath of the ser. file that stores nothing if the main method is never ran and store
     *             <code>RoomManager</code> by the end of the last running if the main method is ran
     * @return return the instance of <code>RoomManager</code> updated based on the given file
     */
    public static RoomManager readRoom(Context context)
    {
        try {
            InputStream fis = context.openFileInput("roommanager.ser");
            InputStream buffer = new BufferedInputStream(fis);
            ObjectInput input = new ObjectInputStream(buffer);
            RoomManager rm = (RoomManager) input.readObject();
            input.close();
            return rm;
        } catch (FileNotFoundException e) {
            return new RoomManager();
        } catch (IOException e) {
            return new RoomManager();
        } catch (ClassNotFoundException e) {
            return new RoomManager();
        }
    }
    /**
     * Read stored information and load the stored EventManager
     * @param path filepath of the ser. file that stores nothing if the main method is never ran and store
     *             <code>EventManager</code> by the end of the last running if the main method is ran
     * @return return the instance of <code>EventManager</code> updated based on the given file
     */
    public static EventManager readEvent(Context context)
    {
        try {
            InputStream fis = context.openFileInput("eventmanager.ser");
            InputStream buffer = new BufferedInputStream(fis);
            ObjectInput input = new ObjectInputStream(buffer);
            EventManager em = (EventManager) input.readObject();
            input.close();
            return em;
        } catch (FileNotFoundException e) {
            return new EventManager();
        } catch (IOException e) {
            return new EventManager();
        } catch (ClassNotFoundException e) {
            return new EventManager();
        }
    }
    /**
     * Read stored information and load the stored MessageManager
     * @param path filepath of the ser. file that stores nothing if the main method is never ran and store
     *             <code>MessageManager</code> by the end of the last running if the main method is ran
     * @return return the instance of <code>MessageManager</code> updated based on the given file
     */
    public static MessageManager readMessage(Context context)
    {
        try {
            InputStream fis = context.openFileInput("messagemanager.ser");
            InputStream buffer = new BufferedInputStream(fis);
            ObjectInput input = new ObjectInputStream(buffer);
            MessageManager mm = (MessageManager) input.readObject();
            input.close();
            return mm;
        } catch (FileNotFoundException e) {
            return new MessageManager();
        } catch (IOException e) {
            return new MessageManager();
        } catch (ClassNotFoundException e) {
            return new MessageManager();
        }
    }
    /**
     * Read stored information and load the stored VipEventManager
     * @param path filepath of the ser. file that stores nothing if the main method is never ran and store
     *             <code>VipEventManager</code> by the end of the last running if the main method is ran
     * @return return the instance of <code>VipEventManager</code> updated based on the given file
     */
    public static VipEventManager readVipEventManager(Context context)
    {
        try {
            InputStream fis = context.openFileInput("vipeventmanager.ser");
            InputStream buffer = new BufferedInputStream(fis);
            ObjectInput input = new ObjectInputStream(buffer);
            VipEventManager vipM = (VipEventManager) input.readObject();
            input.close();
            return vipM;
        } catch (FileNotFoundException e) {
            return new VipEventManager();
        } catch (IOException e) {
            return new VipEventManager();
        } catch (ClassNotFoundException e) {
            return new VipEventManager();
        }
    }
    /**
     * Read stored information and load the stored VipManager
     * @param path filepath of the ser. file that stores nothing if the main method is never ran and store
     *             <code>VipManager</code> by the end of the last running if the main method is ran
     * @return return the instance of <code>VipManager</code> updated based on the given file
     */
    public static VipManager readVipManager(Context context)
    {
        try {
            InputStream fis = context.openFileInput("vipmanager.ser");
            InputStream buffer = new BufferedInputStream(fis);
            ObjectInput input = new ObjectInputStream(buffer);
            VipManager vipM = (VipManager) input.readObject();
            input.close();
            return vipM;
        } catch (FileNotFoundException e) {
            return new VipManager();
        } catch (IOException e) {
            return new VipManager();
        } catch (ClassNotFoundException e) {
            return new VipManager();
        }
    }

    /**
     * store the <code>VipManager</code> in the conference to the given file
     * @param path filepath of the ser. file to store the <code>AttendeeManager</code>
     */
    public static void saveVips(Context context, VipManager vipM)
    {
        try {
            OutputStream file = context.openFileOutput("vipmanager.ser", Context.MODE_PRIVATE);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(vipM);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * store the <code>VipEventManager</code> in the conference to the given file
     * @param path filepath of the ser. file to store the <code>VipEventManager</code>
     */
    public static void saveVipEventManager(Context context, VipEventManager vipEventM)
    {
        try {
            OutputStream file = context.openFileOutput("vipeventmanager.ser", Context.MODE_PRIVATE);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(vipEventM);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * store the <code>AttendeeManager</code> in the conference to the given file
     * @param path filepath of the ser. file to store the <code>AttendeeManager</code>
     */
    public static void saveAttendees(Context context, AttendeeManager am)
    {
        try {
            OutputStream file = context.openFileOutput("attendeemanager.ser", Context.MODE_PRIVATE);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(am);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * store the <code>OrganizerManager</code> in the conference to the given file
     * @param path filepath of the ser. file to store the <code>OrganizerManager</code>
     */
    public static void saveOrganizers(Context context, OrganizerManager om)
    {
        try {
            OutputStream file = context.openFileOutput("organizermanager.ser", Context.MODE_PRIVATE);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(om);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * store the <code>SpeakerManager</code> in the conference to the given file
     * @param path filepath of the ser. file to store the <code>SpeakerManager</code>
     */
    public static void saveSpeakers(Context context, SpeakerManager sm)
    {
        try {
            OutputStream file = context.openFileOutput("speakermanager.ser", Context.MODE_PRIVATE);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(sm);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * store the <code>EventManager</code> in the conference to the given file
     * @param path filepath of the ser. file to store the <code>EventManager</code>
     */
    public static void saveEvent (Context context, EventManager em)
    {
        try {
            OutputStream file = context.openFileOutput("eventmanager.ser", Context.MODE_PRIVATE);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

            // serialize the useCases.UserManager
            output.writeObject(em);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * store the <code>RoomManager</code> in the conference to the given file
     * @param path filepath of the ser. file to store the <code>RoomManager</code>
     */
    public static void saveRoom (Context context, RoomManager rm) {
        try {
            OutputStream file = context.openFileOutput("roommanager.ser", Context.MODE_PRIVATE);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(rm);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * store the <code>MessageManager</code> in the conference to the given file
     * @param path filepath of the ser. file to store the <code>MessageManager</code>
     */
    public static void saveMessage (Context context, MessageManager mm)
    {
        try {
            OutputStream file = context.openFileOutput("messagemanager.ser", Context.MODE_PRIVATE);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(mm);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
