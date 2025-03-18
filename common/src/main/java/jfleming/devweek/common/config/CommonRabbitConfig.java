package jfleming.devweek.common.config;



import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class CommonRabbitConfig {

    public static final String QUEUE_NAME = "devWeek2024";
    public static final String EXCHANGE_NAME = "demoExchange";
    public static final String ROUTING_KEY = "myRoutingKey.#";

    private AmqpAdmin amqpAdmin;

    public CommonRabbitConfig(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    @PostConstruct
    public void createQueues() {
        amqpAdmin.declareQueue(new Queue(CommonRabbitConfig.QUEUE_NAME, true));
    }

    @Bean
    Binding binding(@Qualifier("myDevWeekQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(CommonRabbitConfig.ROUTING_KEY);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(CommonRabbitConfig.EXCHANGE_NAME);
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
        // This queue has the following properties:
        // name: my_durable
        // durable: true
        // exclusive: false
        // auto_delete: false
        return new Queue(CommonRabbitConfig.QUEUE_NAME, true, false, false);
    }
}
