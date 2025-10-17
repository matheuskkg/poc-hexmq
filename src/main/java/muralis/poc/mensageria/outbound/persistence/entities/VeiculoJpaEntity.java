package muralis.poc.mensageria.outbound.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
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

    @Column(nullable = false)
    private String categoria;

    @CreatedDate
    @Column(name = "criado_em")
    private LocalDate dataCriacao;

}

