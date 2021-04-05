package com.company.caclulator;

import java.util.List;

public class Validator {

    public Validator() {
    }

    public boolean isContainsNotAllowedSymbols(String expression) {
        return !expression.matches("^[*+/.\\-\\d\\s]+$");
    }

    public boolean isNotStartWithNumber(String expression) {
        try {
            Double.parseDouble(expression.split("[/*\\-+\\s]")[0]);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    public boolean isNotEndWithNumber(String expression) {
        try {
            String lastNumber = expression.substring(expression.split("[.\\d]+$")[0].length());
            Double.parseDouble(lastNumber);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    public boolean isNotCorrectOperations(List<String> operations) {
        return operations.stream().anyMatch(s -> s.length() > 1);
    }

    public boolean isNotCorrectNumbers(List<String> numbers) {
        try {
            numbers.forEach(Double::parseDouble);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }
}
