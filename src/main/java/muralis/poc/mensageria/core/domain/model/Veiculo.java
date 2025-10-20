package muralis.poc.mensageria.core.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Veiculo {

    private Long id;

    private String placa;

    private String modelo;

    private VeiculoCategoria categoria;

    private LocalDateTime dataCriacao;

}
