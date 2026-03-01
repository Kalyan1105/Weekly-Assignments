package UniversityManagementSystem;

import jakarta.persistence.*;

@Entity
@Table(name = "studentid")
public class StudentID {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String cardNumber;

    @OneToOne(mappedBy = "studentID")
    private Student student;

    public StudentID() {}   // Required

    public StudentID(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}