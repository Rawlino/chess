package handler;

import com.google.gson.Gson;
import io.javalin.http.Context;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import service.UserService;

public class UserHandler {

    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public void register(Context ctx) throws DataAccessException {
        UserData user = new Gson().fromJson(ctx.body(), UserData.class);
        AuthData auth = userService.register(user.username(), user.password(), user.email());
        ctx.result(new Gson().toJson(auth));
    }

    public void login(Context ctx) throws DataAccessException {
        UserData user = new Gson().fromJson(ctx.body(), UserData.class);
        AuthData auth = userService.login(user.username(), user.password());
        ctx.result(new Gson().toJson(auth));
    }

    public void logout(Context ctx) throws DataAccessException {
        boolean authExist = userService.logout(ctx.header("Authorization"));
        if (authExist) {
            ctx.result("{}");
        }
    }

}
