package course_transfer_java.Presentation;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import course_transfer_java.Application.Services;
import course_transfer_java.Logic.CourseHandler;
import course_transfer_java.Logic.CourseHandlerInterface;
import course_transfer_java.Objects.CourseEntry;

public class CoursePresentation extends JFrame {
    final private Font mainFont = new Font("Times New Roman", Font.BOLD, 18);
    private CourseHandlerInterface courseHandler;

    public void initialize() {
        /* setup section */
        // get the logic functions needed
        courseHandler = new CourseHandler(Services.USING_STUB);

        // get the info for the umanitoba courses to go into the drop down
        java.util.List<String> UofMCourses = courseHandler.getUmanitobaCourseNames();

        // the window
        JFrame frame = new JFrame("Umanitoba Course Panel");
        frame.setSize(500, 600);
        frame.setMinimumSize(new Dimension(300, 400));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Create the container that lets us switch panels
        // Create CardLayout and its container
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        // column names for UofM
        String[] columnNames = { "Other Instituition Name", "Other Instituition Course", "Umanitoba Course", "Comments",
                "Effective Date" };

        /* main panel */
        JPanel card1 = new JPanel(new BorderLayout());
        JComboBox<String> dropdown = new JComboBox<>();
        for (int i = 0; i < UofMCourses.size(); i++) {
            dropdown.addItem(UofMCourses.get(i));
        }
        JButton goButton = new JButton("Go");
        JPanel dropdownPanel = new JPanel();
        dropdownPanel.add(new JLabel("Select an option:"));
        dropdownPanel.add(dropdown);
        dropdownPanel.add(goButton);
        card1.add(dropdownPanel, BorderLayout.CENTER);

        /* secondary panel */
        // Secondary panel to display selected option
        JPanel card2 = new JPanel(new BorderLayout());
        JLabel tableLabel = new JLabel("Table for: ", SwingConstants.CENTER);
        JTable table = new JTable(new String[0][0], columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        card2.add(tableLabel, BorderLayout.NORTH);
        card2.add(scrollPane, BorderLayout.CENTER);
        JButton backButton = new JButton("Back to Menu");
        card2.add(backButton, BorderLayout.SOUTH);

        // Add pages to the CardLayout container
        cardPanel.add(card1, "MainPanel");
        cardPanel.add(card2, "TablePanel");

        // Add action listeners
        goButton.addActionListener(e -> {
            // Get the selected option
            String selectedOption = (String) dropdown.getSelectedItem();
            if (selectedOption != null) {
                // Fetch table data dynamically using the helper function
                java.util.List<CourseEntry> correlatedOtherCourses = courseHandler
                        .getSpecificCourseEntries(selectedOption);
                String[][] tableData = new String[correlatedOtherCourses.size()][5];
                for (int i = 0; i < correlatedOtherCourses.size(); i++) {
                    tableData[i][0] = correlatedOtherCourses.get(i).getOtherInstituitionName();
                    tableData[i][1] = correlatedOtherCourses.get(i).getOtherInstituitionCourse();
                    tableData[i][2] = correlatedOtherCourses.get(i).getUmanitobaCourse();
                    tableData[i][3] = correlatedOtherCourses.get(i).getComments();
                    tableData[i][4] = correlatedOtherCourses.get(i).getEffectiveDate();
                }

                if (tableData != null) {
                    // Update the table data and label dynamically
                    table.setModel(new javax.swing.table.DefaultTableModel(tableData, columnNames));
                    tableLabel.setText("Table for: " + selectedOption);
                    cardLayout.show(cardPanel, "TablePanel");
                }
            }
        });

        backButton.addActionListener(e -> cardLayout.show(cardPanel, "MainPanel"));

        // Add components to the frame
        frame.add(cardPanel, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }

}
