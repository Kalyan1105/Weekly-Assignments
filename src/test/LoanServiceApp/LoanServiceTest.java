package LoanServiceApp;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class LoanServiceTest {
    LoanService E;
    @BeforeEach
    void setUp() {
        E=new LoanService();
    }
    @Test
    void ValidEligibility() {
        assertTrue(E.isEligible(35, 30000));
    }
    @Test
    void InvalidAge() {
        assertFalse(E.isEligible(18, 30000));
    }
    @Test
    void InvalidSalary() {
        assertFalse(E.isEligible(30, 20000));
    }
    @Test
    void ValidEMI() {
        assertEquals(2000.0, E.calculateEMI(24000, 1));
    }
    @Test
    void InvalidLoanAmount() {
        assertThrows(IllegalArgumentException.class, () -> E.calculateEMI(0, 1));
    }
    @Test
    void InvalidTenure() {
        assertThrows(IllegalArgumentException.class, () -> E.calculateEMI(10000, 0));
    }
    @Test
    void AllCreditCategories() {
        assertAll(
                () -> assertEquals("Premium", E.getLoanCategory(800)),
                () -> assertEquals("Standard", E.getLoanCategory(700)),
                () -> assertEquals("High Risk", E.getLoanCategory(500))
        );
    }
    @Test
    void BoundaryAge() {
        assertTrue(E.isEligible(21, 25000));
        assertTrue(E.isEligible(60, 25000));
    }

    @Test
    void CategoryNotNull() {
        assertNotNull(E.getLoanCategory(750));
    }

    @Test
    void CategoryNUll(){
        assertNull(E.getLoanCategory(-1));
    }


}

























