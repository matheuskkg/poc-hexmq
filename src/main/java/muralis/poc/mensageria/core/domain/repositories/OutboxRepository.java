package muralis.poc.mensageria.core.domain.repositories;

import muralis.poc.mensageria.core.domain.model.Outbox;

import java.util.List;

public interface OutboxRepository {

    void salvar(Outbox outbox);

    void salvar(List<Outbox> outboxes);

    List<Outbox> consultarPendentes();

}
