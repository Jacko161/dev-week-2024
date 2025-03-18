package jfleming.devweek.back.services;

import jfleming.devweek.back.dao.PersonRepository;
import jfleming.devweek.common.config.CommonRabbitConfig;
import jfleming.devweek.common.model.Person;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

@Component
public class RabbitConsumer {

    private final RabbitTemplate rabbitTemplate;
    private final SimpleMessageConverter messageConverter;
    private final PersonRepository personRepository;

    public RabbitConsumer(final RabbitTemplate rabbitTemplate, final PersonRepository personRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageConverter = new SimpleMessageConverter();
        this.personRepository = personRepository;
    }

    @RabbitListener(queues = CommonRabbitConfig.QUEUE_NAME)
    public void processMessage(Message content) {
        String searchCriteria = new String(content.getBody(), StandardCharsets.UTF_8);
        System.out.println(searchCriteria);
        Optional<Person> foundPerson;
        try {
            foundPerson = personRepository.findByName(searchCriteria);
        } catch (InvalidDataAccessResourceUsageException e){
            foundPerson = Optional.empty();
        }
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        messageProperties.setContentEncoding(StandardCharsets.UTF_8.name());
        messageProperties.setCorrelationId(content.getMessageProperties().getCorrelationId());

        String responseMessage = foundPerson.map(person -> person.getName() + " " + person.getEmail()).orElse("Sorry person was not found!");

        rabbitTemplate.send(content.getMessageProperties().getReplyTo(),
                messageConverter.toMessage(responseMessage, messageProperties));
    }
}
