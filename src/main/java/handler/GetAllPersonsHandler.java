package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.GetAllPersonsResult;
import request.GetPersonRequest;
import request.GetPersonResult;
import service.GetAllPersonsService;
import service.GetPersonService;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

public class GetAllPersonsHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().toLowerCase().equals("get")){
            send400Error(exchange);
            return;
        }
        Headers reqHeaders = exchange.getRequestHeaders();
        if (!reqHeaders.containsKey("Authorization")) send400Error(exchange);
        String authToken = reqHeaders.getFirst("Authorization");

        GetAllPersonsService service = new GetAllPersonsService();
        GetAllPersonsResult result = service.getAllPersons(authToken);

        if(result.isSuccess()) exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        else exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
        Gson gson = new Gson();
        gson.toJson(result, resBody);
        resBody.close();
    }
}