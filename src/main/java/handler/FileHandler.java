package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import request.ClearResult;
import service.ClearService;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().toLowerCase().equals("get")) send400Error(exchange);

        String path = "./web" + exchange.getRequestURI().getPath();
        File file = new File(path);

        System.out.println("fetching file " + path);
        OutputStream os = exchange.getResponseBody();

        try {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            String response = "";
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                response += line;
            }

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());

            System.out.println(response);
            writeString(response, os);

        } catch (Exception ex){
            ex.printStackTrace();
        }

        os.close();
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
}