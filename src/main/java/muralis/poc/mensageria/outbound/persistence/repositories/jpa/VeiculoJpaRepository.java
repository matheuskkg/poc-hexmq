package muralis.poc.mensageria.outbound.persistence.repositories.jpa;

import muralis.poc.mensageria.outbound.persistence.entities.VeiculoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoJpaRepository extends JpaRepository<VeiculoJpaEntity, Long> {
}
