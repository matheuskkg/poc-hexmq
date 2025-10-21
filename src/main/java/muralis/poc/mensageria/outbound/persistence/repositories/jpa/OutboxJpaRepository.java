package muralis.poc.mensageria.outbound.persistence.repositories.jpa;

import muralis.poc.mensageria.core.domain.model.OutboxStatus;
import muralis.poc.mensageria.outbound.persistence.entities.OutboxJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutboxJpaRepository extends JpaRepository<OutboxJpaEntity, Long> {

    List<OutboxJpaEntity> findAllByStatus(OutboxStatus status);

}
