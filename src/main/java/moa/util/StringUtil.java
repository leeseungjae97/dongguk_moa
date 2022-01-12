package moa.util;

import java.time.LocalDate;

public class StringUtil {
    public static String lectureDate(String str) {
        String aStr = str.trim();
        for (int i = 0; i < aStr.length(); i++) {
            if(aStr.substring(i, i+4).equals(String.valueOf(LocalDate.now().getYear()))) {
                aStr = aStr.substring(i+2, aStr.length()-7);
                aStr = aStr.replace("-", ".");
                break;
            }
        }
        return aStr;
    }
    public static String lectureWeek(String str) {
        String aStr = str.trim();
        for (int i = 0; i < aStr.length(); i++) {
            if(aStr.substring(i, i+2).equals("주차")) {
                aStr = aStr.substring(0, i+2);
                break;
            }
        }
        return aStr;
    }
//    String ll = "\n" +
//            "          [nV6Be$V] [보강] 3강\n" +
//            "        ";
    //[보강] 1강 [2022-01-03 09:30]
    //now = 2022-01-12
    public static String getLectureName(String str) {
        String aStr = "";
        for (int i = 0; i < str.length(); i++) {
            if(i+10 > str.length()) {break;}
// test code
//            if(str.substring(i, i+10).equals(LocalDate.now().plusDays(1).toString())) {
//                aStr = str.substring(i-1);
//                break;
//            }

            if(str.substring(i, i+10).equals(LocalDate.now().toString())) {
                aStr = str.substring(i-1);
                break;
            }
        }
        str = str.replace(aStr, ""); // 날짜 없애고 제목만.
        str = str.substring(0, str.length()-1); // 마지막 공백없앰

        System.out.println(str);
        return str;
    }
    public static boolean findTextInText(String webExStr, String eclassStr) {
        for (int i = 0; i < webExStr.length(); i++) {
            System.out.println(webExStr.substring(i, i+eclassStr.length()));
            if(webExStr.substring(i, i+eclassStr.length()).equals(eclassStr)) {
                System.out.println("true");
                System.out.println(webExStr.substring(i, i+eclassStr.length()));
                return true;
            }
        }
        return false;
    }
}
