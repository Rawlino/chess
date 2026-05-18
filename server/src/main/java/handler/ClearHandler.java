package handler;

import io.javalin.http.Context;
import dataaccess.DataAccessException;
import service.ClearService;

public class ClearHandler {

    private final ClearService clearService;

    ClearHandler(ClearService clearService) {
        this.clearService = clearService;
    }

    public void clearDB(Context ctx) throws DataAccessException {
        clearService.clearDB();
        ctx.status(200);
    }

}
