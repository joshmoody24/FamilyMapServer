package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.GetAllEventsResult;
import request.GetAllPersonsResult;
import request.GetPersonRequest;
import request.GetPersonResult;
import service.GetAllEventsService;
import service.GetAllPersonsService;
import service.GetPersonService;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

public class GetAllEventsHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().toLowerCase().equals("get")){
            send400Error(exchange);
            return;
        }
        Headers reqHeaders = exchange.getRequestHeaders();
        if (!reqHeaders.containsKey("Authorization")) send400Error(exchange);
        String authToken = reqHeaders.getFirst("Authorization");

        GetAllEventsService service = new GetAllEventsService();
        GetAllEventsResult result = service.getAllEvents(authToken);

        int responseCode = HttpURLConnection.HTTP_OK;
        if(result.isSuccess() == false) responseCode = HttpURLConnection.HTTP_BAD_REQUEST;
        exchange.sendResponseHeaders(responseCode, 0);

        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
        Gson gson = new Gson();
        gson.toJson(result, resBody);
        resBody.close();
    }
}