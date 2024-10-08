import io.restassured.path.json.JsonPath;
import com.github.javafaker.Faker;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class UserTestRunner extends Setup {
    @Test (priority = 1, enabled = true, description = "admin log in")
    public void doLogin() throws IOException, ConfigurationException {
        UserController userController = new UserController();
        userController.doLogin("admin@roadtocareer.net", "1234");
    }

    @Test(priority = 2, enabled = true, description = "Create a new user")
    public void createUser() throws IOException, ConfigurationException {
        Faker faker = new Faker();
        UserController userController = new UserController();
        UserModel userModel = new UserModel();
        userModel.setName(faker.name().fullName());
        userModel.setEmail(faker.internet().emailAddress().toLowerCase());
        userModel.setPassword("1234");
        String phoneNumber= Utils.selectRandomNumberPrefix() +Utils.generateRandomId(10000000,99999999);
        userModel.setPhone_number(phoneNumber);
        userModel.setNid(String.valueOf(Utils.generateRandomId(100000000,999999999)));
        userModel.setRole("Customer");
        JsonPath jsonPath = userController.createUser(userModel);
        int userId = jsonPath.get("user.id");
        Utils.setENVVar("userId", String.valueOf(userId));
    }
    @Test(priority = 3, enabled = true, description = "Search user by ID")
    public void searchUser() throws IOException {
        UserController userController = new UserController();
        JsonPath jsonPath = userController.searchUser(prop.getProperty("userId"));
        String searchMsg = jsonPath.get("message");
        Assert.assertTrue(searchMsg.contains("User found"));
    }
}
