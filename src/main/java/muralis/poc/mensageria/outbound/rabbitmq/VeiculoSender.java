package muralis.poc.mensageria.outbound.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VeiculoSender implements AdicionarNaFila {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void adicionarNaFila(Object object) {
        rabbitTemplate.convertAndSend("veiculo", "veiculo.log", object);
    }

}
