package muralis.poc.mensageria.util.mappers;

import muralis.poc.mensageria.inbound.rest.dtos.VeiculoRequest;
import muralis.poc.mensageria.inbound.rest.dtos.VeiculoResponse;
import muralis.poc.mensageria.outbound.persistence.entities.VeiculoJpaEntity;
import muralis.poc.mensageria.core.domain.model.Veiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = VeiculoCategoriaMapper.class)
public interface VeiculoMapper {

    VeiculoJpaEntity toJpaEntity(Veiculo veiculo);

    Veiculo toEntity(VeiculoJpaEntity veiculoJpaEntity);

    Veiculo toEntity(VeiculoRequest veiculoRequest);

    VeiculoResponse toResponse(Veiculo veiculo);

    default Integer booleanToInteger(Boolean value) {
        if (value == null) return null;

        return value ? 1 : 0;
    }

}
