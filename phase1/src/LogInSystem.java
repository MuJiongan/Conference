public class LogInSystem implements UserController {
    private UserManager um;


    public LogInSystem(UserManager um)
    {
        this.um = um;
    }
    public UserController run()
    {
        //options to log-in or create a new account
        //returns specific controller for different accounts
    }

}
