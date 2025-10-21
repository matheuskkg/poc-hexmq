package muralis.poc.mensageria.core.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Outbox {

    private Long id;

    private Long aggregateId;

    private String aggregateType;

    private String payload;

    @Builder.Default
    private OutboxStatus status = OutboxStatus.PENDENTE;

}
