package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestParserTest {

    private static final byte[] REQUEST_BYTE =
            ("POST / HTTP/1.1\r\n" +
                    "Content-Type: application/json\r\n" +
                    "User-Agent: PostmanRuntime/7.16.3\r\n" +
                    "Accept: */*\r\n" +
                    "Cache-Control: no-cache\r\n" +
                    "Postman-Token: ec4860d5-f98f-4d1d-ad88-0618352cbd4a\r\n" +
                    "Host: localhost:8080\r\n" +
                    "Accept-Encoding: gzip, deflate\r\n" +
                    "Content-Length: 26\r\n" +
                    "Cookie: JSESSIONID=471133FFBC577D4F5BCB5F45AE944BF7\r\n" +
                    "Connection: keep-alive\r\n" +
                    "\r\n" +
                    "This is body").getBytes();

    @Test
    @DisplayName("Request 파싱")
    void parse() throws IOException {
        Request req = RequestParser.parse(new ByteArrayInputStream(REQUEST_BYTE));

        assertThat(req.getMethod()).isEqualTo("POST");
        assertThat(req.getUrl()).isEqualTo("/");
        assertThat(req.getHeader("Content-Length")).isEqualTo("26");
        assertThat(new String(req.getBody()).trim()).isEqualTo("This is body");
    }

    @Test
    @DisplayName("Query String 파싱")
    void querystring() throws IOException {
        Request req = RequestParser.parse(new ByteArrayInputStream("GET /some-resource?q=abcde&email=john%40example.com".getBytes()));

        assertThat(req.getUrl()).isEqualTo("/some-resource");
        assertThat(req.getQuery("q")).isEqualTo("abcde");
        assertThat(req.getQuery("email")).isEqualTo("john@example.com");
    }
}
