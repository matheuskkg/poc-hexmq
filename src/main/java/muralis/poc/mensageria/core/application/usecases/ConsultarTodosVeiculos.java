package muralis.poc.mensageria.core.application.usecases;

import muralis.poc.mensageria.core.domain.model.Veiculo;
import muralis.poc.mensageria.core.domain.repositories.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsultarTodosVeiculos implements UseCase<List<Veiculo>, Void> {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Override
    public List<Veiculo> execute(Void entidade) {
        return veiculoRepository.buscarTodos();
    }

}
