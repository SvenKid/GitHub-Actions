package edu.demo;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: java -jar calculator-1.0.0.jar <add|sub|mul|div> <a> <b>");
            System.exit(1);
        }
        try {
            double a = Double.parseDouble(args[1]);
            double b = Double.parseDouble(args[2]);
            if (!Double.isFinite(a) || !Double.isFinite(b)) {
                throw new IllegalArgumentException("Please enter finite numbers.");
            }
            Calculator calculator = new Calculator();
            double result = switch (args[0]) {
                case "add" -> calculator.add(a, b);
                case "sub" -> calculator.subtract(a, b);
                case "mul" -> calculator.multiply(a, b);
                case "div" -> calculator.divide(a, b);
                default -> throw new IllegalArgumentException("Unknown operation: " + args[0]);
            };
            System.out.println("Result: " + result);
        } catch (IllegalArgumentException exception) {
            System.err.println("Error: " + exception.getMessage());
            System.exit(1);
        }
    }
}
