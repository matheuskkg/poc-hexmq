package muralis.poc.mensageria.config.rabbitmq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "rabbitmq")
@Data
public class RabbitMQProperties {

    private List<QueueConfig> queues;

}
