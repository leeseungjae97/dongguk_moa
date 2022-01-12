package moa.util;

import java.time.LocalDate;

public class StringUtil {
    public static String lectureDate(String str) {
        String aStr = str.trim();
        String rStr = "";
        for (int i = 0; i < aStr.length(); i++) {
            if(aStr.substring(i, i+4).equals(String.valueOf(LocalDate.now().getYear()))) {
                rStr = aStr.substring(i, aStr.length()-1);
                break;
            }
        }
        System.out.println(LocalDate.now().getYear());

        return rStr;
    }
}
