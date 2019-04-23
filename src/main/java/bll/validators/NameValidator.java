package bll.validators;

import java.util.regex.Pattern;

/**
 * This class is one of the many validator classes that were created in order to
 * make sure that the input will always be fulfilling the requirements.
 */

public class NameValidator {
    private static final String NAME_PATTERN = "^(\\(?\\+?[A-z]*\\)?)?[A-z_\\- ()]*$";

    /**
     * Uses the string NAME_PATTERN (a regex expression) in order to determine if the
     * given string has only letters (from A to z) representing a valid name.
     *
     * @param s is the string to be checked if it has a valid name.
     * @return a boolean. True if the string has a valid name format, false otherwise.
     */
    public static Boolean validate(String s) {
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        return pattern.matcher(s).matches();
    }

}
