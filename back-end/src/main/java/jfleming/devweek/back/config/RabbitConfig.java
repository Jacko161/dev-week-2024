package jfleming.devweek.back.config;

import jfleming.devweek.common.config.CommonRabbitConfig;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    private final AmqpAdmin amqpAdmin;

    public RabbitConfig(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    @Bean
    Binding binding(@Qualifier("myDevWeekQueue") Queue queue, TopicExchange exchange) {
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(CommonRabbitConfig.ROUTING_KEY);
        amqpAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    TopicExchange exchange() {
        TopicExchange exchange = new TopicExchange(CommonRabbitConfig.EXCHANGE_NAME);
        amqpAdmin.declareExchange(exchange);
        return exchange;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory myRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(new CachingConnectionFactory());
        factory.setReceiveTimeout(60000L);
        factory.setMaxConcurrentConsumers(5);
        return factory;
    }

    @Bean
    public Queue myDevWeekQueue() {
        Queue queue = new Queue(CommonRabbitConfig.QUEUE_NAME, true, false, false);
        amqpAdmin.declareQueue(queue);
        return queue;
    }
}
