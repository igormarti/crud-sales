package io.github.igormarti;

import io.github.igormarti.domain.entity.Cliente;
import io.github.igormarti.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner run(@Autowired Clientes clientes){
        return args -> {
            Cliente igor = new Cliente("Igor Martins", "11111111111");
            Cliente juliana = new Cliente("Juliana de Oliveira", "22222222222");

            clientes.saveAll(Arrays.asList(igor,juliana));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}


