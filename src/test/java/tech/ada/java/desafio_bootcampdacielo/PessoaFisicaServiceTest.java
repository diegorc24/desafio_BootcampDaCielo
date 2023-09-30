package tech.ada.java.desafio_bootcampdacielo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.ada.java.desafio_bootcampdacielo.pessoaFisica.PessoaFisica;
import tech.ada.java.desafio_bootcampdacielo.pessoaFisica.PessoaFisicaRepository;
import tech.ada.java.desafio_bootcampdacielo.pessoaFisica.PessoaFisicaService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PessoaFisicaServiceTest {

    @Mock
    private PessoaFisicaRepository repository;

    @InjectMocks
    private PessoaFisicaService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAdicionarPessoaFisica() {
        PessoaFisica pf = new PessoaFisica();
        pf.setId(1L);
        pf.setNome("João Silva");
        pf.setCpf("12345678900");
        pf.setEmail("joao.silva@email.com");
        pf.setMcc("1234");

        when(repository.save(any(PessoaFisica.class))).thenReturn(pf);

        PessoaFisica saved = service.save(pf);

        Assertions.assertNotNull(saved);
        assertEquals(pf, saved);
    }
}
