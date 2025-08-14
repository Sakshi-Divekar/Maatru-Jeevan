package com.priti.utils; // You can change the package as needed

public class UserSession {
    private static String userEmail;
    private static UserSession instance;

        private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public static void setUserEmail(String email) {
        userEmail = email;
    }

    public static String getUserEmail() {
        return userEmail;
    }

        public static void clear() {
        userEmail = null;
    }

}
