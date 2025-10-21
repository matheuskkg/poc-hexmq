package muralis.poc.mensageria.outbound.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import muralis.poc.mensageria.core.domain.model.OutboxStatus;

@Entity
@Table(name = "outbox")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutboxJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long aggregateId;

    private String aggregateType;

    @Column(length = 1000)
    private String payload;

    @Enumerated(EnumType.STRING)
    private OutboxStatus status;

}
