package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads JDBC and storage settings from config/database.properties.
 */
public final class DatabaseConfig {

    private static final String CONFIG_PATH = "config/database.properties";
    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    private DatabaseConfig() {
    }

    private static void loadProperties() {
        try (InputStream input = new FileInputStream(CONFIG_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            try (InputStream fallback = DatabaseConfig.class.getClassLoader()
                    .getResourceAsStream("database.properties")) {
                if (fallback != null) {
                    properties.load(fallback);
                }
            } catch (IOException ignored) {
                applyDefaults();
            }
        }

        if (properties.isEmpty()) {
            applyDefaults();
        }
    }

    private static void applyDefaults() {
        properties.setProperty("storage.type", "file");
        properties.setProperty("mysql.url", "jdbc:mysql://localhost:3306/faculty_workload");
        properties.setProperty("mysql.user", "root");
        properties.setProperty("mysql.password", "");
        properties.setProperty("mysql.driver", "com.mysql.cj.jdbc.Driver");
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static String getJdbcUrl() {
        return getProperty("mysql.url", "jdbc:mysql://localhost:3306/faculty_workload");
    }

    public static String getJdbcUser() {
        return getProperty("mysql.user", "root");
    }

    public static String getJdbcPassword() {
        return getProperty("mysql.password", "");
    }

    public static String getJdbcDriver() {
        return getProperty("mysql.driver", "com.mysql.cj.jdbc.Driver");
    }
}
