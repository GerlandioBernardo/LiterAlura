package br.com.gerlandio.LiterAlura.service;

import br.com.gerlandio.LiterAlura.dto.GutendexAutorDTO;
import br.com.gerlandio.LiterAlura.dto.GutendexLivroDTO;
import br.com.gerlandio.LiterAlura.model.Autor;
import br.com.gerlandio.LiterAlura.model.Livro;
import br.com.gerlandio.LiterAlura.repository.AutorRepository;
import br.com.gerlandio.LiterAlura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LiterAluraService {

    @Autowired
    private GutendexClient gutendexClient;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Transactional
    public Optional<Livro> buscarSalvarLivroPorTitulo(String titulo) {
        Optional<GutendexLivroDTO> livroApi = gutendexClient.buscarPrimeiroLivroPorTitulo(titulo);
        if (livroApi.isEmpty()) {
            return Optional.empty();
        }

        GutendexLivroDTO livroDTO = livroApi.get();

        Optional<Livro> existente = livroRepository.findByGutendexId(livroDTO.id());
        if (existente.isPresent()) {
            return existente;
        }

        GutendexAutorDTO autorDTO = (livroDTO.authors() == null || livroDTO.authors().isEmpty())
                ? null
                : livroDTO.authors().get(0);

        Autor autor = null;
        if (autorDTO != null) {
            autor = autorRepository
                    .findByNomeIgnoreCaseAndAnoNascimentoAndAnoFalecimento(
                            autorDTO.name(),
                            autorDTO.birthYear(),
                            autorDTO.deathYear()
                    )
                    .orElseGet(() -> autorRepository.save(
                            new Autor(autorDTO.name(), autorDTO.birthYear(), autorDTO.deathYear())
                    ));
        }

        String idioma = (livroDTO.languages() == null || livroDTO.languages().isEmpty())
                ? "desconhecido"
                : livroDTO.languages().get(0);

        Livro livro = new Livro(
                livroDTO.id(),
                livroDTO.title(),
                idioma,
                livroDTO.downloadCount(),
                autor
        );

        return Optional.of(livroRepository.save(livro));
    }

    public List<Livro> listarLivrosRegistrados() {
        return livroRepository.findAll();
    }

    public List<Autor> listarAutoresRegistrados() {
        return autorRepository.findAll();
    }

    public List<Autor> listarAutoresVivosNoAno(Integer ano) {
        return autorRepository.buscarAutoresVivosNoAno(ano);
    }

    public List<Livro> listarLivrosPorIdioma(String idioma) {
        if (idioma == null || idioma.isBlank()) {
            return Collections.emptyList();
        }
        return livroRepository.findByIdiomaIgnoreCaseOrderByTituloAsc(idioma.trim());
    }
}
