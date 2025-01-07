package course_transfer_java.Data;

import java.io.*;
import java.util.*;

import course_transfer_java.Application.Services;
import course_transfer_java.Objects.CourseEntry;

public class CourseDatabaseStub implements CourseDatabaseInterface {

    private List<CourseEntry> courseEntryList;

    public CourseDatabaseStub() {
        courseEntryList = Services.readInCourses();
    }

    @Override
    public List<CourseEntry> getAllCourses() {
        return courseEntryList;
    }

    @Override
    public List<String> getUmanitobaCourseNames() {
        // scan in csv file and store info
        List<String> umanitobaNames = new ArrayList<>();

        for (CourseEntry current : courseEntryList) {
            umanitobaNames.add(current.getUmanitobaCourse());
        }

        return umanitobaNames;
    }

}
