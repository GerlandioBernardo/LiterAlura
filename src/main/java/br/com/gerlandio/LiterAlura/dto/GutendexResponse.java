package br.com.gerlandio.LiterAlura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexResponse(List<GutendexLivroDTO> results) {
}
