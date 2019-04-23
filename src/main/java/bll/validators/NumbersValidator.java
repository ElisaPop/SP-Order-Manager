package bll.validators;

import java.util.regex.Pattern;

/**
 * This class is one of the many validator classes that were created in order to
 * make sure that the input will always be fulfilling the requirements.
 */

public class NumbersValidator {
    private static final String NUMBER_PATTERN = "^(\\(?\\+?[0-9]*\\)?)?[0-9_\\- ()]*$";

    /**
     * Uses the string NUMBER_PATTERN (a regex expression) in order to determine if the
     * given string has only digits (from 0 to 9).
     *
     * @param s is the string to be checked if it is a number.
     * @return a boolean. True if the string has only digits, false otherwise.
     */
    public static boolean validate(String s) {
        Pattern pattern = Pattern.compile(NUMBER_PATTERN);
        return pattern.matcher(s).matches();
    }
}
