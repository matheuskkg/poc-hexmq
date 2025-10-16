package muralis.poc.mensageria.veiculos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VeiculoReceiver {

    private static final Logger log = LoggerFactory.getLogger(VeiculoReceiver.class);

    @Autowired
    private VeiculoRepository veiculoRepository;

    public void receive(Veiculo mensagem) {
        log.info("Mensagem recebida: {}", mensagem);

        veiculoRepository.save(mensagem);
    }

}
