package Model;

import java.util.Comparator;

public class StringComparator implements Comparator<String> {
    @Override
    public int compare(String str1, String str2) {

        // Compare the lengths of the strings first
        if (str1.length() == str2.length()) {
            return 0;
        }

        //Calculates how many times the letter 'a' shows up in the two strings and compares them, if equal > return 0;
        int numAs1 = countAs(str1);
        int numAs2 = countAs(str2);
        if (numAs2 == numAs1) {
            return 0;
        }

        return 1;
    }

    // Helper method that counts the number of times the letter "a" appears in a given string
    private int countAs(String str) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == 'a') {
                count++;
            }
        }
        return count;
    }
}