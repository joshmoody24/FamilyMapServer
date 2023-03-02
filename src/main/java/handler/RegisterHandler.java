package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.GetPersonRequest;
import request.GetPersonResult;
import request.RegisterRequest;
import request.RegisterResult;
import service.GetPersonService;
import service.RegisterService;

import java.io.*;
import java.net.HttpURLConnection;

public class RegisterHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            if (!exchange.getRequestMethod().toLowerCase().equals("post")){
                send400Error(exchange);
                return;
            }

            InputStream reqBody = exchange.getRequestBody();
            String reqData = readString(reqBody);
            Gson gson = new Gson();
            RegisterRequest request = gson.fromJson(reqData, RegisterRequest.class);

            RegisterService service = new RegisterService();
            RegisterResult result = service.register(request);

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
            gson.toJson(result, resBody);
            System.out.println(result.getPersonId());
            resBody.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}