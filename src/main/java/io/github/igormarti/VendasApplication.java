package io.github.igormarti;

import io.github.igormarti.domain.entity.Cliente;
import io.github.igormarti.domain.entity.Produto;
import io.github.igormarti.domain.repository.Clientes;
import io.github.igormarti.domain.repository.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner run(
            @Autowired Clientes clientes
            , @Autowired Produtos produtos
            ){
        return args -> {
            Cliente igor = new Cliente("Igor Martins", "48326252093");
            Cliente juliana = new Cliente("Juliana de Oliveira", "08064217032");

            clientes.saveAll(Arrays.asList(igor,juliana));

            Produto fogao = new Produto("Fogão 4 bocas", BigDecimal.valueOf(550.00));
            Produto notebook = new Produto("Notebook 8 RAM HD 250GB", BigDecimal.valueOf(2800));

            produtos.saveAll(Arrays.asList(fogao,notebook));

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}


