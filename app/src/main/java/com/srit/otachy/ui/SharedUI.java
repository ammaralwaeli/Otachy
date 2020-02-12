package com.srit.otachy.ui;

public class SharedUI {

    public static boolean isValidPhoneNumber(String phone) {

        if ((phone.startsWith("+96477") ||
                phone.startsWith("+96478") ||
                phone.startsWith("+96479") ||
                phone.startsWith("+96475")) &&
                phone.length() == 14) {
            return true;
        } else {
            return false;
        }
    }
}
