package muralis.poc.mensageria.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties({RabbitMQProperties.class})
public class RabbitMQConfig {

    @Bean
    public Declarables rabbitMQDeclarables(RabbitMQProperties props) {
        List<Declarable> declarables = new ArrayList<>();

        for (QueueConfig config : props.getQueues()) {
            Queue queue = QueueBuilder.durable(config.getName())
                    .withArgument("x-dead-letter-exchange", config.getDlxExchange())
                    .withArgument("x-dead-letter-routing-key", config.getDlqRoutingKey())
                    .build();

            DirectExchange exchange = new DirectExchange(config.getExchange());
            DirectExchange dlx = new DirectExchange(config.getDlxExchange());
            Queue dlq = new Queue(config.getDlqName(), true);

            declarables.add(queue);
            declarables.add(exchange);
            declarables.add(dlx);
            declarables.add(dlq);
            declarables.add(BindingBuilder.bind(queue).to(exchange).with(config.getRoutingKey()));
            declarables.add(BindingBuilder.bind(dlq).to(dlx).with(config.getDlqRoutingKey()));
        }

        return new Declarables(declarables);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

}
