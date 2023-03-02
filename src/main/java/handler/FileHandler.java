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

public class FileHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().toLowerCase().equals("get")){
            send400Error(exchange);
            return;
        }

        int responseCode = 200;

        String path = exchange.getRequestURI().getPath();
        if(path.equals("/") || path.equals("")){
            path = "/index.html";
        }
        path = "./web" + path;
        File file = new File(path);
        if(file.exists() == false){
            path = "./web/HTML/404.html";
            responseCode = 404;
        }
        file = new File(path);

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

            exchange.sendResponseHeaders(responseCode, response.length());

            System.out.println(response);
            writeString(response, os);

        } catch (Exception ex){
            ex.printStackTrace();
        }

        os.close();
    }
}