package edu.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {
    private final Calculator calculator = new Calculator();

    @Test
    void addsTwoNumbers() {
        assertEquals(5.0, calculator.add(2, 3), 0.000001);
    }

    @Test
    void subtractsTwoNumbers() {
        assertEquals(5.0, calculator.subtract(8, 3), 0.000001);
    }

    @Test
    void multipliesNegativeNumber() {
        assertEquals(-12.0, calculator.multiply(-3, 4), 0.000001);
    }

    @Test
    void dividesWithFraction() {
        assertEquals(2.5, calculator.divide(5, 2), 0.000001);
    }

    @Test
    void addsDecimalNumbers() {
        assertEquals(0.3, calculator.add(0.1, 0.2), 0.000001);
    }

    @Test
    void rejectsDivisionByZero() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.divide(10, 0));
    }
}
