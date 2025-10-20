package muralis.poc.mensageria.outbound.persistence.repositories;

import muralis.poc.mensageria.outbound.persistence.entities.VeiculoJpaEntity;
import muralis.poc.mensageria.outbound.persistence.repositories.jpa.VeiculoJpaRepository;
import muralis.poc.mensageria.core.domain.model.Veiculo;
import muralis.poc.mensageria.core.domain.repositories.VeiculoRepository;
import muralis.poc.mensageria.util.mappers.VeiculoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VeiculoRepositoryImpl implements VeiculoRepository {

    @Autowired
    private VeiculoMapper mapper;

    @Autowired
    private VeiculoJpaRepository jpaRepository;

    @Override
    public Veiculo salvar(Veiculo veiculo) {
        VeiculoJpaEntity jpaEntity = mapper.toJpaEntity(veiculo);

        return mapper.toEntity(jpaRepository.save(jpaEntity));
    }

    @Override
    public List<Veiculo> buscarTodos() {
        return jpaRepository.findAll().stream().map(v -> mapper.toEntity(v)).toList();
    }
}
