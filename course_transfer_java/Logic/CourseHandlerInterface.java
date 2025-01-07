package course_transfer_java.Logic;

import java.util.List;

import course_transfer_java.Objects.CourseEntry;

public interface CourseHandlerInterface {
    public List<CourseEntry> getAllCourseEntries();

    public List<String> getUmanitobaCourseNames();

    public List<CourseEntry> getSpecificCourseEntries(String umanitobaCourse);
}
