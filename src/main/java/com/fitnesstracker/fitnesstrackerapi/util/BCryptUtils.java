package com.fitnesstracker.fitnesstrackerapi.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptUtils {

    // utility class, no instantiation
    private BCryptUtils() {
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
