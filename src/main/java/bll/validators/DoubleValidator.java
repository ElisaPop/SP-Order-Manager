package bll.validators;

import java.util.regex.Pattern;

/**
 * This class is one of the many validator classes that were created in order to
 * make sure that the input will always be fulfilling the requirements.
 */

public class DoubleValidator {
    private static final String NUMBER_PATTERN = "[-+]?[0-9]*\\.?[0-9]+";

    /**
     * Uses the string NUMBER_PATTERN (a regex expression)in order to determine
     * if the given string is a floating point number or not.
     *
     * @param s is the string to be checked if it is a floating point number or not.
     * @return a boolean. True if the string has the form of a floating point number, false otherwise.
     */
    public static boolean validate(String s) {
        Pattern pattern = Pattern.compile(NUMBER_PATTERN);
        return pattern.matcher(s).matches();
    }
}

