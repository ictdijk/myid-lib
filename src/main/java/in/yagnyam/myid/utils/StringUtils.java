package in.yagnyam.myid.utils;


/**
 * Json Utility methods
 */
public class StringUtils {

    public static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

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

    public static boolean equals(String a, String b) {
        return a == b || (a != null && a.equals(b));
    }


}

