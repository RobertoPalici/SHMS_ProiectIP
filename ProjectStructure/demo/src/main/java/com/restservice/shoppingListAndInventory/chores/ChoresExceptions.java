package com.restservice.shoppingListAndInventory.chores;

import org.springframework.scripting.ScriptEvaluator;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class ChoresExceptions {
    String number;
    String string;
    public ChoresExceptions(String str) {
        this.number=str;
        try {
            int personID = parseInt(this.number);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }
    public int parseInt(String str) throws NumberFormatException {
        try {
            // Attempt to parse the string to an integer
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            // If parsing fails, throw a custom NumberFormatException with a descriptive message
            throw new NumberFormatException("Failed to parse integer from string: " + str);
        }
    }
}
