package com.opelkad.conway.v1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UiSmokeTest {

    @LocalServerPort
    private int port;

    @Test
    void index_contains_ui_controls() {
        String body = fetch("http://localhost:" + port + "/");
        assertThat(body).isNotBlank();

        Document doc = Jsoup.parse(body);

        assertIdExists(doc, "rows");
        assertIdExists(doc, "cols");
        assertIdExists(doc, "random");
        assertIdExists(doc, "createBtn");
        assertIdExists(doc, "stepBtn");
        assertIdExists(doc, "refreshBtn");
        assertIdExists(doc, "delay");
        assertIdExists(doc, "startAutoBtn");
        assertIdExists(doc, "stopAutoBtn");
        assertIdExists(doc, "gridContainer");
    }

    private String fetch(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertThat(response.statusCode()).isBetween(200, 299);
            return response.body();
        } catch (Exception e) {
            throw new IllegalStateException("HTTP call failed", e);
        }
    }

    private static void assertIdExists(Document doc, String id) {
        Element el = doc.getElementById(id);
        assertThat(el)
                .withFailMessage("Expected element with id '%s' to exist", id)
                .isNotNull();
    }
}
