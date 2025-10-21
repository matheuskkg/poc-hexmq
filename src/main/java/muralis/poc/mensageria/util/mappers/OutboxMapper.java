package muralis.poc.mensageria.util.mappers;

import muralis.poc.mensageria.core.domain.model.Outbox;
import muralis.poc.mensageria.outbound.persistence.entities.OutboxJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OutboxMapper {

    OutboxJpaEntity toJpaEntity(Outbox entity);

    Outbox toEntity(OutboxJpaEntity jpaEntity);

}
