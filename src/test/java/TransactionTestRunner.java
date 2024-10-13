import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TransactionTestRunner extends Setup {

    @Test(priority = 1, enabled = true, description = "Agent log in")
    public void doLoginNewUser() throws ConfigurationException, IOException, ParseException {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        UserController userController = new UserController();
        UserModel userModel = new UserModel();
        String role = "Agent";
        JSONArray userArray = Utils.readJSONData();
        String email = null;
        String password = null;
        for (int i = 0; i < userArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) userArray.get(i);
            if (jsonObject.get("role").equals(role)) {
                email = (String) jsonObject.get("email");
                password = (String) jsonObject.get("password");
                break;
            }
        }
    }
    @Test(priority = 2, enabled = true, description = "Agent deposit to customer")
    public void depositeCustomer() throws IOException, ConfigurationException, ParseException {
        UserController userController = new UserController();
        UserModel userModel = new UserModel();
        JSONArray userArray = Utils.readJSONData();
        String agentPhoneNumber = null;
        String customerPhoneNumber = null;
        int customerCount = 0;

        for (int i = 0; i < userArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) userArray.get(i);

            // Check if the role is 'AGENT'
            String role = (String) jsonObject.get("role");
            if ("Agent".equalsIgnoreCase(role)) {
                agentPhoneNumber = (String) jsonObject.get("phone_number");
            }

            if ("Customer".equalsIgnoreCase(role)) {
                customerCount++;

                // Select the first customer as the sender
                if (customerCount == 1) {
                    customerPhoneNumber = (String) jsonObject.get("phone_number");
                    break;
                }
            }
        }

        userModel.setFrom_account(agentPhoneNumber);
        userModel.setTo_account(customerPhoneNumber);
        userModel.setAmount(String.valueOf(1500));



        JsonPath jsonPath = userController.depositToAgent(userModel);
        String message = jsonPath.get("message");
        Assert.assertTrue(message.contains("Deposit successful"));
    }
    @Test(priority = 3, enabled = true, description = "Customer log in")
    public void doLoginWithNewUser() throws ConfigurationException, IOException, ParseException {
            RestAssured.baseURI = prop.getProperty("baseUrl");
            UserController userController = new UserController();
            UserModel userModel = new UserModel();
            JSONArray userArray = Utils.readJSONData();
            String email = null;
            String password = null;
        int customerCount = 0;
            for (int i = 0; i < userArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) userArray.get(i);

                String role = (String) jsonObject.get("role"); // Assuming the JSON has a key 'name'
                if ("Customer".equalsIgnoreCase(role)) {
                    customerCount++;

                    // Select the first customer as the sender
                    if (customerCount == 1) {
                        email = (String) jsonObject.get("email");
                        password = (String) jsonObject.get("password");
                        break;
                    }
                }
            }

        userController.doLogin(email, password);
    }



    @Test(priority = 4, enabled = true, description = "Agent withdraw from customer")
    public void withdrawAgent() throws IOException, ConfigurationException, ParseException {
        UserController userController = new UserController();
        UserModel userModel = new UserModel();
        JSONArray userArray = Utils.readJSONData();
        String agentPhoneNumber = null;
        String customerPhoneNumber = null;
        int customerCount = 0;
        for (int i = 0; i < userArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) userArray.get(i);

            // Check if the role is 'AGENT'
            String role = (String) jsonObject.get("role");
            String customerName = (String) jsonObject.get("name");

            if ("Agent".equalsIgnoreCase(role)) {
                agentPhoneNumber = (String) jsonObject.get("phone_number");
            }

            if ("Customer".equalsIgnoreCase(role)) {
                customerCount++;

                // Select the first customer as the sender
                if (customerCount == 1) {
                    customerPhoneNumber = (String) jsonObject.get("phone_number");
                    break;
                }
            }
        }

        userModel.setFrom_account(customerPhoneNumber);
        userModel.setTo_account(agentPhoneNumber);
        userModel.setAmount(String.valueOf(50));



        JsonPath jsonPath = userController.withdrawCustomer(userModel);
        String message = jsonPath.get("message");
        Assert.assertTrue(message.contains("Withdraw successful"));
    }

    @Test(priority = 5, enabled = true, description = "Customer send money to other customer")
    public void sendMoneyToCustomer() throws IOException, ConfigurationException, ParseException {
        UserController userController = new UserController();
        UserModel userModel = new UserModel();
        JSONArray userArray = Utils.readJSONData();
        String fromCustomer = null;
        String toCustomer = null;
        int customerCount = 0;
        for (int i = 0; i < userArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) userArray.get(i);


            String role = (String) jsonObject.get("role");

            if ("Customer".equalsIgnoreCase(role)) {
                customerCount++;

                // Select the first customer as the sender
                if (customerCount == 1) {
                    fromCustomer = (String) jsonObject.get("phone_number");
                }

                // Select the second customer as the receiver
                if (customerCount == 2) {
                    toCustomer = (String) jsonObject.get("phone_number");
                    break;  // Exit the loop after finding the second customer
                }
            }
        }

        userModel.setFrom_account(fromCustomer);
        userModel.setTo_account(toCustomer);
        userModel.setAmount(String.valueOf(50));



        JsonPath jsonPath = userController.sendMoneyCustomer(userModel);
        String message = jsonPath.get("message");
        Assert.assertTrue(message.contains("Send money successful"));
    }

    @Test(priority = 6, enabled = true, description = "Customer payment to merchant")
    public void paymentToMerchant() throws IOException, ConfigurationException, ParseException {
        UserController userController = new UserController();
        UserModel userModel = new UserModel();
        JSONArray userArray = Utils.readJSONData();
        String customer = null;
        int customerCount = 0;
        String merchant = "01686606905";
        for (int i = 0; i < userArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) userArray.get(i);


            String role = (String) jsonObject.get("role");
            if ("Customer".equalsIgnoreCase(role)) {
                customerCount++;

                // Select the first customer as the sender
                if (customerCount == 1) {
                    customer = (String) jsonObject.get("phone_number");
                    break;
                }
            }
        }

        userModel.setFrom_account(customer);
        userModel.setTo_account(merchant);
        userModel.setAmount(String.valueOf(10));


        JsonPath jsonPath = userController.paymentMerchant(userModel);
        String message = jsonPath.get("message");
        Assert.assertTrue(message.contains("Payment successful"));
    }

    @Test(priority = 7, enabled = true, description = "Recipient Customer log in")
    public void doLoginRecipientCustomer() throws ConfigurationException, IOException, ParseException {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        UserController userController = new UserController();
        UserModel userModel = new UserModel();
        JSONArray userArray = Utils.readJSONData();
        String email = null;
        String password = null;
        int customerCount = 0;
        for (int i = 0; i < userArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) userArray.get(i);

            String role = (String) jsonObject.get("role"); // Assuming the JSON has a key 'name'
            if ("Customer".equalsIgnoreCase(role)) {
                customerCount++;

                // Select the first customer as the sender
                if (customerCount == 2) {
                    email = (String) jsonObject.get("email");
                    password = (String) jsonObject.get("password");
                    break;
                }
            }
        }

        userController.doLogin(email, password);
    }
    @Test(priority = 8, enabled = true, description = "Check balance of the recipient customer")
    public void checkCustomerBalance() throws IOException, ParseException {
        UserController userController = new UserController();
        JSONArray userArray = Utils.readJSONData();
        String phoneNumber=null;
        int customerCount = 0;
        for (int i = 0; i < userArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) userArray.get(i);
            String role = (String) jsonObject.get("role");
            if ("Customer".equalsIgnoreCase(role)) {
                customerCount++;

                // Select the first customer as the sender
                if (customerCount == 2) {
                    phoneNumber = (String) jsonObject.get("phone_number");
                    break;
                }
            }
        }

        userController.checkBalance(phoneNumber);

    }

}
