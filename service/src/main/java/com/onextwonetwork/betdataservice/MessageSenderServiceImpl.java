package com.onextwonetwork.betdataservice;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class MessageSenderServiceImpl implements MessageSenderService {
    private static final Logger LOGGER = Logger.getLogger(MessageSenderServiceImpl.class.getName());
    private final KafkaTemplate kafkaTemplate;

    public MessageSenderServiceImpl(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(String message){
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("bet_detail", message);
        future.thenAccept(result-> handleResult(message, result));
        future.exceptionally(t -> {
            LOGGER.log(Level.SEVERE, String.format("Unable to send message [%s] due to error ", message), t);
            return null;
        });
    }

    private void handleResult(String message, SendResult<String, String> result) {
        LOGGER.log(Level.FINE, String.format("Sent message [%s] with offset [%s]", message, result.getRecordMetadata().offset()));
    }
}
