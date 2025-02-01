package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadDataFromPropFile {


    public static String AccessPropFile(String key) throws IOException {
        Properties properties = new Properties();
        String path = System.getProperty("user.dir")+"/src/main/resources/get.properties";
        FileInputStream fis = new FileInputStream(path);
        properties.load(fis);
         return properties.getProperty(key);

    }
}
