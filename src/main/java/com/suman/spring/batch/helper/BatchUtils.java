package com.suman.spring.batch.helper;

public class BatchUtils {
    public static String getCompatibleValue(String value, String dataType) {
        if (value == null)
            return null;

        String lowerCaseDataType = dataType.toLowerCase();

        if (lowerCaseDataType.matches(".*char.*"))
            return getString(value.replaceAll("'", "''"));
        if (lowerCaseDataType.matches(".*time.*|.*date.*"))
            return getString(value);
        return value;
    }

    private static String getString(String value) {
        return String.format("'%s'", value);
    }
}
