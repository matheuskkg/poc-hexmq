package muralis.poc.mensageria.core.domain.repositories;

import muralis.poc.mensageria.core.domain.model.Veiculo;

import java.util.List;

public interface VeiculoRepository {

    Veiculo salvar(Veiculo veiculo);

    List<Veiculo> buscarTodos();

}
