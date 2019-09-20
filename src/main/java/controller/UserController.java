package controller;

import db.DataBase;
import model.User;
import utils.UrlEncodedParser;
import webserver.Request;
import webserver.Response;
import webserver.Status;

import java.util.Map;

public class UserController {

    public static final String USER_CREATE_URL = "/user/create";
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String LOCATION_HEADER_KEY = "Location";

    public static Response signUp(Request req) {
        String body = new String(req.getBody());
        Map<String, String> parsedBody = UrlEncodedParser.parse(body);
        User user = new User(parsedBody.get(USER_ID),
                parsedBody.get(PASSWORD),
                parsedBody.get(NAME),
                parsedBody.get(EMAIL));
        DataBase.addUser(user);

        return Response.ResponseBuilder.createBuilder()
                .withStatus(Status.FOUND)
                .withHeader(LOCATION_HEADER_KEY, "/index.html")
                .build();
    }
}
