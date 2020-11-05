import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LogInSystem implements UserController {
    private UserManager um;

    public LogInSystem(UserManager um)
    {
        this.um = um;
    }

    @Override
    public User run()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserPropertiesIterator prompts = new UserPropertiesIterator();
        ArrayList<String> inputs = new ArrayList<>();
        System.out.println("1. Login\n2. Create new Account");
        try {
            String input = br.readLine();
            if (input.equals("1")) {
                System.out.println("Please enter your credentials");
                while (!input.equals("exit") && prompts.hasNext()) {
                    System.out.println(prompts.next());
                    input = br.readLine();
                    if (!input.equals("exit")) {
                        inputs.add(input);
                    }
                }
                return um.validate(inputs.get(2), inputs.get(3), inputs.get(0));
            }
            else if (input.equals("2"))
            {
                prompts.next();
                System.out.println("Please follow the steps to create an account:");
                while (!input.equals("exit") && prompts.hasNext()) {
                    System.out.println(prompts.next());
                    input = br.readLine();
                    if (!input.equals("exit")) {
                        inputs.add(input);
                    }
                }
                if (um.hasUserName(inputs.get(1)))
                {
                    System.out.println("That username is already in use");
                    return null;
                }
                else
                {
                    Attendee newAccount = um.createAttendee(inputs.get(0), inputs.get(1), inputs.get(2));
                    um.addAttendee(newAccount);
                    return (User) newAccount;
                }
            }
            else {
                return null;
            }

        } catch (IOException e) {
            System.out.println("Something went wrong");
            return null;
        }
    }

}
