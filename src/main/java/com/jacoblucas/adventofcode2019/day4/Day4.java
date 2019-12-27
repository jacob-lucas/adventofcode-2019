package com.jacoblucas.adventofcode2019.day4;

public class Day4 {
    public static void main(String[] args) {
        final String input = "236491-713787";
        final PasswordGuesser passwordGuesser = new PasswordGuesser(
                Integer.parseInt(input.split("-")[0]),
                Integer.parseInt(input.split("-")[1]));

        final int validInRange = passwordGuesser.validInRange();
        System.out.println(validInRange);
    }
}
