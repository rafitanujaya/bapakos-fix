package org.bapakos.session;

import org.bapakos.model.entity.UserEntity;

public class Session {
    private static UserEntity currentUser;

    public static void set(UserEntity user) { currentUser = user; }

    public static UserEntity get() { return currentUser; }

    public static void clear() { currentUser = null; }

    public static boolean isLoggedIn() { return currentUser != null; }

}
