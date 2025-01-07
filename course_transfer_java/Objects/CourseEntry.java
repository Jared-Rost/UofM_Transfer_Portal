package course_transfer_java.Objects;

public class CourseEntry {
    String otherInstituitionName;
    String otherInstituitionCourse;
    String umanitobaCourse;
    String comments;
    String effectiveDate;

    public CourseEntry(String otherInstituitionName, String otherInstituitionCourse, String umanitobaCourse,
            String comments, String effectiveDate) {
        this.otherInstituitionName = otherInstituitionName;
        this.otherInstituitionCourse = otherInstituitionCourse;
        this.umanitobaCourse = umanitobaCourse;
        this.comments = comments;
        this.effectiveDate = effectiveDate;
    }

    public String getOtherInstituitionName() {
        return otherInstituitionName;
    }

    public String getOtherInstituitionCourse() {
        return otherInstituitionCourse;
    }

    public String getUmanitobaCourse() {
        return umanitobaCourse;
    }

    public String getComments() {
        return comments;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }
}
