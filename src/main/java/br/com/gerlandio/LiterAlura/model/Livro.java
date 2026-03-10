package br.com.gerlandio.LiterAlura.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "livros", uniqueConstraints = {
        @UniqueConstraint(name = "uk_livro_gutendex", columnNames = "gutendex_id")
})
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gutendex_id", nullable = false)
    private Long gutendexId;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String idioma;

    @Column(name = "numero_downloads")
    private Double numeroDownloads;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Livro() {
    }

    public Livro(Long gutendexId, String titulo, String idioma, Double numeroDownloads, Autor autor) {
        this.gutendexId = gutendexId;
        this.titulo = titulo;
        this.idioma = idioma;
        this.numeroDownloads = numeroDownloads;
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public Long getGutendexId() {
        return gutendexId;
    }

    public void setGutendexId(Long gutendexId) {
        this.gutendexId = gutendexId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Double numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", autor=" + (autor != null ? autor.getNome() : "Desconhecido") +
                ", idioma='" + idioma + '\'' +
                ", numeroDownloads=" + numeroDownloads +
                '}';
    }
}
