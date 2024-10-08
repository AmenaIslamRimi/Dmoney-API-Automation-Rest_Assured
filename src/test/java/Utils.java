import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

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

//    public static void main(String[] args) {
//        String numPrefix = selectRandomNumberPrefix(" ");
//        System.out.println(numPrefix);
//    }
    }

