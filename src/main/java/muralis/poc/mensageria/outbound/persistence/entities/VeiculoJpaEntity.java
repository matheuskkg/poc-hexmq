package muralis.poc.mensageria.outbound.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "veiculos", uniqueConstraints = {
        @UniqueConstraint(columnNames = "placa")
})
@Data
public class VeiculoJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String placa;

    @Column(nullable = false)
    private String modelo;

    @ManyToOne
    @JoinColumn(nullable = false)
    private VeiculoCategoriaJpaEntity categoria;

    @CreatedDate
    @Column(name = "criado_em")
    private LocalDateTime dataCriacao;

}

