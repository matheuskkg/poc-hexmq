package muralis.poc.mensageria.inbound.scheduler;

import muralis.poc.mensageria.core.application.usecases.ProcessarPendentesOutbox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OutboxScheduler {

    @Autowired
    private ProcessarPendentesOutbox processarPendentesOutbox;

    @Scheduled(fixedDelay = 5000)
    public void processarPendentesOutbox() {
        processarPendentesOutbox.execute(null);
    }

}
