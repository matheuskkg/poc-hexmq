package muralis.poc.mensageria.inbound.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import muralis.poc.mensageria.core.domain.model.Veiculo;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VeiculoConsumer {

    @RabbitListener(queues = "veiculo.log.queue")
    public void logSucesso(Veiculo veiculo) {
        try {
            log.info("Veículo {} processado", veiculo);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException("");
        }
    }

    @RabbitListener(queues = "veiculo.test.queue")
    public void test(Veiculo veiculo) {
        log.info("Fila de teste {}", veiculo);
    }

}
