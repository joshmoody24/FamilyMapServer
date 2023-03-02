package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.*;
import service.GetPersonService;
import service.LoginService;
import service.RegisterService;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().toLowerCase().equals("post")){
            send400Error(exchange);
            return;
        }
        InputStream reqBody = exchange.getRequestBody();
        String reqData = readString(reqBody);
        Gson gson = new Gson();
        LoginRequest request = gson.fromJson(reqData, LoginRequest.class);

        LoginService service = new LoginService();
        LoginResult result = service.login(request);

        if(result.isSuccess() == false) exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        else exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
        gson.toJson(result, resBody);
        resBody.close();
    }
}