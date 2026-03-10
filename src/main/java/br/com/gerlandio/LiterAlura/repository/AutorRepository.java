package br.com.gerlandio.LiterAlura.repository;

import br.com.gerlandio.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNomeIgnoreCaseAndAnoNascimentoAndAnoFalecimento(
            String nome,
            Integer anoNascimento,
            Integer anoFalecimento
    );

    @Query("""
            SELECT a FROM Autor a
            WHERE a.anoNascimento IS NOT NULL
            AND a.anoNascimento <= :ano
            AND (a.anoFalecimento IS NULL OR a.anoFalecimento >= :ano)
            ORDER BY a.nome
            """)
    List<Autor> buscarAutoresVivosNoAno(Integer ano);
}
