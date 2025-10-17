package muralis.poc.mensageria.inbound.rest.controllers;

import muralis.poc.mensageria.inbound.rest.dtos.VeiculoRequest;
import muralis.poc.mensageria.core.application.usecases.EnviarVeiculoParaFila;
import muralis.poc.mensageria.core.domain.model.Veiculo;
import muralis.poc.mensageria.util.mappers.VeiculoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoRestController {

    @Autowired
    private VeiculoMapper mapper;

    @Autowired
    private EnviarVeiculoParaFila usecase;

    @PostMapping("/batch")
    public ResponseEntity salvarVeiculos(@RequestBody List<VeiculoRequest> request) {
        List<Veiculo> veiculos = request.stream().map(vr -> mapper.toEntity(vr)).toList();

        veiculos.forEach(v -> usecase.execute(v));

        return ResponseEntity.ok().build();
    }

}
