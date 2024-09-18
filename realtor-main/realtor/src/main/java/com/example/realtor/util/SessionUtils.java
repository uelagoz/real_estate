package com.example.realtor.util;

import jakarta.servlet.http.HttpSession;

public class SessionUtils {

    private static final String USERNAME = "username";

    public static String getUsernameFromSession(HttpSession session) {
        return (String) session.getAttribute(USERNAME);
    }

    public static void saveUsernameToSession(HttpSession session, String username) {
        session.setAttribute(USERNAME, username);
    }

}
