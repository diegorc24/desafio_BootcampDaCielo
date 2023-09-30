package tech.ada.java.desafio_2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Desafios_2_Application implements CommandLineRunner {

    public static void main(String[] args)
    {
        SpringApplication.run(Desafios_2_Application.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        // Your startup logic here
        System.out.println("Starting Desafio 2 Application...");
    }

}
