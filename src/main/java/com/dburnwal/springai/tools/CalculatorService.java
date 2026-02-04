
package com.dburnwal.springai.tools;

import org.springframework.stereotype.Component;
import org.springframework.ai.tool.annotation.Tool;

@Component

public class CalculatorService {

    // Request classes for function calling
    public record CalculationRequest(double a, double b) {}

    public record SingleNumberRequest(double number) {}


    /**
     * Add two numbers together
     * 
     * @param a First number
     * @param b Second number
     * @return Sum of a and b
     */
    @Tool(description = "Add two numbers together")
    public double add(double a, double b) {
        return a + b;
    }

    /**
     * Subtract second number from first number
     * 
     * @param a First number
     * @param b Second number
     * @return Difference of a and b
     */
    @Tool(description = "Subtract second number from first number")
    public double subtract(double a, double b) {
        return a - b;
    }

    /**
     * Multiply two numbers
     * 
     * @param a First number
     * @param b Second number
     * @return Product of a and b
     */
    @Tool(description = "Multiply two numbers")
    public double multiply(double a, double b) {
        return a * b;
    }

    /**
     * Divide first number by second number
     * 
     * @param a First number (dividend)
     * @param b Second number (divisor)
     * @return Quotient of a and b
     * @throws ArithmeticException if b is zero
     */
    @Tool(description = "Divide first number by second number")
    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }

    /**
     * Calculate the power of a number
     * 
     * @param base The base number
     * @param exponent The exponent
     * @return base raised to the power of exponent
     */
    @Tool(description = "Calculate the power of a number")
    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    /**
     * Calculate the square root of a number
     * 
     * @param number The number to calculate the square root of
     * @return Square root of the number
     * @throws IllegalArgumentException if number is negative
     */
    @Tool(description = "Calculate the square root of a number")
    public double squareRoot(double number) {
        if (number < 0) {
            throw new IllegalArgumentException("Cannot calculate square root of negative number");
        }
        return Math.sqrt(number);
    }
}
