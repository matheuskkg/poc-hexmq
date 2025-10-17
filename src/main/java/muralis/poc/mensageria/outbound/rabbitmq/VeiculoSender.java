package muralis.poc.mensageria.outbound.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class VeiculoSender implements AdicionarNaFila {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Async
    @Override
    public void adicionarNaFila(Object object) {
        rabbitTemplate.convertAndSend("veiculo", "cadastrar.veiculo", object);
    }
}
