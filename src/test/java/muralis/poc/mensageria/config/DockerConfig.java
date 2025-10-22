package muralis.poc.mensageria.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class DockerConfig {

    private static final PostgreSQLContainer<?> postgres;
    private static final RabbitMQContainer rabbitMQ;

    static {
        postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
                .withDatabaseName("poc-teste");

        rabbitMQ = new RabbitMQContainer(DockerImageName.parse("rabbitmq:management"));

        postgres.start();
        rabbitMQ.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            postgres.stop();
            rabbitMQ.stop();
        }));
    }

    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        registry.add("spring.rabbitmq.host", rabbitMQ::getHost);
        registry.add("spring.rabbitmq.port", rabbitMQ::getAmqpPort);
    }

}
