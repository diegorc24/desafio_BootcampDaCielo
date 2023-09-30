package tech.ada.java.desafio_2.pessoaFisica;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.java.desafio_2.filaClientes.ClientQueueService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping(value = "/api/pessoas-fisicas", produces = {"application/json"})
@Tag(name = "Pessoa Física")
public class PessoaFisicaController
{
    private final PessoaFisicaService pessoaFisicaService;
    @Autowired
    private ClientQueueService clientQueueService;

    @Autowired
    public PessoaFisicaController(PessoaFisicaService pessoaFisicaService)
    {
        this.pessoaFisicaService = pessoaFisicaService;
    }

    @Operation(summary = "Realiza a pesquisa de todas as Pessoa Física cadastradas")
    @GetMapping
    public ResponseEntity<List<PessoaFisicaDto>> findAll()
    {
        List<PessoaFisica> pessoasFisicas = pessoaFisicaService.findAll();
        List<PessoaFisicaDto> dtos = pessoasFisicas.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Realiza a pesquisa por id de Pessoas Física")
    @GetMapping("/{id}")
    public ResponseEntity<PessoaFisicaDto> findById(@PathVariable Long id)
    {
        PessoaFisica pessoaFisica = pessoaFisicaService.findById(id);
        return ResponseEntity.ok(convertToDto(pessoaFisica));
    }

    @Operation(summary = "Realiza o cadastro de novas Pessoa Física")
    @ApiResponse(responseCode = "201", description = "Pessoa Física cadastrada com sucesso.")
    @ApiResponse(responseCode = "403", description = "Campo obrigatório não informado")
    @PostMapping
    public ResponseEntity<PessoaFisicaDto> save(@RequestBody PessoaFisicaDto dto)
    {
        PessoaFisica pessoaFisica = pessoaFisicaService.save(convertToEntity(dto));
        return new ResponseEntity<>(convertToDto(pessoaFisica), HttpStatus.CREATED);
    }

    @Operation(summary = "Realiza a atualização de Pessoas Física ja cadastradas")
    @PutMapping("/{id}")
    public ResponseEntity<PessoaFisicaDto> update(@PathVariable Long id, @RequestBody PessoaFisicaDto dto)
    {
        PessoaFisica updatedPessoaFisica = pessoaFisicaService.update(id, convertToEntity(dto));
        return ResponseEntity.ok(convertToDto(updatedPessoaFisica));
    }

    @Operation(summary = "Apaga por id as Pessoas Física cadastradas")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id)
    {
        pessoaFisicaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ApiResponse(responseCode = "204", description = "Nenhuma Pessoa Física na fila.")
    @Operation(summary = "Visualização de Pessoas Físicas na fila de castramento")
    @GetMapping("/next-client")
    public ResponseEntity<PessoaFisicaDto> getNextClient() {
        long clientId = clientQueueService.removeFromQueue();
        if (clientId == -1) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        PessoaFisica pessoaFisica = pessoaFisicaService.findById(clientId);
        return ResponseEntity.ok(convertToDto(pessoaFisica));
    }


    private PessoaFisicaDto convertToDto(PessoaFisica entity)
    {
        return new PessoaFisicaDto(entity.getId(), entity.getNome(),
                entity.getCpf(), entity.getEmail(), entity.getMcc());
    }

    private PessoaFisica convertToEntity(PessoaFisicaDto dto)
    {
        return new PessoaFisica(dto.getId(), dto.getNome(), dto.getCpf(), dto.getEmail(), dto.getMcc());
    }

}

