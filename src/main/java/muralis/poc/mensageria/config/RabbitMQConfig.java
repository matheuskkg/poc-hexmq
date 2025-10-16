package muralis.poc.mensageria.config;

import muralis.poc.mensageria.veiculos.VeiculoDLQReceiver;
import muralis.poc.mensageria.veiculos.VeiculoReceiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private final String exchangeName = "veiculo";
    private final String queueName = "cadastrar.veiculo.queue";
    private final String routingKey = "cadastrar.veiculo";

    private final String dlqExchangeName = "veiculo.dlx";
    private final String dlqQueueName = "cadastrar.veiculo.dlq";
    private final String dlqRoutingKey = "cadastrar.veiculo.dlq";

    @Bean
    Queue queue() {
        return QueueBuilder.durable(queueName)
                .withArgument("x-dead-letter-exchange", dlqExchangeName)
                .withArgument("x-dead-letter-routing-key", dlqRoutingKey)
                .build();
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(VeiculoReceiver receiver) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiver, "receive");
        messageListenerAdapter.setMessageConverter(messageConverter());
        return messageListenerAdapter;
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        container.setDefaultRequeueRejected(false);
        return container;
    }

    @Bean
    Queue deadLetterQueue() {
        return new Queue(dlqQueueName, true);
    }

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange(dlqExchangeName);
    }

    @Bean
    Binding deadLetterBinding(Queue deadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(dlqRoutingKey);
    }

    @Bean
    MessageListenerAdapter dlqListenerAdapter(VeiculoDLQReceiver dlqReceiver) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(dlqReceiver, "receive");
        messageListenerAdapter.setMessageConverter(messageConverter());
        return messageListenerAdapter;
    }

    @Bean
    SimpleMessageListenerContainer dlqContainer(ConnectionFactory connectionFactory,
                                                MessageListenerAdapter dlqListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(dlqQueueName);
        container.setMessageListener(dlqListenerAdapter);
        return container;
    }

}
