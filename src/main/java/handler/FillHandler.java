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

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        System.out.println("Handling fill");

        if (!exchange.getRequestMethod().toLowerCase().equals("post")){
            send400Error(exchange);
            return;
        }

        FillService service = new FillService();
        String path = exchange.getRequestURI().getPath();
        String[] pathSlices = path.split("/");
        if(pathSlices.length != 3){
            send400Error(exchange);
            return;
        }

        String username = pathSlices[1];
        int generations;
        try {
            generations = Integer.parseInt(pathSlices[2]);
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

    private void send400Error(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        exchange.getResponseBody().close();
    }

    /*
        The writeString method shows how to write a String to an OutputStream.
    */
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}