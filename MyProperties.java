import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
// this exception is necessary
public class MyProperties{
    public static void main(String[] args) throws Exception{
        Properties p = new Properties();
        FileInputStream fi = new FileInputStream("/home/shivanshkumar/Desktop/java/Key.properties");
        p.load(fi); // this first load all the key/val from file and return map like data
        System.out.println("Properties Data : "+p);


        // so is peroperty doent exsit the we will get null
        String name = p.getProperty("Name"); // theh by this method we are aces particular property value
        System.out.println("name from properties "+ name);

        System.out.println(p.stringPropertyNames()); // this return set of String


        System.out.println("---------------- Seting the property ------------");
        p.setProperty("URL", "google.com");
        FileOutputStream fos = new FileOutputStream("/home/shivanshkumar/Desktop/java/Key.properties");
        p.store(fos, "Comment, chaged by shivans kumar"); // her ewe are string our new one
    }
}
