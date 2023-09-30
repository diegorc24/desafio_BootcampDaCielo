package tech.ada.java.desafio_bootcampdacielo.pessoaJuridica;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long>
{
        Optional<PessoaJuridica> findByNomeContainingIgnoreCase(String nome);

        Optional<PessoaJuridica> findByEmail(String email);

        Optional<PessoaJuridica> findByCpf(String cpf);
}
