package config;

import repository.FacultyRepository;
import repository.FileFacultyRepository;
import repository.MySqlFacultyRepository;

/**
 * Central application configuration.
 * Set storage.type to "file" (default) or "mysql" in config/database.properties.
 */
public final class AppConfig {

    public static final int MAX_HOURS_PER_WEEK = 40;
    public static final int MIN_HOURS_PER_WEEK = 1;
    public static final int MAX_ALLOWED_HOURS = 60;

    public static final String DEFAULT_ADMIN_USER = "admin";
    public static final String DEFAULT_ADMIN_PASS = "admin123";

    private static FacultyRepository repository;

    private AppConfig() {
    }

    public static synchronized FacultyRepository getRepository() {
        if (repository == null) {
            repository = createRepository();
        }
        return repository;
    }

    private static FacultyRepository createRepository() {
        String storageType = DatabaseConfig.getProperty("storage.type", "file").trim().toLowerCase();

        if ("mysql".equals(storageType)) {
            return new MySqlFacultyRepository();
        }
        return new FileFacultyRepository();
    }

    /** Allows switching repository at runtime (e.g. after config change). */
    public static synchronized void resetRepository() {
        repository = null;
    }
}
