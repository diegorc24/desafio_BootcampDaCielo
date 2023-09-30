package tech.ada.java.desafio_2.pessoaFisica;

import lombok.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PessoaFisicaDto
{
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 50, message = "Nome deve ter no máximo 50 caracteres.")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve ter 11 dígitos.")
    private String cpf;

    @NotBlank(message = "Email é obrigatório")
    @Email(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$",
            message = "E-mail inválido.")
    private String email;

    @NotBlank(message = "MCC é obrigatório")
    @Pattern(regexp = "^\\d{1,4}$", message = "MCC deve ter no máximo 4 dígitos.")
    private String mcc;
}


