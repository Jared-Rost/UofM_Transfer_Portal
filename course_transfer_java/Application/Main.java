package course_transfer_java.Application;

import course_transfer_java.Objects.CourseEntry;
import course_transfer_java.Presentation.CoursePresentation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (!Services.USING_STUB) {
            setUpDatabase();
        }

        CoursePresentation start = new CoursePresentation();
        start.initialize();
    }

    private static void setUpDatabase() {
        try (Connection conn = DriverManager.getConnection(Services.DB_URL)) {

            if (conn != null) {
                /*
                 * // Create a new table
                 * String createTableSQL = "CREATE TABLE IF NOT EXISTS courses ("
                 * + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                 * + "otherInstituitionName TEXT, "
                 * + "otherInstituitionCourse TEXT,"
                 * + "umanitobaCourse TEXT,"
                 * + "comments TEXT,"
                 * + "effectiveDate TEXT,"
                 * + ");";
                 */
                String dropTableSQL = "DELETE FROM courses;";
                Statement stmt = conn.createStatement();
                stmt.execute(dropTableSQL);
                // stmt.execute(createTableSQL);

                List<CourseEntry> allCourses = Services.readInCourses();
                for (CourseEntry current : allCourses) {
                    String courseSQL = String.format(
                            "INSERT INTO courses (otherInstituitionName, otherInstituitionCourse, umanitobaCourse, comments, effectiveDate)"
                                    + "VALUES (%s, %s, %s, %s, %s)",
                            current.getOtherInstituitionName(), current.getOtherInstituitionCourse(),
                            current.getUmanitobaCourse(), current.getComments(), current.getEffectiveDate());
                    System.out.println(courseSQL);
                    stmt.execute(courseSQL);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}