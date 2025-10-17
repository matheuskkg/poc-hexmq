package muralis.poc.mensageria.inbound.rest.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VeiculoRequest {

    private Long id;

    private String placa;

    private String modelo;

    private String categoria;

    private LocalDate dataCriacao;

}
