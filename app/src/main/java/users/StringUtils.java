package users;

/**
 * Created by ibrahim on 2/23/16.
 */
public class StringUtils {
    /*
     * returns empty string if passed string is null, otherwise returns passed string
     */
    public static String notNullStr(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }

    /**
     * returns true if two strings are equal or two strings are null
     */
    public static boolean equals(String s1, String s2) {
        if (s1 == null) {
            return s2 == null;
        }
        return s1.equals(s2);
    }

    public static boolean isEmpty(String string) {
        return string == null || string.contains("null") || string.length() == 0;
    }
}
