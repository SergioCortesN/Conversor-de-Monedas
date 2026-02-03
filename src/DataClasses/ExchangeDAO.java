package DataClasses;

import DataClasses.response.CodesResponse;
import DataClasses.response.ExchangeResponse;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.Executors;

public class ExchangeDAO {
    private String BASE_URL = "https://v6.exchangerate-api.com/v6/";
    private String API_KEY = cargarApiKey();
    private String PAIR = "/pair";
    private String CODES = "/codes";


    private static final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(15))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .executor(Executors.newVirtualThreadPerTaskExecutor())
            .build();

    private String cargarApiKey() {
        try (InputStream input = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty("exchange.api.key");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getConverterValue(int value, String code, String code2) throws IOException, InterruptedException {
        String url = BASE_URL + API_KEY + PAIR + code + code2 + "/" + value;
        var request = HttpRequest.newBuilder(URI.create(url))
                .GET()
                .build();

        var response  = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        ExchangeResponse exchangeResponse = gson.fromJson(response.body(), ExchangeResponse.class);
        return exchangeResponse.getConversion_result();
    }

    public String[][] getCodes() throws IOException, InterruptedException {
        String url = BASE_URL + API_KEY + CODES;
        var request = HttpRequest.newBuilder(URI.create(url))
                .GET()
                .build();

        var response  = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        CodesResponse exchangeResponse = gson.fromJson(response.body(), CodesResponse.class);
        return exchangeResponse.getSupported_codes();
    }

}
