import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class Student {
    private String name;
    private ArrayList<Double> grades;

    public Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addGrade(double grade) {
        grades.add(grade);
    }

    public ArrayList<Double> getGrades() {
        return grades;
    }
}

public class StudentGradeTrackerGUI extends JFrame {
    private JTextField nameField;
    private JTextField gradeField;
    private JButton addButton;
    private JButton avgButton;
    private JButton highestButton;
    private JButton lowestButton;
    private JTable table;
    private DefaultTableModel tableModel;

    private Map<String, Student> studentMap = new HashMap<>();

    public StudentGradeTrackerGUI() {
        setTitle("Student Grade Tracker");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Top panel: Inputs and Add Grade button
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField(10);
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField(5);
        inputPanel.add(gradeField);

        addButton = new JButton("Add Grade");
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        // Center: Table
        String[] columnNames = {"Student Name", "Grades"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom panel: Average, Highest, Lowest buttons
        JPanel statsPanel = new JPanel(new FlowLayout());
        avgButton = new JButton("Average");
        highestButton = new JButton("Highest");
        lowestButton = new JButton("Lowest");

        statsPanel.add(avgButton);
        statsPanel.add(highestButton);
        statsPanel.add(lowestButton);

        add(statsPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> addGrade());
        avgButton.addActionListener(e -> showOverallAverage());
        highestButton.addActionListener(e -> showOverallHighest());
        lowestButton.addActionListener(e -> showOverallLowest());
    }

    private void addGrade() {
        String name = nameField.getText().trim();
        String gradeText = gradeField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a student name.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double grade;
        try {
            grade = Double.parseDouble(gradeText);
            if (grade < 0 || grade > 100) {
                JOptionPane.showMessageDialog(this, "Grade must be between 0 and 100.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric grade.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student student = studentMap.get(name.toLowerCase());
        if (student == null) {
            student = new Student(name);
            studentMap.put(name.toLowerCase(), student);
        }
        student.addGrade(grade);
        updateTable();

        // Clear inputs
        nameField.setText("");
        gradeField.setText("");
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Student s : studentMap.values()) {
            String gradesStr = s.getGrades().toString();
            tableModel.addRow(new Object[]{s.getName(), gradesStr});
        }
    }

    private void showOverallAverage() {
        if (studentMap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No grades available.", "Average Grade", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        double sum = 0;
        int count = 0;
        for (Student s : studentMap.values()) {
            for (double g : s.getGrades()) {
                sum += g;
                count++;
            }
        }
        if (count == 0) {
            JOptionPane.showMessageDialog(this, "No grades available.", "Average Grade", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        double average = sum / count;
        JOptionPane.showMessageDialog(this, String.format("Overall Average Grade: %.2f", average), "Average Grade", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showOverallHighest() {
        if (studentMap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No grades available.", "Highest Grade", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Double max = null;
        ArrayList<String> topStudents = new ArrayList<>();
        for (Student s : studentMap.values()) {
            for (double g : s.getGrades()) {
                if (max == null || g > max) {
                    max = g;
                    topStudents.clear();
                    topStudents.add(s.getName());
                } else if (g == max) {
                    if (!topStudents.contains(s.getName())) {
                        topStudents.add(s.getName());
                    }
                }
            }
        }
        if (max == null) {
            JOptionPane.showMessageDialog(this, "No grades available.", "Highest Grade", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String names = String.join(", ", topStudents);
        JOptionPane.showMessageDialog(this,
            String.format("Overall Highest Grade: %.2f\nStudent(s): %s", max, names),
            "Highest Grade",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void showOverallLowest() {
        if (studentMap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No grades available.", "Lowest Grade", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Double min = null;
        ArrayList<String> bottomStudents = new ArrayList<>();
        for (Student s : studentMap.values()) {
            for (double g : s.getGrades()) {
                if (min == null || g < min) {
                    min = g;
                    bottomStudents.clear();
                    bottomStudents.add(s.getName());
                } else if (g == min) {
                    if (!bottomStudents.contains(s.getName())) {
                        bottomStudents.add(s.getName());
                    }
                }
            }
        }
        if (min == null) {
            JOptionPane.showMessageDialog(this, "No grades available.", "Lowest Grade", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String names = String.join(", ", bottomStudents);
        JOptionPane.showMessageDialog(this,
            String.format("Overall Lowest Grade: %.2f\nStudent(s): %s", min, names),
            "Lowest Grade",
            JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentGradeTrackerGUI().setVisible(true);
        });
    }
}

