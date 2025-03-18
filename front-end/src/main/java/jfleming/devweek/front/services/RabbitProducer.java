package jfleming.devweek.front.services;


import jfleming.devweek.common.config.CommonRabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RabbitProducer {
    final private RabbitTemplate rabbitTemplate;
    final private SimpleMessageConverter simpleMessageConverter;

    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.simpleMessageConverter = new SimpleMessageConverter();
    }

    public String createMessage(String message){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("text/plain");
        messageProperties.setContentEncoding("UTF-8");
        messageProperties.setCorrelationId(UUID.randomUUID().toString());
        Object object = rabbitTemplate.sendAndReceive(CommonRabbitConfig.EXCHANGE_NAME,
                "myRoutingKey.messages",
                simpleMessageConverter.toMessage(message,messageProperties));

        return simpleMessageConverter.fromMessage((Message) object).toString();

    }
}
