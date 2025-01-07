package course_transfer_java.Logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import course_transfer_java.Application.Services;
import course_transfer_java.Objects.CourseEntry;
import course_transfer_java.Data.CourseDatabaseInterface;

public class CourseHandler implements CourseHandlerInterface {
    CourseDatabaseInterface courseDatabase;

    public CourseHandler(boolean useStub) {
        courseDatabase = Services.getDatabaseInstance(useStub);
    }

    @Override
    public List<CourseEntry> getAllCourseEntries() {
        return courseDatabase.getAllCourses();
    }

    @Override
    public List<CourseEntry> getSpecificCourseEntries(String umanitobaCourse) {
        List<CourseEntry> allEntries = courseDatabase.getAllCourses();
        List<CourseEntry> specificEntries = new ArrayList<>();

        for (CourseEntry current : allEntries) {
            String xxxRegex = "\\dXXX";

            // check to see if current umanitoba course is xxx or an exact number
            if (containsPattern(umanitobaCourse, xxxRegex)) {
                String regex = umanitobaCourse.substring(0, 5) + " " + umanitobaCourse.charAt(5) + "\\d{3}";

                if (containsPattern(current.getUmanitobaCourse(), regex)
                        || containsPattern(current.getUmanitobaCourse(), umanitobaCourse)) {
                    specificEntries.add(current);
                }
            } else {
                String regex = umanitobaCourse.substring(0, 5) + " " + umanitobaCourse.charAt(5) + "XXX";

                if (containsPattern(current.getUmanitobaCourse(), regex)
                        || containsPattern(current.getUmanitobaCourse(), umanitobaCourse)) {
                    specificEntries.add(current);
                }
            }
        }

        return specificEntries;
    }

    private static boolean containsPattern(String text, String regex) {
        // Compile the regular expression pattern
        Pattern pattern = Pattern.compile(regex);

        // Create a matcher for the given text
        Matcher matcher = pattern.matcher(text);

        // Check if the string contains the pattern
        return matcher.find();
    }

    @Override
    public List<String> getUmanitobaCourseNames() {
        List<String> allNames = courseDatabase.getUmanitobaCourseNames();
        HashSet<String> set = new HashSet<>(); // use hashset to ensure uniqueness

        // now we want to make sure all of them are unique and that there is no combo
        for (String current : allNames) {
            if (!current.equals("NO CREDIT")) {
                // regular expression
                String regex = "[A-Z]{4} \\d{4}";

                // Compile the pattern
                Pattern pattern = Pattern.compile(regex);

                // Create a matcher for the input string
                Matcher matcher = pattern.matcher(current);

                // Find all matches
                while (matcher.find()) {
                    // Add each match to the list
                    set.add(matcher.group());
                }

                // now, check for XXX instead
                regex = "[A-Z]{4} \\dXXX";

                // Compile the pattern
                pattern = Pattern.compile(regex);

                // Create a matcher for the input string
                matcher = pattern.matcher(current);

                // Find all matches
                while (matcher.find()) {
                    // Add each match to the list
                    set.add(matcher.group());
                }

                /*
                 * String[] courses = current.split("&");
                 * for (String currentCourse : courses) {
                 * set.add(currentCourse.trim());
                 * }
                 */
            }
        }

        return new ArrayList<>(set);
    }
}
