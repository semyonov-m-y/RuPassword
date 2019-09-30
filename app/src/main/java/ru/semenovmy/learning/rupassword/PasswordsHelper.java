package ru.semenovmy.learning.rupassword;

import java.security.SecureRandom;

public class PasswordsHelper {

    private final String[] russians;
    private final String[] latins;
    private static SecureRandom random = new SecureRandom();

    public PasswordsHelper(String[] russians, String[] latins) {
        this.russians = russians;
        this.latins = latins;

        if(russians.length != latins.length) {
            throw new IllegalArgumentException();
        }
    }

    public String convert(CharSequence charSequence) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < charSequence.length(); i++) {
            char c = charSequence.charAt(i);
            String s = String.valueOf(c);
            boolean found = false;

            for (int j = 0; j < russians.length; j++) {
                if(russians[j].equals(s)) {
                    result.append(Character.isUpperCase(c) ? latins[j].toUpperCase() : latins[j]);
                    found = true;
                    break;
                }
            }

            if (!found) {
                result.append(c);
            }
        }

        return result.toString();
    }

    public static String generatePasswordConstructor(String dictionary, int symbolsCount) {
        String result = "";

        for (int i = 0; i < symbolsCount; i++) {
            int index = random.nextInt(dictionary.length());
            result += dictionary.charAt(index);
        }

        return result;
    }
}
