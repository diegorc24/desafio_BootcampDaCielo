package tech.ada.java.desafio_2.pessoaJuridica;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.java.desafio_2.filaClientes.ClientQueueService;
import tech.ada.java.desafio_2.pessoaFisica.PessoaFisicaDto;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/pessoas-juridicas", produces = {"application/json"})
@Tag(name = "Pessoa Jurídicas")
public class PessoaJuridicaController {
    private final PessoaJuridicaService pessoaJuridicaService;
    @Autowired
    private ClientQueueService clientQueueService;

    @Autowired
    public PessoaJuridicaController(PessoaJuridicaService pessoaJuridicaService) {
        this.pessoaJuridicaService = pessoaJuridicaService;
    }

    @Operation(summary = "Realiza a pesquisa de todas as Pessoa Jurídicas cadastradas")
    @GetMapping
    public ResponseEntity<List<PessoaJuridicaDto>> findAll() {
        List<PessoaJuridica> pessoaJuridicas = pessoaJuridicaService.findAll();
        List<PessoaJuridicaDto> dtos = pessoaJuridicas.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Realiza a pesquisa por id de Pessoas Jurídicas")
    @GetMapping("/{id}")
    public ResponseEntity<PessoaJuridicaDto> findById(@PathVariable Long id) {
        PessoaJuridica pessoaJuridica = pessoaJuridicaService.findById(id);
        return ResponseEntity.ok(convertToDto(pessoaJuridica));
    }

    @Operation(summary = "Realiza o cadastro de novas Pessoa Jurídicas")
    @ApiResponse(responseCode = "201", description = "Pessoa Jurídicas cadastrada com sucesso.")
    @ApiResponse(responseCode = "403", description = "Campo obrigatório não informado")
    @PostMapping
    public ResponseEntity<PessoaJuridicaDto> save(@RequestBody PessoaJuridicaDto dto) {
        PessoaJuridica pessoaJuridica = pessoaJuridicaService.save(convertToEntity(dto));
        return new ResponseEntity<>(convertToDto(pessoaJuridica), HttpStatus.CREATED);
    }

    @Operation(summary = "Realiza a atualização de Pessoas Jurídicas ja cadastradas")
    @PutMapping("/{id}")
    public ResponseEntity<PessoaJuridicaDto> update(@PathVariable Long id, @RequestBody PessoaJuridicaDto dto) {
        PessoaJuridica updatedPessoaJuridica = pessoaJuridicaService.update(id, convertToEntity(dto));
        return ResponseEntity.ok(convertToDto(updatedPessoaJuridica));
    }

    @Operation(summary = "Apaga por id as Pessoas Jurídicas cadastradas")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        pessoaJuridicaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ApiResponse(responseCode = "204", description = "Nenhuma Pessoa Jurídicas na fila.")
    @Operation(summary = "Visualização de Pessoas Jurídicas na fila de castramento")
    @GetMapping("/next-client")
    public ResponseEntity<PessoaJuridicaDto> getNextClient() {
        long clientId = clientQueueService.removeFromQueue();
        if (clientId == -1) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        PessoaJuridica pessoaJuridica = pessoaJuridicaService.findById(clientId);
        return ResponseEntity.ok(convertToDto(pessoaJuridica));
    }

    private PessoaJuridicaDto convertToDto(PessoaJuridica entity) {
        return new PessoaJuridicaDto(entity.getId(), entity.getNome(), entity.getCpf(), entity.getRazaoSocial(),
                entity.getCnpj(), entity.getEmail(), entity.getMcc());
    }

    private PessoaJuridica convertToEntity(PessoaJuridicaDto dto) {
        return new PessoaJuridica(dto.getId(), dto.getNome(), dto.getCpf(), dto.getRazaoSocial(),
                dto.getCnpj(), dto.getEmail(), dto.getMcc());
    }
}
