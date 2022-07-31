package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class Util {
    private static final String url = "jdbc:mysql://localhost:3306/testDB";
    private static final String user = "root";
    private static final String password = "12345678";
    private static Connection connect;
    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry registry;

    static {
        try {
            Configuration configuration = new Configuration();
            Properties properties = new Properties();

            properties.put(Environment.USER, user);
            properties.put(Environment.PASS, password);
            properties.put(Environment.URL, url);
            properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            properties.put(Environment.HBM2DDL_CHARSET_NAME, "utf8");
            properties.put(Environment.SHOW_SQL, "false");
            properties.put(Environment.FORMAT_SQL, "true");

            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);

            registry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(registry);
        } catch (Throwable t) {
            t.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Connection getConnection() {
        try {
            connect = DriverManager.getConnection(url, user, password);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return connect;
    }

    public static void connectionClose() {
        try {
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
