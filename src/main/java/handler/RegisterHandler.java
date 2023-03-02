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

public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            if (!exchange.getRequestMethod().toLowerCase().equals("post")) send400Error(exchange);

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