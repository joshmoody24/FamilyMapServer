package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.GetEventRequest;
import request.GetEventResult;
import request.GetPersonRequest;
import request.GetPersonResult;
import service.GetEventService;
import service.GetPersonService;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

public class GetEventHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().toLowerCase().equals("get")){
            send400Error(exchange);
            return;
        }
        Headers reqHeaders = exchange.getRequestHeaders();
        if (!reqHeaders.containsKey("Authorization")) send400Error(exchange);
        String authToken = reqHeaders.getFirst("Authorization");
        if (!authToken.equals("afj232hj2332")) send400Error(exchange);

        String[] pathSegments = exchange.getRequestURI().getPath().split("/");
        String id = pathSegments[pathSegments.length - 1];
        System.out.println("Requested event with id: " + id);

        GetEventRequest request = new GetEventRequest(id);
        GetEventService service = new GetEventService();
        GetEventResult result = service.getEvent(request);

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
        Gson gson = new Gson();
        gson.toJson(result, resBody);
        resBody.close();
    }
}