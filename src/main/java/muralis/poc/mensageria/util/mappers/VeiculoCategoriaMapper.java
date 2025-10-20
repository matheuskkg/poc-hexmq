package muralis.poc.mensageria.util.mappers;

import muralis.poc.mensageria.core.domain.model.VeiculoCategoria;
import muralis.poc.mensageria.outbound.persistence.entities.VeiculoCategoriaJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VeiculoCategoriaMapper {

    VeiculoCategoria toEntity(VeiculoCategoriaJpaEntity jpaEntity);

    default Boolean integerToBoolean(Integer value) {
        if (value == null) return null;

        return value != 0;
    }

}
