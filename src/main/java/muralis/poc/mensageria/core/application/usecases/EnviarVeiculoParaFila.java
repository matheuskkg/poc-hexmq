package muralis.poc.mensageria.core.application.usecases;

import muralis.poc.mensageria.outbound.rabbitmq.AdicionarNaFila;
import muralis.poc.mensageria.core.domain.model.Veiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnviarVeiculoParaFila implements UseCase<Veiculo> {

    @Autowired
    private AdicionarNaFila outbound;

    @Override
    public void execute(Veiculo entidade) {
        outbound.adicionarNaFila(entidade);
    }
}
