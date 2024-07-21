package org.jshapiro.tradereporter;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AnagramCheck {
    public boolean isAnagram(String a, String b) {
        if (a == null || b == null) {
            return false;
        }

        // remove all whitespaces and convert to lower case
        char[] arrayA = a.replaceAll("\\s+","").toLowerCase().toCharArray();
        char[] arrayB = b.replaceAll("\\s+","").toLowerCase().toCharArray();

        if (arrayA.length != arrayB.length) {
            return false;
        }

        Arrays.sort(arrayA);
        Arrays.sort(arrayB);

        return Arrays.equals(arrayA, arrayB);
    }
}
