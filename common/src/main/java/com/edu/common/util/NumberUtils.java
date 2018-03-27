package com.edu.common.util;

public class NumberUtils {
    public static Long object2Long(Object obj) {
        try {
            if (obj instanceof Long) {
                return (Long) obj;
            } else {
                return Long.parseLong(String.valueOf(obj));
            }
        } catch (Exception e) {
            return null;
        }
    }
    
    public static Long string2Long(String strValue) {
        try {
            return Long.parseLong(strValue);
        } catch (Exception e) {
            return null;
        }
    }
    
    public static Integer object2Integer(Object obj) {
        try {
            if (obj instanceof Integer) {
                return (Integer) obj;
            } else {
                return Integer.parseInt(String.valueOf(obj));
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer string2Integer(String strValue) {
        try {
            return Integer.parseInt(strValue);
        } catch (Exception e) {
            return null;
        }
    }
    
    public static Double object2Double(Object obj) {
        try {
            if (obj instanceof Double) {
                return (Double) obj;
            } else {
                return Double.parseDouble(String.valueOf(obj));
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static Double string2Double(String strValue) {
        try {
            return Double.parseDouble(strValue);
        } catch (Exception e) {
            return null;
        }
    }
    
    public static boolean isLongValueEqual(Long longVal1, Long longVal2) {
        long val1 = (longVal1 == null) ? 0L : longVal1.longValue();
        long val2 = (longVal2 == null) ? 0L : longVal2.longValue();
        return val1 == val2;
    }
    
    public static boolean isIntegerValueEqual(Integer intVal1, Integer intVal2) {
        long val1 = (intVal1 == null) ? 0L : intVal1.longValue();
        long val2 = (intVal2 == null) ? 0L : intVal2.longValue();
        return val1 == val2;
    }
}
