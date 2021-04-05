package com.company.caclulator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Console {

    private final Scanner scanner;

    public Console() {
        scanner = new Scanner(System.in);
        greeting();
    }

    public void greeting() {
        System.out.println("************************");
        System.out.println("*  Console Calculator  *");
        System.out.println("************************");
        System.out.println();
    }

    public String getExpression() {
        System.out.print("Write your expression with +, -, *, / and decimal point: ");
        return scanner.nextLine();
    }

    public void write(String outString) {
        System.out.println(outString);
    }

    public boolean getExit() {
        System.out.print("Type \"exit\" to stop calculator or something else to try again: ");
        return scanner.nextLine().equals("exit");
    }

    public void writeExpression(List<BigDecimal> decimals, List<String> operators) {
        IntStream.range(0, operators.size())
                .forEach(i -> {
                    System.out.print(decimals.get(i).toString() + " " + operators.get(i) + " ");
                });
        System.out.println(decimals.get(operators.size()).toString());
    }
}
