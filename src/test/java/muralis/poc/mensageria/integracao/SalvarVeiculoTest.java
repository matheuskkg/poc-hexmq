package muralis.poc.mensageria.integracao;

import com.fasterxml.jackson.databind.ObjectMapper;
import muralis.poc.mensageria.inbound.rest.dtos.VeiculoRequest;
import muralis.poc.mensageria.outbound.persistence.repositories.jpa.VeiculoJpaRepository;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SalvarVeiculoTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    VeiculoJpaRepository veiculoJpaRepository;

    @Autowired
    ObjectMapper objectMapper;

    MediaType JSON = MediaType.APPLICATION_JSON;

    @Container
    static RabbitMQContainer rabbitMQ = new RabbitMQContainer(DockerImageName.parse("rabbitmq:management"))
            .withExposedPorts(5672, 15672);

    @DynamicPropertySource
    static void rabbitProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbitMQ::getHost);
        registry.add("spring.rabbitmq.port", rabbitMQ::getAmqpPort);
    }

    @AfterEach
    void afterEach() {
        veiculoJpaRepository.deleteAll();
    }

    @Test
    void deveSalvarVeiculo_requestHttp() throws Exception {
        List<VeiculoRequest> veiculos = List.of(VeiculoRequest.builder()
                .placa("ABC1234")
                .modelo("Onix")
                .categoria("Automóvel, Caminhonete e Furgão")
                .build());

        String json = objectMapper.writeValueAsString(veiculos);

        MockHttpServletRequestBuilder httpRequest = MockMvcRequestBuilders.post("/api/veiculos/batch")
                .accept(JSON)
                .contentType(JSON)
                .content(json);

        mvc.perform(httpRequest)
                .andExpect(MockMvcResultMatchers.status().isOk());

        Awaitility.await().atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> assertEquals(1, veiculoJpaRepository.count()));
    }

    @Test
    void deveSalvarDiversosVeiculos_requestHttp() throws Exception {
        List<VeiculoRequest> veiculos = List.of(
                VeiculoRequest.builder().placa("ABC1234").modelo("Onix").categoria("Automóvel, Caminhonete e Furgão").build(),
                VeiculoRequest.builder().placa("PQR4S89").modelo("Onix").categoria("Automóvel, Caminhonete e Furgão").build(),
                VeiculoRequest.builder().placa("MNO-1234").modelo("Onix").categoria("Automóvel, Caminhonete e Furgão").build()
        );

        String json = objectMapper.writeValueAsString(veiculos);

        MockHttpServletRequestBuilder httpRequest = MockMvcRequestBuilders.post("/api/veiculos/batch")
                .accept(JSON)
                .contentType(JSON)
                .content(json);

        mvc.perform(httpRequest)
                .andExpect(MockMvcResultMatchers.status().isOk());

        Awaitility.await().atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> assertEquals(3, veiculoJpaRepository.count()));
    }

    @Test
    void naoDeveSalvar_quandoPlacaJaEstiverSalva_requestHttp() throws Exception {
        List<VeiculoRequest> veiculos = List.of(
                VeiculoRequest.builder().placa("ABC1234").modelo("Onix").categoria("Automóvel, Caminhonete e Furgão").build(),
                VeiculoRequest.builder().placa("ABC1234").modelo("Onix").categoria("Automóvel, Caminhonete e Furgão").build()
        );

        String json = objectMapper.writeValueAsString(veiculos);

        MockHttpServletRequestBuilder httpRequest = MockMvcRequestBuilders.post("/api/veiculos/batch")
                .accept(JSON)
                .contentType(JSON)
                .content(json);

        mvc.perform(httpRequest)
                .andExpect(MockMvcResultMatchers.status().isOk());

        Awaitility.await().atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> assertEquals(1, veiculoJpaRepository.count()));
    }

}
