package muralis.poc.mensageria.inbound.rabbitmq;

import muralis.poc.mensageria.core.domain.model.Veiculo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class VeiculoDLQReceiver {

    private static final Logger log = LoggerFactory.getLogger(VeiculoDLQReceiver.class);

    @RabbitListener(queues = "cadastrar.veiculo.dlq")
    public void receive(Veiculo mensagem) {
        log.info("Falha ao processar a mensagem: {}", mensagem);
    }

}
