package trefis.moderntour.sql;

import org.bukkit.Bukkit;
import trefis.moderntour.Main;

import java.sql.*;
import java.util.Properties;
import trefis.moderntour.Utils;


public class SQLWorker {
    public static Connection connection;

    public static boolean getConnection(String jdbc, String user, String password) {
        try {
            Properties properties = new Properties();
            properties.setProperty("user", user);
            properties.setProperty("password", password);
            properties.setProperty("useSSL", "false");
            properties.setProperty("autoReconnect", "true");
            properties.setProperty("characterEncoding", "utf-8");
            properties.setProperty("useUnicode", "true");

            connection = DriverManager.getConnection(jdbc, properties);
            if(connection != null){
                Utils.log("&a[ModernTour] Successfully connected to a database.");
            }
            initializeTable();
            return true;
        } catch (Exception e) {
            Utils.log("&c[ModernTour] Unable to connect to a database. Error: " + e.getMessage());
            return false;
        }
    }

    public static boolean executeUpdate(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            Utils.log("[ModernTour] Error while connecting executing a command: " + query + ". Error: " + e.getMessage());
            return false;
        }
    }

    public static ResultSet executeQuery(String query) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            Utils.log("[ModernTour] Error while connecting executing a command: " + query + ". Error: " + e.getMessage());
        }
        return resultSet;
    }

    private static void initializeTable() {
        executeUpdate("""
                CREATE TABLE IF NOT EXISTS `mt_data` (
                `id` INT(11) NOT NULL AUTO_INCREMENT,
                `uuid` VARCHAR(36) NOT NULL,
                `role` VARCHAR(10) NOT NULL,
                PRIMARY KEY (`id`) USING BTREE,
                UNIQUE KEY `uuid_role_uniq` (`uuid`, `role`)
                )
                COLLATE='utf8mb4_general_ci' ENGINE=InnoDB;""");
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                Utils.log("§c[ModernTour] Error while closing a database connection. " + "Error: " + e.getMessage());
            }
        }

    }
}
