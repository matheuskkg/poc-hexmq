package muralis.poc.mensageria.inbound.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VeiculoReceiver {

    @RabbitListener(queues = "veiculo.log.queue")
    public void logSucesso(String mensagem) {
        log.info("Mensagem {} processada", mensagem);
    }

}
