package muralis.poc.mensageria.veiculos;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class VeiculoReceiver {

    private static final Logger log = LoggerFactory.getLogger(VeiculoReceiver.class);

    @Autowired
    private VeiculoRepository veiculoRepository;

    @RabbitListener(queues = "cadastrar.veiculo.queue", ackMode = "MANUAL")
    public void receive(Veiculo mensagem, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            veiculoRepository.save(mensagem);
            channel.basicAck(tag, false);
            log.info("Mensagem processada: {}", mensagem);
        } catch (Exception e) {
            log.error("Falha ao processar mensagem: {}", mensagem);

            try {
                channel.basicNack(tag, false, false);
            } catch (Exception ex) {
                log.error("Falha ao enviar para a DLQ");
            }
        }
    }

}
