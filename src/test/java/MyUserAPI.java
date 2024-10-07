import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;


public class MyUserAPI {

    public MyUserAPI () throws IOException {
        readConfigFile();
    }

    @Test
    public void doLogin() throws ConfigurationException {
        RestAssured.baseURI = "https://dmoney.roadtocareer.net";
        Response res = given().contentType("application/json").body("{\n" +
                "    \"email\":\"admin@roadtocareer.net\",\n" +
                "    \"password\":\"1234\"\n" +
                "}").post("/user/login");
        System.out.println(res.asString());
        JsonPath jsonPath = res.jsonPath();
        String token = jsonPath.get("token");
        System.out.println(token);
        setENVVar("token", token);
    }

    @Test
    public void searchUser()  {
        RestAssured.baseURI = "https://dmoney.roadtocareer.net";
        Response res = given().contentType("application/json").header("Authorization", prop.get("token")).when().get("/user/search/id/24724");
        System.out.println(res.asString());

    }
    Properties prop;
    FileInputStream fs;
    public void readConfigFile() throws IOException {
        prop = new Properties();
        fs = new FileInputStream("./src/test/resources/config.properties");
        prop.load(fs);
        //System.out.println(prop.get("token"));
    }

    public void setENVVar(String key, String value) throws ConfigurationException {
        PropertiesConfiguration config = new PropertiesConfiguration("./src/test/resources/config.properties");
        config.setProperty(key, value);
        config.save();

    }
}
