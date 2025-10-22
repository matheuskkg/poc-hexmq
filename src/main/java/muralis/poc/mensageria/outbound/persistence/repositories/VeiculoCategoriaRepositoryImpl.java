package muralis.poc.mensageria.outbound.persistence.repositories;

import muralis.poc.mensageria.core.domain.repositories.VeiculoCategoriaRepository;
import muralis.poc.mensageria.outbound.persistence.repositories.jpa.VeiculoCategoriaJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VeiculoCategoriaRepositoryImpl implements VeiculoCategoriaRepository {

    @Autowired
    private VeiculoCategoriaJpaRepository jpaRepository;

    @Override
    public boolean existePorId(String id) {
        return jpaRepository.existsById(id);
    }
}
