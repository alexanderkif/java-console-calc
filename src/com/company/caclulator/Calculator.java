package com.company.caclulator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Calculator {

    private static final int SCALE = 3;

    private final Console console;
    private final Validator validator;

    public Calculator() {
        console = new Console();
        validator = new Validator();
        do {
            runCalculatorSession();
        } while (!console.getExit());
        console.write("Calculator stopped.");
    }

    private void runCalculatorSession() {
        String expression = console.getExpression();

        if (validator.isContainsNotAllowedSymbols(expression)) {
            console.write("Expression contains not allowed symbols.");
            return;
        }
        if (validator.isNotStartWithNumber(expression)) {
            console.write("Expression must start with number.");
            return;
        }
        if (validator.isNotEndWithNumber(expression)) {
            console.write("Expression must end with number.");
            return;
        }

        List<String> operations = Arrays.stream(expression.split("[.\\d\\s]+"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
        if (validator.isNotCorrectOperations(operations)) {
            console.write("Expression is wrong. Check operators.");
            console.write("operations: " + operations);
            return;
        }

        List<String> numbers = Arrays.stream(expression.split("[/*\\-+\\s]+"))
                .collect(Collectors.toList());
        if (validator.isNotCorrectNumbers(numbers)) {
            console.write("Expression is wrong. Check numbers.");
            console.write("numbers: " + numbers);
            return;
        }

        List<BigDecimal> decimals = numbers.stream()
                .map((String t) -> BigDecimal.valueOf(Double.parseDouble(t)))
                .collect(Collectors.toList());

        console.writeExpression(decimals,operations);

        try {
            while (!operations.isEmpty()) {
                int indexFirstOperation = getIndexFirstOperation(operations);
                BigDecimal currentResult = null;
                switch (operations.get(indexFirstOperation)) {
                    case "*":
                        currentResult = decimals.get(indexFirstOperation)
                                .multiply(decimals.get(indexFirstOperation + 1));
                        break;
                    case "/":
                        currentResult = decimals.get(indexFirstOperation)
                                .divide(decimals.get(indexFirstOperation + 1), SCALE, RoundingMode.HALF_UP);
                        break;
                    case "+":
                        currentResult = decimals.get(indexFirstOperation)
                                .add(decimals.get(indexFirstOperation + 1));
                        break;
                    case "-":
                        currentResult = decimals.get(indexFirstOperation)
                                .subtract(decimals.get(indexFirstOperation + 1));
                        break;
                }
                operations.remove(indexFirstOperation);
                decimals.set(indexFirstOperation, currentResult);
                decimals.remove(indexFirstOperation + 1);

                console.writeExpression(decimals, operations);
            }
        } catch (ArithmeticException e) {
            console.write("Wrong arithmetic operation: " + e.toString().split(":")[1]);
        }

    }

    private int getIndexFirstOperation(List<String> operations) {
        if (operations.contains("*") || operations.contains("/")) {
            if (!operations.contains("*")) return operations.indexOf("/");
            if (!operations.contains("/")) return operations.indexOf("*");
            return Math.min(operations.indexOf("*"), operations.indexOf("/"));
        } else {
            if (!operations.contains("+")) return operations.indexOf("-");
            if (!operations.contains("-")) return operations.indexOf("+");
            return Math.min(operations.indexOf("+"), operations.indexOf("-"));
        }
    }
}
