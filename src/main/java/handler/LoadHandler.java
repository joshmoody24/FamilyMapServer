package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import passoffresult.LoadResult;
import request.LoadRequest;
import request.LoginRequest;
import request.LoginResult;
import request.Result;
import service.LoadService;
import service.LoginService;

import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().toLowerCase().equals("post")){
            send400Error(exchange);
            return;
        }

        InputStream reqBody = exchange.getRequestBody();
        String reqData = readString(reqBody);
        Gson gson = new Gson();
        LoadRequest request = gson.fromJson(reqData, LoadRequest.class);
        LoadService service = new LoadService();
        Result result = service.load(request);

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
        gson.toJson(result, resBody);
        resBody.close();
    }
}