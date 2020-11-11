import java.io.Serializable;
import java.util.ArrayList;
/**
 * Represents a organizer in the conference.
 */

public class Speaker extends User implements Serializable {


    /**
     * Create an instance of Organizer
     */
    public Speaker(String name, String username, String password){
        super(name, username, password);
    }


}