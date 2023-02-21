package org.example.rabbit.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessageToMail(Object payload){
        rabbitTemplate.convertAndSend("ns.exchange-topic.mail.v0", "ns.mail.cmd.v0", payload);
    }

}
