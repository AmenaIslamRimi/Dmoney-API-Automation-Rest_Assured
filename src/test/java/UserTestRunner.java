import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;

import java.io.IOException;

public class UserTestRunner {
    @Test (priority = 1)
    public void doLogin() throws IOException, ConfigurationException {
        UserController userController = new UserController();
        userController.doLogin();
    }

    @Test(priority = 2)
    public void searchUser() throws IOException {
        UserController userController = new UserController();
        userController.searchUser();
    }
}
