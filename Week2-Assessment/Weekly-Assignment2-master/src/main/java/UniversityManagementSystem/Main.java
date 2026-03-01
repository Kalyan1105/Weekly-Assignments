package UniversityManagementSystem;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        cfg.addAnnotatedClass(Department.class);
        cfg.addAnnotatedClass(Student.class);
        cfg.addAnnotatedClass(StudentID.class);
        cfg.addAnnotatedClass(Course.class);

        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();

        Transaction tx = session.beginTransaction();


        System.out.print("Enter Department Name: ");
        String deptName = sc.nextLine();

        Department dept = new Department(deptName);


        System.out.print("Enter Number of Students: ");
        int n = sc.nextInt();
        sc.nextLine();

        Student[] students = new Student[n];

        for (int i = 0; i < n; i++) {

            System.out.println("\nStudent " + (i + 1));

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter ID Card Number: ");
            String card = sc.nextLine();

            Student s = new Student();
            s.setName(name);

            StudentID id = new StudentID(card);

            s.setStudentID(id);
            id.setStudent(s);

            dept.addStudent(s);

            students[i] = s;
        }

        // Courses
        System.out.print("\nEnter Number of Courses: ");
        int c = sc.nextInt();
        sc.nextLine();

        Course[] courses = new Course[c];

        for (int i = 0; i < c; i++) {

            System.out.print("Enter Course " + (i + 1) + " Name: ");
            String cname = sc.nextLine();

            courses[i] = new Course(cname);
        }

        // Enrollment
        for (int i = 0; i < n; i++) {

            System.out.println("\nEnroll Courses for " + students[i].getName());

            for (int j = 0; j < c; j++) {

                System.out.print("Enroll in " + courses[j].getCourseName() + " ? (yes/no): ");
                String ans = sc.nextLine();

                if (ans.equalsIgnoreCase("yes")) {
                    students[i].addCourse(courses[j]);
                }
            }
        }

        // Save
        session.persist(dept);

        for (Course course : courses) {
            session.persist(course);
        }

        tx.commit();

        session.close();
        factory.close();
        sc.close();

        System.out.println("\nData Saved Successfully!");
    }
}