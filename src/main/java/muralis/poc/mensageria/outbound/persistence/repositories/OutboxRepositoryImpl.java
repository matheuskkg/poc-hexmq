package muralis.poc.mensageria.outbound.persistence.repositories;

import muralis.poc.mensageria.core.domain.model.Outbox;
import muralis.poc.mensageria.core.domain.model.OutboxStatus;
import muralis.poc.mensageria.core.domain.repositories.OutboxRepository;
import muralis.poc.mensageria.outbound.persistence.entities.OutboxJpaEntity;
import muralis.poc.mensageria.outbound.persistence.repositories.jpa.OutboxJpaRepository;
import muralis.poc.mensageria.util.mappers.OutboxMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OutboxRepositoryImpl implements OutboxRepository {

    @Autowired
    private OutboxJpaRepository jpaRepository;

    @Autowired
    private OutboxMapper mapper;

    @Override
    public void salvar(Outbox outbox) {
        OutboxJpaEntity jpaEntity = mapper.toJpaEntity(outbox);

        jpaRepository.save(jpaEntity);
    }

    @Override
    public List<Outbox> consultarPendentes() {
        List<OutboxJpaEntity> jpaResponse = jpaRepository.findAllByStatus(OutboxStatus.PENDENTE);

        return jpaResponse.stream().map(j -> mapper.toEntity(j)).toList();
    }
}
