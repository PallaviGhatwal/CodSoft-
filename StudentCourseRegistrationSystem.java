import java.util.ArrayList;
import java.util.Scanner;

class Course {
    private final String code;
    private final String title;
    private final String description;
    private final int capacity;
    private final String schedule;
    private int enrolled;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolled = 0;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public void enrollStudent() {
        if (!isFull()) {
            enrolled++;
        }
    }

    public void dropStudent() {
        if (enrolled > 0) {
            enrolled--;
        }
    }

    public boolean isFull() {
        return enrolled >= capacity;
    }
}

class Student {
    private final int id;
    private final String name;
    private final ArrayList<String> registeredCourseCodes;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourseCodes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getRegisteredCourseCodes() {
        return registeredCourseCodes;
    }

    public void registerCourse(String courseCode) {
        if (!registeredCourseCodes.contains(courseCode)) {
            registeredCourseCodes.add(courseCode);
        }
    }

    public void dropCourse(String courseCode) {
        registeredCourseCodes.remove(courseCode);
    }
}

public class StudentCourseRegistrationSystem {
    private final ArrayList<Course> courses;
    private final ArrayList<Student> students;
    private final Scanner scanner;

    public StudentCourseRegistrationSystem() {
        courses = new ArrayList<>();
        students = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    private void addCourse() {
        System.out.println("Enter course code:");
        String code = scanner.nextLine();
        System.out.println("Enter course title:");
        String title = scanner.nextLine();
        System.out.println("Enter course description:");
        String description = scanner.nextLine();
        System.out.println("Enter course capacity:");
        int capacity = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter course schedule:");
        String schedule = scanner.nextLine();

        courses.add(new Course(code, title, description, capacity, schedule));
        System.out.println("Course added successfully!");
    }

    private void displayCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courses) {
            System.out.println("Course Code: " + course.getCode());
            System.out.println("Title: " + course.getTitle());
            System.out.println("Description: " + course.getDescription());
            System.out.println("Capacity: " + course.getCapacity());
            System.out.println("Enrolled: " + course.getEnrolled());
            System.out.println("Schedule: " + course.getSchedule());
            System.out.println("-----------------------");
        }
    }

    private void registerStudent() {
        System.out.println("Enter student ID:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter student name:");
        String name = scanner.nextLine();

        students.add(new Student(id, name));
        System.out.println("Student registered successfully!");
    }

    private void registerCourseForStudent() {
        displayCourses();
        System.out.println("Enter course code to register:");
        String code = scanner.nextLine();

        Course course = findCourseByCode(code);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (course.isFull()) {
            System.out.println("Course is full.");
            return;
        }

        System.out.println("Enter student ID:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        student.registerCourse(course.getCode());
        course.enrollStudent();
        System.out.println("Student " + student.getName() + " successfully registered for course " + course.getTitle() + ".");
    }

    private void dropCourseForStudent() {
        System.out.println("Enter student ID:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Registered Courses:");
        for (String courseCode : student.getRegisteredCourseCodes()) {
            System.out.println(courseCode);
        }

        System.out.println("Enter course code to drop:");
        String code = scanner.nextLine();

        Course course = findCourseByCode(code);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (!student.getRegisteredCourseCodes().contains(code)) {
            System.out.println("Student is not registered for this course.");
            return;
        }

        student.dropCourse(code);
        course.dropStudent();
        System.out.println("Course dropped successfully.");
    }

    private Course findCourseByCode(String code) {
        for (Course course : courses) {
            if (course.getCode().equalsIgnoreCase(code)) {
                return course;
            }
        }
        return null;
    }

    private Student findStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public void startSystem() {
        int choice;
        do {
            System.out.println("\nStudent Course Registration System");
            System.out.println("1. Add Course");
            System.out.println("2. Display Available Courses");
            System.out.println("3. Register Student");
            System.out.println("4. Register Course for Student");
            System.out.println("5. Drop Course for Student");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addCourse();
                case 2 -> displayCourses();
                case 3 -> registerStudent();
                case 4 -> registerCourseForStudent();
                case 5 -> dropCourseForStudent();
                case 0 -> System.out.println("Exiting system...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    public static void main(String[] args) {
        StudentCourseRegistrationSystem system = new StudentCourseRegistrationSystem();
        system.startSystem();
    }
}
