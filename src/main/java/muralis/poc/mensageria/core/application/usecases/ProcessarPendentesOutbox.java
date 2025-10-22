package muralis.poc.mensageria.core.application.usecases;

import muralis.poc.mensageria.core.domain.model.Outbox;
import muralis.poc.mensageria.core.domain.model.OutboxStatus;
import muralis.poc.mensageria.core.domain.repositories.OutboxRepository;
import muralis.poc.mensageria.outbound.rabbitmq.AdicionarNaFila;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProcessarPendentesOutbox implements UseCase<Void, Void> {

    @Autowired
    private OutboxRepository outboxRepository;

    @Autowired
    private AdicionarNaFila adicionarNaFila;

    @Override
    public Void execute(Void entidade) {
        List<Outbox> pendentes = outboxRepository.consultarPendentes();

        pendentes.forEach(p -> {
            adicionarNaFila.adicionarNaFila(p.getPayload(), p.getExchange(), p.getRoutingKey());
            p.setStatus(OutboxStatus.PROCESSADO);
        });

        outboxRepository.salvar(pendentes);

        return null;
    }

}
