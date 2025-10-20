package muralis.poc.mensageria.core.application.usecases;

import muralis.poc.mensageria.core.domain.model.Veiculo;
import org.springframework.stereotype.Component;

@Component
public class ProcessarVeiculoNaDLQ implements UseCase<Void, Veiculo> {

    @Override
    public Void execute(Veiculo entidade) {
        return null;
    }

}
