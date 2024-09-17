import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APITests {

    public static final String BASE_URI = "https://api.github.com/";
    HttpClient httpClient ;
    HttpRequest get ;
    HttpResponse<Void> response;

    @BeforeClass
    public void setup() throws IOException, InterruptedException {
        httpClient = HttpClient.newBuilder().build();
        get = HttpRequest.newBuilder(URI.create(BASE_URI)).build();
        response =  httpClient.send(get, HttpResponse.BodyHandlers.discarding());
    }

    @Test
    public void validateAPIReponseStatusCode() throws IOException, InterruptedException {
//        HttpClient httpClient = HttpClient.newBuilder().build();
//        HttpRequest get = HttpRequest.newBuilder(URI.create(BASE_URI)).build();
//        HttpResponse<Void> response =  httpClient.send(get, HttpResponse.BodyHandlers.discarding());
        Assertion assertion = new Assertion();
        assertion.assertEquals(response.statusCode(),200);

    }
//
//    @Test(description = "Validate that the content type is present in the response")
//    public void contentTypeIsPresent() throws IOException, InterruptedException {
//        HttpClient httpClient = HttpClient.newBuilder().build();
//        HttpRequest get = HttpRequest.newBuilder(URI.create(BASE_URI)).build();
//        HttpResponse<Void> response =  httpClient.send(get, HttpResponse.BodyHandlers.discarding());
//        String contentType = response.headers().firstValue("content-type").get();
//        Assertion assertion = new Assertion();
//        assertion.assertEquals(contentType,"application/json; charset=utf-8");
//
//    }

    @DataProvider(name = "Headers")
    public static Object[][] headers() {
        return new Object[][] {{"content-type", "application/json; charset=utf-8"}, {"X-Ratelimit-Limit", "60"},{"Referrer-Policy", "origin-when-cross-origin, strict-origin-when-cross-origin"},
                {"Server", "github.com"},{"Strict-Transport-Security","max-age=31536000; includeSubdomains; preload"},{"x-ratelimit-remaining","45"},{"x-ratelimit-resource","core"},
                {"Referrer-Policy","origin-when-cross-origin, strict-origin-when-cross-origin"}};
    }

    @Test(description = "Validate that the XRate Limit is present in the response",dataProvider = "Headers")
    public void validateHeaders(String headerName,String value) throws IOException, InterruptedException {
//        HttpClient httpClient = HttpClient.newBuilder().build();
//        HttpRequest get = HttpRequest.newBuilder(URI.create(BASE_URI)).build();
//        HttpResponse<Void> response =  httpClient.send(get, HttpResponse.BodyHandlers.discarding());
        Assertion assertion = new Assertion();
        String xRateLimit = response.headers().firstValue(headerName).get();
        assertion.assertEquals(xRateLimit,value);

    }

}
