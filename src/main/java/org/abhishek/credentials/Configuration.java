package org.abhishek.credentials;

import java.sql.*;

public final class Configuration {

    private static final String  url = "jdbc:postgresql://localhost:5432/abhishek";
    private static final String  user = "abhishek";
    private static final String  password = "test123";

    public static String getURL() {
        return url;
    }
    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }

    public static Connection connectToDatabase() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            if(connection != null) {
                return connection;
            }else {
                System.out.println("Failed to connect to Database!");
                return null;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void disconnectToDatabase(Connection connection){
        try {
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
