package muralis.poc.mensageria.core.application.usecases;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import muralis.poc.mensageria.core.domain.model.Outbox;
import muralis.poc.mensageria.core.domain.model.Veiculo;
import muralis.poc.mensageria.core.domain.repositories.OutboxRepository;
import muralis.poc.mensageria.core.domain.repositories.VeiculoCategoriaRepository;
import muralis.poc.mensageria.core.domain.repositories.VeiculoRepository;
import muralis.poc.mensageria.util.mappers.VeiculoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class ProcessarVeiculos implements UseCase<Future<Void>, List<Veiculo>> {

    @Autowired
    private VeiculoMapper mapper;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private OutboxRepository outboxRepository;

    @Autowired
    private VeiculoCategoriaRepository categoriaRepository;

    private final Pattern regexPlaca = Pattern.compile("^(?:[A-Za-z]{3}-?\\d{4}|[A-Za-z]{3}\\d[A-Za-z]\\d{2})$");

    @Async
    @Override
    public Future<Void> execute(List<Veiculo> entidade) {
        entidade.forEach(this::processar);

        return null;
    }

    @SneakyThrows
    @Transactional
    private void processar(Veiculo veiculo) {
        String placa = veiculo.getPlaca();
        if (!validarCamposObrigatorios(veiculo)) return;

        placa = placa.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
        veiculo.setPlaca(placa);

        try {
            Veiculo veiculoSalvo = veiculoRepository.salvar(veiculo);
            outboxRepository.salvar(Outbox.builder()
                    .aggregateId(veiculoSalvo.getId())
                    .aggregateType(veiculoSalvo.getClass().getName())
                    .payload(veiculoSalvo)
                    .exchange("veiculo")
                    .routingKey("veiculo.log")
                    .build());
        } catch (DataAccessException e) {
            Throwable root = e.getRootCause();

            if (root instanceof SQLException sqlException) {
                String sqlState = sqlException.getSQLState();

                if ("23505".equals(sqlState)) {
                    log.warn("Veículo com a placa {} já está salvo", placa);
                } else {
                    log.error("Erro ao persistir veículo: {}, SQLState: {}", veiculo, sqlState);
                    throw e;
                }
            } else {
                log.error("Erro ao persistir veículo: {}", veiculo);
                throw e;
            }
        }
    }

    private boolean validarCamposObrigatorios(Veiculo veiculo) {
        boolean isValido = true;

        log.info("Validando o veículo {}", veiculo);

        if (veiculo.getPlaca() == null || veiculo.getPlaca().isBlank() || !validarPlaca(veiculo.getPlaca())) {
            log.info("Placa inválida: {}", veiculo.getPlaca());
            isValido = false;
        }

        if (veiculo.getModelo() == null || veiculo.getModelo().isBlank()) {
            log.info("Modelo inválido: {}", veiculo.getModelo());
            isValido = false;
        }

        if (veiculo.getCategoria() == null
                || veiculo.getCategoria().getCodigo() == null
                || !categoriaRepository.existePorId(veiculo.getCategoria().getCodigo())) {
            log.info("Categoria inválida: {}", veiculo.getCategoria());
            isValido = false;
        }

        return isValido;
    }

    private boolean validarPlaca(String placa) {
        Matcher matcher = regexPlaca.matcher(placa);

        return matcher.matches();
    }
}
