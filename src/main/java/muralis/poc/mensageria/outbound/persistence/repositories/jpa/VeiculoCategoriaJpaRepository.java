package muralis.poc.mensageria.outbound.persistence.repositories.jpa;

import muralis.poc.mensageria.outbound.persistence.entities.VeiculoCategoriaJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoCategoriaJpaRepository extends JpaRepository<VeiculoCategoriaJpaEntity, String> {
}
