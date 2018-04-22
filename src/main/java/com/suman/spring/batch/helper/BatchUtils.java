package com.suman.spring.batch.helper;

public class BatchUtils {
    public static String getCompatibleValue(String value, String dataType) {
        if (value == null)
            return null;

        switch (dataType.toLowerCase().replaceFirst("\\(.*\\)","")) {
            case "varchar":
            case "char":
                return getString(value.replaceAll("'", "''"));
            case "date":
            case "datetime":
            case "time":
                return getString(value);
            default:
                return value;
        }
    }

    private static String getString(String value) {
        return String.format("'%s'", value);
    }
}
