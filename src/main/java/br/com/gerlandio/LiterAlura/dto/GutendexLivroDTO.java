package br.com.gerlandio.LiterAlura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexLivroDTO(
        Long id,
        String title,
        List<GutendexAutorDTO> authors,
        List<String> languages,
        @JsonAlias("download_count") Double downloadCount
) {
}
