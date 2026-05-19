package handler;

import com.google.gson.Gson;
import io.javalin.http.Context;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import service.UserService;
import service.UserService.*;

public class UserHandler {

    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public void register(Context ctx) throws DataAccessException {
        UserData user = new Gson().fromJson(ctx.body(), UserData.class);
        userService.register(user.username(), user.password(), user.email());
        ctx.result(new Gson().toJson(userService));
    }

    public void login(Context ctx) throws DataAccessException {
        UserData user = new Gson().fromJson(ctx.body(), UserData.class);
        userService.login(user.username(), user.password());
        ctx.result(new Gson().toJson(userService));
    }

    public void logout(Context ctx) throws DataAccessException {
        AuthData auth = new Gson().fromJson(ctx.body(), AuthData.class);
        if (userService.logout(auth.authToken())) {
            ctx.status(200);
        }
    }

}
