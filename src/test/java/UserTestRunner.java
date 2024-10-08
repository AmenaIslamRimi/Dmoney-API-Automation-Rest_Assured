import io.restassured.path.json.JsonPath;
import com.github.javafaker.Faker;
import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class UserTestRunner extends Setup {
    @Test (priority = 1, enabled = true, description = "admin log in")
    public void doLogin() throws IOException, ConfigurationException {
        UserController userController = new UserController();
        userController.doLogin("admin@roadtocareer.net", "1234");
    }

    @Test(priority = 2, enabled = false, description = "Create a new user")
    public void createUser() throws IOException, ConfigurationException, ParseException {
        Faker faker = new Faker();
        UserController userController = new UserController();
        UserModel userModel = new UserModel();
        userModel.setName(faker.name().fullName());
        userModel.setEmail(faker.internet().emailAddress().toLowerCase());
        userModel.setPassword("P@ssword123");
        String phoneNumber= Utils.selectRandomNumberPrefix() +Utils.generateRandomId(10000000,99999999);
        userModel.setPhone_number(phoneNumber);
        userModel.setNid(String.valueOf(Utils.generateRandomId(100000000,999999999)));
        userModel.setRole("Customer");
        JsonPath jsonPath = userController.createUser(userModel);
        int userId = jsonPath.get("user.id");
        Utils.setENVVar("userId", String.valueOf(userId));
        Utils.saveUsers(userModel);
    }
    @Test(priority = 3, enabled = false, description = "Search user by ID")
    public void searchUser() throws IOException {
        UserController userController = new UserController();
        JsonPath jsonPath = userController.searchUser(prop.getProperty("userId"));
        String searchMsg = jsonPath.get("message");
        Assert.assertTrue(searchMsg.contains("User found"));
    }

    @Test(priority = 4, enabled = true, description = "System deposit to agent")
    public void depositeAgent() throws IOException, ConfigurationException, ParseException {
        UserController userController = new UserController();
        UserModel userModel = new UserModel();
        JSONArray userArray = Utils.readJSONData();
        String agentPhoneNumber = null;
        for (int i = 0; i < userArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) userArray.get(i);

            // Check if the role is 'AGENT'
            String role = (String) jsonObject.get("role");
            if ("Agent".equalsIgnoreCase(role)) {
                agentPhoneNumber = (String) jsonObject.get("phone_number");
                break;  // Stop when the first agent is found
            }
        }

        userModel.setFrom_account("SYSTEM");
        userModel.setTo_account(agentPhoneNumber);
        userModel.setAmount(String.valueOf(2000));

        JsonPath jsonPath = userController.depositToAgent(userModel);
        String message = jsonPath.get("message");
        Assert.assertTrue(message.contains("Deposit successful"));
    }
}
