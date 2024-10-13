import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;


public class UserController extends Setup {

    public UserController() throws IOException {
        initConfigFile();
    }

    public void doLogin(String email, String password) throws ConfigurationException {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        UserModel userModel = new UserModel();
        userModel.setEmail(email);
        userModel.setPassword(password);
        Response res = given().contentType("application/json").body(userModel).post("/user/login");
        //System.out.println(res.asString());
        JsonPath jsonPath = res.jsonPath();
        String token = jsonPath.get("token");
        //System.out.println(token);
        Utils.setENVVar("token", token);
    }

    public JsonPath searchUser(String userId) {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res = given().contentType("application/json").header("Authorization", "Bearer " + prop.get("token"))
                .when().get("/user/search/id/" + userId);
        //System.out.println(res.asString());

        return res.jsonPath();

    }

    public JsonPath createUser(UserModel userModel) throws ConfigurationException {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res = given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.getProperty("token"))
                .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                .body(userModel)
                .when().post("/user/create");

//        JsonPath jsonResponse = res.jsonPath();
////        // Extract the userId from the response (assuming the response has a field "userId")
//        int userId = jsonResponse.getInt("user.id");
        return res.jsonPath();

    }

    public JsonPath depositToAgent(UserModel userModel) throws ConfigurationException {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res = given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.get("token"))
                .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                .body(userModel)
                .when().post("/transaction/deposit");
        System.out.println(res.asString());

//        JsonPath jsonResponse = res.jsonPath();
//        // Extract the userId from the response (assuming the response has a field "userId")
//        int userId = jsonResponse.getInt("user.id");
        return res.jsonPath();

    }

    public JsonPath withdrawCustomer(UserModel userModel) throws ConfigurationException {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res = given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.get("token"))
                .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                .body(userModel)
                .when().post("/transaction/withdraw");
        System.out.println(res.asString());

//        JsonPath jsonResponse = res.jsonPath();
//        // Extract the userId from the response (assuming the response has a field "userId")
//        int userId = jsonResponse.getInt("user.id");
        return res.jsonPath();

    }

    public JsonPath sendMoneyCustomer(UserModel userModel) throws ConfigurationException {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res = given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.get("token"))
                .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                .body(userModel)
                .when().post("/transaction/sendmoney");
        System.out.println(res.asString());

//        JsonPath jsonResponse = res.jsonPath();
//        // Extract the userId from the response (assuming the response has a field "userId")
//        int userId = jsonResponse.getInt("user.id");
        return res.jsonPath();

    }

    public JsonPath paymentMerchant(UserModel userModel) throws ConfigurationException {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res = given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.get("token"))
                .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                .body(userModel)
                .when().post("/transaction/payment");
        System.out.println(res.asString());

//        JsonPath jsonResponse = res.jsonPath();
//        // Extract the userId from the response (assuming the response has a field "userId")
//        int userId = jsonResponse.getInt("user.id");
        return res.jsonPath();

    }

    public JsonPath checkBalance(String phone_number) {
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res = given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.get("token"))
                .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerKey"))
                .when().get("/transaction/statement/" + phone_number);
        System.out.println("Response Body: " + res.asString());

        return res.jsonPath();

    }

}


