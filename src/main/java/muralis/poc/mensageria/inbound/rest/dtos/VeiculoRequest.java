package muralis.poc.mensageria.inbound.rest.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class VeiculoRequest {

    private String placa;

    private String modelo;

    private String categoria;

    private LocalDate dataCriacao;

}
