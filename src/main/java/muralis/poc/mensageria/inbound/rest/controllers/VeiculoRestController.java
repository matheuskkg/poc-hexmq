package muralis.poc.mensageria.inbound.rest.controllers;

import muralis.poc.mensageria.core.application.usecases.ConsultarTodosVeiculos;
import muralis.poc.mensageria.inbound.rest.dtos.VeiculoRequest;
import muralis.poc.mensageria.core.application.usecases.EnviarVeiculoParaFila;
import muralis.poc.mensageria.core.domain.model.Veiculo;
import muralis.poc.mensageria.inbound.rest.dtos.VeiculoResponse;
import muralis.poc.mensageria.util.mappers.VeiculoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoRestController {

    @Autowired
    private VeiculoMapper mapper;

    @Autowired
    private EnviarVeiculoParaFila enviarVeiculoParaFila;

    @Autowired
    private ConsultarTodosVeiculos consultarTodosVeiculos;

    @PostMapping("/batch")
    public ResponseEntity salvarVeiculos(@RequestBody List<VeiculoRequest> request) {
        List<Veiculo> veiculos = request.stream().map(vr -> mapper.toEntity(vr)).toList();

        veiculos.forEach(v -> enviarVeiculoParaFila.execute(v));

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity consultarVeiculos() {
        List<Veiculo> veiculos = consultarTodosVeiculos.execute(null);

        List<VeiculoResponse> response = veiculos.stream().map(v -> mapper.toResponse(v)).toList();

        return ResponseEntity.ok(response);
    }

}
