package muralis.poc.mensageria.usecases;

import muralis.poc.mensageria.config.DockerConfig;
import muralis.poc.mensageria.config.TestConfig;
import muralis.poc.mensageria.core.application.usecases.ProcessarVeiculos;
import muralis.poc.mensageria.core.domain.model.Veiculo;
import muralis.poc.mensageria.core.domain.model.VeiculoCategoria;
import muralis.poc.mensageria.outbound.persistence.repositories.jpa.OutboxJpaRepository;
import muralis.poc.mensageria.outbound.persistence.repositories.jpa.VeiculoJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Import({DockerConfig.class, TestConfig.class})
class ProcessarVeiculosTest extends DockerConfig {

    @Autowired
    ProcessarVeiculos useCase;

    @Autowired
    VeiculoJpaRepository veiculoJpaRepository;

    @Autowired
    OutboxJpaRepository outboxJpaRepository;

    @AfterEach
    void afterEach() {
        veiculoJpaRepository.deleteAll();
        outboxJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve salvar veículos válidos")
    void deveSalvarVeiculosValidos() {
        List<Veiculo> veiculos = List.of(
                Veiculo.builder().placa("DEF2G45").modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build(),
                Veiculo.builder().placa("dgs-0124").modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build(),
                Veiculo.builder().placa("dgs0125").modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build(),
                Veiculo.builder().placa("DGS-0125").modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build(),
                Veiculo.builder().placa("hhh5431").modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build(),
                Veiculo.builder().placa("hha1231").modelo(" ").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build(),
                Veiculo.builder().placa("ldf6b24").modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build(),
                Veiculo.builder().placa("laf6b24").modelo("Onix").build(),
                Veiculo.builder().placa("asd-1234").modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build()
        );

        useCase.execute(veiculos);

        verificarSalvou(6);
    }

    @Test
    @DisplayName("Não deve salvar veículos com placas inválidas")
    void naoDeveSalvarVeiculosPlacasInvalidas() {
        List<Veiculo> veiculos = List.of(
                Veiculo.builder().placa("DEF2G4").modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build(),
                Veiculo.builder().placa("ASDFGHJA").modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build(),
                Veiculo.builder().placa("1AB4R76").modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build(),
                Veiculo.builder().placa("asd-j234").modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build(),
                Veiculo.builder().placa("643aopc").modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build(),
                Veiculo.builder().placa("pas-654h").modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build(),
                Veiculo.builder().placa("").modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build(),
                Veiculo.builder().placa(" ").modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build(),
                Veiculo.builder().placa(null).modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build()
        );

        useCase.execute(veiculos);

        verificarNaoSalvou();
    }

    @Test
    @DisplayName("Não deve salvar veículos quando a placa já estiver salva")
    void naoDeveSalvarVeiculo_quandoPlacaEstiverSalva() {
        List<Veiculo> veiculos = List.of(
                Veiculo.builder().placa("ABC-1234").modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build(),
                Veiculo.builder().placa("abc-1234").modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build(),
                Veiculo.builder().placa("Abc1234").modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build()
        );

        useCase.execute(veiculos);

        verificarSalvou(1);
    }

    @Test
    @DisplayName("Não deve salvar veículos com modelo inválido")
    void naoDeveSalvarVeiculosSemModelo() {
        List<Veiculo> veiculos = List.of(
                Veiculo.builder().placa("").modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build(),
                Veiculo.builder().placa("    ").modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build(),
                Veiculo.builder().placa(null).modelo("Onix").categoria(VeiculoCategoria.builder().codigo("CAT01").build()).build()
        );

        useCase.execute(veiculos);

        verificarNaoSalvou();
    }

    @Test
    @DisplayName("Não deve salvar veículo sem categoria")
    void naoDeveSalvarVeiculoSemCategoria() {
        List<Veiculo> veiculos = List.of(
                Veiculo.builder().placa("ASD2G56").modelo("Onix").build()
        );

        useCase.execute(veiculos);

        verificarNaoSalvou();
    }

    @Test
    @DisplayName("Não deve salvar veículo sem o código da categoria")
    void naoDeveSalvarVeiculoSemCodigoDaCategoria() {
        List<Veiculo> veiculos = List.of(
                Veiculo.builder().placa("ASD2G56").modelo("Onix").categoria(VeiculoCategoria.builder().build()).build()
        );

        useCase.execute(veiculos);

        verificarNaoSalvou();
    }

    void verificarNaoSalvou() {
        assertEquals(0, veiculoJpaRepository.count());
        assertEquals(0, outboxJpaRepository.count());
    }

    void verificarSalvou(int expected) {
        assertEquals(expected, veiculoJpaRepository.count());
        assertEquals(expected, outboxJpaRepository.count());
    }


}
