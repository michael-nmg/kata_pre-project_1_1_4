package jm.task.core.jdbc.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Util {
    private static final String infoFilePath = "src/main/java/jm/task/core/jdbc/resources/info";
    private static final String scriptFilePath = "src/main/java/jm/task/core/jdbc/resources/init_table";
    private static final Properties properts = Util.getPropConnect();
    public static final String TABLE_NAME = properts.getProperty("TABLE");
    public static final String SCRIPTS = Util.getScript();

    public static Properties getPropConnect() {
        Properties propert = new Properties();
        try (FileReader reader = new FileReader(infoFilePath)) {
            propert.load(reader);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return propert;
    }

    public static String getScript() {
        StringWriter string = new StringWriter();
        try (BufferedReader reader = new BufferedReader(new FileReader(scriptFilePath))) {
            String read;
            while ((read = reader.readLine()) != null) {
                string.append(read);
            }
            string.flush();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return string.toString();
    }

    public static Connection getConnection() {
        Connection connect = null;
        try {
            connect = DriverManager.getConnection(
                    properts.getProperty("URL"),
                    properts.getProperty("USER"),
                    properts.getProperty("PASSWORD"));
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return connect;
    }
}
