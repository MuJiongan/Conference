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
        System.out.println("Type 'exit' to quit or 'ok' to continue.");
        try {
            String input = br.readLine();
            while (!input.equals("exit") && prompts.hasNext()) {
                System.out.println(prompts.next());
                input = br.readLine();
                if (!input.equals("exit")) {
                    inputs.add(input);
                }
            }
            return um.validate(inputs.get(1), inputs.get(2), inputs.get(0));
        } catch (IOException e) {
            System.out.println("Something went wrong");
            return null;
        }
    }

}
