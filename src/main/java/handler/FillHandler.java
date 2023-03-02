package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.*;
import service.DoesNotExistException;
import service.FillService;
import service.GetPersonService;
import service.LoadService;

import java.io.*;
import java.net.HttpURLConnection;

public class FillHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().toLowerCase().equals("post")){
            send400Error(exchange);
            return;
        }

        FillService service = new FillService();
        String path = exchange.getRequestURI().getPath();
        String[] pathSlices = path.split("/");
        // first slice is empty string
        if(pathSlices.length != 4){
            send400Error(exchange);
            return;
        }

        String username = pathSlices[2];
        int generations;
        try {
            generations = Integer.parseInt(pathSlices[3]);
            if(generations < 0) throw new Exception("Generations must be non-negative");
        } catch(Exception ex){
            send400Error(exchange);
            return;
        }
        FillResult result = service.fill(new FillRequest(username, generations));

        if(result.isSuccess()) exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        else exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

        System.out.println(result.getMessage());

        Gson gson = new Gson();
        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
        gson.toJson(result, resBody);
        resBody.close();
    }
}