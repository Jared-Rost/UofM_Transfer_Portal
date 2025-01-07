package course_transfer_java.Application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import course_transfer_java.Data.CourseDatabaseInterface;
import course_transfer_java.Data.CourseDatabaseStub;
import course_transfer_java.Objects.CourseEntry;
import course_transfer_java.Data.CourseDatabaseSQL;

public class Services {
    private static CourseDatabaseInterface courseDatabase = null;
    public static boolean USING_STUB = false;
    public static String DB_URL = "jdbc:sqlite:course_transfer_java/Application/lib/courseDB.db"; // Path to SQLite
                                                                                                  // database
    private static final String COMMA_DELIMITER = ",";
    private static final String csv_file = "course_transfer_java/Application/lib/Output.csv";

    public static synchronized CourseDatabaseInterface getDatabaseInstance(boolean useStub) {
        if (courseDatabase != null) {
            return courseDatabase;
        } else if (useStub) {
            courseDatabase = new CourseDatabaseStub();
            return courseDatabase;
        } else {
            courseDatabase = new CourseDatabaseSQL();
            return courseDatabase;
        }
    }

    public static List<CourseEntry> readInCourses() {
        // scan in csv file and store info
        List<List<String>> entries = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(csv_file));
            while (scanner.hasNextLine()) {
                entries.add(getRecordFromLine(scanner.nextLine()));
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Problem occurred " + e.getMessage());
        }

        // now convert it to objects
        List<CourseEntry> courseEntryList = new ArrayList<CourseEntry>();
        for (int i = 0; i < entries.size(); i++) {
            CourseEntry newEntry;
            if (entries.get(i).size() == 3) {
                newEntry = new CourseEntry(entries.get(i).get(0), entries.get(i).get(1), entries.get(i).get(2),
                        "\"NA\"", "\"unknown\"");
            } else {
                if ((int) entries.get(i).get(3).charAt(0) == 160) {
                    newEntry = new CourseEntry(entries.get(i).get(0), entries.get(i).get(1), entries.get(i).get(2),
                            "\"NA\"", entries.get(i).get(4));
                } else {
                    newEntry = new CourseEntry(entries.get(i).get(0), entries.get(i).get(1), entries.get(i).get(2),
                            entries.get(i).get(3), entries.get(i).get(4));
                }
            }
            courseEntryList.add(newEntry);
        }

        return courseEntryList;
    }

    private static List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }
}
