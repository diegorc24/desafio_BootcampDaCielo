package tech.ada.java.desafio_3.pessoaFisica;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long>
{
    Optional<PessoaFisica> findByNomeContainingIgnoreCase(String nome);
    Optional<PessoaFisica> findByCpf(String cpf);
    Optional<PessoaFisica> findByEmail(String email);
}
