package com.roloduck.utils;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/25/14
 * RoloDuck
 */

public class StringUtils {

    /**
     * Used to build a relevant column section of a SQL query using the RoloDuck Entity's getColumnNames methods
     * @param strings the array of all column names
     * @return a comma separated string of all column names for use in queries
     */
    public static String convertStrArrToSQLColStr(String[] strings) {
        int length = strings.length;
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < length; i++) {
            builder.append(strings[i]);
            if(i < length - 1) {
                builder.append(",");
                builder.append(" ");
            }
        }
        return builder.toString();
    }
}
