package muralis.poc.mensageria.core.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Veiculo {

    private Long id;

    private String placa;

    private String modelo;

    private VeiculoCategoria categoria;

    private LocalDateTime dataCriacao;

}
