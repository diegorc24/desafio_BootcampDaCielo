package tech.ada.java.desafio_3.pessoaFisica;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ada.java.desafio_3.excecao.RecursoDuplicadoExcecao;
import tech.ada.java.desafio_3.excecao.RecursoNaoEncontradoExcecao;
import tech.ada.java.desafio_3.filaClientes.ClientQueueService;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaFisicaService
{
    private final PessoaFisicaRepository pessoaFisicaRepository;
    @Autowired
    private ClientQueueService clientQueueService;

    @Autowired
    public PessoaFisicaService(PessoaFisicaRepository pessoaFisicaRepository)
    {
        this.pessoaFisicaRepository = pessoaFisicaRepository;
    }

    public List<PessoaFisica> findAll() {
        return pessoaFisicaRepository.findAll();
    }

    public PessoaFisica findById(Long id) {
        return pessoaFisicaRepository.findById(id).orElseThrow(() ->
                new RecursoNaoEncontradoExcecao("Pessoa Física não encontrada com o ID: " + id)
        );
    }

    public PessoaFisica save(@NotNull PessoaFisica pessoaFisica)
    {
        if (pessoaFisicaRepository == null) {
            throw new IllegalStateException("Repositório não inicializado");
        }
        Optional<PessoaFisica> existingPessoaByCpf = pessoaFisicaRepository.findByCpf(pessoaFisica.getCpf());
        Optional<PessoaFisica> existingPessoaByEmail = pessoaFisicaRepository.findByEmail(pessoaFisica.getEmail());

        if (existingPessoaByCpf.isPresent()) {
            throw new RecursoDuplicadoExcecao("Pessoa Física já cadastrada com o CPF informado.");
        }

        if (existingPessoaByEmail.isPresent()) {
            throw new RecursoDuplicadoExcecao("Pessoa Física já cadastrada com o Email informado.");
        }
        PessoaFisica savedPessoaFisica = pessoaFisicaRepository.save(pessoaFisica);
        clientQueueService.addToQueue(savedPessoaFisica.getId());
        return savedPessoaFisica;
    }

    public PessoaFisica update(Long id, PessoaFisica newPessoaFisica)
    {
        return pessoaFisicaRepository.findById(id).map(pessoaFisica ->
        {
            pessoaFisica.setNome(newPessoaFisica.getNome());
            pessoaFisica.setCpf(newPessoaFisica.getCpf());
            pessoaFisica.setEmail(newPessoaFisica.getEmail());
            pessoaFisica.setMcc(newPessoaFisica.getMcc());
            PessoaFisica savedPessoaFisica = pessoaFisicaRepository.save(pessoaFisica);
            clientQueueService.addToQueue(savedPessoaFisica.getId());
            return savedPessoaFisica;
        }).orElseThrow(() -> new RecursoNaoEncontradoExcecao("Pessoa Física não encontrada com o ID: " + id));
    }

    public void deleteById(Long id)
    {
        if (pessoaFisicaRepository.existsById(id)) {
            pessoaFisicaRepository.deleteById(id);
        } else {
            throw new RecursoNaoEncontradoExcecao("Pessoa Física não encontrada com o ID: " + id);
        }
    }

    public Optional<PessoaFisica> findByNome(String nome) {
        return pessoaFisicaRepository.findByNomeContainingIgnoreCase(nome);
    }

}