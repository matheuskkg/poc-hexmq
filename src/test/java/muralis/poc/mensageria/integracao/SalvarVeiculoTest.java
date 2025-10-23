package muralis.poc.mensageria.integracao;

import com.fasterxml.jackson.databind.ObjectMapper;
import muralis.poc.mensageria.config.DockerConfig;
import muralis.poc.mensageria.inbound.rest.dtos.VeiculoRequest;
import muralis.poc.mensageria.outbound.persistence.repositories.jpa.VeiculoJpaRepository;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SalvarVeiculoTest extends DockerConfig {

    @Autowired
    MockMvc mvc;

    @Autowired
    VeiculoJpaRepository veiculoJpaRepository;

    @Autowired
    ObjectMapper objectMapper;

    MediaType JSON = MediaType.APPLICATION_JSON;

    Integer waitSeconds = 10;

    @AfterEach
    void afterEach() {
        veiculoJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve salvar veículo em requisições HTTP")
    void deveSalvarVeiculo_requestHttp() throws Exception {
        List<VeiculoRequest> veiculos = List.of(VeiculoRequest.builder()
                .placa("ABC1234")
                .modelo("Onix")
                .categoria("CAT01")
                .build());

        String json = objectMapper.writeValueAsString(veiculos);

        MockHttpServletRequestBuilder httpRequest = MockMvcRequestBuilders.post("/api/veiculos/batch")
                .accept(JSON)
                .contentType(JSON)
                .content(json);

        mvc.perform(httpRequest)
                .andExpect(MockMvcResultMatchers.status().isOk());

        Awaitility.await().atMost(Duration.ofSeconds(waitSeconds))
                .untilAsserted(() -> assertEquals(1, veiculoJpaRepository.count()));
    }

    @Test
    @DisplayName("Deve salvar diversos veículos em requisições HTTP")
    void deveSalvarDiversosVeiculos_requestHttp() throws Exception {
        List<VeiculoRequest> veiculos = List.of(
                VeiculoRequest.builder().placa("ABC1234").modelo("Onix").categoria("CAT01").build(),
                VeiculoRequest.builder().placa("PQR4S89").modelo("Onix").categoria("CAT01").build(),
                VeiculoRequest.builder().placa("MNO-1234").modelo("Onix").categoria("CAT01").build()
        );

        String json = objectMapper.writeValueAsString(veiculos);

        MockHttpServletRequestBuilder httpRequest = MockMvcRequestBuilders.post("/api/veiculos/batch")
                .accept(JSON)
                .contentType(JSON)
                .content(json);

        mvc.perform(httpRequest)
                .andExpect(MockMvcResultMatchers.status().isOk());

        Awaitility.await().atMost(Duration.ofSeconds(waitSeconds))
                .untilAsserted(() -> assertEquals(3, veiculoJpaRepository.count()));
    }

    @Test
    @DisplayName("Não deve salvar veículos quando a placa já estiver em uso em requisições HTTP")
    void naoDeveSalvar_quandoPlacaJaEstiverSalva_requestHttp() throws Exception {
        List<VeiculoRequest> veiculos = List.of(
                VeiculoRequest.builder().placa("ABC1234").modelo("Onix").categoria("CAT01").build(),
                VeiculoRequest.builder().placa("ABC1234").modelo("Onix").categoria("CAT01").build()
        );

        String json = objectMapper.writeValueAsString(veiculos);

        MockHttpServletRequestBuilder httpRequest = MockMvcRequestBuilders.post("/api/veiculos/batch")
                .accept(JSON)
                .contentType(JSON)
                .content(json);

        mvc.perform(httpRequest)
                .andExpect(MockMvcResultMatchers.status().isOk());

        Awaitility.await().atMost(Duration.ofSeconds(waitSeconds))
                .untilAsserted(() -> assertEquals(1, veiculoJpaRepository.count()));
    }

}
