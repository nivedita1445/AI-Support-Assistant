package com.nivedita.ai_support_assistant.util;

import java.util.regex.Pattern;

public class DataMaskingUtil {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("\\b\\d{10}\\b");

    public static String maskSensitiveData(String input) {

        if (input == null) {
            return null;
        }

        String masked = EMAIL_PATTERN.matcher(input)
                .replaceAll("[EMAIL_MASKED]");

        masked = PHONE_PATTERN.matcher(masked)
                .replaceAll("[PHONE_MASKED]");

        return masked;
    }
}
