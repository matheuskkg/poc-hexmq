package muralis.poc.mensageria.veiculos;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VeiculoSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Async
    public void send(List<Veiculo> veiculos) {
        veiculos.forEach(v -> rabbitTemplate.convertAndSend("veiculo", "cadastrar.veiculo", v));
    }

}
