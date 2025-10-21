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

            Queue dlq = new Queue(config.getDlqName(), true);

            switch (config.getExchangeType()) {
                case "direct":
                    DirectExchange directExchange = new DirectExchange(config.getExchange());
                    declarables.add(BindingBuilder.bind(queue).to(directExchange).with(config.getRoutingKey()));
                    declarables.add(directExchange);

                    DirectExchange directDlxExchange = new DirectExchange(config.getDlxExchange());
                    declarables.add(BindingBuilder.bind(dlq).to(directDlxExchange).with(config.getDlqRoutingKey()));
                    declarables.add(directDlxExchange);

                    break;
                case "topic":
                    TopicExchange topicExchange = new TopicExchange(config.getExchange());
                    declarables.add(BindingBuilder.bind(queue).to(topicExchange).with(config.getRoutingKey()));
                    declarables.add(topicExchange);

                    TopicExchange topicDlxExchange = new TopicExchange(config.getDlxExchange());
                    declarables.add(BindingBuilder.bind(dlq).to(topicDlxExchange).with(config.getDlqRoutingKey()));
                    declarables.add(topicDlxExchange);

                    break;
            }

            declarables.add(queue);
            declarables.add(dlq);
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
