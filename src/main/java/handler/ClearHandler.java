package handler;

import java.io.*;
import java.net.*;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import request.ClearResult;
import service.ClearService;

public class ClearHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().toLowerCase().equals("post")){
            send400Error(exchange);
            return;
        }

        ClearService service = new ClearService();
        ClearResult result = service.clear();

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
        Gson gson = new Gson();
        gson.toJson(result, resBody);
        resBody.close();
    }
}