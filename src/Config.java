import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by zzhfeng on 2017/7/12.
 */
public class Config {
    private static Properties prop;
    private static String fpath = "";

    public Config(String path) {
        fpath = path;
        prop = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(path));
            prop.load(in);
            in.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

    public static int getPropertyLen(String key) {
//        return Integer.parseInt(prop.getProperty(key + "type"));
        return 80;
    }

    public static void store() {
        try {
            FileOutputStream oFile = new FileOutputStream(fpath);
            prop.store(oFile, "demo config file");
            oFile.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void setProp(String key, String value) {
        prop.setProperty(key, value);
    }

}
