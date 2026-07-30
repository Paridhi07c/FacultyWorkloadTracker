package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConfig;
import model.Faculty;

/**
 * MySQL persistence via JDBC.
 * Requires mysql-connector-j on the classpath and config/database.properties configured.
 */
public class MySqlFacultyRepository implements FacultyRepository {

    static {
        try {
            Class.forName(DatabaseConfig.getJdbcDriver());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    "MySQL JDBC driver not found. Add mysql-connector-j to the classpath.", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DatabaseConfig.getJdbcUrl(),
                DatabaseConfig.getJdbcUser(),
                DatabaseConfig.getJdbcPassword());
    }

    @Override
    public List<Faculty> findAll() {
        List<Faculty> list = new ArrayList<>();
        String sql = "SELECT faculty_id, faculty_name, department, subject, hours_per_week FROM faculty ORDER BY faculty_id";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load faculty from MySQL.", e);
        }
        return list;
    }

    @Override
    public Faculty findById(int facultyId) {
        String sql = "SELECT faculty_id, faculty_name, department, subject, hours_per_week FROM faculty WHERE faculty_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, facultyId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find faculty by ID.", e);
        }
        return null;
    }

    @Override
    public void save(Faculty faculty) {
        String sql = "INSERT INTO faculty (faculty_id, faculty_name, department, subject, hours_per_week) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            bindFaculty(ps, faculty);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save faculty.", e);
        }
    }

    @Override
    public void saveAll(List<Faculty> facultyList) {
        String deleteSql = "DELETE FROM faculty";
        String insertSql = "INSERT INTO faculty (faculty_id, faculty_name, department, subject, hours_per_week) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try (Statement delete = conn.createStatement();
                 PreparedStatement insert = conn.prepareStatement(insertSql)) {

                delete.executeUpdate(deleteSql);
                for (Faculty faculty : facultyList) {
                    bindFaculty(insert, faculty);
                    insert.addBatch();
                }
                insert.executeBatch();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save all faculty records.", e);
        }
    }

    @Override
    public void update(Faculty faculty) {
        String sql = "UPDATE faculty SET faculty_name = ?, department = ?, subject = ?, hours_per_week = ? WHERE faculty_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, faculty.getFacultyName());
            ps.setString(2, faculty.getDepartment());
            ps.setString(3, faculty.getSubject());
            ps.setInt(4, faculty.getHoursPerWeek());
            ps.setInt(5, faculty.getFacultyId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update faculty.", e);
        }
    }

    @Override
    public void deleteById(int facultyId) {
        String sql = "DELETE FROM faculty WHERE faculty_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, facultyId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete faculty.", e);
        }
    }

    @Override
    public boolean existsById(int facultyId) {
        return findById(facultyId) != null;
    }

    @Override
    public void reload() {
        // MySQL reads are always live; nothing to reload.
    }

    private Faculty mapRow(ResultSet rs) throws SQLException {
        return new Faculty(
                rs.getInt("faculty_id"),
                rs.getString("faculty_name"),
                rs.getString("department"),
                rs.getString("subject"),
                rs.getInt("hours_per_week"));
    }

    private void bindFaculty(PreparedStatement ps, Faculty faculty) throws SQLException {
        ps.setInt(1, faculty.getFacultyId());
        ps.setString(2, faculty.getFacultyName());
        ps.setString(3, faculty.getDepartment());
        ps.setString(4, faculty.getSubject());
        ps.setInt(5, faculty.getHoursPerWeek());
    }
}
