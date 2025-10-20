package muralis.poc.mensageria.inbound.rest.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VeiculoResponse {

    private Long id;

    private String placa;

    private String modelo;

    private String categoria;

}
