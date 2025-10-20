package muralis.poc.mensageria.core.application.usecases;

import lombok.extern.slf4j.Slf4j;
import muralis.poc.mensageria.core.domain.model.Veiculo;
import muralis.poc.mensageria.core.domain.repositories.VeiculoRepository;
import muralis.poc.mensageria.util.mappers.VeiculoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class ProcessarVeiculoNaFila implements UseCase<Void, Veiculo> {

    @Autowired
    private VeiculoMapper mapper;

    @Autowired
    private VeiculoRepository repository;

    private final Pattern regexPlaca = Pattern.compile("^(?:[A-Za-z]{3}-?\\d{4}|[A-Za-z]{3}\\d[A-Za-z]\\d{2})$");

    @Override
    public Void execute(Veiculo entidade) {
        String placa = entidade.getPlaca();
        if (!validarCamposObrigatorios(entidade) || !validarPlaca(placa)) return null;

        try {
            repository.salvar(entidade);

            log.info("Veículo processado: {}", placa);
        } catch (DataAccessException e) {
            Throwable root = e.getRootCause();

            if (root instanceof SQLException sqlException) {
                String sqlState = sqlException.getSQLState();

                if ("23505".equals(sqlState)) {
                    log.warn("Veículo {} já está salvo", placa);
                } else {
                    log.error("Erro ao persistir veículo: {}, SQLState: {}", placa, sqlState);
                    throw e;
                }
            } else {
                log.error("Erro ao persistir veículo: {}", placa);
                throw e;
            }
        }

        return null;
    }

    private boolean validarCamposObrigatorios(Veiculo veiculo) {
        boolean isValido = true;

        if (veiculo.getPlaca() == null || veiculo.getPlaca().isBlank()) {
            log.info("Placa null ou vazia");
            isValido = false;
        }

        if (veiculo.getModelo() == null || veiculo.getModelo().isBlank()) {
            log.info("Modelo null ou vazio");
            isValido = false;
        }

        if (veiculo.getCategoria() == null || veiculo.getCategoria().getCodigo() == null || veiculo.getCategoria().getCodigo().isBlank()) {
            log.info("Categoria null ou vazia");
            isValido = false;
        }

        return isValido;
    }

    private boolean validarPlaca(String placa) {
        Matcher matcher = regexPlaca.matcher(placa);
        if (!matcher.matches()) {
            log.info("Placa de formato inválido: {}", placa);

            return false;
        }

        return true;
    }
}
