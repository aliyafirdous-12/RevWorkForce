package com.revworkforce.util;

public class SessionManager {

    private static long lastAccess = System.currentTimeMillis();
    private static final long TIMEOUT = 2 * 60 * 1000; // 2 minutes

    public static void refresh() {
        lastAccess = System.currentTimeMillis();
    }

    public static boolean isSessionExpired() {
        return System.currentTimeMillis() - lastAccess > TIMEOUT;
    }
    
//    private static int loggedInUserId;
//
//    public static void setUserId(int id) {
//        loggedInUserId = id;
//    }
//
//    public static int getUserId() {
//        return loggedInUserId;
//    }
//
//    public static void clear() {
//        loggedInUserId = 0;
//    }
    
}