package in.yagnyam.myid.utils;


/**
 * String Utility methods
 */
public class StringUtils {

    /**
     * Safely check if a string is empty or null
     *
     * @param s String input to check
     * @return true if string is null or empty
     */
    public static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    /**
     * Return first non null string from the arguments
     * @param strings Strings to find non-null
     * @return First non null string or empty string all arguments are null
     */
    public static String nonNull(String... strings) {
        if (strings == null) {
            return "";
        }
        for (String s : strings) {
            if (!isEmpty(s)) {
                return s;
            }
        }
        return "";
    }

    /**
     * Safely check if two string are equal or not
     * @param a One of the strings in comparison
     * @param b Other string in comparison
     * @return true if both are pointing to same address (possibly null) or equal in content. False otherwise.
     */
    public static boolean equals(String a, String b) {
        return a == b || (a != null && a.equals(b));
    }


}

