package tech.ada.java.desafio_3.pessoaFisica;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PessoaFisica
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 50, message = "Nome deve ter no máximo 50 caracteres.")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve ter 11 dígitos.")
    @Column(unique = true)
    private String cpf;

    @NotBlank(message = "Email é obrigatório")
    @Email(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$",
            message = "E-mail inválido.")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "MCC é obrigatório")
    @Pattern(regexp = "^\\d{1,4}$", message = "MCC deve ter no máximo 4 dígitos.")
    private String mcc;
}
