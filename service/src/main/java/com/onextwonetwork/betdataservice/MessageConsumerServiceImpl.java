package com.onextwonetwork.betdataservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class MessageConsumerServiceImpl implements MessageConsumerService{
    private static final Logger LOGGER = Logger.getLogger(MessageConsumerServiceImpl.class.getName());

    private final ObjectMapper mapper;
    private final BigDecimal minRetursToNotify = new BigDecimal(1500);

    public MessageConsumerServiceImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @KafkaListener(topics = "bet_detail", groupId = "bets")
    public void consumeMessage(String message) throws JsonProcessingException {

        BetDTO bet = mapper.readValue(message, BetDTO.class);
        if(bet.getReturns().compareTo(minRetursToNotify) >= 0){
            LOGGER.log(Level.INFO, String.format("Received message [%s] with returns [%s]", message, bet.getReturns()));
        }
    }
}
