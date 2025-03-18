package jfleming.devweek.front.controllers;

import jfleming.devweek.front.services.RabbitProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final RabbitProducer rabbitProducer;
    public TestController(RabbitProducer rabbitProducer) {
        this.rabbitProducer = rabbitProducer;
    }

    @GetMapping("/test")
    String all() {
        return rabbitProducer.createMessage("Hello, World!");
    }


}
