package handler;

import com.google.gson.Gson;
import io.javalin.http.Context;
import dataaccess.DataAccessException;
import service.ClearService;

public class ClearHandler {

    private final ClearService clearService;

    public ClearHandler(ClearService clearService) {
        this.clearService = clearService;
    }

    public void clearDB(Context ctx) throws DataAccessException {
        clearService.clearDB();
        ctx.result("{}");
    }

}
