import io.restassured.path.json.JsonPath;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {
        public static void setENVVar(String key, String value) throws ConfigurationException {
            PropertiesConfiguration config = new PropertiesConfiguration("./src/test/resources/config.properties");
            config.setProperty(key, value);
            config.save();

        }
    public static int generateRandomId(int min, int max){
        double random= Math.random()*(max-min)+min;
        int randId= (int) random;
        return randId;

    }

    public static String selectRandomNumberPrefix() {
            String [] prefix = {"015","016","017","018","019"};
            int randomIndex = (int) (Math.random() * prefix.length);
            String randomPrefix = prefix[randomIndex];
            return randomPrefix;
    }

    public static void saveUsers(UserModel userModel) throws IOException, ParseException, ConfigurationException {
        String fileLocation = "./src/test/resources/usersInfo.json";
        JSONParser parser = new JSONParser();
        JSONArray userArray = (JSONArray) parser.parse(new FileReader(fileLocation));
        UserController userController = new UserController();
        JsonPath jsonPath = userController.createUser(userModel);
        String userId = jsonPath.get("user.id");

        JSONObject userObj = new JSONObject();

        userObj.put("id", userId);
        userObj.put("name", userModel.getName());
        userObj.put("email", userModel.getEmail());
        userObj.put("password", userModel.getPassword());
        userObj.put("phone_number", userModel.getPhone_number());
        userObj.put("nid", userModel.getNid());
        userObj.put("role", userModel.getRole());

        userArray.add(userObj);
        FileWriter writer = new FileWriter(fileLocation);
        writer.write(userArray.toJSONString());
        writer.flush();
        writer.close();
    }
    public static JSONArray readJSONData() throws IOException, ParseException {
        String fileLocation = "./src/test/resources/usersInfo.json";
        JSONParser parser = new JSONParser();
        JSONArray userArray = (JSONArray) parser.parse(new FileReader(fileLocation));
        return userArray;

    }
    }

