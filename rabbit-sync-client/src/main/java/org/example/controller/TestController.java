package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final RabbitTemplate rabbitTemplate;

//    private final Queue queue;

    @GetMapping("/test")
    public Object test(){
        Message build = MessageBuilder.withBody("give".getBytes())
                .build();

        Message message = rabbitTemplate.sendAndReceive("test.exchange-topic.sync.server.v0", "#.test", build);

        String s = new String(message.getBody());
        System.out.println("s = " + s);

        return message;

    }


}
