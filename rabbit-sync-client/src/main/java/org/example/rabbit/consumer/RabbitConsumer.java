package org.example.rabbit.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rabbit.RabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitConsumer {

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receiveDispatch(Message message){
        System.out.println("message = " + message);

    }
}
