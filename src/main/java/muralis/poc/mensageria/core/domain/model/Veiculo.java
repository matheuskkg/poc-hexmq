package muralis.poc.mensageria.core.domain.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Veiculo {

    private Long id;

    private String placa;

    private String modelo;

    private String categoria;

    private LocalDate dataCriacao;

}
