package br.com.gerlandio.LiterAlura.repository;

import br.com.gerlandio.LiterAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByGutendexId(Long gutendexId);

    List<Livro> findByIdiomaIgnoreCaseOrderByTituloAsc(String idioma);
}
