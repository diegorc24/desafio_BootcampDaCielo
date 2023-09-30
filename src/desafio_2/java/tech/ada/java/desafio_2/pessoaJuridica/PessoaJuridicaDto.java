package tech.ada.java.desafio_2.pessoaJuridica;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PessoaJuridicaDto
{
    private Long id;

    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(regexp = "^\\d{14}$", message = "CNPJ deve ter 14 dígitos.")
    private String cnpj;

    @NotBlank(message = "Razao Social é obrigatório")
    @Size(max = 50, message = "Razao Social deve ter no máximo 50 caracteres.")
    private String razaoSocial;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 50, message = "Nome deve ter no máximo 50 caracteres.")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve ter 11 dígitos.")
    private String cpf;

    @NotBlank(message = "Email é obrigatório")
    @Email(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", message = "E-mail inválido.")
    private String email;

    @NotBlank(message = "MCC é obrigatório")
    @Pattern(regexp = "^\\d{1,4}$", message = "MCC deve ter no máximo 4 dígitos.")
    private String mcc;
}
