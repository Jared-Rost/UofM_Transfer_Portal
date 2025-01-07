package course_transfer_java.Data;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import course_transfer_java.Application.Services;
import course_transfer_java.Objects.CourseEntry;

public class CourseDatabaseSQL implements CourseDatabaseInterface {

    @Override
    public List<CourseEntry> getAllCourses() {
        try (Connection conn = DriverManager.getConnection(Services.DB_URL)) {
            if (conn != null) {
                List<CourseEntry> allCourses = new ArrayList<>();
                Statement stmt = conn.createStatement();

                // Query the table
                String querySQL = "SELECT * FROM courses;";
                ResultSet rs = stmt.executeQuery(querySQL);
                while (rs.next()) {
                    allCourses.add(new CourseEntry(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                            rs.getString(5)));
                }

                return allCourses;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public List<String> getUmanitobaCourseNames() {
        try (Connection conn = DriverManager.getConnection(Services.DB_URL)) {
            if (conn != null) {
                List<String> allNames = new ArrayList<>();
                Statement stmt = conn.createStatement();

                // Query the table
                String querySQL = "SELECT * FROM courses;";
                ResultSet rs = stmt.executeQuery(querySQL);
                while (rs.next()) {
                    allNames.add(rs.getString(3));
                }

                return allNames;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
