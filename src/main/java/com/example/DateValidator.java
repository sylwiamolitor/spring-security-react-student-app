package com.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidator {
    public static boolean isValid(final String date) {

        if (date == null || date.isBlank() || "null".equalsIgnoreCase(date.trim())) {
            return false;
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setLenient(false);
            formatter.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
