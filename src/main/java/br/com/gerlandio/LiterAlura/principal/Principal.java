package br.com.gerlandio.LiterAlura.principal;

import br.com.gerlandio.LiterAlura.model.Autor;
import br.com.gerlandio.LiterAlura.model.Livro;
import br.com.gerlandio.LiterAlura.service.LiterAluraService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    private LiterAluraService literAluraService;

    public Principal(LiterAluraService literAluraService) {
        this.literAluraService = literAluraService;
    }

    public void exibirMenu() {
        Scanner leitura = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\nEscolha o número de sua opção:");
            System.out.println("1- buscar livro pelo titulo");
            System.out.println("2- listar livros registrados");
            System.out.println("3- listar autores registrados");
            System.out.println("4- listar autores vivos em um determinado ano");
            System.out.println("5- listar livros em um determinado idioma");
            System.out.println("0 - sair");

            String valorDigitado = leitura.nextLine();

            try {
                opcao = Integer.parseInt(valorDigitado);
            } catch (NumberFormatException exception) {
                System.out.println("Opção inválida. Digite apenas números.");
                continue;
            }

            switch (opcao) {
                case 1 -> buscarLivroPorTitulo(leitura);
                case 2 -> listarLivrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosNoAno(leitura);
                case 5 -> listarLivrosPorIdioma(leitura);
                case 0 -> System.out.println("Encerrando aplicação...");
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void buscarLivroPorTitulo(Scanner leitura) {
        System.out.println("Digite o título do livro para busca:");
        String titulo = leitura.nextLine();

        if (titulo == null || titulo.isBlank()) {
            System.out.println("Título inválido.");
            return;
        }

        Optional<Livro> livro = literAluraService.buscarSalvarLivroPorTitulo(titulo.trim());

        if (livro.isPresent()) {
            System.out.println("Livro encontrado e salvo no banco:");
            System.out.println(livro.get());
        } else {
            System.out.println("Livro não encontrado na API Gutendex.");
        }
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = literAluraService.listarLivrosRegistrados();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
            return;
        }

        livros.forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = literAluraService.listarAutoresRegistrados();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
            return;
        }

        autores.forEach(System.out::println);
    }

    private void listarAutoresVivosNoAno(Scanner leitura) {
        System.out.println("Digite o ano que deseja pesquisar:");
        String anoTexto = leitura.nextLine();

        Integer ano;
        try {
            ano = Integer.parseInt(anoTexto);
        } catch (NumberFormatException exception) {
            System.out.println("Ano inválido.");
            return;
        }

        List<Autor> autores = literAluraService.listarAutoresVivosNoAno(ano);

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado para o ano informado.");
            return;
        }

        autores.forEach(System.out::println);
    }

    private void listarLivrosPorIdioma(Scanner leitura) {
        System.out.println("Digite o idioma para busca (ex: en, pt, es, fr):");
        String idioma = leitura.nextLine();

        List<Livro> livros = literAluraService.listarLivrosPorIdioma(idioma);

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o idioma informado.");
            return;
        }

        livros.forEach(System.out::println);
    }
}
