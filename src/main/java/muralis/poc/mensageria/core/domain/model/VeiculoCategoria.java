package muralis.poc.mensageria.core.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VeiculoCategoria {

    private String codigo;

    private String nome;

    private String rotulo;

    private Integer numeroEixos;

    private Boolean rodadoDuplo;

    private String categoriaPortico;

    private Boolean ativo;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

}
