package org.bapakos.util;

import org.bapakos.config.EnvLoader;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    public static String hashPassword (String password) {
        int cost = Integer.parseInt(EnvLoader.get("SALT"));
        return BCrypt.hashpw(password, BCrypt.gensalt(cost));
    }

    public static boolean checkPassword (String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
