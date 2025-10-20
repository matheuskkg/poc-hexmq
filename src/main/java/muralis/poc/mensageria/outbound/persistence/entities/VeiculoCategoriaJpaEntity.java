package muralis.poc.mensageria.outbound.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "categorias_veiculo")
@Data
public class VeiculoCategoriaJpaEntity {

    @Id
    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "rotulo")
    private String rotulo;

    @Column(name = "num_eixos")
    private Integer numeroEixos;

    @Column(name = "rodado_duplo")
    private Integer rodadoDuplo;

    @Column(name = "categoria_portico")
    private String categoriaPortico;

    @Column(name = "peso_bruto_min")
    private Double pesoBrutoMinimo;

    @Column(name = "peso_bruto_max")
    private Double pesoBrutoMaxima;

    @Column(name = "altura_max")
    private Double alturaMaxima;

    @Column(name = "comprimento_max")
    private Double comprimentoMaximo;

    @Column(name = "exemplos_veiculos")
    private String exemplosVeiculos;

    @Column(name = "ativo")
    private Boolean ativo;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
