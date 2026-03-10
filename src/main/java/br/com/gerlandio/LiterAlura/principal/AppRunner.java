package br.com.gerlandio.LiterAlura.principal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    private final Principal principal;

    public AppRunner(Principal principal) {
        this.principal = principal;
    }

    @Override
    public void run(String... args) {
        principal.exibirMenu();
    }
}
