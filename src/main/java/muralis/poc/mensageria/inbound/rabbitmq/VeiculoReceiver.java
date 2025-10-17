package muralis.poc.mensageria.inbound.rabbitmq;

import muralis.poc.mensageria.core.application.usecases.ProcessarVeiculoNaFila;
import muralis.poc.mensageria.core.domain.model.Veiculo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VeiculoReceiver {

    @Autowired
    private ProcessarVeiculoNaFila processarVeiculoNaFila;

    @RabbitListener(queues = "cadastrar.veiculo.queue")
    public void receive(Veiculo mensagem) {
        processarVeiculoNaFila.execute(mensagem);
    }

}
