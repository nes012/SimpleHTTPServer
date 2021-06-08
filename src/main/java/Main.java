import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import entities.User;

/*
 * a simple static http server
 */
public class Main
{
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/GetPossibleMatches", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {

        Gson gson = new Gson();

        public void handle(HttpExchange httpExchange) throws IOException {

            User newUser = new User("Alex", 35);
            byte [] response = gson.toJson(newUser).getBytes();

            Headers headers = httpExchange.getResponseHeaders();
            headers.add("Content-Type", "application/json");

            httpExchange.sendResponseHeaders(200, response.length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response);
            outputStream.close();
        }
    }
}
