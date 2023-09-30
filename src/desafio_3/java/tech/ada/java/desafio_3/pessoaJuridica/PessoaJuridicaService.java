package tech.ada.java.desafio_3.pessoaJuridica;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ada.java.desafio_3.excecao.RecursoDuplicadoExcecao;
import tech.ada.java.desafio_3.excecao.RecursoNaoEncontradoExcecao;
import tech.ada.java.desafio_3.filaClientes.ClientQueueService;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaJuridicaService
{
    @Autowired
    private ClientQueueService clientQueueService;
    private final PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    public PessoaJuridicaService(PessoaJuridicaRepository pessoaJuridicaRepository)
    {
        this.pessoaJuridicaRepository = pessoaJuridicaRepository;
    }

    public List<PessoaJuridica> findAll()
    {
        return pessoaJuridicaRepository.findAll();
    }

    public PessoaJuridica findById(Long id)
    {
        return pessoaJuridicaRepository.findById(id).orElseThrow(() ->
                new RecursoNaoEncontradoExcecao("Pessoa Física não encontrada com o ID: " + id)
        );
    }

    public PessoaJuridica save(@NotNull PessoaJuridica pessoaJuridica)
    {
        if (pessoaJuridicaRepository == null) {
            throw new IllegalStateException("Repositório não inicializado");
        }
        Optional<PessoaJuridica> existingPessoaByCpf =
                pessoaJuridicaRepository.findByCpf(pessoaJuridica.getCpf());

        Optional<PessoaJuridica> existingPessoaByEmail =
                pessoaJuridicaRepository.findByEmail(pessoaJuridica.getEmail());

        if (existingPessoaByCpf.isPresent()) {
            throw new RecursoDuplicadoExcecao("Pessoa Juridica já cadastrada com o CPF informado.");
        }

        if (existingPessoaByEmail.isPresent()) {
            throw new RecursoDuplicadoExcecao("Pessoa Juridica já cadastrada com o Email informado.");
        }

        PessoaJuridica savedPessoaJuridica = pessoaJuridicaRepository.save(pessoaJuridica);
        clientQueueService.addToQueue(savedPessoaJuridica.getId());
        return savedPessoaJuridica;
    }

    public PessoaJuridica update(Long id, PessoaJuridica newPessoaJuridica) {
        return pessoaJuridicaRepository.findById(id).map(pessoaJuridica -> {
            pessoaJuridica.setCnpj(newPessoaJuridica.getCnpj());
            pessoaJuridica.setRazaoSocial(newPessoaJuridica.getRazaoSocial());
            pessoaJuridica.setMcc(newPessoaJuridica.getMcc());
            pessoaJuridica.setCpf(newPessoaJuridica.getCpf());
            pessoaJuridica.setNome(newPessoaJuridica.getNome());
            pessoaJuridica.setEmail(newPessoaJuridica.getEmail());
            PessoaJuridica savedPessoaJuridica = pessoaJuridicaRepository.save(pessoaJuridica);
            clientQueueService.addToQueue(savedPessoaJuridica.getId());
            return savedPessoaJuridica;
        }).orElseThrow(() ->
                new RecursoNaoEncontradoExcecao("Pessoa Física não encontrada com o ID: " + id)
        );
    }

    public void deleteById(Long id) {
        if (pessoaJuridicaRepository.existsById(id)) {
            pessoaJuridicaRepository.deleteById(id);
        } else {
            throw new RecursoNaoEncontradoExcecao("Pessoa Jurídica não encontrada com o ID: " + id);
        }
    }

    public Optional<PessoaJuridica> findByNome(String nome) {
        return pessoaJuridicaRepository.findByNomeContainingIgnoreCase(nome);
    }
}
