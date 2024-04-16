package com.gero.newpass.utilities;

public class StringHelper {
    private static String sharedString;

    public static void setSharedString(String value) {
        sharedString = value;
    }

    public static String getSharedString() {
        return sharedString;
    }
}

