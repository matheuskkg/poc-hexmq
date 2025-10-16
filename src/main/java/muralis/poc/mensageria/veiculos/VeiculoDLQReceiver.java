package muralis.poc.mensageria.veiculos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VeiculoDLQReceiver {

    private static final Logger log = LoggerFactory.getLogger(VeiculoDLQReceiver.class);

    public void receive(Veiculo mensagem) {
        log.info("Falha ao processar a mensagem: {}", mensagem);
    }

}
