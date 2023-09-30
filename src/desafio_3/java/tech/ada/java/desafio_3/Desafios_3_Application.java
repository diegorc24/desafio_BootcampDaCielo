package tech.ada.java.desafio_3;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Desafios_3_Application implements CommandLineRunner
{
    public static void main(String[] args)
    {
            SpringApplication.run(Desafios_3_Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Your startup logic here
        System.out.println("Starting Desafio 3 Application...");
    }
}
