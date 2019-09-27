package controller;

import db.DataBase;
import model.User;
import utils.UrlEncodedParser;
import webserver.CookieLoginStatus;
import webserver.Request;
import webserver.Response;

import java.util.Map;

public class LoginController {

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String LOGINED_COOKIE_KEY = "logined";

    public static Response doPost(Request request) {
        Map<String, String> parsedBody = UrlEncodedParser.parse(new String(request.getBody()));
        User user = DataBase.findUserById(parsedBody.get(USER_ID));

        String redirectUrl = "/user/login_failed.html";
        String loginedCookie = CookieLoginStatus.False.getText();

        if (verify(user, parsedBody.get(PASSWORD))) {
            redirectUrl = "/index.html";
            loginedCookie = CookieLoginStatus.TRUE.getText();
        }

        return Response.ResponseBuilder.redirect(redirectUrl)
                .withCookie(LOGINED_COOKIE_KEY, loginedCookie)
                .build();
    }

    private static boolean verify(User user, String password) {
        return user != null && user.matchPassword(password);
    }
}
