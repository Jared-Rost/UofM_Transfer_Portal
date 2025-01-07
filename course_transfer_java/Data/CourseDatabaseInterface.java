package course_transfer_java.Data;

import java.util.List;

import course_transfer_java.Objects.CourseEntry;

public interface CourseDatabaseInterface {
    public List<CourseEntry> getAllCourses();

    public List<String> getUmanitobaCourseNames();
}
