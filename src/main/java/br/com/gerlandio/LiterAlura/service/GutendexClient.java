package br.com.gerlandio.LiterAlura.service;

import br.com.gerlandio.LiterAlura.dto.GutendexLivroDTO;
import br.com.gerlandio.LiterAlura.dto.GutendexResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;

@Component
public class GutendexClient {

    private static final String URL_BASE = "https://gutendex.com/books/?search=";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GutendexClient() {
        this.objectMapper = new ObjectMapper();
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20))
                .build();
    }

    public Optional<GutendexLivroDTO> buscarPrimeiroLivroPorTitulo(String titulo) {
        try {
            String termo = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_BASE + termo))
                    .timeout(Duration.ofSeconds(30))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return Optional.empty();
            }

            GutendexResponse resultado = objectMapper.readValue(response.body(), GutendexResponse.class);
            if (resultado.results() == null || resultado.results().isEmpty()) {
                return Optional.empty();
            }

            return Optional.ofNullable(resultado.results().get(0));
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
}
