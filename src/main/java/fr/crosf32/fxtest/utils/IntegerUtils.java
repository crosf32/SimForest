package fr.crosf32.fxtest.utils;

public class IntegerUtils {

    public static int getSafeInt(String s) {
        try {
            return Integer.valueOf(s);
        } catch(Exception e) {
            return -1;
        }
    }
}
