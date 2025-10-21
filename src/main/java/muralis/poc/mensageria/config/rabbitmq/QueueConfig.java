package muralis.poc.mensageria.config.rabbitmq;

import lombok.Data;

@Data
public class QueueConfig {

    private String name;

    private String exchangeType;

    private String exchange;

    private String routingKey;

    private String dlxExchange;

    private String dlqName;

    private String dlqRoutingKey;

}
