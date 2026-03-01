package Student;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    StudentService service = new StudentService();


    @Test
    void testGradeCalculation() {

        assertEquals("Distinction", service.calculateGrade(80));
        assertEquals("First Class", service.calculateGrade(65));
        assertEquals("Second Class", service.calculateGrade(55));
        assertEquals("Fail", service.calculateGrade(40));
    }


    @Test
    void testPassFailStatus() {

        assertTrue(service.isPassed(75));
        assertFalse(service.isPassed(45));
    }


    @Test
    void testInvalidMarks() {

        assertThrows(IllegalArgumentException.class,
                () -> service.calculateGrade(-10));

        assertThrows(IllegalArgumentException.class,
                () -> service.calculateGrade(120));
    }


    @Test
    void testNonNullResponse() {

        String result = service.calculateGrade(70);
        assertNotNull(result);
    }
}
