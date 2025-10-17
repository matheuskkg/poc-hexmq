package muralis.poc.mensageria.core.application.usecases;

import muralis.poc.mensageria.core.domain.model.Veiculo;
import org.springframework.stereotype.Component;

@Component
public class ProcessarVeiculoNaDLQ implements UseCase<Veiculo> {
    @Override
    public void execute(Veiculo entidade) {

    }
}
