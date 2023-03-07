package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import request.GetPersonRequest;
import request.GetPersonResult;
import service.GetPersonService;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

public class GetPersonHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().toLowerCase().equals("get")){
            send400Error(exchange);
            return;
        }
        Headers reqHeaders = exchange.getRequestHeaders();
        if (!reqHeaders.containsKey("Authorization")) send400Error(exchange);
        String authToken = reqHeaders.getFirst("Authorization");

        String[] pathSegments = exchange.getRequestURI().getPath().split("/");
        String username = pathSegments[pathSegments.length - 1];

        GetPersonRequest request = new GetPersonRequest(username);

        GetPersonService service = new GetPersonService();
        GetPersonResult result = service.getPerson(request, authToken);

        int responseCode = HttpURLConnection.HTTP_OK;
        if(result.isSuccess() == false) responseCode = HttpURLConnection.HTTP_BAD_REQUEST;
        exchange.sendResponseHeaders(responseCode, 0);

        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
        Gson gson = new Gson();
        gson.toJson(result, resBody);
        resBody.close();
    }
}